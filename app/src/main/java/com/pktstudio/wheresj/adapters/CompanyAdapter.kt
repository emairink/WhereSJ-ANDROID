/*
 * Copyright (c) 2020.
 * Developed by : Pocket Studio.
 * Contact: tel:  +55 (61) 99948-6774 | Skype & Mail: pocketstudioapp@gmail.com |
 * www.pocketstudio.com.br
 */

package com.pktstudio.wheresj.adapters

import android.content.Context
import android.content.Intent
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.pktstudio.wheresj.activities.Profile
import com.pktstudio.wheresj.R
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
        site.text = compPosition.website
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Profile::class.java)
            intent.putExtra("photoURL", compPosition.photo_url)
            intent.putExtra("fantasia", compPosition.fantasia)
            intent.putExtra("endereco", compPosition.endereco)
            intent.putExtra("pTel", compPosition.telefone)
            intent.putExtra("email", compPosition.email)
            intent.putExtra("site", compPosition.website)
            intent.putExtra("description", compPosition.description)

            //intent.putExtra("site", "Site não definido") // TODO()
            //intent.putExtra("description", ) //TODO()
            holder.itemView.context.startActivity(intent)

        }
        holder.itemView.setOnLongClickListener {
            //Toast.makeText(holder.itemView.context, "Longo click", Toast.LENGTH_LONG).show()
            true
        }

    }
}

