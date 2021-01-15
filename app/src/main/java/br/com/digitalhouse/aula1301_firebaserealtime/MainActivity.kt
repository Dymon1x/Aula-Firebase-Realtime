package br.com.digitalhouse.aula1301_firebaserealtime

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.digitalhouse.aula1301_firebaserealtime.databinding.ActivityMainBinding
import com.google.firebase.database.*
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main) // o findview nao será mais usado
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        connectDatabase() //conectando ao database nosql
        getData() // lendo o db

        bind.btnCreate.setOnClickListener {
            val prod = getProd("Carro")
            val res = saveProduct(prod)
/*
            showMsg(res)
*/

        }

        bind.btnUpdate.setOnClickListener {
            var prod = getProd("Bicicleta")
            var res = updateProd(prod)

        }

        bind.btnDelete.setOnClickListener {
            deleteProd("2")

        }

    }

    //Realiza a conexão com o database
    fun connectDatabase() {
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("products")
    }

    fun getProd(name: String): Product {
        var cat = Category(1, "office")
        return Product(name, 1, 2.0, cat)
    }

    fun saveProduct(prod: Product){
        /*var map = mutableMapOf<String, Any>()
        map["prod"] = prod*/

        /*var res = FirebaseDatabase.getInstance().reference
            .child("products")
            .child("1")
            .setValue(prod)*/

       var ref1 = reference.child("1")
           .setValue(prod)

        var ref2 = reference.child("2").setValue(prod)

        Log.i("Keys", "key1: $ref1  key2: $ref2")
/*
        return res.toString()*/

    }

    fun updateProd(prod: Product){
        var ref1 = reference.child("1")
            .setValue(prod)
    }

    fun deleteProd(id: String){
        var ref1 = reference
            .child(id).removeValue()
    }

   fun getData(){
       var listProds = arrayListOf<Product>()
       // Read from the database
       reference.addValueEventListener(object : ValueEventListener {
           override fun onDataChange(dataSnapshot: DataSnapshot) {
               // This method is called once with the initial value and again
               // whenever data at this location is updated

               dataSnapshot.children.forEach {
                   var prod =  Gson().fromJson(it.value.toString(), Product::class.java)
                   listProds.add(prod)
                   Log.i("res: ", prod.toString())
               }
           }

           override fun onCancelled(error: DatabaseError) {
               // Failed to read value
               Log.i("ERRO ", error.toString())

           }
       })
   }


    fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}