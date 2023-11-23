package dev.kata.template

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class TemplateEngineShouldFirst {

    //    null, null -> ???
//    "", [] -> ???
//    "hola", [] -> "hola"
//    "{$var1}", [] -> ???
//    "{$var1}", [$var2: hola] -> ???
//    "hola {$placeholder}", [$placeholder: mundo] -> "hola mundo"
//    "hola {$a1} {$b2} {$a1}", [$a1: foo, $b2: bar] -> "hola foo bar foo"
//    "hola {$a1} {$b2}", [$a1: foo] -> ???
    class Template {

        companion object {
            fun parse(template: String?, variables: HashMap<String, String>?): String {
                return if (isTemplateNotNUllAndVariablesNUll(template, variables)) {
                    "$template"
                } else if (isTemplateNUllAndVariablesNotNUll(template, variables)) {
                    "$variables"
                } else if (isTemplateNUllAndVariablesNUll(template, variables)) {
                    ""
                } else {
                    "$template $variables"
                }
            }

            private fun isTemplateNotNUllAndVariablesNUll(
                template: String?,
                variables: HashMap<String, String>?,
            ): Boolean {
                return (!template.isNullOrBlank() && variables.isNullOrEmpty())
            }

            private fun isTemplateNUllAndVariablesNotNUll(
                template: String?,
                variables: HashMap<String, String>?,
            ): Boolean {
                return (template.isNullOrBlank() && !variables.isNullOrEmpty())
            }

            private fun isTemplateNUllAndVariablesNUll(
                template: String?,
                variables: HashMap<String, String>?,
            ): Boolean {
                return (template.isNullOrBlank() && variables.isNullOrEmpty())
            }
        }
    }

    @Test
    fun `given a template and an empty map should return only the template successfully`() {
        val template = getTemplate()
        val map = hashMapOf<String, String>()

        val response = Template.parse(template, map)

        assertEquals(response, template)
    }

    @Test
    fun `given a template and a map should return the template and the variables successfully`() {
        val template = getTemplate()
        val map = getMap()

        val response = Template.parse(template, map)

        assertEquals(response, "$template $map")
    }

    @Test
    fun `not given a template and a map should return an empty String successfully`() {
        val response = Template.parse(null, null)

        assertEquals(response, "")
    }

    @Test
    fun `given an empty template and an empty map should return the template and the variables successfully`() {
        val template = ""
        val map = hashMapOf<String, String>()

        val response = Template.parse(template, map)

        assertEquals(response, "")
    }

    @Test
    fun `given an empty template and a map should return the template and the variables successfully`() {
        val template = ""
        val map = getMap()

        val response = Template.parse(template, map)

        assertEquals(response, "$map")
    }

    private fun getTemplate() = "hello"
    private fun getMap(): HashMap<String, String> {
        val variable = "Some"
        return hashMapOf(variable to variable)
    }
}
