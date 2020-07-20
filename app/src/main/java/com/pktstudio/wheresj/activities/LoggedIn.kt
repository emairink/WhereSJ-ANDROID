package com.pktstudio.wheresj.activities

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.OPEN_READWRITE
import android.database.sqlite.SQLiteDatabase.openDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import com.pktstudio.wheresj.R
import com.pktstudio.wheresj.adapters.CompanyAdapter
import com.pktstudio.wheresj.api.Api
import com.pktstudio.wheresj.model.WhereCompany
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_logged_in.*
import kotlinx.android.synthetic.main.search_toolbar.*
import org.json.JSONArray
import org.json.JSONObject

class LoggedIn : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    var companyList: MutableList<WhereCompany> = mutableListOf();
    lateinit var companyAdapter: CompanyAdapter

    val API = Api()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
            fastAndroidNetworkReq()

        /** <-- Configuração de pesquisa e listagem --> **/
        //Recycler View Config
        authenticated_recycler_view.layoutManager = LinearLayoutManager(applicationContext)

        //Adapters
        companyAdapter = CompanyAdapter(companyList)
        authenticated_recycler_view.adapter = companyAdapter

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


        // <-- Configuração do Drawer e Toolbar -->

        //Toolbar Config
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
            toolbar.title = "São João del Rei"
            toolbar.subtitle = "Minas Gerais - Brasil"
        setSupportActionBar(toolbar)

        //Drawer
        var navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val Name = headerView.findViewById<TextView>(R.id.navFantas)
        val Email = headerView.findViewById<TextView>(R.id.navUserName)
        val profilePictureView = headerView.findViewById<ImageView>(R.id.navLogo)


        val sqliteDb : SQLiteDatabase = openDatabase("/data/user/0/com.pktstudio.wheresj/databases/where_user_data",null, OPEN_READWRITE);
        val cursor : Cursor = sqliteDb.rawQuery("SELECT uid, photoURL, fantasia, endereco, email, telefone, cep ,website, cidade, estado, pais, token FROM company", null)
        cursor.moveToFirst()
        val photourl = cursor.getString(1)
        val fantasia = cursor.getString(2)
        val endereco = cursor.getString(3)


        /**"Configuração das infos do usuario logado */
        Name.text = fantasia
        Email.text = endereco
        Picasso.get().load(photourl).into(profilePictureView)
        //----------------------------------------------------------------//
        val drawer: DrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.logged_in, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (id == R.id.nav_home) {
            //Pagina principal
        }
        if (id == R.id.nav_contact) {
            //Abrir perfil do WHERE SJ
        } else if (id == R.id.nav_manage) {
            openConfigs();
        } else if (id == R.id.nav_changePass) {
        //    openUpdatePassword()
        } else if (id == R.id.nav_deleteAccount) {
            //EXCLUIR CONTA
        }
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    // <!-- Fun -->

    fun fastAndroidNetworkReq() {

        AndroidNetworking.initialize(applicationContext)
        val progress = authenticated_progress_bar

        AndroidNetworking.get( API.BASEURL + "/companies")
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    try {
                        if (response != null) {
                            companyList.clear()
                            for (pos in 0 until response.length()){
                                var companies = response.get(pos)
                                var a = response.getJSONObject(pos)
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
                                println("Erick2 :  $topic")
                                companyList.add(topic)
                                companyAdapter.notifyDataSetChanged()
                            }
                        }
                        authenticated_recycler_view.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext,"tryCatch error:" + e.message, Toast.LENGTH_LONG).show()
                        Log.i("error1", e.message.toString())

                    }
                }
                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext, "onError :" + anError.toString(), Toast.LENGTH_LONG).show()
                    Log.i("error2", anError.toString())
                    progress.visibility = View.GONE
                    //no_conection_text.visibility = View.VISIBLE

                }
            })
    }


    fun nameSearch() {
        val progress = authenticated_progress_bar
        val nameToSearch = search_input.text
        authenticated_recycler_view.visibility = View.GONE
        progress.visibility = View.VISIBLE

        if(nameToSearch.toString() == "" || nameToSearch.isEmpty())
            //fastAndroidNetworkReq()

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
                                searchResult.__v = a.getInt("__v")
                                searchResult.dataDeCriacao = a.getString("dataDeCriacao")
                                searchResult.telefone = a.getString("telefone")
                                searchResult._id = a.getString("_id")
                                println("search :  $searchResult")
                                companyList.add(searchResult)
                                companyAdapter.notifyDataSetChanged()
                            }
                        }
                        progress.visibility = View.GONE
                        authenticated_recycler_view.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext,"tryCatch error:" + e.message, Toast.LENGTH_LONG).show()
                        Log.i("searchErro:", e.message.toString())

                    }
                }
                override fun onError(anError: ANError?) {
                    Toast.makeText(applicationContext, "onError :" + anError.toString(), Toast.LENGTH_LONG).show()
                    Log.i("searchAnError", anError.toString())
                    //no_conection_text.visibility = View.VISIBLE

                }
            })
    }
    fun openConfigs(){
        val i : Intent = Intent(this, CompanyInfoEditor::class.java)
        startActivity(i)
    }
}