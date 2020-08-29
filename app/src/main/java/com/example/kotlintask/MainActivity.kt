package com.example.kotlintask

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.database.*
import com.example.kotlintask.MainActivity as KotlintaskMainActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gunler = listOf("1.gün", "2.gün", "3.gün", "4.gün", "5.gün", "6.gün", "7.gün")
        val mgunler = findViewById<ListView>(R.id.gunler)
        val adapter = ArrayAdapter<String>(this, R.layout.listviewitem, gunler)
        mgunler.adapter = adapter
        var i: Int
        var j: Int
        mgunler.setOnItemClickListener { parent, view, position, id ->
            seanslistele(position)
        }
    }

    fun seanslistele(i: Int) {
        val seanslar = listOf("Sabah dersi", "Öğlen dersi")
        val mseanslar = findViewById<ListView>(R.id.gunler)
        val adapter = ArrayAdapter<String>(this, R.layout.listviewitem, seanslar)
        mseanslar.adapter = adapter
        mseanslar.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                read(i, 0)
            } else if (position == 1) {
                read(i, 1)
            }
        }
    }

    fun read(i: Int, j: Int) {
        var link: Uri
        var ref = FirebaseDatabase.getInstance().getReference(i.toString()).child(j.toString())
        val menuListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var s: String = dataSnapshot.getValue().toString()
                link = Uri.parse(s)
                val intent = Intent(Intent.ACTION_VIEW, link)
                if (s.isNotEmpty()) startActivity(intent)
                else Toast.makeText(this@MainActivity, "Ders işlemediniz", Toast.LENGTH_LONG).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        ref.addListenerForSingleValueEvent(menuListener)
    }

    override fun onBackPressed() {
        val intent = Intent(this, KotlintaskMainActivity::class.java)
        // start your next activity
        startActivity(intent)
    }
}