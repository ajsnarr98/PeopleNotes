package com.ajsnarr.linknotes.search

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajsnarr.linknotes.R
import com.ajsnarr.linknotes.data.Note
import com.ajsnarr.linknotes.databinding.ItemSearchResultBinding
import com.google.android.material.chip.Chip

class SearchAdapter(val context: Context, val actionListener: ActionListener)
    : RecyclerView.Adapter<SearchAdapter.ResultViewHolder>() {

    private var results: List<Note> = mutableListOf()

    interface ActionListener {
        fun onResultClick(note: Note)
    }

    fun updateResults(results: List<Note>) {
        this.results = results
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return ResultViewHolder(view)
    }

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

        val note = results[position]

        holder.binding.title.text = note.name

        holder.binding.resultCard.setOnClickListener {
            actionListener.onResultClick(note)
        }

        holder.binding.chipGroup.addTags(note.tags)
        holder.binding.icon.setImageResource(R.drawable.default_profile) // TODO - load image
    }

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemSearchResultBinding.bind(view)
    }
}