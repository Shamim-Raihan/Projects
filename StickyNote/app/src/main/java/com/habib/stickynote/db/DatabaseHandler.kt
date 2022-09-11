package com.habib.stickynote.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.habib.stickynote.model.SaveNote


const val DATABASE_NAME = "note_database"
const val VERSION_NUMBER = 7

const val TABLE_NAME = "note_table"

const val COL_ID = "id"
const val COL_TITLE = "title"
const val COL_NOTE = "note"
const val COL_DATE = "date"
const val COL_TIME = "time"
const val COL_COLOR_CODE = "colorCode"
const val COL_TIME_MILLI = "milli"

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_NUMBER) {
    override fun onCreate(db: SQLiteDatabase?) {

        val noteTable = "CREATE TABLE " + TABLE_NAME + "("+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " VARCHAR(255)," +
               COL_NOTE + " VARCHAR(255)," + COL_DATE + " VARCHAR(255)," + COL_TIME + " VARCHAR(255)," + COL_COLOR_CODE + " INTEGER(255)," + COL_TIME_MILLI + " VARCHAR(255))"
        db?.execSQL(noteTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) { }

    fun insertNoteData(saveNote: SaveNote){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_TITLE, saveNote.title)
        cv.put(COL_NOTE, saveNote.note)
        cv.put(COL_DATE, saveNote.date)
        cv.put(COL_TIME, saveNote.time)
        cv.put(COL_COLOR_CODE, saveNote.colorCode)
        cv.put(COL_TIME_MILLI, saveNote.timeMilli)
        db.insert(TABLE_NAME, null, cv)
    }

    fun getAllNote(): ArrayList<SaveNote>{
        val noteList = ArrayList<SaveNote>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COL_ID DESC"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                val saveNote = SaveNote(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6))
                noteList.add(saveNote)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return noteList
    }

    @SuppressLint("Recycle")
    fun updateNote(saveNote: SaveNote){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"

        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                val cv = ContentValues()
                cv.put(COL_TITLE, saveNote.title)
                cv.put(COL_NOTE, saveNote.note)
                db.update(TABLE_NAME, cv, "$COL_TIME_MILLI=?", arrayOf(saveNote.timeMilli))
            }while (result.moveToNext())
        }
    }

    @SuppressLint("Recycle")
    fun updateColorCode(saveNote: SaveNote){
        val db = writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                val cv = ContentValues()
                cv.put(COL_COLOR_CODE, saveNote.colorCode)
                db.update(TABLE_NAME, cv, "$COL_TIME_MILLI=?", arrayOf(saveNote.timeMilli))
            }while (result.moveToNext())
        }
    }

    fun deleteNote(timeMilli: String){
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COL_TIME_MILLI=?", arrayOf(timeMilli))
        db.close()
    }

    fun sortByColor(): ArrayList<SaveNote> {
        val noteList = ArrayList<SaveNote>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COL_COLOR_CODE"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                val saveNote = SaveNote(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6))
                noteList.add(saveNote)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return noteList
    }

    fun sortDateAscending(): ArrayList<SaveNote> {
        val noteList: ArrayList<SaveNote> = ArrayList()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COL_ID"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                val saveNote = SaveNote(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6))
                noteList.add(saveNote)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return noteList
    }

    fun sortByDateDescending(): ArrayList<SaveNote> {
        val noteList: ArrayList<SaveNote> = ArrayList()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COL_ID DESC"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                val saveNote = SaveNote(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getInt(5), result.getString(6))
                noteList.add(saveNote)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return noteList
    }
}













