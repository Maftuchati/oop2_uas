package com.kelompok.uas_oop2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok.uas_oop2.database.TanamanDB
import com.kelompok.uas_oop2.database.Constant
import com.kelompok.uas_oop2.database.Tanaman
import kotlinx.android.synthetic.main.activity_list_tanaman.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListTanamanActivity : AppCompatActivity() {

    val db by lazy { TanamanDB.getDatabase(this)}
    lateinit var tanamanAdapter: TanamanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tanaman)
        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val tanaman =  db.tanamanDao().getTanaman()
            Log.d("ListTanamanActivity", "respons : $tanaman")
            withContext(Dispatchers.Main){
                tanamanAdapter.setData( tanaman )
            }
        }
    }

    fun intentEdit(tanamanId: String, intentType: Int){
        startActivity(
            Intent(applicationContext, AddTanamanActivity::class.java)
                .putExtra("intent_id", tanamanId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView(){
        tanamanAdapter = TanamanAdapter(arrayListOf(), object  : TanamanAdapter.onAdapterListener{
            override fun onClick(tanaman: Tanaman) {
                Toast.makeText(applicationContext, tanaman.nama_tanaman + ", " + tanaman.ukuran + ", " + tanaman.jenis, Toast.LENGTH_LONG).show()
            }

            override fun onDelete(tanaman: Tanaman) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.tanamanDao().deleteTanaman(tanaman)
                    loadData()
                }
            }

            override fun onUpdate(tanaman: Tanaman) {
                intentEdit(tanaman.id,Constant.TYPE_UPDATE)
            }

        })
        list_tanaman.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = tanamanAdapter
        }
    }
}