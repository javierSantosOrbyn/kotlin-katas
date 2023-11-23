package dev.kata.wordWrap

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Columns private constructor(val columns: Int) {

    companion object {
        fun create(columns: String): Either<Exception, Columns> {
            return try {
                if (columns.toInt() <= 0) {
                    Exception("columns has to be one or bigger").left()
                } else {
                    Columns(columns.toInt()).right()
                }
            } catch (ex: Exception) {
                Exception("The input was not a number").left()
            }
        }
    }
}

class WordWrap {
    companion object {

        fun wordWrap(fullText: String, safeColumns: Columns): String {
            val column = safeColumns.columns
            if (fullText.length <= column) {
                return fullText.trim()
            }

            var tentativeStr = fullText.substring(0, column)

            if (tentativeStr.contains(" ")) {
                val lastPositionSpace = tentativeStr.indexOfLast { it == ' ' }
                tentativeStr = tentativeStr.substring(0, lastPositionSpace)
            }

            return tentativeStr.trim() + "\n" + wordWrap(fullText.substring(tentativeStr.length).trim(), safeColumns)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            println("Give a string")
            val str = readln()

            println("Give max columns")
            val columns = Columns.create(readln())

            when (columns) {
                is Either.Left -> print("Invalid input")
                is Either.Right -> print("Result -> ${wordWrap(str, columns.value)}")
            }
        }
    }
}

class WordWordWrapShould {

    @Test
    fun `Wrap a word with no spaces`() {
        val response = runWordWrap("hola", "2")

        assertEquals(response, "ho\nla")
    }

    @Test
    fun `Wrap a word with spaces and longer`() {
        val response = runWordWrap("abracadabra", "2")

        assertEquals(response, "ab\nra\nca\nda\nbr\na")
    }

    @Test
    fun `Wrap a word with spaces and max more than length`() {
        val response = runWordWrap("abc", "4")

        assertEquals(response, "abc")
    }

    @Test
    fun `If there is a blank space insert a line break in the blank space`() {
        val response = runWordWrap("hola mundo", "8")

        assertEquals(response, "hola\nmundo")
    }

    @Test
    fun `If there is a blank space insert a line break in the blank space before a long text`() {
        val response = runWordWrap("hola abracadabra", "3")

        assertEquals(response, "hol\na\nabr\naca\ndab\nra")
    }

    @Test
    fun `If there is a long blank space insert a line break in the blank space before a long text`() {
        val response = runWordWrap("hola    abracadabra", "3")

        assertEquals(response, "hol\na\nabr\naca\ndab\nra")
    }

    private fun runWordWrap(text: String, validColumn: String): String {
        return Columns.create(validColumn).map {
            WordWrap.wordWrap(text, it)
        }.getOrElse {
            throw Exception("Please give a valid column more than 0")
        }
    }
}
