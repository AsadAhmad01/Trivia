package com.goally.assignment.presentations.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.databinding.SingleItemCategoryPointsBinding
import com.goally.assignment.domain.db.AppDataBase


class PointsByCategoryAdapter(
    val context: Context,
    private val list: ArrayList<String>
) : RecyclerView.Adapter<PointsByCategoryAdapter.PointsViewHolder>() {

    private lateinit var binding: SingleItemCategoryPointsBinding

    class PointsViewHolder(val bind: SingleItemCategoryPointsBinding) :
        RecyclerView.ViewHolder(bind.root) {


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PointsViewHolder {

        binding = SingleItemCategoryPointsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PointsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PointsViewHolder, position: Int) {


        val thread = Thread {
            val points =
                AppDataBase.getAppDB(context)?.questionDao()
                    ?.getPointsByCategories(list[position], Singleton.userID)
            "$points Points".also { holder.bind.txtCategoryPoints.text = it }
        }

        thread.start()
        thread.join()

        holder.bind.txtCategoryName.text = list[position]

    }

    override fun getItemCount(): Int {
        return list.size
    }
}