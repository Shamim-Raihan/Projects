package com.habib.stickynote.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.habib.stickynote.R
import com.habib.stickynote.db.DatabaseHandler
import com.habib.stickynote.model.SaveNote
import com.habib.stickynote.ui.AddNoteActivity

class NoteAdapter(private val context: Context, private val notes: List<SaveNote>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.titleAdapter.text = notes[position].title
        holder.noteAdapter.text = notes[position].note
        holder.dateAdapter.text = notes[position].date
        holder.timeAdapter.text = notes[position].time

        val db = DatabaseHandler(context)

        val colorCode: Int = notes[position].colorCode
        var drawable1: Drawable? = null
        var drawable2: Drawable? = null
        var drawable3: Drawable? = null
        var drawable4: Drawable? = null
        var drawable5: Drawable? = null
        var drawable6: Drawable? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable1 = context.getDrawable(R.drawable.view_backgraound_1)
            drawable2 = context.getDrawable(R.drawable.view_backgraound_2)
            drawable3 = context.getDrawable(R.drawable.view_backgraound_3)
            drawable4 = context.getDrawable(R.drawable.view_backgraound_4)
            drawable5 = context.getDrawable(R.drawable.view_backgraound_5)
            drawable6 = context.getDrawable(R.drawable.view_backgraound_6)
        }

        if (colorCode == 1){
            holder.listItem.background = drawable1
        }
        if (colorCode == 2){
            holder.listItem.background = drawable2
        }
        if (colorCode == 3){
            holder.listItem.background = drawable3
        }
        if (colorCode == 4){
            holder.listItem.background = drawable4
        }
        if (colorCode == 5){
            holder.listItem.background = drawable5
        }
        if (colorCode == 6){
            holder.listItem.background = drawable6
        }


        holder.itemView.setOnClickListener {
            val intent = Intent(context, AddNoteActivity::class.java)
            intent.putExtra("title", notes[position].title)
            intent.putExtra("note", notes[position].note)
            intent.putExtra("date", notes[position].date)
            intent.putExtra("time", notes[position].time)
            intent.putExtra("timeMilli", notes[position].timeMilli)
            context.startActivity(intent)
        }

        holder.colorAdapter.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val color1: View = dialog.findViewById(R.id.color1)
            val color2: View = dialog.findViewById(R.id.color2)
            val color3: View = dialog.findViewById(R.id.color3)
            val color4: View = dialog.findViewById(R.id.color4)
            val color5: View = dialog.findViewById(R.id.color5)
            val color6: View = dialog.findViewById(R.id.color6)

            color1.setOnClickListener {
                holder.listItem.background = drawable1
                val saveNote = SaveNote(notes[position].title, notes[position].note, notes[position].date, notes[position].time, 1, notes[position].timeMilli)
                db.updateColorCode(saveNote)
                dialog.dismiss()
            }

            color2.setOnClickListener {
                holder.listItem.background = drawable2
                val saveNote = SaveNote(notes[position].title, notes[position].note, notes[position].date, notes[position].time, 2, notes[position].timeMilli)
                db.updateColorCode(saveNote)
                dialog.dismiss()
            }

            color3.setOnClickListener {
                holder.listItem.background = drawable3
                val saveNote = SaveNote(notes[position].title, notes[position].note, notes[position].date, notes[position].time, 3, notes[position].timeMilli)
                db.updateColorCode(saveNote)
                dialog.dismiss()
            }

            color4.setOnClickListener {
                holder.listItem.background = drawable4
                val saveNote = SaveNote(notes[position].title, notes[position].note, notes[position].date, notes[position].time, 4, notes[position].timeMilli)
                db.updateColorCode(saveNote)
                dialog.dismiss()
            }

            color5.setOnClickListener {
                holder.listItem.background = drawable5
                val saveNote = SaveNote(notes[position].title, notes[position].note, notes[position].date, notes[position].time, 5, notes[position].timeMilli)
                db.updateColorCode(saveNote)
                dialog.dismiss()
            }

            color6.setOnClickListener {
                holder.listItem.background = drawable6
                val saveNote = SaveNote(notes[position].title, notes[position].note, notes[position].date, notes[position].time, 6, notes[position].timeMilli)
                db.updateColorCode(saveNote)
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.show()
        }
    }

    class NoteViewHolder(view: View): RecyclerView.ViewHolder(view){
        var titleAdapter: TextView = view.findViewById(R.id.text_view_my_title)
        var noteAdapter: TextView
        var dateAdapter: TextView
        var timeAdapter: TextView
        var colorAdapter: ImageButton
        var listItem: RelativeLayout

        init {
            titleAdapter.ellipsize = TextUtils.TruncateAt.MARQUEE
            titleAdapter.isSelected = true
            noteAdapter = view.findViewById(R.id.text_view_my_note)
            dateAdapter = view.findViewById(R.id.text_view_current_date)
            timeAdapter = view.findViewById(R.id.text_view_current_time)
            colorAdapter = view.findViewById(R.id.colorChangeId)
            listItem = view.findViewById(R.id.RelativeLayoutId)
        }
    }
}
























