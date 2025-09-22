package com.fpstudio.stretchreminder.data.local

import androidx.room.*
import com.fpstudio.stretchreminder.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    suspend fun getLastUser(): User?
}
