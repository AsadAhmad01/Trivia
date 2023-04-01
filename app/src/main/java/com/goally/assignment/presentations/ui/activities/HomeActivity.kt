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
import com.goally.assignment.databinding.ActivityHomeBinding
import com.goally.assignment.presentations.ui.fragments.BottomSheetFragment

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var name: String = ""
    private lateinit var userModel: UserModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        dbHandler()

        setViews()
    }

    private fun setViews() {
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.layTxtStatistics.setOnClickListener {
            val i = Intent(this, StatisticsActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        binding.layTxtProfile.setOnClickListener {
            val i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.btnPlay.setOnClickListener {
            val i = Intent(this, GameStartActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.txtViewPointsByCategory.setOnClickListener {
            val i = Intent(this, ViewPointsByCategoryActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        binding.btnViewAttemptQuestions.setOnClickListener {
            val i = Intent(this, ViewAttemptedQuestionsActivity::class.java)
            startActivity(i)
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        binding.layTxtSettings.setOnClickListener {
            setBottomSheetFragment()
        }

        binding.btnQuickPlay.setOnClickListener {
            startActivity(Intent(this, QuickPlayActivity::class.java))
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onResume() {
        super.onResume()
        dbHandler()
    }

    private fun setBottomSheetFragment() {
        val bottomSheetDialog = BottomSheetFragment.newInstance()
        bottomSheetDialog.show(
            this.supportFragmentManager,
            "Bottom Sheet Dialog Fragment"
        )
        bottomSheetDialog.isCancelable = true
    }

    private fun dbHandler() {
        binding.isLoading.loadingBar.visibility = View.VISIBLE
        callForSingleUser()

        getUser()
        handleError()
    }

    private fun handleError() {
        userViewModel.getErrors().observe(this) {
            binding.isLoading.loadingBar.visibility = View.GONE
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUser() {
        userViewModel.getCurrentUser().observe(this) {
            if (it != null) {
                binding.isLoading.loadingBar.visibility = View.GONE
                userModel = it
                setData()
            } else {
                binding.isLoading.loadingBar.visibility = View.GONE
            }
        }
    }

    private fun setData() {
        val value = userModel.userPoints + userModel.userQuickPoints
        binding.txtUserName.text = userModel.userName
        name = userModel.userName

        "$value Points".also { binding.txtUserPoints.text = it }

        "Attempts (${userModel.userAttempts + userModel.userQuickAttempts})".also {
            binding.txtUserAttempts.text = it
        }
    }

    private fun callForSingleUser() {
        userViewModel.callForSingleCurrent()

    }
}