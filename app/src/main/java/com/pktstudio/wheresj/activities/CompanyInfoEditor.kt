/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.activities

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.pktstudio.wheresj.R

class CompanyInfoEditor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        //setTheme(R.style.Theme_AppCompat_Light_Dialog)
        //mt.popupTheme = R.style.ThemeOverlay_AppCompat_Light

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val sqliteDb : SQLiteDatabase = SQLiteDatabase.openDatabase(
                "/data/user/0/com.pktstudio.wheresj/databases/where_user_data",
                null,
                SQLiteDatabase.OPEN_READWRITE
            );
            val cursor : Cursor = sqliteDb.rawQuery("SELECT uid, photoURL, fantasia, endereco, email, telefone, cep ,website, cidade, estado, pais, token FROM company", null)
            cursor.moveToFirst()
        //    val photourl = cursor.getString(1)
            val fantasia = cursor.getString(2)
          //  val endereco = cursor.getString(3)

            //bindPreferenceSummaryToValue(findPreference("fantasia"))
         val fantasiaPreference = findPreference<EditTextPreference>("fantasia")
            fantasiaPreference?.summary = fantasia


        val descriptionPreference = findPreference<EditTextPreference>("textDescription")

            descriptionPreference?.setOnBindEditTextListener { editText ->
                run {
                    editText.height = 600
                    editText.setSelection(0)

                }
            }
        }
    }
}