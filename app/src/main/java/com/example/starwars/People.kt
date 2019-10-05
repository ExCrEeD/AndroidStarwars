package com.example.starwars

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_people.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class People : AppCompatActivity() {

    val listPeoples: MutableList<JSONObject> = ArrayList()
    var indice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)

        txt_birthyear.setText("cargando")
        getPeople(object :resultsCallbacks{
            override fun getJSon(json: JSONObject) {
                val results = json.getJSONArray("results")
                for (i in 0 until results.length() - 1)
                {
                    listPeoples.add(results.get(i) as JSONObject)
                }
                txt_birthyear.setText("")
                cargardatos()
            }
        })

    }

    fun cargardatos(){
        Log.v("hqwe",indice.toString())
        if(indice==0)
            btn_atras.setVisibility(View.INVISIBLE);
        else
            btn_atras.setVisibility(View.VISIBLE);


        if(indice == listPeoples.size -1)
            btn_siguiente.setVisibility(View.INVISIBLE);
        else
            btn_siguiente.setVisibility(View.VISIBLE);

        txt_name.setText(listPeoples[indice].getString("name"))
        txt_birthyear.setText(listPeoples[indice].getString("birth_year"))
        txt_eyecolor.setText(listPeoples[indice].getString("eye_color"))
        txt_hairColor.setText(listPeoples[indice].getString("hair_color"))
        txt_skincolor.setText(listPeoples[indice].getString("skin_color"))
        txt_gender.setText(listPeoples[indice].getString("gender"))
        txt_height.setText(listPeoples[indice].getString("height"))
        txt_mass.setText(listPeoples[indice].getString("mass"))
    }

    public fun atras(view: View)
    {
        indice -=1
        cargardatos()
    }
    public fun siguiente(view: View)
    {
        indice +=1
        cargardatos()
    }
    interface resultsCallbacks {
        fun getJSon(json: JSONObject)
    }
    fun getPeople(callback:resultsCallbacks) {
        val retrofit : Retrofit = Retrofit.Builder().
            baseUrl("https://swapi.co/api/").
            addConverterFactory(GsonConverterFactory.create()).
            build()

        val service = retrofit.create(ApiService::class.java)

        service.getPeople().enqueue(object :Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful() && response.body() != null) {
                    val jsonQuestions =  JSONObject(response.body().toString());
                     callback.getJSon(jsonQuestions)
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t?.printStackTrace()
            }
        })
    }

}

