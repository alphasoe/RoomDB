package com.myanmaritc.roomdb.repository

import androidx.lifecycle.LiveData
import com.myanmaritc.roomdb.dao.WordDao
import com.myanmaritc.roomdb.entity.Word

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    fun searchWord(word: String): LiveData<List<Word>> {
        return wordDao.getSearchWords(word)
    }

    suspend fun delete(word: String) {
        wordDao.delete(word)
    }

    suspend fun update(oldvalue: String, newvalue: String) {
        wordDao.update(oldvalue, newvalue)
    }
}