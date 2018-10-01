package com.example.mateusoliveira.projectkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.welcome.*



class Welcome : AppCompatActivity() {
    private var stringBuilder = StringBuilder();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        val nomeDado: String = intent.getStringExtra("nome")
        val cpfDado: String = intent.getStringExtra("cpf")
        dados.text = stringBuilder.append("\n")
                .append(cpfDado)
                .append("\n")
                .append(nomeDado);
    }

}




