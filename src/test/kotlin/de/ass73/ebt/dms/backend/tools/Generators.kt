package de.ass73.ebt.dms.backend.tools

import java.util.*

object Generators {
    fun generateString(length: Int): String {
        val leftLimit = 48 // Zahl '0'
        val rightLimit = 122 // Buchstabe 'z'
        return Random().ints(leftLimit, rightLimit + 1)
            .filter { i: Int -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97) }
            .limit(length.toLong())
            .collect(
                { StringBuilder() },
                { obj: java.lang.StringBuilder, codePoint: Int -> obj.appendCodePoint(codePoint) }) { obj: java.lang.StringBuilder, s: java.lang.StringBuilder? ->
                obj.append(
                    s
                )
            }
            .toString()
    }
}
