package com.kelompok.uas_oop2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompok.uas_oop2.database.Tanaman
import kotlinx.android.synthetic.main.adapter_tanaman.view.*

class TanamanAdapter (private val tanaman: ArrayList<Tanaman>, private val listener: onAdapterListener) :
    RecyclerView.Adapter<TanamanAdapter.TanamanViewHolder>() {

    class TanamanViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Tanaman>){
        tanaman.clear()
        tanaman.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TanamanViewHolder {
        return TanamanViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_tanaman, parent,false)
        )
    }

    override fun onBindViewHolder(holder: TanamanViewHolder, position: Int) {
        val h = tanaman[position]
        holder.view.text_nama.text = h.nama_tanaman
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
        Log.d("count", tanaman.size.toString())
        return tanaman.size
    }

    interface onAdapterListener{
        fun onClick(tanaman: Tanaman)
        fun onDelete(tanaman: Tanaman)
        fun onUpdate(tanaman: Tanaman)
    }

}