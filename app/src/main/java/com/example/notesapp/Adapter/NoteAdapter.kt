package com.example.notesapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Models.Note
import com.example.notesapp.R
import com.example.notesapp.databinding.ListItemBinding
import kotlin.random.Random

class NoteAdapter(
    private val context: Context,
    val listener: NoteItemClickListener
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private lateinit var binding: ListItemBinding

    private val NotesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    inner class NoteViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface NoteItemClickListener{
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
        binding = ListItemBinding. inflate(view, parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NotesList[position]
        binding.tvTitle.text = currentNote.title
        binding.tvTitle.isSelected = true

        binding.tvNote.text = currentNote.note
        binding.tvDate.text = currentNote.date
        binding.tvDate.isSelected = true

        binding.cartLayout.setCardBackgroundColor(randomColor())

        binding.cartLayout.setOnClickListener {

            listener.onItemClicked(NotesList[holder.adapterPosition])
        }

        binding.cartLayout.setOnLongClickListener {
            listener.onLongItemClicked(NotesList[holder.adapterPosition], binding.cartLayout)
            true
        }

    }

    private fun randomColor(): Int{
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    fun updateList(newList: List<Note>){
        fullList.clear()
        fullList.addAll(newList)

        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String){
        NotesList.clear()

        for (item in fullList){
            if(item.title?.lowercase()?.contains(search.lowercase()) == true
                || item.note?.lowercase()?.contains(search.lowercase()) == true){

                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }
}