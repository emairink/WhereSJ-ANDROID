/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.pktstudio.wheresj.R
import com.pktstudio.wheresj.activities.Login
import com.pktstudio.wheresj.activities.MainActivity
import com.pktstudio.wheresj.model.WhereCompany
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.company_list_item.view.*

class CompanyAdapter (private val companyList : MutableList<WhereCompany>) : RecyclerView.Adapter<RHolder>() {



    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RHolder {
        context = parent.context
        return RHolder(
            LayoutInflater.from(context).inflate(R.layout.alt_company_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    override fun onBindViewHolder(holder: RHolder, p: Int) {
        val compPosition = companyList[p]
        val logo = holder.itemView.companyLogo
        val name = holder.itemView.textName
        val address = holder.itemView.textAddress
        val pTel = holder.itemView.textPrimaryTel
        val email = holder.itemView.textEmail
        val site = holder.itemView.textWebsite

        Picasso.get().load(compPosition.photo_url).into(logo)
        name.text = compPosition.fantasia
        address.text = compPosition.endereco
        pTel.text = compPosition.telefone
        email.text = compPosition.email
        //site.text = compPosition.segmentos[0]

        holder.itemView.setOnClickListener { v: View ->
            //val intent = Intent(holder.itemView.context, Login::class.java)
            //holder.itemView.context.startActivity(intent)

        }
        holder.itemView.setOnLongClickListener { v : View ->
            //Toast.makeText(holder.itemView.context, "Longo click", Toast.LENGTH_LONG).show()
            true
        }

    }
}

