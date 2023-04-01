package com.goally.assignment.presentations.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.goally.assignment.R
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAnimations()
        setLooper()
    }

    private fun setAnimations() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.bounceb)
        binding.imgSplash.startAnimation(anim)
    }

    private fun sendToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun sendToHome() {
        val i = Intent(this, HomeActivity::class.java)
        startActivity(i)
        finish()
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun setLooper() {

        Handler(Looper.getMainLooper()).postDelayed({

            if (customPreferences.isLogin("USER")) {
                Singleton.userID = customPreferences.getValueInt("ID")
                sendToHome()
            } else {
                sendToLogin()
            }

        }, 1500)
    }


}