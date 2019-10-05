package com.example.starwars

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


     fun showPlanets(view: View){
        val intent = Intent(this, Planets::class.java)
        startActivity(intent)
    }
    fun showPeople(view: View)
    {
        val intent = Intent(this, People::class.java)
        startActivity(intent)
    }
}
