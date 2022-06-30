package ru.hivislav.nasaexplorer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.hivislav.nasaexplorer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, MainFragment.newInstance()).commit()
        }
    }
}