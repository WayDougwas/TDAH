package com.example.tdah.util

import android.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tdah.R

object PopupUtils {

    // Função para exibir o diálogo de confirmação
    fun showExitConfirmationDialog(activity: AppCompatActivity, mode: String? = null) {
        val builder = AlertDialog.Builder(activity)

        val msg: String = if (mode == "p") {
            "Tem certeza que deseja voltar para a tela inicial? Todo o progresso será perdido."
        } else {
            "Tem certeza que deseja voltar para a tela inicial?"
        }

        builder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("Sim") { _, _ ->
                activity.finish() // Finaliza a atividade
            }
            .setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss() // Fecha o diálogo
            }

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.rounded_background_nb))
        alert.show()
    }

    // Função para exibir o popup de login
    fun showLoginPopup(activity: AppCompatActivity) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_login, null)
        builder.setView(dialogView)

        val editTextUsername: EditText = dialogView.findViewById(R.id.editTextUsername)
        val editTextPassword: EditText = dialogView.findViewById(R.id.editTextPassword)
        val buttonLoginSubmit: Button = dialogView.findViewById(R.id.buttonLoginSubmit)

        val dialog = builder.create()

        buttonLoginSubmit.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidLogin(username, password)) {
                dialog.dismiss()
                NavigationUtils.toDashboard(activity)
            } else {
                editTextUsername.error = activity.getString(R.string.admin_login_error)
            }
        }

        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.rounded_background_nb))
        dialog.show()
    }

    // Função para validar o login (simulada)
    private fun isValidLogin(username: String, password: String): Boolean {
        // Lógica de validação de login simulada
        // TODO: Alterar para um usuario de admin seguro depois
        return username == "admin" && password == "1234"
    }

    // Função para exibir o resultado do quiz
    fun showQuizResult(activity: AppCompatActivity, percentage: Double,cancelable: Boolean = false) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_result, null)

        val porcentView: TextView = dialogView.findViewById(R.id.txt_porcent)
        val restartButton: Button = dialogView.findViewById(R.id.btn_restart)
        val message: TextView = dialogView.findViewById(R.id.txt_result_popup_message)

        // Atualiza a mensagem com base na porcentagem
        message.text = if (percentage >= 50) {
            activity.getString(R.string.high_percent)
        } else {
            activity.getString(R.string.low_percent)
        }

        // Usa a string do recurso com o placeholder
        porcentView.text = String.format(activity.getString(R.string.quiz_result_percentage), percentage)

        // Configura o botão de reinício
        restartButton.setOnClickListener {
            NavigationUtils.toHome(activity)
        }

        // Constrói e exibe o diálogo
        builder.setView(dialogView)
        val dialog = builder.create()
        if (cancelable){
            dialog.setCancelable(false) // Impede que o diálogo seja fechado ao clicar fora
            dialog.setOnKeyListener { _, keyCode, _ ->
                // Ignora o botão de voltar
                keyCode == android.view.KeyEvent.KEYCODE_BACK
            }
        }
        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.rounded_background_nb))
        dialog.show()
    }


}

