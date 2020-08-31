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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.google.gson.Gson
import com.pktstudio.wheresj.R
import com.pktstudio.wheresj.adapters.CompanyAdapter
import com.pktstudio.wheresj.model.WhereCompany
import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.main_toolbar.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.*
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.firebase.database.*
import com.pktstudio.wheresj.api.Api
import kotlinx.android.synthetic.main.main_toolbar.*
import kotlinx.android.synthetic.main.search_toolbar.*
import org.json.JSONArray
import org.json.JSONObject

open class MainActivity : AppCompatActivity() {

    var companyList: MutableList<WhereCompany> = mutableListOf();
    lateinit var companyAdapter: CompanyAdapter
    val API = Api()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        try {
            val sqliteDb: SQLiteDatabase = SQLiteDatabase.openDatabase(
                "/data/user/0/com.pktstudio.wheresj/databases/where_user_data",
                null,
                SQLiteDatabase.OPEN_READWRITE
            );

            val cursor: Cursor = sqliteDb.rawQuery("SELECT  token FROM company", null)
            cursor.moveToFirst()
            val token = cursor.getString(0)

            if (token !== null) {
                val i: Intent = Intent(applicationContext, LoggedIn::class.java)
                startActivity(i)
                this.finish()
            }
        }catch (e : Exception){
            //DO NOTHING
        }

            fastAndroidNetworkReq()


        //Toolbar config
        val mt : Toolbar = findViewById(R.id.main_toolbar)
        mt.title= "São João del Rei"
        mt.subtitle = "Minas Gerais - Brazil"
        mt.setTitleMargin(50, 0, 0, 0)

        mt.popupTheme = R.style.ThemeOverlay_AppCompat_Light
        setSupportActionBar(mt)


        //Recycler View Config
       // home_recycler_view.layoutManager = LinearLayoutManager(applicationContext)
        home_recycler_view.layoutManager = GridLayoutManager(applicationContext, 1)
        /** Lista horizontal **/
        //home_recycler_view.layoutManager = LinearLayoutManager(this,  RecyclerView.VERTICAL, false)

/*

            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(v: View, pos: Int) {
                    super.onItemClick(v, pos)
                }

                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onLongItemClick(v: View, pos: Int) {
                    super.onLongItemClick(v, pos)
                }
            }
        ) )

*/

        /*for (pos in 0 until 20) {
            home_recycler_view.smoothScrollToPosition(pos)
        }
        */

                    /**Divisor de itens (desativado) **/

        /**Divisor de itens (desativado) **/
        /*home_recycler_view.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
            )
        )*/
        //Adapters

        companyAdapter = CompanyAdapter(companyList)
        home_recycler_view.adapter = companyAdapter


        //Name Search
        val searchInput = search_input

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                nameSearch()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                nameSearch()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
  //             nameSearch()
            }
        } )
    } //  /onCreate();


        //Functions
     override fun onCreateOptionsMenu(menu : Menu): Boolean {
         val inflater  = MenuInflater(applicationContext)
         main_toolbar.inflateMenu(R.menu.main_menu)
         return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //return super.onOptionsItemSelected(item)
        val i = item.itemId
        if (i == R.id.loginMenuItem) {
            val intent : Intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
            return true;
        } else if(i == R.id.signUpMenuItem) {
            val intent : Intent = Intent(this@MainActivity, CompanySignUp::class.java)
            startActivity(intent)
            return true
        }else {
            Toast.makeText(applicationContext, "Esta opção ainda não foi implementada", Toast.LENGTH_LONG).show()
            return super.onOptionsItemSelected(item)
            }
        }

     fun fastAndroidNetworkReq() {

        AndroidNetworking.initialize(applicationContext)
        val progress = home_progress_bar

        AndroidNetworking.get( API.BASEURL + "/companies")
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    try {
                        if (response != null) {
                            companyList.clear()
                            for (pos in 0 until response.length()){
                                var companies = response.get(pos)
                                val a = response.getJSONObject(pos)
                                //println("Erick :  $a")
                                //Log.i("Teste1", companies.toString())
                                val newjson = Gson().toJson(a)

                                val topic = Gson().fromJson(newjson, WhereCompany::class.java)

                                topic.photo_url = a.getString("photo_url")
                                topic.fantasia = a.getString("fantasia")
                                topic.email = a.getString("email")
                                topic.endereco = a.getString("endereco")
                                topic.__v = a.getInt("__v")
                                topic.dataDeCriacao = a.getString("dataDeCriacao")
                                topic.telefone = a.getString("telefone")
                                topic._id = a.getString("_id")
                                topic.website = a.getString("website")
                                topic.description = a.getString("descricao")

                                companyList.add(topic)
                                companyAdapter.notifyDataSetChanged()
                            }
                        }
                        home_recycler_view.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                    } catch (e: Exception) {
                        Log.i("error1", e.message.toString())

                    }
                }
                override fun onError(anError: ANError?) {
                    Log.i("error2", anError.toString())
                    progress.visibility = View.GONE
                    no_conection_text.visibility = View.VISIBLE

                }
            })
    }

    fun nameSearch() {
        val progress = home_progress_bar
        val nameToSearch = search_input.text
        home_recycler_view.visibility = View.GONE
        progress.visibility = View.VISIBLE

        if(nameToSearch.toString() == "" || nameToSearch.isEmpty())
            fastAndroidNetworkReq()

        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.get(API.BASEURL + "/search?fantasia=$nameToSearch")
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                try {
                    if (response != null) {
                        companyList.clear()
                        for (pos in 0 until response.getJSONArray("companies").length()){
                            val a = response.getJSONArray("companies").getJSONObject(pos)
                            val newJson = Gson().toJson(a)
                            val searchResult = Gson().fromJson(newJson, WhereCompany::class.java)

                            searchResult.photo_url = a.getString("photo_url")
                            searchResult.fantasia = a.getString("fantasia")
                            searchResult.email = a.getString("email")
                            searchResult.endereco = a.getString("endereco")
                         //   searchResult.__v = a.getInt("__v")
                         //   searchResult.dataDeCriacao = a.getString("dataDeCriacao")
                            searchResult.telefone = a.getString("telefone")
                            searchResult._id = a.getString("_id")
                            searchResult.website = a.getString("website")
                            searchResult.website = a.getString("website")
                            searchResult.description = a.getString("descricao")

                            companyList.add(searchResult)
                            companyAdapter.notifyDataSetChanged()
                        }
                    }
                    progress.visibility = View.GONE
                    home_recycler_view.visibility = View.VISIBLE
                } catch (e: Exception) {
                    Toast.makeText(applicationContext,"tryCatch error:" + e.message, Toast.LENGTH_LONG).show()
                    Log.i("searchErro:", e.message.toString())

                }
            }
            override fun onError(anError: ANError?) {
                Toast.makeText(applicationContext, "onError :" + anError.toString(), Toast.LENGTH_LONG).show()
                Log.i("searchAnError", anError.toString())
                no_conection_text.visibility = View.VISIBLE

            }
        })
    }
    fun volleyReq() {
                /* Volley
          val queue = newRequestQueue(this)
          val url = "/companies"




          val request = JsonArrayRequest(url,
              Response.Listener<JSONArray> { response ->
                  for (pos in 0 until response.length()) {
                      val j = response.getJSONObject(pos)

                      companyList.addAll( )
                      companyAdapter.notifyDataSetChanged()
                  }
                  },
              Response.ErrorListener { e ->
                  Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
                  Log.i("JsonObjectVolleyExc", "${e.message}")

              })

          queue.add(request)
          queue.start() */
            }

}






