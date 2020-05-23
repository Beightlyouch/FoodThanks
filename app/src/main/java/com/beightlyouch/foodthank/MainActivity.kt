package com.beightlyouch.foodthank

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val choices_num = 9
    private val thanks_num = 7
    private val abuses_num = 2
    //Lis
    private var thanks_choice = arrayListOf<Int>()
    private var abuses_choice = arrayListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.setImageResource(R.drawable.pan)

        val button_array = listOf(button, button2, button3, button4, button5, button6, button7, button8, button9)

        val choices = takeChoices()
        for(i in 0..button_array.size-1) {
            button_array[i].setText(choices[i])
        }

        button_array.forEach { button ->
            button.setOnClickListener {
                val wm = WordMaster()
                val btnText: String = button.getText().toString()
                choiceTextAnimation(btnText)
                if(wm.thanks.keys.contains(btnText)) {
                    button.visibility = View.INVISIBLE
                } else {
                    button_array.forEach { button -> button.visibility = View.VISIBLE }
                    imageView.setImageResource(R.drawable.pan_kabi)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    //sizeの中からpick_num個ランダムに選ぶ
    fun takeAtRandom(pick_num: Int, size: Int, random: Random = Random): ArrayList<Int> {

        val taken = arrayListOf<Int>() // ランダムに選択された要素を持たせるリスト
        val remaining = (0..size-1).toMutableList() // 残っている要素のリスト

        repeat(pick_num) {
            val remainingCount = remaining.size // 残っている要素の数
            val index = random.nextInt(remainingCount) // ランダムに選択されたインデックス

            taken += remaining[index] // ランダムに選択された要素

            val lastIndex = remainingCount - 1 // 残っている要素のリストの末尾のインデックス
            val lastElement = remaining.removeAt(lastIndex) // 残っている要素のリストから末尾を削除する。
            if (index < lastIndex) { // ランダムに選択された要素が末尾以外なら…
                remaining[index] = lastElement // それを末尾の要素で置換する。
            }
        }

        return taken
    }

    //選択肢６つをチョイス
    fun takeChoices(): List<String?>{
        val wm = WordMaster()
        val thanks_taken = takeAtRandom(thanks_num, wm.thanks.size)
        val abuses_taken = takeAtRandom(abuses_num, wm.abuses.size)

        thanks_choice = thanks_taken
        abuses_choice = abuses_taken

        val choices = mutableListOf<String?>()

        for (i in 0..thanks_num-1) {
            choices.add(wm.thanks.entries.elementAt(thanks_taken[i]).key)
        }
        for (i in 0..abuses_num-1) {
            choices.add(wm.abuses.entries.elementAt(abuses_taken[i]).key)
        }
        choices.shuffle()

        return choices
    }

    fun choiceTextAnimation(btnText: String) {
        val animation = ScaleAnimation(
                1.0f, 1.3f, 1.0f,1.3f,
                Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        choiceTextView.setText("＼" + btnText + "／")
        // animation時間 msec
        animation.setDuration(800);
        // animationが終わったそのまま表示にする
        animation.setFillAfter(true);
        choiceTextView.startAnimation(animation)
    }

}

