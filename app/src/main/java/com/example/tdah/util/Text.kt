package com.example.tdah.util


object TextUtils {

    // Função para converter a resposta de texto para um código numérico
    fun encodeAnswer(answerText: String): Int {
        return when (answerText) {
            "Nem um pouco" -> 0
            "Só um pouco" -> 1
            "Bastante" -> 2
            "Demais" -> 3
            else -> 0 // Valor padrão para casos inesperados
        }
    }

    // Função para converter o código numérico de volta para o texto original
    private fun decodeAnswer(encodedAnswer: Int): String {
        return when (encodedAnswer) {
            0 -> "Nem um pouco"
            1 -> "Só um pouco"
            2 -> "Bastante"
            3 -> "Demais"
            else -> "Desconhecido"
        }
    }

    fun formatPhoneInput(phone: String): String {
        val cleaned = phone.replace("\\D".toRegex(), "") // Remove todos os caracteres não numéricos
        val formatted = StringBuilder()

        // Aplicar a formatação (ex: (XX) XXXX-XXXX)
        for (i in cleaned.indices) {
            when (i) {
                0 -> formatted.append("(") // Abre parênteses no começo
                2 -> formatted.append(") ") // Fecha parênteses e adiciona um espaço
                7 -> formatted.append("-") // Adiciona o hífen após os 4 primeiros dígitos
            }
            formatted.append(cleaned[i])
        }

        return formatted.toString()
    }

}