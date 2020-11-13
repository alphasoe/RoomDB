package com.myanmaritc.roomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myanmaritc.roomdb.adapter.WordAdapter
import com.myanmaritc.roomdb.entity.Word
import com.myanmaritc.roomdb.viewmodel.WordViewmodel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WordAdapter.OnClickListener {
    private val wordAdapter: WordAdapter = WordAdapter()

    private lateinit var wordViewmodel: WordViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordViewmodel = ViewModelProvider(this).get(WordViewmodel::class.java)

//        val wordAdapter = WordAdapter()

        wordAdapter.setOnClickListener(this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordAdapter
        }

        btnSave.setOnClickListener {
            val word = Word(edtWord.text.toString())

            wordViewmodel.insert(word)

            wordViewmodel.allWords.observe(this, Observer {
                wordAdapter.loadWord(it)
            })
        }

        btnDelete.setOnClickListener {
            val word = edtWord.text.toString()

            wordViewmodel.delete(word)
            wordViewmodel.allWords.observe(this, Observer {
                wordAdapter.loadWord(it)
            })
        }

        btnUpdate.setOnClickListener {
            val newvalue = newValue.text.toString()

            wordViewmodel.update(oldValue.text.toString(), newvalue)
            wordViewmodel.allWords.observe(this, Observer {
                wordAdapter.loadWord(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu?.findItem(R.id.search)
        val searchView = menuItem?.actionView as SearchView

        searchView.queryHint = "Search any"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var searchQuery = "%$newText%"

                wordViewmodel.getSearchWord(searchQuery!!).observe(this@MainActivity, Observer {
                    Log.d("Search>>>>>", searchQuery)
                    Log.d("Search>>>>", it.toString())

                    wordAdapter.loadWord(it)
                })
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.adapter = wordAdapter

                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(word: Word) {
        edtWord.setText(word.word)
        oldValue.text = word.word
    }
}