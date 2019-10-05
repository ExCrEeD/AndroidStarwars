package com.example.starwars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_people.*
import kotlinx.android.synthetic.main.activity_people.btn_atras
import kotlinx.android.synthetic.main.activity_people.btn_siguiente
import kotlinx.android.synthetic.main.activity_people.txt_name
import kotlinx.android.synthetic.main.activity_planets.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Planets : AppCompatActivity() {
    val listPlanets: MutableList<JSONObject> = ArrayList()
    var indice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planets)
        getPlanets(object : resultsCallbacks {
            override fun getJSon(json: JSONObject) {
                val results = json.getJSONArray("results")
                for (i in 0 until results.length() - 1)
                {
                    listPlanets.add(results.get(i) as JSONObject)
                }
                cargardatos()
            }
        })
    }

    fun cargardatos()
    {
        if(indice==0)
            btn_atras.setVisibility(View.INVISIBLE);
        else
            btn_atras.setVisibility(View.VISIBLE);


        if(indice == listPlanets.size -1)
            btn_siguiente.setVisibility(View.INVISIBLE);
        else
            btn_siguiente.setVisibility(View.VISIBLE);

        txt_name.setText(listPlanets[indice].getString("name"))
        txt_rotation_period.setText(listPlanets[indice].getString("rotation_period"))
        txt_orbital_period.setText(listPlanets[indice].getString("orbital_period"))
        txt_diameter.setText(listPlanets[indice].getString("diameter"))
        txt_climate.setText(listPlanets[indice].getString("climate"))
        txt_gravity.setText(listPlanets[indice].getString("gravity"))
        txt_terrain.setText(listPlanets[indice].getString("terrain"))
        txt_surface_Water.setText(listPlanets[indice].getString("surface_water"))
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
    fun getPlanets(callback:resultsCallbacks) {
        val retrofit : Retrofit = Retrofit.Builder().
            baseUrl("https://swapi.co/api/").
            addConverterFactory(GsonConverterFactory.create()).
            build()

        val service = retrofit.create(ApiService::class.java)

        service.getPlanets().enqueue(object : Callback<JsonObject> {
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
