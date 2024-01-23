package com.dev.reCode.converter.blocks.vba

import com.dev.reCode.converter.blocks.mediators.Condition
import com.dev.reCode.converter.blocks.mediators.Function
import com.dev.reCode.converter.blocks.mediators.Loop
import com.dev.reCode.converter.blocks.models.Block

private var stringsInQuotes = mutableListOf<String>()

fun String.vbaToBlock(): Block {
    val a = this.split('\n').toMutableList()

//    var functions = mutableListOf()

    var block = Block()

    for (i in a) {
        if (Regex("\\bsub\\s*(\\w*)\\s*\\(\\)", (RegexOption.IGNORE_CASE)).containsMatchIn(i)) {
            val name =
                Regex("\\bsub\\s*(\\w*)\\s*\\(\\)", (RegexOption.IGNORE_CASE)).findAll(i).map { it.groupValues[1] }
                    .toList()
//            functions
//            block.inner =
        }
    }
    return Block()
}

fun Block.toVba(): String {
    var out = ""
    for (i in inner) {
        out += when (i) {
            is Loop -> "kakoyto${i.toVba()} konverter"
            is Condition -> "drugoi${i.toVba()} converter"
            else -> ""
        }
    }
    return out
}

private fun String.vbaToCondition(): Condition {
    return Condition(listOf())
}

private fun String.vbaToLoop(): Loop {
    return Loop(listOf())
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
            stringsInQuotes += matchedText
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
    for (i in 0..<stringsInQuotes.size) {
        newText = newText.replace("$x$i$x", stringsInQuotes[i])
    }
    // Unhide 3 quotes " " "
    newText = newText.replace("\\x08", "\\\"")
    newText = newText.replace("\"\"", "\\\"")
    return newText
}