package com.ait.stopwatch

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.time_display.*
import kotlinx.android.synthetic.main.time_display.view.*
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var threadEnabled = false
    lateinit var timer: Timer
    var millisecondTime = 0L
    var startTime = 0L
    var timeBuff = 0L
    var updateTime = 0L
    var seconds = 0
    var minutes = 0
    var milliSeconds = 0
    var startAlreadyClicked = false
    var allowMark = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvTime.text = "00:00:00"

        btnStart.setOnClickListener {
            if (startAlreadyClicked == false) {
                startAlreadyClicked = true
                allowMark = true
                timer = Timer()
                startTime = SystemClock.uptimeMillis()
                timer.schedule(RunTimeTimerTask(), 10, 10)

            }


        }

        btnMark.setOnClickListener {
            if (startAlreadyClicked == true && allowMark == true) {
                addTime()
            }
        }

        btnStop.setOnClickListener {
            timer.cancel()
            allowMark = false

        }

        btnClear.setOnClickListener {
            onStop()
            tvTime.text = "00:00:00"
            millisecondTime = 0L
            startTime = 0L
            timeBuff = 0L
            updateTime = 0L
            seconds = 0
            minutes = 0
            milliSeconds = 0
           startAlreadyClicked = false
            allowMark = false


        }

    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
        timer.purge()

    }

    private fun addTime() {

        var markView = layoutInflater.inflate(
            R.layout.time_display, null, false
        )

        markView.btnDelete.setOnClickListener {
            layoutContent.removeView(markView)
        }


        layoutContent.addView(markView, 0)

        markView.etMark.setText(
            "" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliSeconds)
        )

    }


    private inner class RunTimeTimerTask : TimerTask() {
        override fun run() {
            runOnUiThread {
                    millisecondTime = SystemClock.uptimeMillis() - startTime

                    updateTime = timeBuff + millisecondTime

                    seconds = (updateTime / 1000).toInt()

                    minutes = seconds / 60

                    seconds = seconds % 60

                    milliSeconds = (updateTime % 1000).toInt()

                    tvTime.setText(
                        "" + minutes + ":"
                                + String.format("%02d", seconds) + ":"
                                + String.format("%03d", milliSeconds)
                    )

            }
        }
    }


}

