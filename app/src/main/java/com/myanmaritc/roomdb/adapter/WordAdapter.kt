package com.myanmaritc.roomdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myanmaritc.roomdb.R
import com.myanmaritc.roomdb.entity.Word
import kotlinx.android.synthetic.main.item_word.view.*

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private var wordList: List<Word> = ArrayList()

    private var onClickListener: OnClickListener? = null

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        lateinit var word: Word
        fun bind(word: Word) {
            this.word = word
            itemView.wordtxt.text = word.word
        }

        override fun onClick(v: View?) {
            onClickListener?.onClick(word)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int = wordList.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordList[position])
    }

    fun loadWord(wordList: List<Word>) {
        this.wordList = wordList
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(word: Word)
    }

    fun setOnClickListener(onClickListener:OnClickListener){
        this.onClickListener=onClickListener
    }

}