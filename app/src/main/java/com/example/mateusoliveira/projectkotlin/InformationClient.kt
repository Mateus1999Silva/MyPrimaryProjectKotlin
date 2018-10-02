package com.example.mateusoliveira.projectkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_information_client.*

import android.widget.Toast
import com.example.mateusoliveira.projectkotlin.model.Pessoa
import java.io.Serializable

class InformationClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_client)
        cpf.addTextChangedListener(mask("###.###.###-##", cpf))

        button.setOnClickListener {
            if (validationCampos()) {
                nome.clearFocus()
                textErrorCpf.error = null
                var pessoa = Pessoa(cpf.text.toString(), nome.text.toString())
                var intent = Intent(this, Welcome::class.java).apply {
                    putExtra("pessoa", pessoa as Serializable)
                }
                startActivity(intent)
            }
        }
    }

    private fun validationCampos():Boolean{
        if (isEmpty(nome.text.toString())) {
            nome?.error = "Preencha o campo"
            nome?.requestFocus()
            return false
        }

        if (isEmpty(cpf.text.toString())) {
            textErrorCpf?.error = "Preencha o campo"
            textErrorCpf?.requestFocus()
            return false
        }

        if (!validationCpf(cpf.text.toString())) {
            textErrorCpf?.error = "Preencha com um CPF válido"
            textErrorCpf?.requestFocus()
            return false
        }

        return true
    }


    private fun isSequenceValidCpf(cpf: String): Boolean {
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999")) {
            return false
        }
        return true
    }

    private fun replaceChars(cpfFull: String): String {
        return cpfFull.replace(".", "").replace("-", "")
                .replace("(", "").replace(")", "")
                .replace("/", "").replace(" ", "")
                .replace("*", "")
    }

    fun validationCpf(cpf: String): Boolean {
        val cpfClean = cpf.replace(".", "").replace("-", "")

        if (!isSequenceValidCpf(cpfClean))
            return false

        if (cpfClean.length != 11)
            return false

        //continue
        var dvCurrent10 = cpfClean.substring(9, 10).toInt()
        var dvCurrent11 = cpfClean.substring(10, 11).toInt()

        //the sum of the nine first digits determines the tenth digit
        val cpfNineFirst = IntArray(9)
        var i = 9
        while (i > 0) {
            cpfNineFirst[i - 1] = cpfClean.substring(i - 1, i).toInt()
            i--
        }
        //multiple the nine digits for your weights: 10,9..2
        var sumProductNine = IntArray(9)
        var weight = 10
        var position = 0
        while (weight >= 2) {
            sumProductNine[position] = weight * cpfNineFirst[position]
            weight--
            position++
        }
        //Verify the nineth digit
        var dvForTenthDigit = sumProductNine.sum() % 11
        dvForTenthDigit = 11 - dvForTenthDigit //rule for tenth digit
        if (dvForTenthDigit > 9)
            dvForTenthDigit = 0
        if (dvForTenthDigit != dvCurrent10)
            return false

        //### verify tenth digit
        var cpfTenFirst = cpfNineFirst.copyOf(10)
        cpfTenFirst[9] = dvCurrent10
        //multiple the nine digits for your weights: 10,9..2
        var sumProductTen = IntArray(10)
        var w = 11
        var p = 0
        while (w >= 2) {
            sumProductTen[p] = w * cpfTenFirst[p]
            w--
            p++
        }
        //Verify the nineth digit
        var dvForeleventhDigit = sumProductTen.sum() % 11
        dvForeleventhDigit = 11 - dvForeleventhDigit //rule for tenth digit
        if (dvForeleventhDigit > 9)
            dvForeleventhDigit = 0
        if (dvForeleventhDigit != dvCurrent11)
            return false

        return true
    }

    private fun isEmpty(dado: String): Boolean {
        if (dado.isEmpty() || dado.equals(null))
            return true

        return false
    }

    fun mask(mask: String, etCpf: EditText): TextWatcher {
        val textWatcher: TextWatcher = object : TextWatcher {
            var isUpdating: Boolean = false
            var oldString: String = ""
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = replaceChars(s.toString())
                var cpfWithMask = ""

                if (count == 0)//is deleting
                    isUpdating = true

                if (isUpdating) {
                    oldString = str
                    isUpdating = false
                    return
                }

                var i = 0
                for (m: Char in mask.toCharArray()) {
                    if (m != '#' && str.length > oldString.length) {
                        cpfWithMask += m
                        continue
                    }
                    try {
                        cpfWithMask += str.get(i)
                    } catch (e: Exception) {
                        break
                    }
                    i++
                }

                isUpdating = true
                etCpf.setText(cpfWithMask)
                etCpf.setSelection(cpfWithMask.length)

            }

            override fun afterTextChanged(editable: Editable) {

            }
        }

        return textWatcher
    }
}

