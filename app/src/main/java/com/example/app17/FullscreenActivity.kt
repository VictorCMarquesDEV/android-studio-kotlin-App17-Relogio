package com.example.app17

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import com.example.app17.databinding.ActivityFullscreenBinding

// App17: Relógio

class FullscreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityFullscreenBinding
    private var isChecked = true
    // private var isView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(android.view.WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val bateriaReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    val nivel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    // Toast.makeText(applicationContext, nivel.toString(), Toast.LENGTH_SHORT).show()
                    binding.textBateria.text = "${nivel.toString()}%"
                }
            }
        }

        registerReceiver(bateriaReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        binding.checkBateria.setOnClickListener {
            if (isChecked) {
                isChecked = false
                binding.checkBateria.isChecked = false
                binding.textBateria.visibility = View.GONE
            } else {
                isChecked = true
                binding.checkBateria.isChecked = true
                binding.textBateria.visibility = View.VISIBLE
            }
        }

        binding.layoutMenu.animate().translationY(500F)

        binding.imagePrefer.setOnClickListener {
            /*if (!isView){
                isView = true
                binding.layoutMenu.visibility = View.VISIBLE
            }
             */
            binding.layoutMenu.visibility = View.VISIBLE
            binding.layoutMenu.animate().translationY(0F).duration =
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }

        binding.imageCancel.setOnClickListener {
            /*if (isView){
                isView = false
                binding.layoutMenu.visibility = View.GONE
            }
             */
            binding.layoutMenu.animate().translationY(binding.layoutMenu.measuredHeight.toFloat()).duration =
                resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }
    }
}
