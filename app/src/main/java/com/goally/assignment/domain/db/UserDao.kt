package com.goally.assignment.domain.db

import androidx.room.*
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.utils.Singleton

@Dao
interface UserDao {


    @Query("SELECT * FROM ${Singleton.USERS}")
    fun getUsers(): List<UserModel>

    @Query("SELECT * FROM ${Singleton.USERS} WHERE U_ID  = :id ")
    fun getUser(id: Int): UserModel

    @Query("SELECT SUM(${Singleton.U_ATTEMPTS}) FROM ${Singleton.USERS} WHERE U_ID =:id")
    fun getAttempts(id: Int): Int

    @Insert
    fun addUser(userModel: UserModel)

    @Delete
    fun deleteUser(userModel: UserModel)

    @Update
    fun updateUser(userModel: UserModel)

}