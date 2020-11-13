package com.myanmaritc.roomdb.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.myanmaritc.roomdb.database.WordDatabase
import com.myanmaritc.roomdb.entity.Word
import com.myanmaritc.roomdb.repository.WordRepository
import kotlinx.coroutines.launch

class WordViewmodel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    val allWords: LiveData<List<Word>>

    init {
        val wordDao = WordDatabase.getDatabase(application, viewModelScope).wordDao()

        repository = WordRepository(wordDao)
        allWords = repository.allWords
    }

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

    fun getSearchWord(word: String): LiveData<List<Word>> {
        return repository.searchWord(word)
    }

    fun delete(word: String) = viewModelScope.launch {
        repository.delete(word)
        Log.d("Delete>>>>", "success")
    }

    fun update(oldvalue: String, newvalue: String) = viewModelScope.launch {
        repository.update(oldvalue, newvalue)
    }
}