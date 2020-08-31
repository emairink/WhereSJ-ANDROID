/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.api

import android.util.Log
import com.google.firebase.database.*
import java.util.*


class Api  {
/*
    fun o () : String {
        FirebaseDatabase.getInstance().reference.child("A").setValue("AEIOEIO")

        val reference = FirebaseDatabase.getInstance().reference
            //reference.setValue("AAOAOA")
        val event = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("snapshot result", snapshot.toString())
                var a = snapshot.child("BASEURL").toString()

            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(event)

        return ""
        }

        */


    val BASEURL = "https://backend-where.herokuapp.com"
    val LOGINURL = "/auth/authenticate"
    val SIGNUPURL = "/auth/register"
}

