package com.goally.assignment.presentations.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goally.assignment.R
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.viewModels.UserViewModel
import com.goally.assignment.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var list: ArrayList<UserModel>? = ArrayList()
    private var emailCheck: Boolean = false
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
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
        binding.btnPlay.setOnClickListener {
            binding.isLoading.loadingBar.visibility = View.VISIBLE
            saveDataIntoDB()
        }

        binding.loginTxtText.setOnClickListener {
            finish()
        }
    }

    private fun saveDataIntoDB() {

        if (binding.editTxtUserName.text.isNullOrEmpty()) {
            binding.isLoading.loadingBar.visibility = View.GONE
            binding.editTxtUserName.error = "Please Enter Username"
        } else if (binding.editTxtUserEmail.text.isNullOrEmpty()) {
            binding.isLoading.loadingBar.visibility = View.GONE
            binding.editTxtUserEmail.error = "Please Enter Email"
        } else if (binding.editTxtUserPhone.text.isNullOrEmpty()) {
            binding.isLoading.loadingBar.visibility = View.GONE
            binding.editTxtUserPhone.error = "Please Enter Phone number"
        } else if (binding.editTxtUserPassword.text.isNullOrEmpty()) {
            binding.isLoading.loadingBar.visibility = View.GONE
            binding.editTxtUserPassword.error = "Please Enter Password"
        } else if (binding.editTxtUserPasswordConfirm.text.isNullOrEmpty()) {
            binding.isLoading.loadingBar.visibility = View.GONE
            binding.editTxtUserPasswordConfirm.error = "Please Confirm Password"
        } else {

            if (checkEmail()) {
                if (binding.editTxtUserPassword.text.toString() == binding.editTxtUserPasswordConfirm.text.toString()) {
                    val model = UserModel()
                    model.userName = binding.editTxtUserName.text.toString()
                    model.userEmail = binding.editTxtUserEmail.text.toString()
                    model.userPhone = binding.editTxtUserPhone.text.toString()
                    model.userPassword = binding.editTxtUserPassword.text.toString()

                    userViewModel.saveUserIntoDB(model)
                    binding.isLoading.loadingBar.visibility = View.GONE
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this, "Password not matched!", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.isLoading.loadingBar.visibility = View.GONE
                binding.editTxtUserEmail.error = "Email already register! Try another one!"
            }
        }

    }


    override fun finish() {
        super.finish()
        this.overridePendingTransition(0, R.anim.slide_out_right)
    }

    private fun checkEmail(): Boolean {
        if (!list.isNullOrEmpty()) {
            for (i in 0 until list!!.size) {
                emailCheck = binding.editTxtUserEmail.text.toString() != list!![i].userEmail
            }
        } else
            emailCheck = true
        return emailCheck
    }

}