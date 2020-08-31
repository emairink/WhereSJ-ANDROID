/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.activities

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pktstudio.wheresj.R
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable {

            try {
                val  sqliteDb: SQLiteDatabase = SQLiteDatabase.openDatabase(
                    "/data/user/0/com.pktstudio.wheresj/databases/where_user_data",
                    null,
                    SQLiteDatabase.OPEN_READWRITE
                );

                val cursor: Cursor = sqliteDb.rawQuery("SELECT  token FROM company", null)
                cursor.moveToFirst()
                val token = cursor.getString(0)

                if (token !== null){
                    val i : Intent = Intent(applicationContext, LoggedIn::class.java)
                    startActivity(i)
                    this.finish()
                }


            }catch (e : Exception){
                //Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()
                Log.i("CATCH SPLASH", e.toString())
                val i : Intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                this.finish()
            }
            finish()
        }, 5000)
    }
}
