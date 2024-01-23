package com.dev.reCode.converter.blocks.js

import com.dev.reCode.converter.blocks.mediators.Condition
import com.dev.reCode.converter.blocks.mediators.Loop
import com.dev.reCode.converter.blocks.models.Block


private var strs = mutableListOf<String>()


fun String.jsToBlock(): Block {
    return Block(listOf())
}

fun Block.toJs(): String {
    var out = ""
    for (i in inner) {
        i.toJs()
    }

    out += when (this) {
        is Loop -> toJs()
        is Condition -> toJs()
        else -> ""
    }
    return out
}

fun Loop.toJs(): String {
    return ""
}

fun Condition.toJs(): String {
    return ""
}


private fun String.jsToCondition(): Condition {
    return Condition(listOf())
}

private fun String.jsToLoop(): Loop {
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