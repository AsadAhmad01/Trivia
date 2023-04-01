package com.goally.assignment.presentations.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goally.assignment.R
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.data.viewModels.UserViewModel
import com.goally.assignment.databinding.ActivityUpdateProfileBinding

class UpdateProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityUpdateProfileBinding
    private lateinit var userModel: UserModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        handleData()
        setViews()


    }

    private fun setViews() {

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnSaveUpdate.setOnClickListener {
            saveIntoDB()
            finish()
        }

        binding.Logout.setOnClickListener {

            finish()
        }
    }

    private fun saveIntoDB() {
        userModel.id = Singleton.userID
        userModel.userEmail = binding.editTxtUserEmail.text.toString()
        userModel.userPhone = binding.editTxtUserPhone.text.toString()
        userModel.userName = binding.editTxtUserName.text.toString()
        userModel.userPassword = binding.editTxtUserPassword.text.toString()

        userViewModel.updateUserIntoDB(userModel)
    }

    private fun setDataIntoViews() {
        binding.editTxtUserName.setText(userModel.userName)
        binding.editTxtUserEmail.setText(userModel.userEmail)
        binding.editTxtUserPhone.setText(userModel.userPhone)
        binding.editTxtUserPassword.setText(userModel.userPassword)
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(
            0, R.anim.slide_out_right
        )
    }


    private fun handleData() {
        userViewModel.callForSingleCurrent()

        getCurrentUser()

        handleError()
    }

    private fun handleError() {
        userViewModel.getErrors().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentUser() {
        userViewModel.getCurrentUser().observe(this) {
            if (it != null) {
                userModel = it
                setDataIntoViews()
            } else {
                Toast.makeText(this, "No User Found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}