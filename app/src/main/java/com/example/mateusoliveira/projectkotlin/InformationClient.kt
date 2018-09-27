package com.example.mateusoliveira.projectkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_information_client.*

class InformationClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_client)

        button.setOnClickListener {
            if (!cpf.text.isEmpty() && !nome.text.isEmpty() && cpf.text.length == 11) {
                var intent = Intent(this, Welcome::class.java)
                intent.putExtra("cpf", cpf.text.toString())
                intent.putExtra("nome", nome.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Dados Invalidos", Toast.LENGTH_LONG).show()
            }
        }
    }
}
