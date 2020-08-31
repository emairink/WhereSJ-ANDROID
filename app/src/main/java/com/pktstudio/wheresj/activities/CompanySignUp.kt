/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.activities

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.view.View.OnTouchListener
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.pktstudio.wheresj.R
import com.pktstudio.wheresj.api.Api
import kotlinx.android.synthetic.main.activity_company_sign_up.*
import org.json.JSONException
import org.json.JSONObject


class CompanySignUp : AppCompatActivity() {
    val jsonSignup = JSONObject()
    val API = Api()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_sign_up)


        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager,
            FragmentPagerItems.with(this)
                .add("Identificação",  FirstSignupFragment::class.java)
                .add("Autenticação",  SecondSignupFragment::class.java)
                .add("Conclusão",  ThirdSignupFragment::class.java)
                .create()
        )

        viewpager.setAdapter(adapter)
        smartTabVP.setViewPager(viewpager)
        viewpager.setOnTouchListener(OnTouchListener { _, _ -> true })
    }

    fun p(v: View) {
        var canProceed = false
        var fantasiaOk = false
        var enderecoOk = false
        var telefoneOk = false
        var emailOk = false


        val fantasia = findViewById<EditText>(R.id.editText_fantasia)
        val endereco = findViewById<EditText>(R.id.editText_endereco)
        val telefone = findViewById<EditText>(R.id.editText_telefone)
        val website = findViewById<EditText>(R.id.editText_website)

        val errorTV = findViewById<TextView>(R.id.errorTextView)

        if(fantasia.text.isEmpty() || fantasia.text === null ) {
            errorTV.visibility = View.VISIBLE
            errorTV.text = "Nome obrigatório"
        }else {
            errorTV.visibility = View.GONE
            fantasiaOk = true
        }

        if(endereco.text.isEmpty() || endereco.text == null ) {
            errorTV.visibility = View.VISIBLE
            errorTV.text = "Endereço obrigatório"
        }else {
            enderecoOk = true;
        }

        if(telefone.text.isEmpty() || telefone.text == null ) {
            errorTV.visibility = View.VISIBLE
            errorTV.text = "Telefone obrigatório"
        }else {
            telefoneOk = true;
        }

        if (fantasiaOk && enderecoOk && telefoneOk ) {
            try {
                jsonSignup.put("fantasia", fantasia.text)
                jsonSignup.put("endereco", endereco.text)
                jsonSignup.put("telefone", telefone.text)
               if (website.text.isEmpty()) {
                   jsonSignup.put("website", "Nenhum site informado")
               }else {
                   jsonSignup.put("website", website.text)
               }
                Log.i("JSONSIGNUP", jsonSignup.toString())
                viewpager.currentItem +=1
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
    fun p2(v: View) {
        var canProceed = false
        var emailOk = false
        var senhaOk = false
        var confSenhaOk = false

        val email = findViewById<EditText>(R.id.editText_email)
        val senha = findViewById<EditText>(R.id.editText_senha)
        val confSenha = findViewById<EditText>(R.id.editText_confSenha)
        val authErrorTextView = findViewById<TextView>(R.id.authErrorTextView)

        if(email.text.isEmpty() || email.text === null ) {
            authErrorTextView.visibility = View.VISIBLE
            authErrorTextView.text = "Insira um email válido (email@example.com)"
        }else {
            authErrorTextView.visibility = View.GONE
            emailOk = true
        }

        if(senha.text.isEmpty() || senha.text == null) {
            authErrorTextView.visibility = View.VISIBLE
            authErrorTextView.text = "Verifique a senha com atenção"
        }else if (senha.text.length < 8){
            authErrorTextView.text = "A senha possui menos que 8 caracteres"
        }else {
            senhaOk = true
        }

       if (senha.text.toString() == confSenha.text.toString() && senha.text.length >= 8 && confSenha.text.length >= 8 && senha.text.isNotEmpty() && confSenha.text.isNotEmpty()){
            confSenhaOk = true
            authErrorTextView.visibility = View.GONE
        }else {
       // Log.i("EXCEPTION CONF 1",(senha.text.toString() == confSenha.text.toString()).toString())
       // Log.i("EXCEPTION CONF 2",(senha.text.length >= 8 && confSenha.text.length >= 8).toString())
       // Log.i("EXCEPTION CONF 3",( senha.text.isNotEmpty() && confSenha.text.isNotEmpty() ).toString())
            authErrorTextView.visibility = View.VISIBLE
            authErrorTextView.text = "Confirme se os campos de senha correspondem"
        }

       /* if(confSenha.text.isEmpty() || confSenha.text == null) {
            authErrorTextView.visibility = View.VISIBLE
            authErrorTextView.text = "Verifique a senha"
        }else if (confSenha.text.length < 8){
            authErrorTextView.visibility = View.VISIBLE
            authErrorTextView.text = "A senha possui menos que 8 caracteres"
        }else if ( confSenha.text != senha.text ){
            authErrorTextView.visibility = View.VISIBLE
            authErrorTextView.text = "As senhas não correspondem"
        }else {
            authErrorTextView.visibility = View.GONE
            confSenhaOk = true
        }*/
        if (emailOk && senhaOk && confSenhaOk){
                Log.i("EOK", "${emailOk.toString() + senhaOk.toString() + confSenhaOk.toString()} ")
            try {
                jsonSignup.put("email", email.text)
                jsonSignup.put("senha", senha.text)
                Log.i("JSONSIGNUPAUTH", jsonSignup.toString())
                viewpager.currentItem +=1
            } catch (e: JSONException) {
                Log.i("JSON EXCEPTION", e.toString())
                e.printStackTrace()
            }
        }else {
            Log.i("ENONOK", "${emailOk.toString() + senhaOk.toString() + confSenhaOk.toString()} ")

        }
    }

    fun p3(v : View) {
        val progress = findViewById<ProgressBar>(R.id.progress_bar_signup)
        val errorTextView = findViewById<TextView>(R.id.textView_error_signup)
        var textoResponsavel : String = ""
        progress.visibility = VISIBLE

        val responsavel = findViewById<EditText>(R.id.editText_nomeResponsavel)
        if (responsavel.text.isEmpty()) {
            textoResponsavel = jsonSignup.getString("fantasia")
        }else {
            textoResponsavel = responsavel.text.toString()
        }

        jsonSignup.put("nome_responsavel", textoResponsavel)
        jsonSignup.put("descricao", "Nenhuma descrição por enquanto")
        AndroidNetworking.post(API.BASEURL + API.SIGNUPURL)
            .addJSONObjectBody(jsonSignup)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject( object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.i("SIGNUP RESPONSE", response.toString())
                    if (response != null) {
                        try {
                            val company = response.getJSONObject("user")
                            Log.i("jsonString", company.toString())

                            val sqliteDb: SQLiteDatabase =
                                openOrCreateDatabase("where_user_data", Context.MODE_PRIVATE, null)
                            sqliteDb.execSQL(
                                "CREATE TABLE IF NOT EXISTS company (uid VARCHAR, photoURL VARCHAR, fantasia VARCHAR, endereco VARCHAR, email VARCHAR ,telefone VARCHAR, cep VARCHAR,website VARCHAR, cidade VARCHAR, estado VARCHAR, pais VARCHAR, token VARCHAR )"
                            )
                            val token = response.getString("token").toString()
                            val photoUrl = company.getString("photo_url")
                            val fantasia = company.getString("fantasia")
                            val endereco = company.getString("endereco")
                            val dbMail = company.getString("email")
                            val website = company.getString("website").toString()
                            val telefone = company.getString("telefone")
                            val id = company.getString("_id").toString()
                            sqliteDb.execSQL("INSERT INTO company (uid, photoURL, fantasia, endereco, email, telefone, cep ,website, cidade, estado, pais, token) VALUES ('$id' ,'$photoUrl', '$fantasia', '$endereco', '$dbMail', '$telefone', null , '$website' , null, null, null, '$token')")

                            val dialog = AlertDialog.Builder(this@CompanySignUp)
                                //AlertDialog.Builder(applicationContext)
                            dialog.setTitle("Cadastro concluído!")
                            dialog.setIcon(R.drawable.icon_done)
                            dialog.setCancelable(false)
                            dialog.setMessage("Seja bem vindo(a) ${textoResponsavel}! \nObrigado por se cadastrar conosco.")
                            dialog.setPositiveButton(
                                "Continuar",
                                DialogInterface.OnClickListener { dialog, which ->
                                    val i : Intent = Intent(applicationContext, LoggedIn::class.java)
                                    startActivity(i)
                                })
                            dialog.create()
                            dialog.show()
                        } catch (e: Exception) {
                            Log.i("CATCH EXCEPTION SIGNUP", e.toString())
                            errorTextView.setText(e.toString())
                            errorTextView.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                        }
                    }
                }
                override fun onError(anError: ANError?) {
                  //  Log.i("SIGNUP ONERROR",  anError?.response?.message().toString())
                  //  Log.i("ERRO BODY", anError?.errorBody)
                  //  Log.i("ERRO BODY", anError?.errorDetail)
                  //  Log.i("ERRO BODY", anError?.response.toString())
                    errorTextView.setText("${anError?.errorBody}")
                    errorTextView.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                }
            })
    }
}
