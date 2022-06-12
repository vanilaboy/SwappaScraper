package com.example.swappascraper

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddProductDialog : DialogFragment() {

    private var editTextLink: TextView? = null
    private var editTextCost: TextView? = null
    private var editTextName: TextView? = null

    private var lock: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var result: Dialog? = null

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.add_product_dialog, null, false)

        editTextLink = view.findViewById(R.id.addDialogUrl)
        editTextCost = view.findViewById(R.id.addDialogCost)
        editTextName = view.findViewById(R.id.addDialogName)

        arguments?.let { arg ->
            arg.getString("name")?.let { editTextName?.text = it }
            arg.getString("url")?.let { editTextLink?.text = it }
            arg.getInt("cost")?.let { editTextCost?.text = it.toString() }
        }

//        editTextCost?.doAfterTextChanged { text ->
//            if (lock)
//                return@doAfterTextChanged
//
//            text?.let { editable ->
//                val cost = editable.toString().replace("$", "")
//                lock = true
//                editTextCost?.setText(
//                    if (cost.isNotEmpty()) "$$cost"
//                    else ""
//                )
//                lock = false
//            }
//        }


        result = MaterialAlertDialogBuilder(requireContext()).setView(view)
            .setPositiveButton("Save") { p0, p1 ->
                try {
                    val product = Product(
                        editTextName?.text.toString(),
                        editTextCost?.text.toString().toInt(),
                        editTextLink?.text.toString()
                    )
                    GlobalScope.launch(Dispatchers.IO) {
                        ProductDatabase.getDatabase(requireContext()).computerDao().add(product)
                    }
                } catch (e: Exception) {}
                result?.dismiss()
            }
            .setNegativeButton("Cancel") { p0, p1 ->
                result?.dismiss()
            }
            .create()
        return result
    }

    companion object {
        fun newInstance(product: Product? = null) : AddProductDialog {
            return AddProductDialog().apply {
                if (product != null)
                    arguments = Bundle().apply {
                        putString("name", product.name)
                        putString("url", product.url)
                        putInt("cost", product.minPrice)
                    }
            }
        }
    }

}