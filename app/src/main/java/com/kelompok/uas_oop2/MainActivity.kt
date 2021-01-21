package com.kelompok.uas_oop2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kelompok.uas_oop2.database.TanamanDB
import com.kelompok.uas_oop2.database.Constant
import com.kelompok.uas_oop2.database.Tanaman
import com.kelompok.uas_oop2.database.Customer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { TanamanDB.getDatabase(this)}
    val database = Firebase.database
    lateinit var tanamanAdapter: TanamanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addTanaman()
        getListTanaman()
        addCustomer()
        getListCustomer()
        sync_tanaman()
        sync_customer()
    }

     fun addTanaman() {
         btn_add_tanaman.setOnClickListener{
             intentEdit(0, Constant.TYPE_CREATE)
         }
     }

    fun intentEdit(tanamanId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddTanamanActivity::class.java)
                .putExtra("intent_id", tanamanId)
                .putExtra("intent_type", intentType)
        )
    }

    fun getListTanaman(){
        btn_list_tanaman.setOnClickListener{
            startActivity(Intent(this, ListTanamanActivity::class.java))
        }
    }

    fun addCustomer() {
        btn_add_customer.setOnClickListener{
            intentEditCustomer(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEditCustomer(customerId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddCustomerActivity::class.java)
                .putExtra("intent_id", customerId)
                .putExtra("intent_type", intentType)
        )
    }

    fun getListCustomer(){
        btn_list_customer.setOnClickListener{
            startActivity(Intent(this, ListCustomer::class.java))
        }
    }

    fun sync_tanaman(){
        btnSyncTnmn.setOnClickListener {
            getDataFromFirebase()
            addDataToFirebase()
        }
    }

    private fun getDataFromFirebase(){

        val refTanaman = database.getReference("tanaman")

        refTanaman.addListenerForSingleValueEvent(object : ValueEventListener {
            var uid = ""
            var nama_tanaman = ""
            var jenis = ""
            var ukuran = ""
            var tinggi = ""
            var harga = ""

            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    uid = ds.child("id").getValue(String::class.java).toString()
                    nama_tanaman = ds.child("nama_tanaman").getValue(String::class.java).toString()
                    jenis = ds.child("jenis").getValue(String::class.java).toString()
                    jenis = ds.child("ukuran").getValue(String::class.java).toString()
                    tinggi = ds.child("tinggi").getValue(String::class.java).toString()
                    harga = ds.child("harga").getValue(String::class.java).toString()


                    CoroutineScope(Dispatchers.IO).launch {
                        if (snapshot != null) {

                            db.tanamanDao().insert(Tanaman(uid, nama_tanaman, jenis, ukuran, tinggi, harga))

                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.d("errornya", error.toString())
            }
        })
    }

    private fun addDataToFirebase(){
        CoroutineScope(Dispatchers.IO).launch {
            val tanaman =  db.tanamanDao().getTanaman()
            Log.d("ListTanamanMain", "respons : $tanaman")
            withContext(Dispatchers.Main){
                val refTanaman = database.getReference("tanaman")
                refTanaman.setValue(tanaman)
            }
        }

    }
    fun sync_customer(){
        btnSyncCstmr.setOnClickListener {
            getDataCustomerFromFirebase()
            addDataCustomerToFirebase()
        }
    }
    private fun getDataCustomerFromFirebase(){
        val refCustomer = database.getReference("customer")

        refCustomer.addListenerForSingleValueEvent(object : ValueEventListener {
            var uid = ""
            var nama = ""
            var no = ""
            var usia = ""
            var alamat = ""
            var kode = ""

            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    uid = ds.child("id").getValue(String::class.java).toString()
                    nama = ds.child("nama_customer").getValue(String::class.java).toString()
                    no = ds.child("no").getValue(String::class.java).toString()
                    usia = ds.child("usia").getValue(String::class.java).toString()
                    alamat = ds.child("alamat").getValue(String::class.java).toString()
                    kode = ds.child("tanaman_code").getValue(String::class.java).toString()


                    CoroutineScope(Dispatchers.IO).launch {
                        if (snapshot != null) {

                            db.customerDao().insert(Customer(uid, nama, no, usia, alamat, kode))

                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.d("errornya", error.toString())
            }
        })
    }
    private fun addDataCustomerToFirebase(){
        Log.d("pass2", "pass2")
        CoroutineScope(Dispatchers.IO).launch {
            val customer =  db.customerDao().getCustomer()
            Log.d("ListCustomerMain", "respons : $customer")
            withContext(Dispatchers.Main){
                val refCustomer = database.getReference("customer")
                refCustomer.setValue(customer)
            }
        }

    }
}