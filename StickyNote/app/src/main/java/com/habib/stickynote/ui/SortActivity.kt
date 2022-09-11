package com.habib.stickynote.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.habib.stickynote.R
import com.habib.stickynote.adapter.NoteAdapter
import com.habib.stickynote.db.DatabaseHandler
import com.habib.stickynote.model.SaveNote

class SortActivity : AppCompatActivity() {

    private lateinit var sortRecyclerView: RecyclerView
    private lateinit var notes: ArrayList<SaveNote>
    private lateinit var check: String
    private val db = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)

        val intent = intent
        check = intent.getStringExtra("sort")!!

        sortRecyclerView = findViewById(R.id.sort_recycler_view_id)

        sortRecyclerView.setHasFixedSize(true)
        sortRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        if (check == "color"){
            notes = db.sortByColor()
        }

        if (check == "ascending"){
            notes = db.sortDateAscending()
        }

        else if (check == "descending"){
            notes = db.sortByDateDescending()
        }

        sortRecyclerView.adapter = NoteAdapter(this, notes)
    }

    override fun onBackPressed() {
        finish()
    }

}