package com.dev.reCode

import java.util.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking


class Vba2JsConverter {
    fun vbaToJs(input: String): String = runBlocking {
        val pattern = "(?i)sub.*?end sub".toRegex(RegexOption.DOT_MATCHES_ALL)
        val functions = pattern.findAll(input).map { it.value }.toList()
        val deferredResults = functions.map { item ->
            async { thProcess(item) }
        }
        val results = deferredResults.awaitAll()
        return@runBlocking results.joinToString(separator = "\n")
    }


    private var strs = mutableListOf<String>()
    private fun thProcess(vbs: String): String {
        var s = vbs
        var vars = ""
        var fx = ""
        var fxHead = ""

        // prep function block
        s = s.replace("Sub", "(function(){")
        s = hideStrings(s)

        s = s.replace("&".toRegex(), "+")
        s = s.replace("_\n".toRegex(), "")
        s = s.replace(":".toRegex(), "\n")
        s = s.replace("\\bthen\\b[ \\t](.+)".toRegex(), "then\n$1\nEnd If")

        // split block into separate lines
        var a = s.split('\n').toMutableList()

        // trim spaces and remove empty lines
        for (i in 0..<a.size) {
            a[i] = a[i].trim()
        }
        a = a.filter { it.isNotBlank() }.toMutableList()

        // Fix FUNCTION tags

        a[0] = a[0].replace(fx.toRegex(), "").replace("[()]".toRegex(), "")
        a[0] = a[0].replace("\\bbyval\\b".toRegex(RegexOption.IGNORE_CASE), "")
            .replace("\\bbyref\\b".toRegex(RegexOption.IGNORE_CASE), "")
            .replace("\\boptional\\b".toRegex(RegexOption.IGNORE_CASE), "")
        a[0] = a[0].replace("\\bas\\s+\\w+\\b".toRegex(RegexOption.IGNORE_CASE), "")
        a[0] = a[0].replace("\\s+".toRegex(), "")
        a[0] = a[0].replace(",".toRegex(), ", ")
        fxHead = "(function(){"
        a[0] = ""
        // Remove END FUNCTION tags
        a = a.dropLast(1).toTypedArray().toMutableList()

        // Fix Syntax
        for (i in 1..<a.size) {
            // Vars
            if (Regex("^dim\\s+", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = a[i].replace("dim\\s*".toRegex(RegexOption.IGNORE_CASE), "")
                vars += "${a[i]},"
                a[i] = ""
            } else if (Regex("^msgbox\\(").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^msgbox\\(".toRegex(RegexOption.IGNORE_CASE), "alert(")
            } else if (Regex("(^msgbox\\s)+(.*)").containsMatchIn(a[i])) {
                a[i] = a[i].replace("(^msgbox\\s)+(.*)".toRegex(RegexOption.IGNORE_CASE), "alert($2)")
            } else if (Regex("^inputbox\\(").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^inputbox\\(".toRegex(RegexOption.IGNORE_CASE), "prompt(")
            } else if (Regex("(^inputbox\\s)+(.*)").containsMatchIn(a[i])) {
                a[i] = a[i].replace("(^inputbox\\s)+(.*)".toRegex(RegexOption.IGNORE_CASE), "prompt($2)")
            } else if (Regex("^sub\\s").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^sub\\s".toRegex(RegexOption.IGNORE_CASE), "")
            } else if (Regex("\\bEnd Sub\\b", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = a[i].replace("\\bEnd Sub\\b".toRegex(RegexOption.IGNORE_CASE), "})();")
            } else if (Regex("\\bcall\\s").containsMatchIn(a[i])) {
                a[i] = a[i].replace("\\bcall\\s".toRegex(RegexOption.IGNORE_CASE), "")
            } else if (Regex("\\sSET\\s+").containsMatchIn(a[i])) {
                a[i] = a[i].replace("\\sSET\\s+".toRegex(RegexOption.IGNORE_CASE), "")
            } else if (Regex(
                    "^Range(.*)\\.Interior\\.Color.*=.*RGB(.*)\\s",
                    RegexOption.IGNORE_CASE
                ).containsMatchIn(a[i])
            ) {
                a[i] =
                    "Api.GetActiveSheet().GetRange${Regex("Range(.*).Interior").find(a[i])!!.groupValues[1]}.SetFillColor(Api.CreateColorFromRGB${
                        Regex("RGB(.*)").find(a[i])!!.groupValues[1]
                    });"

            } else if (Regex(
                    "\\bcells\\([0-9]+\\s*,\\s*[0-9]+\\s*\\)\\s*=",
                    RegexOption.IGNORE_CASE
                ).containsMatchIn(a[i])
            ) {
//             Cells(3, 4)="Hello world" --->>> Api.GetActiveSheet().GetRange("C4").SetValue("Hello world");
                val (cInt, r) = Regex(
                    "\\bcells\\(([0-9]+)\\s*,\\s*([0-9]+)\\s*\\)",
                    RegexOption.IGNORE_CASE
                ).findAll(a[i])
                    .map { Pair(it.groupValues[1].toInt(), it.groupValues[2].toInt()) }.toList()[0]
                a[i] = "Api.GetActiveSheet().GetRange(${addHideString("\"${convertToAlphabet(cInt)}$r\"")}).SetValue(\"Hello world\")"
            } else if (Regex("(?<![a-zA-Z])ActiveSheet.*", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = a[i].replace("ActiveSheet".toRegex(RegexOption.IGNORE_CASE), "Api.GetActiveSheet()")
            } else if (Regex("'nothing'").containsMatchIn(a[i])) {
                a[i] = a[i].replace("'nothing'".toRegex(RegexOption.IGNORE_CASE), "null")
            } else if (Regex("^\\bFOR\\b\\s+", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = a[i].replace("^\\bFOR\\b\\s+".toRegex(RegexOption.IGNORE_CASE), "")
                val counter = Regex("^\\w+").find(a[i])!!.value
                val from = Regex("=\\s*[\\w\\(\\)]+").find(a[i])!!.value.replace("=", "").replace("\\s+".toRegex(), "")
                a[i] = a[i].replace(counter.toRegex(), "").replace(from.toRegex(), "")
                    .replace("\\bTO\\b".toRegex(RegexOption.IGNORE_CASE), "")
                val to = Regex("\\s*[\\w()]+\\s*").find(a[i])!!.value
                    .replace("=", "").replace("\\s+".toRegex(), "")
                a[i] = "for ($counter=$from; $counter<=$to; $counter++){"
            } else if (Regex("^NEXT\\b", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = "}"
            } else if (Regex("\\bEXIT\\b\\s*\\bFOR\\b").containsMatchIn(a[i])) {
                a[i] = "break"
            } else if (Regex("^\\bIF\\b\\s+").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^\\bIF\\b\\s+".toRegex(RegexOption.IGNORE_CASE), "")
                a[i] = a[i].replace("\\bTHEN$\\b".toRegex(RegexOption.IGNORE_CASE), "")
                a[i] = a[i].replace("=".toRegex(), "==").replace("<>".toRegex(), "!=")
                a[i] = a[i].replace("\\bOR\\b".toRegex(RegexOption.IGNORE_CASE), "||")
                    .replace("\\bAND\\b".toRegex(RegexOption.IGNORE_CASE), "&&")
                a[i] = "if ($a[i]){"
            } else if (Regex("^ELSE\\b").containsMatchIn(a[i])) {
                a[i] = "}else{"
                a[i] = a[i].replace("=".toRegex(), "==").replace("<>".toRegex(), "!=")
                a[i] = a[i].replace("\\bOR\\b".toRegex(RegexOption.IGNORE_CASE), "||")
                    .replace("\\bAND\\b".toRegex(RegexOption.IGNORE_CASE), "&&")
            } else if (Regex("^ELSEIF").containsMatchIn(a[i])) {
                a[i] = "}else if{"
                a[i] = a[i].replace("=".toRegex(), "==").replace("<>".toRegex(), "!=")
                a[i] = a[i].replace("\\bOR\\b".toRegex(RegexOption.IGNORE_CASE), "||")
                    .replace("\\bAND\\b".toRegex(RegexOption.IGNORE_CASE), "&&")
            } else if (Regex("^END\\s*IF").containsMatchIn(a[i])) {
                a[i] = "}"
            } else if (Regex("^WHILE\\s").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^WHILE(.+)".toRegex(RegexOption.IGNORE_CASE), "while($1){")
            } else if (Regex("^WEND").containsMatchIn(a[i])) {
                a[i] = "}"
            } else if (Regex("^DO\\s+WHILE\\s").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^DO\\s+WHILE(.+)".toRegex(RegexOption.IGNORE_CASE), "while($1){")
            } else if (Regex("^DO\\s+UNTIL\\s").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^DO\\s+WHILE(.+)".toRegex(RegexOption.IGNORE_CASE), "while($1){")
            } else if (Regex("^LOOP$").containsMatchIn(a[i])) {
                a[i] = "}"
            } else if (Regex("\\bEXIT\\b\\s*\\bFUNCTION\\b", (RegexOption.IGNORE_CASE)).containsMatchIn(a[i])) {
                a[i] = "return"
            } else if (Regex("\\sSTEP\\s").containsMatchIn(a[i])) {
                a[i] = a[i].replace("\\sSTEP\\s".toRegex(), "+")
            } else if (Regex("^SELECT\\s+CASE(.+$)", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = a[i].replace("^SELECT\\s+CASE(.+$)".toRegex(RegexOption.IGNORE_CASE), "switch($1){")
            } else if (Regex("^END\\s+SELECT", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = "}"
            } else if (Regex("^CASE\\s+ELSE", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = "default:"
            } else if (Regex("^CASE[\\w\\W]+$", RegexOption.IGNORE_CASE).containsMatchIn(a[i])) {
                a[i] = "${a[i].lowercase(Locale.getDefault())}:"
            } else if (Regex("^On\\s+Error\\s+Resume\\s+Next.*[\\r\\n]").containsMatchIn(a[i])) {
                a[i] = a[i].replace(
                    "^On\\s+Error\\s+Resume\\s+Next.*[\\r\\n]".toRegex(RegexOption.IGNORE_CASE),
                    "window.onerror=null\r\n"
                )
            } else if (Regex("^On\\s+Error\\s+.+.*[\\r\\n]").containsMatchIn(a[i])) {
                a[i] = a[i].replace(
                    "^On\\s+Error\\s+.+.*[\\r\\n]".toRegex(RegexOption.IGNORE_CASE),
                    "window.detachEvent('onerror')\r\n"
                )
            } else if (Regex("(?=\\s*)&(?!#|[a-z]+;)").containsMatchIn(a[i])) {
                a[i] = a[i].replace("(?=\\s*)&(?!#|[a-z]+;)".toRegex(), "+")
            } else if (Regex("(\\s+)NOT(\\s+)").containsMatchIn(a[i])) {
                a[i] = a[i].replace("(\\s+)NOT(\\s+)".toRegex(), "$1!$2")
            } else if (Regex("(\\s*)<>(\\s*)").containsMatchIn(a[i])) {
                a[i] = a[i].replace("(\\s*)<>(\\s*)".toRegex(), "$1!=$2")
            } else if (Regex("(\\s+)AND(\\s+)").containsMatchIn(a[i])) {
                a[i] = a[i].replace("(\\s+)AND(\\s+)", "$1&&$2")
            } else if (Regex("(\\s+)OR(\\s+)").containsMatchIn(a[i])) {
                a[i] = a[i].replace("(\\s+)OR(\\s+)", "$1||$2")
            } else if (Regex("^CONST").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^CONST".toRegex(), "const")
            } else if (Regex("^Option\\s+Explicit.*[\\r\\n]").containsMatchIn(a[i])) {
                a[i] = a[i].replace("^Option\\s+Explicit.*[\\r\\n]".toRegex(RegexOption.IGNORE_CASE), "")
            } else {
                // alert(a[i])
            }

            if (Regex("(?<![a-zA-Z])range.*", RegexOption.IGNORE_CASE).containsMatchIn(a[i]))
                a[i] = a[i].replace("range".toRegex(RegexOption.IGNORE_CASE), "GetRange")
            if (Regex(".*GetRange\\([^)]+\\)\\s*=\\s*", RegexOption.IGNORE_CASE).containsMatchIn(a[i]))
                a[i] = Regex(".*GetRange\\([^)]+\\)", RegexOption.IGNORE_CASE).replace(a[i]) {
                    it.value + ".Value"
                }
        }

        // other stuff
        for (i in 0..<a.size) {
            // comments
            a[i] = a[i].replace("^\'".toRegex(), "//")
            // attempt to catch inline comments
            a[i] = a[i].replace("\\s\\s\'".toRegex(), "  //")

            a[i] = a[i].replace("\\sByVal\\s".toRegex(), " ")
            a[i] = a[i].replace("\\sByRef\\s".toRegex(), " ")
            a[i] = a[i].replace("vbCRLF".toRegex(), "\\r\\n")
            a[i] = a[i].replace("vbCR".toRegex(), "\\r")
            a[i] = a[i].replace("vbLF".toRegex(), "\\n")
            a[i] = a[i].replace("vbTab".toRegex(), "\\t")
            a[i] = a[i].replace("vbOK".toRegex(), "1")
            a[i] = a[i].replace("vbCancel".toRegex(), "2")
            a[i] = a[i].replace("vbCancel".toRegex(), "2");
            a[i] = a[i].replace("vbAbort".toRegex(), "3");
            a[i] = a[i].replace("vbRetry".toRegex(), "4");
            a[i] = a[i].replace("vbIgnore".toRegex(), "5");
            a[i] = a[i].replace("vbYes".toRegex(), "6");
            a[i] = a[i].replace("vbNo".toRegex(), "7");
            a[i] = a[i].replace("vbBinaryCompare".toRegex(), "0");
            a[i] = a[i].replace("vbTextCompare".toRegex(), "1");
            a[i] = a[i].replace("vbUseDefault".toRegex(), "-2");
            a[i] = a[i].replace("vbTrue".toRegex(), "-1");
            a[i] = a[i].replace("vbFalse".toRegex(), "0");

        }

        //добавление break в конструкции switch-case
        var i = 1
        while (i < a.size) {
            if (a[i].matches(Regex(".*case\\s.*", RegexOption.IGNORE_CASE)) && a[i - 1].matches(Regex("[^{]+\$"))) {
                a.add(i, "break;")
                i++
            }
            i++
        }

        vars = vars.replace(Regex("\\s*AS\\s+\\w+\\s*", RegexOption.IGNORE_CASE), "")
        if (vars.isNotBlank())
            vars = "var $vars".replace(Regex(",$"), ";").replace(Regex(","), ", ")
        fxHead + '\n' + vars

        a = a.filter { it.isNotBlank() }.toMutableList() // remove empty items

        for (i in 0..<a.size) {
            if (a[i].matches(Regex("[^}{:;]+\$")))
                a[i] += ";"
        }

        var ss = "$fxHead\n$vars\n${a.joinToString("\n")}\n})();"

//    ss = ss.replace(Regex("$fx\\s*=\\s*", RegexOption.IGNORE_CASE), "return ")

        ss = unHideStrings(ss)

        return jsIndenter(ss)
    }

    private fun jsIndenter(js: String): String {
        var a = js.split("\n", "\tvar").toMutableList()
        var s = ""
        var margin = 0

        // trim
        for (i in 0 until a.size) {
            a[i] = a[i].trim()
        }
        // remove empty items
        a = a.filter { it.isNotBlank() }.toMutableList()

        for (i in 1 until a.size) {
            if (a[i - 1].indexOf("{") > -1)
                margin += 4

            if (a[i].indexOf("}") > -1)
                margin -= 4

            if (margin < 0)
                margin = 0

            a[i] = strFill(margin, " ") + a[i]
        }

        //добавление отступов в конструкции switch-case

        for (i in 1..<a.size) {
            if (a[i - 1].matches(Regex(".*case\\s.*", RegexOption.IGNORE_CASE)))
                margin += 4

            if (a[i].matches(Regex(".*case\\s.*", RegexOption.IGNORE_CASE)) || a[i].indexOf("}") > -1)
                margin -= 4

            if (margin < 0)
                margin = 0

            a[i] = strFill(margin, " ") + a[i]
        }

        return a.joinToString("\n")
    }

    private fun strFill(count: Int, strToFill: String): String {
        var objStr = ""
        for (idx in 1..count) {
            objStr += strToFill
        }
        return objStr
    }


    private fun hideStrings(text: String): String {
        val x = 7.toChar().toString()
        val xxx = 8.toChar().toString()

        var newText = text.replace("\"\"\"", "\"$xxx")  // hide 3 quotes " " "
        var idx = 0
        var f = 0

        while (f > -1) {

            val matchResult = "\"([^\"]+)\"".toRegex().find(newText, f)
            if (matchResult != null) {
                val matchedText = matchResult.value
                strs += matchedText
                newText = newText.replace(matchedText, "$x$idx$x")
                idx++
                f = matchResult.range.last + 1
                if (f > newText.length) {
                    break
                }
            } else {
                f = -1  // Выход из цикла, если не удалось найти больше вхождений
            }
        }
        return newText
    }

    private fun unHideStrings(text: String): String {
        val x = 7.toChar().toString()
        var newText = text
        for (i in 0..<strs.size) {
            newText = newText.replace("$x$i$x", strs[i])
        }
        // Unhide 3 quotes " " "
        newText = newText.replace("\\x08", "\\\"")
        newText = newText.replace("\"\"", "\\\"")
        return newText
    }


    private fun convertToAlphabet(n: Int): String {
        var result = ""
        var num = n
        while (num > 0) {
            num--
            result = (num % 26 + 65).toChar() + result
            num /= 26
        }
        return result.uppercase(Locale.getDefault())
    }

    private fun addHideString(toHide: String): String {
        val x = 7.toChar().toString()
        strs += "${toHide}"
        return "$x${strs.size - 1}$x"
    }
}