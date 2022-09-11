package com.habib.stickynote.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.habib.stickynote.R
import com.habib.stickynote.adapter.NoteAdapter
import com.habib.stickynote.db.DatabaseHandler
import com.habib.stickynote.model.SaveNote

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private val db = DatabaseHandler(this)
    val note: ArrayList<SaveNote> = ArrayList()
    private lateinit var notes: ArrayList<SaveNote>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view_id)
        addButton = findViewById(R.id.add_button_id)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        notes = db.getAllNote()
        recyclerView.adapter = NoteAdapter(this, notes)
        addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("title", "main")
            intent.putExtra("note", "main")
            intent.putExtra("date", "main")
            intent.putExtra("time", "main")
            intent.putExtra("timeMilli", "main")
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.select_dialog_box)
        val no = dialog.findViewById<TextView>(R.id.NoID)
        val yes = dialog.findViewById<TextView>(R.id.YesID)

        no.setOnClickListener { dialog.cancel() }

        yes.setOnClickListener { finish() }
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.submenu_sort_by_color){
            val intent = Intent(this, SortActivity::class.java)
            intent.putExtra("sort", "color")
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        if (item.itemId == R.id.submenu_sort_by_ascending){
            val intent = Intent(this, SortActivity::class.java)
            intent.putExtra("sort", "ascending")
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        if (item.itemId == R.id.submenu_sort_by_descending){
            val intent = Intent(this, SortActivity::class.java)
            intent.putExtra("sort", "descending")
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu_item, menu)
        val myMenuItem: MenuItem = menu!!.findItem(R.id.sortId)
        menuInflater.inflate(R.menu.sub_menu_item, myMenuItem.subMenu)
        return super.onCreateOptionsMenu(menu)
    }
}

















