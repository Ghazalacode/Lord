package com.example.hossam.lord

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ProductDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        val actionBar = supportActionBar
        actionBar!!.hide()
    }
}
