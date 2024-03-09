package com.aryanto.githubfinal.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aryanto.githubfinal.databinding.ActivitySplashBinding
import com.aryanto.githubfinal.ui.activity.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showWelcome()
    }

    private fun showWelcome() {
        val handler = Handler()
        val delay: Long = 1000

        handler.postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, delay)

        Thread {
            var progress = 0

            while (progress < 100) {
                try {
                    Thread.sleep(delay / 100)
                    progress++
                    binding.pBar.progress = progress

                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}