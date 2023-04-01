package com.goally.assignment.data.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goally.assignment.data.preferences.CustomSharedPref
import com.goally.assignment.data.preferences.UserPreferences

open class BaseFragment : Fragment() {

    lateinit var userPreferences: UserPreferences
    lateinit var customSharedPref: CustomSharedPref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferences(requireContext())
        customSharedPref = CustomSharedPref(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

    }
}