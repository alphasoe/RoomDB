package com.myanmaritc.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myanmaritc.roomdb.entity.Word

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAllWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)  //suspend to change data

    @Query("SELECT * FROM word_table WHERE word LIKE :word ORDER BY word ASC")
    fun getSearchWords(word: String): LiveData<List<Word>>

    @Query("DELETE FROM word_table WHERE word = :word")
    suspend fun delete(word: String)

    @Query("UPDATE word_table SET word = :newvalue WHERE word = :oldvalue")
    suspend fun update(oldvalue: String, newvalue: String)
}