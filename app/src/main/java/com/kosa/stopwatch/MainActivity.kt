package com.kosa.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var isRunning = false
    var timer : Timer? = null
    var time = 0


    private lateinit var btn_start: Button
    private lateinit var btn_refreshandrecord: Button
    private lateinit var tv_millisecond: TextView
    private lateinit var tv_second: TextView
    private lateinit var tv_minute: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //뷰 가져오기
        btn_start = findViewById(R.id.btn_start)
        btn_refreshandrecord = findViewById(R.id.btn_refreshandrecord)
        tv_millisecond = findViewById(R.id.tv_millisecond)
        tv_second = findViewById(R.id.tv_second)
        tv_minute = findViewById(R.id.tv_miute)

        //버튼별 리스너 등록
        btn_start.setOnClickListener(this)
        btn_refreshandrecord.setOnClickListener(this)
    }

    override fun onClick(mybtn: View?) {
        when(mybtn?.id){
            R.id.btn_start ->{
                if(isRunning){
                    pause()
                }else{
                    start()
                }
            }
            R.id.btn_refreshandrecord ->{
                if(isRunning){
                    record()
                }else{
                    refresh()
                }

            }
        }
    }

    private fun start(){
        btn_start.text = "일시정지"
        btn_start.setBackgroundColor(getColor(R.color.myred))
        isRunning = true

        //구간기록 활성화
        btn_refreshandrecord.isEnabled = true

        timer = timer(period = 10 ){
            time++

            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000

//            tv_millisecond.text = if (milli_second < 10) ".0${milli_second}" else ".${milli_second}"
//            tv_second.text = if (second < 10) ":0${second}" else ":${second}"
//            tv_minute.text = if (minute < 10) "0${minute}" else "$minute"

            runOnUiThread {
                if(isRunning){
                    tv_millisecond.text = if (milli_second < 10) ".0${milli_second}" else ".${milli_second}"
                    tv_second.text = if (second < 10) ":0${second}" else ":${second}"
                    tv_minute.text = if (minute < 10) "0${minute}" else "$minute"
                }
            }
        }

    }

    private fun pause(){
        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.myblue))

        isRunning = false
        timer?.cancel()
    }

    private fun refresh(){
        timer?.cancel()

        btn_start.text = "시작"
        btn_start.setBackgroundColor(getColor(R.color.myblue))
        isRunning = false

        time = 0
        tv_millisecond.text = ".00"
        tv_second.text = ":00"
        tv_minute.text = "00"
    }

    private fun record(){

    }
}