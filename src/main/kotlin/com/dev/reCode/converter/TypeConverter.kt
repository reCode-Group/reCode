package com.dev.reCode.converter

import com.dev.reCode.converter.blocks.js.jsToBlock
import com.dev.reCode.converter.blocks.js.toJs
import com.dev.reCode.converter.blocks.models.Block
import com.dev.reCode.converter.blocks.vba.toVba
import com.dev.reCode.converter.blocks.vba.vbaToBlock

class TypeConverter {
    fun convert(input: String, type: String): String {
        val (from, to) = type.split(DELIMITER)
        val block = with(input) {
            when (from) {
                VBA -> vbaToBlock()
                JS -> jsToBlock()
                else -> Block()
            }
        }
        return with(block) {
            when (to) {
                VBA -> toVba()
                JS -> toJs()
                else -> ""
            }
        }
    }

    private companion object {
        const val VBA = "vba"
        const val JS = "js"
        const val DELIMITER = "-"
    }
}