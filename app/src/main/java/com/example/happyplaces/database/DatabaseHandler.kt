package com.example.happyplaces.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.happyplaces.models.HappyPlaceModel
import java.lang.Exception

class DatabaseHandler(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HappyPlacesDatabase"
        private const val TABLE_HAPPY_PLACE = "HappyPlacesTable"

        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
        private const val KEY_LONGITUDE = "longitude"
        private const val KEY_LATITUDE = "latitude"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_HAPPY_PLACE_TABLE = ("CREATE TABLE "+ TABLE_HAPPY_PLACE+"( "
                + KEY_ID+" INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)"
                )

        db?.execSQL(CREATE_HAPPY_PLACE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_HAPPY_PLACE")
        onCreate(db)
    }

    fun addHappyPlace(happyPlace:HappyPlaceModel):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE,happyPlace.title)
        contentValues.put(KEY_IMAGE,happyPlace.image)
        contentValues.put(KEY_DESCRIPTION,happyPlace.description)
        contentValues.put(KEY_DATE,happyPlace.date)
        contentValues.put(KEY_LOCATION,happyPlace.location)
        contentValues.put(KEY_LATITUDE,happyPlace.latitude)
        contentValues.put(KEY_LONGITUDE,happyPlace.longitude)

        val result = db.insert(TABLE_HAPPY_PLACE,null,contentValues)
        db.close()

        return result
    }

    @SuppressLint("Range")
    fun getHappyPlacesList():ArrayList<HappyPlaceModel>{
        val happyPlaceList : ArrayList<HappyPlaceModel>  = ArrayList<HappyPlaceModel>()

        val db = this.readableDatabase
        val selectQuery = "Select * from $TABLE_HAPPY_PLACE"

        try{
            val cur:Cursor = db.rawQuery(selectQuery,null)
            while(cur.moveToNext()){

                val place = HappyPlaceModel(
                    cur.getInt(cur.getColumnIndex(KEY_ID)),
                    cur.getString(cur.getColumnIndex(KEY_TITLE)),
                    cur.getString(cur.getColumnIndex(KEY_IMAGE)),
                    cur.getString(cur.getColumnIndex(KEY_DESCRIPTION)),
                    cur.getString(cur.getColumnIndex(KEY_DATE)),
                    cur.getString(cur.getColumnIndex(KEY_LOCATION)),
                    cur.getDouble(cur.getColumnIndex(KEY_LATITUDE)),
                    cur.getDouble(cur.getColumnIndex(KEY_LONGITUDE)),
                )
                happyPlaceList.add(place)
            }

            cur.close()

        }catch (e:SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        return happyPlaceList
    }


}