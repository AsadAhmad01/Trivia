package com.goally.assignment.data.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goally.assignment.data.base.App
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.utils.Singleton
import com.goally.assignment.domain.db.AppDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private var loginLiveData: MutableLiveData<ArrayList<UserModel>> = MutableLiveData()
    private var userModelLiveData: MutableLiveData<UserModel> = MutableLiveData()
    private var errorModel: MutableLiveData<String> = MutableLiveData()
    private var db = AppDataBase.getAppDB(App.context)


    fun saveUserIntoDB(model: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db?.userDao()?.addUser(model)
            } catch (e: Exception) {
                errorModel.postValue(e.message)
            }
        }
    }

    fun updateUserIntoDB(model: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db?.userDao()?.updateUser(model)
            } catch (e: Exception) {
                errorModel.postValue(e.message)
            }
        }
    }

    fun callForSingleCurrent() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userModelLiveData.postValue(db?.userDao()?.getUser(Singleton.userID))
            } catch (e: Exception) {
                errorModel.postValue(e.message)
            }
        }
    }


    fun callForUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val usersList = db?.userDao()?.getUsers() as ArrayList
                loginLiveData.postValue(usersList)
            } catch (e: Exception) {
                errorModel.postValue(e.message)
            }
        }
    }

    fun getUsers(): MutableLiveData<ArrayList<UserModel>> {
        return loginLiveData
    }

    fun getCurrentUser(): MutableLiveData<UserModel> {
        return userModelLiveData
    }


    fun getErrors(): MutableLiveData<String> {
        return errorModel
    }

}