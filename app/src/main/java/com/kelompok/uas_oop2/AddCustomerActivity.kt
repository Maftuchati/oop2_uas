package com.kelompok.uas_oop2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kelompok.uas_oop2.database.TanamanDB
import com.kelompok.uas_oop2.database.Constant
import com.kelompok.uas_oop2.database.Customer
import kotlinx.android.synthetic.main.activity_add_tanaman_.*
import kotlinx.android.synthetic.main.activity_add_customer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddCustomerActivity : AppCompatActivity() {
    val db by lazy { TanamanDB.getDatabase(this)}
    private var customerId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        addCustomer()
        setupView()
    }
    fun setupView(){
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                btn_update_customer.visibility = View.GONE
            }
            Constant.TYPE_UPDATE -> {
                btn_create_customer.visibility = View.GONE
                getCustomer()
            }
        }
    }

    fun addCustomer(){
        btn_create_customer.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val uuid : String = UUID.randomUUID().toString()
                db.customerDao().insert(
                    Customer(uuid, edtNameCustomer.text.toString(), edtUkuranTanaman.text.toString(), edtTelp.text.toString(), edtAlamat.text.toString(), edtCodeCustomer.text.toString())
                )
                finish()
            }
        }
        btn_update_customer.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.customerDao().updateCustomer(
                    Customer(customerId, edtNameCustomer.text.toString(), edtUmurCustomer.text.toString(), edtTelp.text.toString(), edtAlamat.text.toString(), edtCodeCustomer.text.toString())

                )
                finish()
            }
        }
    }
    fun getCustomer(){
        customerId = intent.getStringExtra("intent_id",).toString()
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.customerDao().getCustomerId(customerId)[0]
            edtNameCustomer.setText(data.nama_customer)
            edtUmurCustomer.setText(data.umur)
            edtTelp.setText(data.no_hp)
            edtAlamat.setText(data.alamat)
            edtCodeCustomer.setText(data.kode_customer)
        }
    }
}
