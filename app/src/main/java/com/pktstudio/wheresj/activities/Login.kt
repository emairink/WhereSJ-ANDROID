/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */



package com.pktstudio.wheresj.activities

import android.app.StatusBarManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.pktstudio.wheresj.R
import kotlinx.android.synthetic.main.activity_login0.*
import com.pktstudio.wheresj.api.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login0)
    }


    fun handleLogin(view : View) {
        loginProgress.visibility = View.VISIBLE
        val email =  "pocketstudioapp@gmail.com" //loginEmail.text
        val senha =  "1q@w##4R" //loginPass.text


        if (email.isEmpty()) {
            wrongMail.visibility = View.VISIBLE
            wrongMail.text = "Preencha o email"
        }else {
            wrongMail.visibility = View.GONE
        }
        if(senha.length < 8 || senha.isEmpty()) {
            wrongPass.visibility = View.VISIBLE
            wrongPass.text = "Senha inválida"
        }else {
            wrongPass.visibility = View.GONE
        }

        val api = Api()
        val jsonLogin = JSONObject()

        try {
            jsonLogin.put("email", email)
            jsonLogin.put("senha", senha)
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.i("loginError: ", e.toString())
            loginProgress.visibility = View.GONE
        }
        AndroidNetworking.post(api.BASEURL + api.LOGINURL)
            .addJSONObjectBody(jsonLogin) // posting json
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    //Log.i("Login Success", "Login success : ${response.toString()}" )
                    //Log.i("token", response.getString("token"))
                    loginProgress.visibility = View.GONE

                    Log.i("response succesfull:", response.toString())
                    try {
                        val r = response;
                        val user = response.getJSONObject("user")
                        Log.i("string", user.toString())
                        //Log.i("jsonObject", userData.toString())
                        val sqliteDb : SQLiteDatabase = openOrCreateDatabase("where_user_data", Context.MODE_PRIVATE, null)

                sqliteDb.execSQL(
                "CREATE TABLE IF NOT EXISTS company (uid VARCHAR, photoURL VARCHAR, fantasia VARCHAR, endereco VARCHAR, email VARCHAR ,telefone VARCHAR, cep VARCHAR,website VARCHAR, cidade VARCHAR, estado VARCHAR, pais VARCHAR, token VARCHAR )")

                        val token = r.getString("token").toString()
                        val photoUrl = user.getString("photo_url")
                        val fantasia = user.getString("fantasia")
                        val endereco = user.getString("endereco")
                        val dbMail = user.getString("email")
                        val telefone = user.getString("telefone")
                        val id = user.getString("_id").toString()

                        sqliteDb.execSQL("INSERT INTO company (uid, photoURL, fantasia, endereco, email, telefone, cep ,website, cidade, estado, pais, token) VALUES ('$id' ,'$photoUrl', '$fantasia', '$endereco', '$dbMail', '$telefone', null ,null, null, null, null, '$token')")
                        Log.i("PATH", sqliteDb.toString())
                       // Log.i("db", sqliteDb.toString());
                        val intent : Intent = Intent(applicationContext, LoggedIn::class.java);
                        //intent.putExtra("database", sqliteDb.toString())
                        startActivity(intent);
                    }catch (e : Exception){
                        //Log.i("trycatch: " , e.message.toString())
                    }
                }
                override fun onError(error: ANError) {
                    //Log.i(" Message body : ", "${error.errorBody}" )
                    wrongMail.visibility = View.VISIBLE
                    //Log.i("Login Failed", "Error Message : ${error.errorBody + " Error Code:" + error.errorCode}" )
                    loginProgress.visibility = View.GONE
                }
            })
    }
}
