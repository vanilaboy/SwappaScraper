package com.example.swappascraper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun computerDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context?): ProductDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            if (context != null)
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDatabase::class.java,
                        "product_database")
                        .fallbackToDestructiveMigration()
//                        .addCallback(object : RoomDatabase.Callback() {
//                            override fun onOpen(db: SupportSQLiteDatabase) {
//                                super.onOpen(db)
//                                db.execSQL("DELETE FROM ${Product.TABLE_NAME}")
//                            }
//                        })
                        .build()
                    INSTANCE = instance
                    return instance
                }
            else
                throw RuntimeException("DB not initializer")
        }
    }

}