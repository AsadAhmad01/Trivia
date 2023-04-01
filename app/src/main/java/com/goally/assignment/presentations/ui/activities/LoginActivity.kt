package com.goally.assignment.presentations.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goally.assignment.R
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.data.viewModels.UserViewModel
import com.goally.assignment.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var list: ArrayList<UserModel>? = ArrayList()
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        handleData()
        initViews()
    }

    private fun handleData() {
        binding.isLoading.loadingBar.visibility = View.VISIBLE
        callForUsers()
        getUsers()
        getError()
    }

    private fun getError() {
        userViewModel.getErrors().observe(this) {
            binding.isLoading.loadingBar.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUsers() {
        userViewModel.getUsers().observe(this) {
            if (!it.isNullOrEmpty()) {
                binding.isLoading.loadingBar.visibility = View.GONE
                list = it
            } else {
                binding.isLoading.loadingBar.visibility = View.GONE
                Toast.makeText(this, "User list Empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callForUsers() {
        userViewModel.callForUsers()
    }

    private fun initViews() {
        initListeners()
    }

    private fun initListeners() {
        binding.signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.btnPlay.setOnClickListener {
            checkDataFromDatabase()
        }
    }

    private fun checkDataFromDatabase() {

        if (binding.editTxtUserName.text.isNullOrEmpty()) {
            binding.editTxtUserName.error = "Please Enter Valid Email!"
        } else if (binding.editTxtUserPassword.text.isNullOrEmpty()) {
            binding.editTxtUserPassword.error = "Please Check your Password"
        } else {
            // after All Ok
            if (!list.isNullOrEmpty()) {
                for (i in 0 until list!!.size) {

                    if (list!![i].userEmail == binding.editTxtUserName.text.toString() && list!![i].userPassword == binding.editTxtUserPassword.text.toString()) {

                        customPreferences.save("ID", list!![i].id)
                        Singleton.userID = list!![i].id
                        customPreferences.saveLogin("USER", true)

                        startActivity(Intent(this, HomeActivity::class.java))
                        this.overridePendingTransition(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                        )
                        finish()
                        break
                    } else {
                        Toast.makeText(
                            this,
                            "Username and Password s incorrect!!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            } else {
                Toast.makeText(this, "No User in Database!", Toast.LENGTH_SHORT).show()
                binding.isLoading.loadingBar.visibility = View.GONE
            }
        }

    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(0, R.anim.slide_out_right)
    }


}