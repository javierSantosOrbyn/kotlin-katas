package dev.kata.wordWrap

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Columns private constructor(val columns: Int) {

    companion object {
        fun create(columns: String): Either<Exception, Columns> {
            try {
                if (columns.toInt() <= 0) {
                    return Exception("columns has to be one or bigger").left()
                } else {
                    return Columns(columns.toInt()).right()
                }
            } catch (ex: Exception) {
                return Exception("The input was not a number").left()
            }
        }
    }
}

class WordWrap {
    companion object {

        fun wrap(value: String, safeColumns: Columns): String {
            val column = safeColumns.columns
            if (value.length > column) {
                val remainingText = value.substring(0, column)
                val indexOfLastSpace = remainingText.indexOfLast { it == ' ' }

                if (indexOfLastSpace == -1) {
                    return remainingText + "\n" + wrap(value.substring(remainingText.length), safeColumns)
                }

                return remainingText.substring(0, indexOfLastSpace) + "\n" + wrap(value.substring(remainingText.length), safeColumns)
            } else {
                return value
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            println("Give a string")
            val str = readln()

            println("Give max columns")
            val columns = Columns.create(readln())

            when (columns) {
                is Either.Left -> print("Invalid input")
                is Either.Right -> print("Result -> ${wrap(str, columns.value)}")
            }
        }
    }
}

class WordWordWrapShould {

    // casos limite
    // "hola", 2 -> "ho\nla"
    // "hola mundo", 8   -> "hola\nmundo"
    // "abracadabra", 2 -> "ab\nra\ncad\nab\nra"
    // "hola abracadabra", 3 -> "hol\na\nabr\naca\ndab\nra"
    // "hola    abracadabra", 3 -> "hol\na\nabr\naca\ndab\nra"

    @Test
    fun `Wrap a word with no spaces`() {
        val response = WordWrap.wrap("hola", Columns.create("2") as Either.Right)

        assertEquals(response, "ho\nla")
    }

    @Test
    fun `Wrap a word with spaces and longer`() {
        val response = WordWrap.wrap("abracadabra", 2)

        assertEquals(response, "ab\nra\nca\nda\nbr\na")
    }

    @Test
    fun `Wrap a word with spaces and max 0`() {
        val response = WordWrap.wrap("abracadabra", 0)

        assertEquals(response, "abracadabra")
    }

    @Test
    fun `Wrap a word with spaces and max more than length`() {
        val response = WordWrap.wrap("abc", 4)

        assertEquals(response, "abc")
    }

}
