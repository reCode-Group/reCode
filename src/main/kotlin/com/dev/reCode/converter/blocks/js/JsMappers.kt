package com.dev.reCode.converter.blocks.js

import com.dev.reCode.converter.blocks.mediators.Condition
import com.dev.reCode.converter.blocks.mediators.Loop
import com.dev.reCode.converter.blocks.models.Block


fun String.jsToBlock(): Block {
    return Block(listOf())
}

fun Block.toJs(): String {
    var out = ""
    for (i in inner) {
        out += when (i) {
            is Loop -> "kakoyto${i.toJs()} konverter"
            is Condition -> "drugoi${i.toJs()} converter"
            else -> ""
        }
    }
    return out
}

private fun String.jsToCondition(): Condition {
    return Condition(listOf())
}

private fun String.jsToLoop(): Loop {
    return Loop(listOf())
}