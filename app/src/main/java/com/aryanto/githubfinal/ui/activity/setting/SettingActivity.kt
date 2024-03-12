package com.aryanto.githubfinal.ui.activity.setting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aryanto.githubfinal.R
import com.aryanto.githubfinal.databinding.ActivitySettingBinding
import com.aryanto.githubfinal.ui.activity.home.HomeVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val settingVM: HomeVM by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        settingVM.isDarkMode.observe(this) { isActive ->
            if (isActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }

        settingVM.isDarkMode.observe(this) {
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            settingVM.setDarkMode(isChecked)
        }

    }


}