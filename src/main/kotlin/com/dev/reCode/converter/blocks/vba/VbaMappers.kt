package com.dev.reCode.converter.blocks.vba

import com.dev.reCode.converter.blocks.mediators.Condition
import com.dev.reCode.converter.blocks.mediators.Loop
import com.dev.reCode.converter.blocks.models.Block

fun String.vbaToBlock(): Block {
    return Block(listOf())
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