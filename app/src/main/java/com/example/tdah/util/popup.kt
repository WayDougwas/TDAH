package com.example.tdah.util

import android.app.AlertDialog
import androidx.core.content.ContextCompat
import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.example.tdah.R

object Popup {
    // Função para exibir o diálogo de confirmação
    fun showExitConfirmationDialog(context: Context, activity: AppCompatActivity, mode: String? = null) {
        val builder = AlertDialog.Builder(context)
        val msg: String

        msg = if (mode == "p") {
            "Tem certeza que deseja voltar para a tela inicial? Todo o progresso será perdido."
        } else {
            "Tem certeza que deseja voltar para a tela inicial?"
        }

        builder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton("Sim") { _, _ ->
                // Finaliza a atividade e volta para a tela inicial
                activity.finish()
            }
            .setNegativeButton("não") { dialog, _ ->
                // Apenas fecha o diálogo
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    // Função para exibir o diálogo de login
    fun showLoginPopup(context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = (context as AppCompatActivity).layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_login, null)
        builder.setView(dialogView)

        val editTextUsername: EditText = dialogView.findViewById(R.id.editTextUsername)
        val editTextPassword: EditText = dialogView.findViewById(R.id.editTextPassword)
        val buttonLoginSubmit: Button = dialogView.findViewById(R.id.buttonLoginSubmit)

        val dialog = builder.create()

        buttonLoginSubmit.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            // Simular a autenticação
            if (username == "" && password == "") {
                dialog.dismiss()
                // Assumindo que você tem um método para navegação
                NavigationUtils.toDashboard(context)
            } else {
                // Mostrar mensagem de erro
                editTextUsername.error = context.getString(R.string.admin_login_error)
            }
        }

        dialog.show()
    }

    fun showQuizResult(context: Context, percentageString: String){
        val builder = AlertDialog.Builder(context)
        val inflater = (context as AppCompatActivity).layoutInflater
        val dialogView = inflater.inflate(R.layout.popup_result, null)
        val porcentView: TextView = dialogView.findViewById(R.id.txt_porcent)
        val restartButton: Button = dialogView.findViewById(R.id.btn_restart)

        restartButton.setOnClickListener { NavigationUtils.toHome(context) }
        porcentView.text = percentageString

        builder.setView(dialogView)
        val dialog = builder.create()

        dialog.window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.rounded_background_nb))

        dialog.show()
    }
}
