package com.example.swappascraper

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = Adapter() { item ->
        showDeleteDialog(item)
    }
    private val database: ProductDatabase = ProductDatabase.getDatabase(this.applicationContext)

    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = floating_action_button
        fab.setOnClickListener {

        }

        recyclerView = recycler
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        database.computerDao().getAll().observe(this) {
            adapter.list = it as ArrayList<Product> /* = java.util.ArrayList<com.example.swappascraper.Product> */
            adapter.notifyDataSetChanged()
        }


    }

    private fun showDeleteDialog(item: Product) {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Delete?")
            .setMessage("Do you want delete this product?")
            .setPositiveButton("Yes"
            ) { p0, p1 ->
                GlobalScope.launch(Dispatchers.IO) {
                    database.computerDao().delete(item)
                }
                p0?.dismiss() }
            .setNegativeButton("No"
            ) { p0, p1 -> p0?.dismiss() }
    }
}