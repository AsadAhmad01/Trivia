package com.goally.assignment.data.dataModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goally.assignment.data.utils.Singleton
import java.io.Serializable

@Entity(tableName = Singleton.USERS)
class UserModel : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Singleton.U_ID)
    var id: Int = 0

    @ColumnInfo(name = Singleton.U_NAME)
    var userName: String = ""

    @ColumnInfo(name = Singleton.U_EMAIL)
    var userEmail: String = ""

    @ColumnInfo(name = Singleton.U_PHONE)
    var userPhone: String = ""

    @ColumnInfo(name = Singleton.U_PASSWORD)
    var userPassword: String = ""

    @ColumnInfo(name = Singleton.U_ATTEMPTS)
    var userAttempts: Int = 0

    @ColumnInfo(name = Singleton.U_POINTS)
    var userPoints: Int = 0

    @ColumnInfo(name = Singleton.U_QUICK_ATTEMPTS)
    var userQuickAttempts: Int = 0

    @ColumnInfo(name = Singleton.U_QUICK_POINTS)
    var userQuickPoints: Int = 0

}