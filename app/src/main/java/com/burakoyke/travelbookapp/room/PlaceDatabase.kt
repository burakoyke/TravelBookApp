package com.burakoyke.travelbookapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.burakoyke.travelbookapp.model.Place

@Database(entities = [Place::class], version = 1)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}