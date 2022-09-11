package com.habib.stickynote.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.habib.stickynote.R
import com.habib.stickynote.db.DatabaseHandler
import com.habib.stickynote.model.SaveNote
import java.net.Inet4Address
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var titleLayout: TextInputLayout
    private lateinit var titleEd: TextInputEditText
    private lateinit var noteLayout: TextInputLayout
    private lateinit var noteEd: TextInputEditText
    private lateinit var saveNoteButton: FloatingActionButton
    private val db = DatabaseHandler(this)

    private lateinit var title1: String
    private lateinit var note1: String
    private lateinit var date1: String
    private lateinit var time1: String
    private lateinit var timeMilli1: String

    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        MobileAds.initialize(this) {}

        setAd()

        titleLayout = findViewById(R.id.text_input_title_layout_id)
        titleEd = findViewById(R.id.add_note_title_id)
        noteLayout = findViewById(R.id.text_input_note_layout_id)
        noteEd = findViewById(R.id.add_note_note_id)
        saveNoteButton = findViewById(R.id.save_button_id)

        val intent = intent
        title1 = intent.getStringExtra("title")!!
        note1 = intent.getStringExtra("note")!!
        date1 = intent.getStringExtra("date")!!
        time1 = intent.getStringExtra("time")!!
        timeMilli1 = intent.getStringExtra("timeMilli")!!

        if (title1 == "main"){
            titleEd.setText("")
            noteEd.setText("")
        }
        else if (title1 != "main"){
            titleEd.setText(title1)
            noteEd.setText(note1)
        }

        saveNoteButton.setOnClickListener {

            val title = titleEd.text.toString()
            val note = noteEd.text.toString()
            saveNote(title, note)
        }

        titleEd.doOnTextChanged { text, _, _, _ ->
            if (text!!.length > 49){
                titleLayout.error = "No more"
            }
            else if(text.length<50){
                titleLayout.error = null
            }
        }

        noteEd.doOnTextChanged { text, _, _, _ ->
            if (text!!.length > 249){
                noteLayout.error = "No more"
            }
            else if(text.length<250){
                noteLayout.error = null
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteId){
            if (title1 == "main"){
                Toast.makeText(this, "You can't delete this", Toast.LENGTH_LONG).show()
            }
            else {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.select_dialog_box_delete)
                val no = dialog.findViewById<TextView>(R.id.NoID)
                val yes = dialog.findViewById<TextView>(R.id.YesID)
                no.setOnClickListener {
                    dialog.cancel()
                }
                yes.setOnClickListener {
                    db.deleteNote(timeMilli1)
                    val intent = Intent(this, MainActivity::class.java).apply {  }
                    startActivity(intent)
                    finish()
                }
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SimpleDateFormat")
    private fun saveNote(title: String, note: String) {
        if (TextUtils.isEmpty(title)){
            titleEd.error = "Title Required"
            titleEd.requestFocus()
            return
        }
        if (TextUtils.isEmpty(note)){
            noteEd.error = "Note Required"
            noteEd.requestFocus()
            return
        }

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val date = sdf.format(Date())

        val time: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.now())
        } else {
            val simpleTime = SimpleDateFormat("hh:mm a")
            simpleTime.format(Date())
        }

        val calendar = Calendar.getInstance()
        val timeMilli = calendar.timeInMillis.toString()


        if (title1 == "main"){
            val saveNote = SaveNote(title, note, date, time, 0, timeMilli)
            db.insertNoteData(saveNote)
            intentFromAddNote()
            finish()
        }
        else {
            val saveNote = SaveNote(title, note, date1, time1, 0, timeMilli1)
            db.updateNote(saveNote)
            intentFromAddNote()
            finish()
        }
    }


    fun setAd(){
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-1473962936576135/3962828600", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    fun intentFromAddNote(){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    val intent = Intent(this@AddNoteActivity, MainActivity::class.java).apply {  }
                    startActivity(intent)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    Log.d(TAG, "Ad failed to show.")
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Ad showed fullscreen content.")
                    mInterstitialAd = null
                }
            }
        } else {
            val intent = Intent(this@AddNoteActivity, MainActivity::class.java).apply {  }
            startActivity(intent)
        }
    }
}















