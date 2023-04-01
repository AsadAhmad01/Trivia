package com.goally.assignment.presentations.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goally.assignment.R
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.dataModels.QuickPlayQuestions
import com.goally.assignment.data.viewModels.QuestionViewModel
import com.goally.assignment.databinding.ActivityViewAttemptedQuestionsBinding
import com.goally.assignment.domain.db.QuestionAttempted
import com.goally.assignment.presentations.adapters.AttemptQuestionAdapter
import com.goally.assignment.presentations.adapters.AttemptQuickQuestionAdapter

class ViewAttemptedQuestionsActivity : BaseActivity() {

    private lateinit var binding: ActivityViewAttemptedQuestionsBinding
    private lateinit var questionsViewModel: QuestionViewModel
    private lateinit var normalQuestionList: List<QuestionAttempted>
    private lateinit var quickQuestionList: List<QuickPlayQuestions>
    private lateinit var adapterNormal: AttemptQuestionAdapter
    private lateinit var adapterQuick: AttemptQuickQuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAttemptedQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        questionsViewModel = ViewModelProvider(this)[QuestionViewModel::class.java]

        initData()
        initViews()
    }

    private fun initData() {
        questionsViewModel.callForAllNormalPlayQuestions()
        questionsViewModel.callForAllQuickPlayQuestions()


        handleData()
        handleError()
    }

    private fun handleError() {
        questionsViewModel.getErrorsReported().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleData() {
        normalQuestionsData()
        quickQuestionsData()
    }

    private fun normalQuestionsData() {
        questionsViewModel.getNormalPlayQuestionsList().observe(this) {
            if (it != null) {
                normalQuestionList = it
                setListIntoRecyclerView()
            }
        }
    }

    private fun quickQuestionsData() {
        questionsViewModel.getQuickPlayQuestionsList().observe(this) {
            if (it != null) {
                quickQuestionList = it
            }
        }
    }

    private fun initViews() {
        setOnclickListeners()
    }

    private fun setOnclickListeners() {

        binding.Logout.setOnClickListener {
            finish()
        }

        binding.conTxt.setOnClickListener {
            binding.conTxt.background =
                ContextCompat.getDrawable(this, R.drawable.button_color)
            binding.quickTxt.background =
                ContextCompat.getDrawable(this, R.drawable.cornors)
            setListIntoRecyclerView()
        }


        binding.quickTxt.setOnClickListener {
            binding.quickTxt.background =
                ContextCompat.getDrawable(this, R.drawable.button_color)
            binding.conTxt.background =
                ContextCompat.getDrawable(this, R.drawable.cornors)
            setQuickListIntoRecyclerView()
        }

    }

    private fun setListIntoRecyclerView() {
        binding.recyclerViewAttemptQuestions.layoutManager = LinearLayoutManager(this)
        adapterNormal = AttemptQuestionAdapter(this, normalQuestionList.reversed())
        binding.recyclerViewAttemptQuestions.adapter = adapterNormal

    }

    private fun setQuickListIntoRecyclerView() {
        binding.recyclerViewAttemptQuestions.layoutManager = LinearLayoutManager(this)
        adapterQuick = AttemptQuickQuestionAdapter(this, quickQuestionList.reversed())
        binding.recyclerViewAttemptQuestions.adapter = adapterQuick
    }


    override fun finish() {
        super.finish()
        this.overridePendingTransition(
            0, R.anim.slide_out_right
        )
    }
}