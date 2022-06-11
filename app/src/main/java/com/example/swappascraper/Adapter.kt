package com.example.swappascraper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val longClick: (item: Product) -> Unit) : RecyclerView.Adapter<Adapter.Holder>() {

    var list: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_element, parent, false), longClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        list.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }




    class Holder(itemView: View, callback: (item: Product) -> Unit): RecyclerView.ViewHolder(itemView) {
        private val nameField = itemView.findViewById<TextView>(R.id.productName)
        private val priceField = itemView.findViewById<TextView>(R.id.minimumPrice)

        private var current: Product? = null

        init {
            itemView.setOnLongClickListener {
                current?.let { it1 -> callback(it1) }
                return@setOnLongClickListener true
            }
        }

        fun bind(product: Product) {
            nameField.text = product.name
            priceField.text = "$${product.minPrice}"
            current = product
        }
    }
}