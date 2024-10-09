package com.example.tdah.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tdah.R
import com.example.tdah.data.Response
import com.example.tdah.util.DisplayUtils.setupWindowInsets
import com.example.tdah.util.PopupUtils
import com.example.tdah.util.PopupUtils.showExitConfirmationDialog
import com.example.tdah.util.TextUtils
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
    private val concatenatedAnswers = StringBuilder()

    private var userId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val mainView = findViewById<View>(R.id.quiz)
        setupWindowInsets(mainView)

        // Recuperar os dados do usuário da intent
        val name = intent.getStringExtra("USER_NAME")
        val email = intent.getStringExtra("USER_EMAIL")
        val birtday = intent.getStringExtra("USER_BIRTHDAY")
        val phone = intent.getStringExtra("USER_PHONE")
        val schoolyear = intent.getStringExtra("USER_SCHOOLYEAR")

        if (name == null || email == null || birtday == null || schoolyear == null || phone == null) {
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
                val selectedRadioButton = findViewById<RadioButton>(selectedAnswerId)
                val answerText = selectedRadioButton.text.toString()
                val encodedAnswer = TextUtils.encodeAnswer(answerText)

                // Concatenar a resposta usando StringBuilder
                if (concatenatedAnswers.isEmpty()) {
                    concatenatedAnswers.append(encodedAnswer)
                } else {
                    concatenatedAnswers.append(" ").append(encodedAnswer)
                }

                // Incrementa o total de pontos (convertendo o encodedAnswer para Int)
                totalScore += try {
                    encodedAnswer // Converte para Int se possível
                } catch (e: NumberFormatException) {
                    0 // Se houver erro, não soma nada
                }

                currentQuestionIndex++

                if (currentQuestionIndex < questions.size) {
                    updateQuestion()
                    answersGroup.clearCheck()
                } else {
                    val percentage =
                        (totalScore.toDouble() / (questions.size * 3)) * 100 // Cálculo da porcentagem correto
                    percentageString = String.format(Locale.getDefault(), "%.1f", percentage)
                    // Inserir o usuário e obter o userId
                    userViewModel.insertUser(name, email, birtday, schoolyear, phone, percentage)
                        .observe(this) { id ->
                            userId = id // Atribui o userId correto

                            // Criar uma única resposta concatenada e salvar no banco de dados
                            val finalResponse = Response(
                                userId = userId ?: -1, // Atribui o userId obtido
                                answer = concatenatedAnswers.toString() // Usa a string de respostas concatenadas
                            )

                            // Insere a resposta concatenada no banco de dados
                            responseViewModel.insert(finalResponse)

                            // Mostrar o resultado do quiz
                            PopupUtils.showQuizResult(this, percentage, true)
                        }
                }
            } else {
                Toast.makeText(this, getString(R.string.select_answer_message), Toast.LENGTH_SHORT)
                    .show()
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
