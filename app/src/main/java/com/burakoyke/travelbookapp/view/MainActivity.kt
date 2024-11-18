package com.burakoyke.travelbookapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.burakoyke.travelbookapp.R
import com.burakoyke.travelbookapp.databinding.ActivityMainBinding
import com.burakoyke.travelbookapp.model.Place
import com.burakoyke.travelbookapp.room.PlaceDatabase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mDisposable = CompositeDisposable()
    private lateinit var places: ArrayList<Place>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setTitle("Travel Book")
        setSupportActionBar( binding.toolbar)
        places = ArrayList()

        val db = Room.databaseBuilder(applicationContext, PlaceDatabase::class.java, "Places")
            //.allowMainThreadQueries()
            .build()

        val placeDao = db.placeDao()

        mDisposable.add(placeDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

    }

    private fun handleResponse(placeList: List<Place>) {
        binding.rv.layoutManager = LinearLayoutManager(this)
        val placeAdapter = PlaceAdapter(placeList)
        binding.rv.adapter = placeAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.place_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("info","new")
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}