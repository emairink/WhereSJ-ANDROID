/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.pktstudio.wheresj.R
import kotlinx.android.synthetic.main.activity_profile_site_webview.*
import kotlinx.android.synthetic.main.p_toolbar.*
import java.lang.Exception

class ProfileSiteWebview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_site_webview)

        profileToolbar.setNavigationIcon(R.drawable.abc_vector_test)
        profileToolbar.setNavigationOnClickListener {
            this.finish()
        }
        try {
            val url = intent.getStringExtra("urlToOpen")

            if (url.indexOf("https://") > -1) {
                companySiteWebview.loadUrl(url)
            } else {
                companySiteWebview.loadUrl("https://${url}")
            }
            Log.i("PROGRESS", companySiteWebview.progress.toString())
            profileToolbar.title = "https://${url}"
        }catch (e : Exception){
            Toast.makeText(applicationContext, "Error: \n$e", Toast.LENGTH_LONG).show()
        }
    }
}
