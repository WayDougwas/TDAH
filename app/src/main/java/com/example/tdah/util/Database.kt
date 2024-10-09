package com.example.tdah.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.IOException

object DatabaseUtils {
    fun exportDatabase(context: Context, databaseName: String) {
        try {
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                // Caminhos dos arquivos do banco de dados
                val dbPath = context.getDatabasePath(databaseName).absolutePath
                val walPath = "$dbPath-wal"
                val shmPath = "$dbPath-shm"

                // Diretório de exportação
                val exportDir = File(context.getExternalFilesDir(null), "database_export")
                if (!exportDir.exists()) {
                    exportDir.mkdirs() // Cria o diretório se não existir
                }

                // Arquivos de exportação
                val exportDbPath = File(exportDir, databaseName)
                val exportWalPath = File(exportDir, "$databaseName-wal")
                val exportShmPath = File(exportDir, "$databaseName-shm")

                // Copia os arquivos do banco de dados
                File(dbPath).copyTo(exportDbPath, overwrite = true)
                File(walPath).copyTo(exportWalPath, overwrite = true)
                File(shmPath).copyTo(exportShmPath, overwrite = true)

                // Notifica o usuário sobre o sucesso
                Toast.makeText(
                    context,
                    "Banco de dados exportado com sucesso para ${exportDir.absolutePath}",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Armazenamento externo não está disponível",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Erro ao exportar banco de dados: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
