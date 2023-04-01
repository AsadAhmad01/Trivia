package com.goally.assignment.presentations.ui.activities

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.goally.assignment.R
import com.goally.assignment.data.base.BaseActivity
import com.goally.assignment.data.dataModels.CategoryModel
import com.goally.assignment.databinding.ActivityViewPointsByCategoryBinding
import com.goally.assignment.presentations.adapters.PointsByCategoryAdapter

class ViewPointsByCategoryActivity : BaseActivity() {

    private lateinit var binding: ActivityViewPointsByCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPointsByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setOnClickListeners()
        setAdapterValues()
    }

    private fun setOnClickListeners() {

        binding.Logout.setOnClickListener {
            finish()
        }
    }


    private fun setAdapterValues() {
        val listCat = setCategories()
        binding.recyclerViewPointsCategory.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPointsCategory.adapter = PointsByCategoryAdapter(this, listCat)
    }


    private fun setCategories(): ArrayList<String> {

        val cateList = CategoryModel.setValues()
        val listCat = ArrayList<String>()

        for (i in 0 until cateList.size) {
            listCat.add(cateList[i].name)
        }

        return listCat
    }

    override fun finish() {
        super.finish()
        this.overridePendingTransition(
            0, R.anim.slide_out_right
        )
    }
}