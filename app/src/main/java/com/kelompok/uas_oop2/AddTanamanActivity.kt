package com.kelompok.uas_oop2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kelompok.uas_oop2.database.TanamanDB
import com.kelompok.uas_oop2.database.Constant
import com.kelompok.uas_oop2.database.Tanaman
import kotlinx.android.synthetic.main.activity_add_tanaman_.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddTanamanActivity : AppCompatActivity() {

    val db by lazy {TanamanDB.getDatabase(this)}
    private var tanamanId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tanaman_)
        addTanaman()
        setupView()


    }

    fun setupView(){
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                btn_update_tanaman.visibility = View.GONE
            }
            Constant.TYPE_UPDATE -> {
                btn_create_tanaman.visibility = View.GONE
                getTanaman()
            }
        }
    }

    fun addTanaman(){
        btn_create_tanaman.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val uuid : String = UUID.randomUUID().toString()
                db.tanamanDao().insert(
                    Tanaman(uuid, edtNamaTanaman.text.toString(), edtJenisTanaman.text.toString(), edtHargaTanaman.text.toString(), edtUkuranTanaman.text.toString(), edtTinggiTanaman.text.toString())

                )
                finish()
            }
        }
        btn_update_tanaman.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.tanamanDao().updateTanaman(
                    Tanaman(tanamanId, edtNamaTanaman.text.toString(), edtJenisTanaman.text.toString(), edtHargaTanaman.text.toString(), edtUkuranTanaman.text.toString(), edtTinggiTanaman.text.toString())

                )
                finish()
            }
        }
    }

    fun getTanaman(){
        tanamanId = intent.getStringExtra("intent_id", ).toString()
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.tanamanDao().getTanamanId(tanamanId)[0]
            edtNamaTanaman.setText(data.nama_tanaman)
            edtHargaTanaman.setText(data.harga)
            edtTinggiTanaman.setText(data.tinggi)
            edtUkuranTanaman.setText(data.ukuran)
            edtJenisTanaman.setText(data.jenis)
        }
    }

}