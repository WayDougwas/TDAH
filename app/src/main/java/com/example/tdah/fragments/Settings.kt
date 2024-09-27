package com.example.tdah.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tdah.R
import com.example.tdah.util.DatabaseUtils
import com.example.tdah.util.PopupUtils

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment() {
    // Parâmetros que podem ser passados para o fragmento
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Encontra o botão de depuração de resultado
        val resultPopupButton: Button = view.findViewById(R.id.btn_debug_result_popup)
        val resultInput: TextView = view.findViewById(R.id.in_debug_result_popup)
        val exportDatabaseButton: Button = view.findViewById(R.id.btn_debug_database_export)

        // Configura o clique do botão para exportar o banco de dados
        exportDatabaseButton.setOnClickListener {
            DatabaseUtils.exportDatabase(requireContext(), "app_database")
        }

        // Configura o clique do botão para mostrar o resultado do quiz
        resultPopupButton.setOnClickListener {
            // Obtém a Activity que hospeda o fragmento como AppCompatActivity
            val activity = requireActivity() as? AppCompatActivity
            activity?.let {
                val inputText = resultInput.text.toString() // Obtém o texto do TextView

                // Verifica se o texto não está vazio
                if (inputText.isNotBlank()) {
                    try {
                        val inputValue = inputText.toDouble() // Converte o texto para Double
                        PopupUtils.showQuizResult(it, inputValue) // Passa a atividade e o valor do resultado
                    } catch (e: NumberFormatException) {
                        // Lida com a exceção caso a conversão falhe
                        Toast.makeText(context, "Insira um número válido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "O campo não pode estar vazio", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    companion object {
        /**
         * Método de fábrica para criar uma nova instância do fragmento usando os parâmetros fornecidos.
         *
         * @param param1 Parâmetro 1.
         * @param param2 Parâmetro 2.
         * @return Uma nova instância do fragmento Settings.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
