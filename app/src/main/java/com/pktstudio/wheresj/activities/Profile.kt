/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.pktstudio.wheresj.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.p_toolbar.*
import kotlinx.android.synthetic.main.profile.*

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        profileToolbar.setNavigationIcon(R.drawable.abc_vector_test)
        profileToolbar.setNavigationOnClickListener {
            this.finish()
        }
        val photoUrl: String = intent.getStringExtra("photoURL")
        val fantasia: String = intent.getStringExtra("fantasia")
        val endereco: String = intent.getStringExtra("endereco")
        val pTel: String = intent.getStringExtra("pTel")
        val email: String = intent.getStringExtra("email")
        val site: String = intent.getStringExtra("site")
        val description: String = intent.getStringExtra("description")

        Picasso.get().load(photoUrl).into(imageId)
        nameId.text = fantasia
        addresId.text = endereco
        telId.text = pTel
        websiteId.text = site
        emailId.text = email
        descriptionId.text = description

        action_call_bt.setOnClickListener {

            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${pTel}"))
                startActivity(intent);

            }catch (e : Exception){
            Toast.makeText(applicationContext, "ERRO: Verifique se seu aplicativo de telefone está habilitado", Toast.LENGTH_LONG).show()
            }
        }

        action_mail_btn.setOnClickListener {
            try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${email}"))
            startActivity(intent)
            }catch (e : Exception){
                Toast.makeText(applicationContext, "ERRO: Verifique se seu aplicativo de email está habilitado", Toast.LENGTH_LONG).show()
            }
        }
        action_opensite_bt.setOnClickListener {
            val intent = Intent(applicationContext, ProfileSiteWebview::class.java)
            intent.putExtra("urlToOpen", site)
            startActivity(intent)
        }
        verRotasId.setOnClickListener{
             try {
                 val uriToSearch : Uri = Uri.parse("google.navigation:q=${endereco}&avoid=tf")
                 val mapIntent = Intent(Intent.ACTION_VIEW, uriToSearch )
                 mapIntent.setPackage("com.google.android.apps.maps")
                 startActivity(mapIntent)
/**
 * WAZE
                 val uriToSearch : Uri = Uri.parse("https://waze.com/ul?q=${endereco}")
                 val intent   = Intent( Intent.ACTION_VIEW,uriToSearch)
                 intent.setPackage("com.waze")
                 startActivity( intent );
  */
            } catch (e : Exception) {
                 Toast.makeText(applicationContext, "ERRO: Verifique se seu celular possui o Google Maps ou Waze instalados e habilitados", Toast.LENGTH_LONG).show()
             }
        }
    }
}
/*
     val REQUEST_IMAGE_CAPTURE = 1
     Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
     takePictureIntent.resolveActivity(packageManager)?.also {
         startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
     }
     }
 */