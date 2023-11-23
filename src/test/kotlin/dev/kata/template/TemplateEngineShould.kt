package dev.kata.template

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TemplateEngineShould {

//    null, null -> ???
//    "", [] -> ???
//    "hola", [] -> "hola"
//    "{$var1}", [] -> ???
//    "{$var1}", [$var2: hola] -> ???
//    "hola {$placeholder}", [$placeholder: mundo] -> "hola mundo"
//    "hola {$a1} {$b2} {$a1}", [$a1: foo, $b2: bar] -> "hola foo bar foo"
//    "hola {$a1} {$b2}", [$a1: foo] -> ???

    class Response(val parsedTemplate: String?)

    class Template {
        companion object {

            // TODO Add errors in response
            fun parse(template: String?, variables: Map<String, String>?): Response {
                var parsedTemplate = template
                variables?.forEach { (key, value) ->
                    parsedTemplate = parsedTemplate?.replace("{$key}", value)
                }

                return Response(
                    parsedTemplate = parsedTemplate,
                )
            }
        }
    }

    @Test
    fun `Parse an empty template and an empty variables`() {
        val response = Template.parse("", mapOf())

        assertEquals(response.parsedTemplate, "")
    }

    @Test
    fun `Parse template and an empty variables`() {
        val response = Template.parse("hola", mapOf())

        assertEquals(response.parsedTemplate, "hola")
    }

    @Test
    fun `Parse null template and null variables`() {
        val response = Template.parse(null, null)

        assertEquals(response.parsedTemplate, null)
    }

    @Test
    fun `Parse template and an variables with one value`() {
        val response = Template.parse("hola {\$placeholder}", mapOf("\$placeholder" to "mundo"))

        assertEquals(response.parsedTemplate, "hola mundo")
    }

    @Test
    fun `Parse template and an variables with same values`() {
        val response = Template.parse("hola {\$a1} {\$b2} {\$a1}", mapOf("\$a1" to "foo", "\$b2" to "bar"))

        assertEquals(response.parsedTemplate, "hola foo bar foo")
    }

//    @Test
//    fun `Parse template and an variables with more values in template`() {
//        val response = Template.parse("hola {\$a1} {\$b2} {\$a1}", mapOf("\$a1" to "foo"))
//
//        assertEquals(response.parsedTemplate, "hola foo bar foo")
//    }
}
