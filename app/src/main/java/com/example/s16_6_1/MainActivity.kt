package com.example.s16_6_1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val edName = findViewById<EditText>(R.id.edName)
        val tvtext = findViewById<TextView>(R.id.tvtext)
        val radioGroup = findViewById<RadioGroup>(R.id.radiogroup)
        val btnmora = findViewById<Button>(R.id.mora)
        val tvName = findViewById<TextView>(R.id.tvname)
        val winner = findViewById<TextView>(R.id.winner)
        val mymora = findViewById<TextView>(R.id.tvmymora)
        val target = findViewById<TextView>(R.id.tvtargetmora)
        val result = findViewById<TextView>(R.id.result)
        val resbut = findViewById<Button>(R.id.resetButton)
        var win = 0
        var iwin = 0
        var nowin = 0

        result.text = "戰績\n" + " 勝:$win 敗:$iwin 平:$nowin"

        resbut.setOnClickListener {
            win = 0
            iwin = 0
            nowin = 0
            result.text = "戰績\n" + " 勝:$win 敗:$iwin 平:$nowin"
        }

        btnmora.setOnClickListener {
            val playerName = edName.text.toString().trim()
            if (playerName.isEmpty()) {
                Toast.makeText(this, "請輸入玩家姓名", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val selectedOption = radioGroup.checkedRadioButtonId
            if (selectedOption == -1) {
                Toast.makeText(this, "請選擇剪刀、石頭或布", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val playChoice = when (selectedOption) {
                R.id.s -> "剪刀"
                R.id.rook -> "石頭"
                R.id.paper -> "布"
                else -> ""
            }

            val choices = listOf("剪刀", "石頭", "布")
            val targetmora = choices.random()


            val resultText = when {
                playChoice == targetmora -> {
                    nowin++
                    "平手"
                }

                (playChoice == "剪刀" && targetmora == "布") ||
                        (playChoice == "石頭" && targetmora == "剪刀") ||
                        (playChoice == "布" && targetmora == "石頭") -> {
                    win++
                    "$playerName 獲勝！"
                }

                else -> {
                    iwin++
                    "電腦勝利"
                }
            }
            val intent = Intent(this, s16_6::class.java)
            intent.putExtra("nowin", nowin)
            intent.putExtra("win", win)
            intent.putExtra("iwin", iwin)
            if (win == 4) {
                intent.putExtra("winner", playerName)
                startActivity(intent)
                finish()
            } else if (iwin == 4) {
                intent.putExtra("winner", "電腦")
                startActivity(intent)
                finish()
            }
            tvName.text = "名字\n$playerName"
            mymora.text = "我方出\n$playChoice"
            target.text = "電腦出\n$targetmora"
            winner.text = "勝利者\n$resultText"
            result.text = "戰績\n 勝:$win 敗:$iwin 平:$nowin"
        }

    }
}

