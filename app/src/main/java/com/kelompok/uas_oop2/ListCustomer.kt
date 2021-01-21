package com.kelompok.uas_oop2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok.uas_oop2.database.TanamanDB
import com.kelompok.uas_oop2.database.Constant
import com.kelompok.uas_oop2.database.Customer
import kotlinx.android.synthetic.main.activity_list_customer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListCustomer : AppCompatActivity() {
    val db by lazy { TanamanDB.getDatabase(this)}
    lateinit var customerAdapter: CustomerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_customer)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }
    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val customer = db.customerDao().getCustomer()
            Log.d("ListCustomerActivity", "data customer : $customer")
            withContext(Dispatchers.Main) {
                customerAdapter.setData(customer)
            }
        }
    }
    fun intentEdit(customerId: String, intentType: Int){
        startActivity(
            Intent(applicationContext, AddCustomerActivity::class.java)
                .putExtra("intent_id", customerId)
                .putExtra("intent_type", intentType)
        )
    }
    private fun setupRecyclerView(){
        customerAdapter = CustomerAdapter(arrayListOf(), object  : CustomerAdapter.onAdapterListener{
            override fun onClick(customer: Customer) {
                Toast.makeText(applicationContext, customer.nama_customer + ", " + customer.umur + ", " + customer.alamat, Toast.LENGTH_LONG).show()
            }

            override fun onDelete(customer: Customer) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.customerDao().deleteCustomer(customer)
                    loadData()
                }
            }
            override fun onUpdate(customer: Customer) {
                intentEdit(customer.id, Constant.TYPE_UPDATE)
            }
        })
        list_customer.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = customerAdapter
        }
    }
}