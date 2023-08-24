package com.xplor.android.challenge.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xplor.android.challenge.App
import com.xplor.android.challenge.databinding.ActivityMainBinding
import com.xplor.android.challenge.ui.compose.ComposeActivity
import com.xplor.android.challenge.ui.xml.XmlListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.xmlButton.setOnClickListener {
            val intent = Intent(this, XmlListActivity::class.java)
            startActivity(intent)
        }

        binding.composeButton.setOnClickListener {
            val intent = Intent(this, ComposeActivity::class.java)
            startActivity(intent)
        }
    }
}
