/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.activities

import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.pktstudio.wheresj.R
import kotlinx.android.synthetic.main.activity_login0.*
import kotlinx.android.synthetic.main.fragment_first_signup.*
import kotlinx.android.synthetic.main.fragment_first_signup.view.*
import org.json.JSONException
import org.json.JSONObject

private const val ARG_PARAM1 = ""
private const val ARG_PARAM2 = ""

/**
 * A simple [Fragment] subclass.
 * Use the [first_signup.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstSignupFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_first_signup, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment first_signup.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstSignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}