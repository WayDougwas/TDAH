package com.example.tdah.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tdah.R
import com.example.tdah.data.Response
import com.example.tdah.util.PopupUtils
import com.example.tdah.util.PopupUtils.showExitConfirmationDialog
import com.example.tdah.viewmodel.ResponseViewModel
import com.example.tdah.viewmodel.UserViewModel
import java.util.Locale

class QuizActivity : AppCompatActivity() {

    private lateinit var questions: Array<String>
    private var currentQuestionIndex = 0
    private var totalScore = 0
    private var percentageString: String? = null
    private val responseViewModel: ResponseViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

     var userId: Long? = null // Adicione um campo para armazenar o userId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Recuperar os dados do usuário da intent
        val name = intent.getStringExtra("USER_NAME")
        val email = intent.getStringExtra("USER_EMAIL")
        val birtday = intent.getStringExtra("USER_BIRTHDAY")
        val phone = intent.getIntExtra("USER_PHONE", 0)
        val schoolyear = intent.getStringExtra("USER_SCHOOLYEAR")

        if (name == null || email == null || birtday == null||schoolyear ==null) {
            Toast.makeText(this, getString(R.string.error_user_id), Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Recuperar o array de perguntas dos recursos
        questions = resources.getStringArray(R.array.questions)
        val answersGroup: RadioGroup = findViewById(R.id.answersGroup)
        val nextButton: Button = findViewById(R.id.nextButton)
        val homeButton: ImageButton = findViewById(R.id.homeButton)

        // Exibir a primeira pergunta
        updateQuestion()

        nextButton.setOnClickListener {
            val selectedAnswerId = answersGroup.checkedRadioButtonId
            if (selectedAnswerId != -1) {
                val answerScore = when (selectedAnswerId) {
                    R.id.answer1 -> 0
                    R.id.answer2 -> 1
                    R.id.answer3 -> 2
                    R.id.answer4 -> 3
                    else -> 0
                }

                // Criar uma string representando a resposta
                val answerString = "$currentQuestionIndex-$selectedAnswerId"

                // Salvar a resposta temporariamente
                if (userId != null) {
                    val response = Response(
                        userId = userId!!,
                        question = currentQuestionIndex, // Você pode usar um identificador ou índice para a pergunta
                        answer = answerString,
                    )
                    responseViewModel.insert(response)
                }

                totalScore += answerScore
                currentQuestionIndex++

                if (currentQuestionIndex < questions.size) {
                    updateQuestion()
                    answersGroup.clearCheck()
                } else {
                    // Calcular percentual
                    val percentage = (totalScore.toDouble() / (questions.size * 3)) * 100
                    percentageString = String.format(Locale.getDefault(), "%.1f", percentage)

                    // Inserir o usuário e obter o userId
                    userViewModel.insertUser(name, email, birtday, schoolyear,phone,percentage).observe(this) { id ->
                        userId = id
                    }

                    // Mostrar resultado
                    PopupUtils.showQuizResult(this, "$percentageString%")

                    // Atualizar o resultado do usuário no banco de dados com o totalScore
                    // (Se desejar atualizar algum dado adicional no usuário)
                }
            } else {
                Toast.makeText(this, getString(R.string.select_answer_message), Toast.LENGTH_SHORT).show()
            }
        }

        homeButton.setOnClickListener {
            showExitConfirmationDialog(this, "p")
        }
    }

    private fun updateQuestion() {
        val questionText: TextView = findViewById(R.id.questionText)
        questionText.text = questions[currentQuestionIndex]
    }
}
