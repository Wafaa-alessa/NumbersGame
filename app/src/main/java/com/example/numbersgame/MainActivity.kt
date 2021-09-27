package com.example.numbersgame

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var guesstext: EditText
    private lateinit var guessbutton: Button
    private lateinit var messages: ArrayList<String>
    private lateinit var Text: TextView
    private lateinit var clMain: ConstraintLayout
    private var input = 2
    private var guesses = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input = Random.nextInt(10)
        clMain = findViewById(R.id.clMain)
        messages = ArrayList()
        Text = findViewById(R.id.Text)

        recyc.adapter = MessAdapter(this, messages)
        recyc.layoutManager = LinearLayoutManager(this)

        guesstext = findViewById(R.id.Edit)
        guessbutton = findViewById(R.id.button)
        guessbutton.setOnClickListener { addMessage() }
    }

    private fun addMessage(){
        val msg = guesstext.text.toString()
        if(msg.isNotEmpty()){
            if(guesses>0){
                if(msg.toInt() == input){
                    disableEntry()
                    showAlertDialog("You win!\n\nPlay again?")
                }else{
                    guesses--
                    messages.add("You guess $msg")
                    messages.add("You have $guesses guesses left")
                }
                if(guesses==0){
                    disableEntry()
                    messages.add("You lose - The correct answer was $input")
                    messages.add("Game Over")
                    showAlertDialog("You lose...\nThe correct answer was $input.\n\nPlay again?")
                }
            }
            guesstext.text.clear()
            guesstext.clearFocus()
            recyc.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(clMain, "Please enter number", Snackbar.LENGTH_LONG).show()
        }
    }
    private fun disableEntry(){
        guessbutton.isEnabled = false
        guessbutton.isClickable = false
        guesstext.isEnabled = false
        guesstext.isClickable = false
    }

    private fun showAlertDialog(title: String) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(title)
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Game Over")
        alert.show()
    }
}
