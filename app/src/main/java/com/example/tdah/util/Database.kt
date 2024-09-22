package com.example.tdah.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.IOException

object DatabaseUtils {
    fun exportDatabase(context: Context) {
        try {
            // Verifica se o armazenamento externo está disponível para leitura/escrita
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val dbPath = context.getDatabasePath("app_database").absolutePath
                val exportPath = File(context.getExternalFilesDir(null), "user_database_export.db")

                // Copia o banco de dados para o caminho de exportação
                File(dbPath).copyTo(exportPath, overwrite = true)

                // Notifica o usuário sobre o sucesso
                Toast.makeText(context, "Banco de dados exportado com sucesso para ${exportPath.absolutePath}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Armazenamento externo não está disponível", Toast.LENGTH_LONG).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Erro ao exportar banco de dados: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
