package com.example.swappascraper

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Product.TABLE_NAME)
class Product(
    @PrimaryKey @NonNull @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = MIN_PRICE) var minPrice: Int = Int.MAX_VALUE
) {

    companion object {
        const val TABLE_NAME = "product_table"
        const val NAME = "name"
        const val MIN_PRICE = "min_price"
    }
}