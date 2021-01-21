package com.kelompok.uas_oop2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompok.uas_oop2.database.Customer
import kotlinx.android.synthetic.main.adapter_tanaman.view.*

class CustomerAdapter (private val customer: ArrayList<Customer>, private val listener: onAdapterListener) :
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

        class CustomerViewHolder(val view: View) : RecyclerView.ViewHolder(view)

        fun setData(list: List<Customer>){
            customer.clear()
            customer.addAll(list)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
            return CustomerViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.adapter_tanaman, parent,false)
            )
        }

        override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
            val h = customer[position]
            holder.view.text_nama.text = h.nama_customer
            holder.view.text_nama.setOnClickListener {
                listener.onClick(h)
            }
            holder.view.icon_delete.setOnClickListener {
                listener.onDelete(h)
            }
            holder.view.icon_edit.setOnClickListener {
                listener.onUpdate(h)
            }
        }

        override fun getItemCount(): Int {
            Log.d("count", customer.size.toString())
            return customer.size
        }

        interface onAdapterListener{
            fun onClick(customer: Customer)
            fun onDelete(customer: Customer)
            fun onUpdate(customer: Customer)
        }
}