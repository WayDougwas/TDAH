package com.example.tdah

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tdah.util.Popup

class QuizActivity : AppCompatActivity() {

    // Lista de perguntas
    private val questions = arrayOf(
        "Você se distrai facilmente com barulhos externos?",
        "Tem dificuldades em concluir tarefas que exigem muita atenção?",
        "Costuma perder objetos com frequência?",
        // Adicione as demais perguntas aqui...
    )

    // Variáveis para controlar o progresso
    private var currentQuestionIndex = 0
    private var totalScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val questionText: TextView = findViewById(R.id.questionText)
        val answersGroup: RadioGroup = findViewById(R.id.answersGroup)
        val nextButton: Button = findViewById(R.id.nextButton)
        val finishButton: Button = findViewById(R.id.finishButton)
        val homeButton: ImageButton = findViewById(R.id.homeButton)

        // Exibir a primeira pergunta
        questionText.text = questions[currentQuestionIndex]

        // Ação do botão "Próxima"
        nextButton.setOnClickListener {
            val selectedAnswerId = answersGroup.checkedRadioButtonId
            if (selectedAnswerId != -1) {
                // Obter o valor da resposta
                val answerScore = when (selectedAnswerId) {
                    R.id.answer1 -> 0
                    R.id.answer2 -> 1
                    R.id.answer3 -> 2
                    R.id.answer4 -> 3
                    else -> 0
                }

                totalScore += answerScore

                // Avançar para a próxima pergunta
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    questionText.text = questions[currentQuestionIndex]
                    answersGroup.clearCheck()
                } else {
                    // Última pergunta respondida, exibir botão de finalizar
                    nextButton.visibility = Button.GONE
                    finishButton.visibility = Button.VISIBLE
                }
            } else {
                Toast.makeText(this, "Selecione uma resposta!", Toast.LENGTH_SHORT).show()
            }
        }

        // Ação do botão "Finalizar"
        finishButton.setOnClickListener {
            // Calcular a porcentagem
            val percentage = (totalScore.toDouble() / (questions.size * 3)) * 100

            // Formatando a porcentagem como uma string com uma casa decimal
            val percentageString = String.format("%.1f", percentage)

            // Mostrar o resultado do quiz usando o popup
            Popup.showQuizResult(this, "$percentageString%")
        }

        // Ação do botão "Voltar para Home" com confirmação
        homeButton.setOnClickListener {
            Popup.showExitConfirmationDialog(this, this, "p")
        }
    }
}
