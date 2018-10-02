package com.example.mateusoliveira.projectkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mateusoliveira.projectkotlin.model.Pessoa
import kotlinx.android.synthetic.main.welcome.*



class Welcome : AppCompatActivity() {
    private var stringBuilder = StringBuilder();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)

        val pessoa= intent.extras.get("pessoa") as Pessoa
        dados.text = stringBuilder.append("\n")
                .append(pessoa.cpf)
                .append("\n")
                .append(pessoa.nome);
    }

}




