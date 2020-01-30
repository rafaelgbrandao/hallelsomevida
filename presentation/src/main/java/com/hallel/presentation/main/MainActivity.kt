package com.hallel.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hallel.presentation.R
import com.hallel.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
