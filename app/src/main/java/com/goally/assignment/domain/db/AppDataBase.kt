package com.goally.assignment.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arhamsoft.online.trivizia.db.QuestionDAO
import com.goally.assignment.data.dataModels.QuickPlayQuestions
import com.goally.assignment.data.dataModels.UserModel
import com.goally.assignment.data.utils.Singleton

//QuestionAttempted::class, UserModel::class, QuickPlayQuestions::class

@Database(
    entities = [QuestionAttempted::class, UserModel::class, QuickPlayQuestions::class], version = 2
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun questionDao(): QuestionDAO?
    abstract fun userDao(): UserDao?
    abstract fun quickPlayDao(): QuickPlayDAO?

    companion object {
        private var INSTENCE: AppDataBase? = null
        fun getAppDB(context: Context): AppDataBase? {
            if (INSTENCE == null) {
                INSTENCE = Room.databaseBuilder(context, AppDataBase::class.java, Singleton.DB)
                    .build()
            }

            return INSTENCE
        }
    }


}