package com.kosa.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var isRunning = false
    var rank = 1 //처음인지 확인
    var timer : Timer? = null
    var time = 0

    var milli_second = 0
    var second = 0
    var minute = 0

//    val milli_second = time % 100
//    val second = (time % 6000) / 100
//    val minute = time / 6000
    private lateinit var scrollview : ScrollView
    private lateinit var record_list : LinearLayout
    private lateinit var btn_start: Button
    private lateinit var btn_refreshandrecord: Button
    private lateinit var tv_millisecond: TextView
    private lateinit var tv_second: TextView
    private lateinit var tv_minute: TextView

    private lateinit var myinflater : LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //뷰 가져오기
        scrollview = findViewById(R.id.myscroll)
        record_list = findViewById(R.id.record_list)
        btn_start = findViewById(R.id.btn_start)
        btn_refreshandrecord = findViewById(R.id.btn_refreshandrecord)
        tv_millisecond = findViewById(R.id.tv_millisecond)
        tv_second = findViewById(R.id.tv_second)
        tv_minute = findViewById(R.id.tv_miute)

        //버튼별 리스너 등록
        btn_start.setOnClickListener(this)
        btn_refreshandrecord.setOnClickListener(this)

        //레코드를 list에 넣기전에 뷰를 만들어줄 inflater
        myinflater = LayoutInflater.from(this)
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

        //처음이면 버튼 활성화 및 false로 바꾸고
        if(rank == 1){
            //구간기록 활성화
            btn_refreshandrecord.isEnabled = true
        }

        btn_start.text = "일시정지"
        btn_start.setBackgroundColor(getColor(R.color.myred))
        isRunning = true


        btn_refreshandrecord.text = "구간기록"
        btn_refreshandrecord.setBackgroundColor(getColor(R.color.myyello))
        btn_refreshandrecord.setTextColor(getColor(R.color.white))

        timer = timer(period = 10 ){
            time++

            milli_second = time % 100
            second = (time % 6000) / 100
            minute = time / 6000

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

        btn_refreshandrecord.text = "초기화"
        btn_refreshandrecord.setBackgroundColor(getColor(R.color.mygray))

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

        //이제 처음부터 시작할꺼니까 처음으로 바꿔주기
        rank = 1
        btn_refreshandrecord.text = "구간기록"
        btn_refreshandrecord.setBackgroundColor(getColor(R.color.unable))
        btn_refreshandrecord.setTextColor(getColor(R.color.myyello))
        btn_refreshandrecord.isEnabled = false
        //list 다 지우기
        record_list.removeAllViews()
    }

    private fun record(){
        val row_record : TextView = myinflater.inflate(R.layout.textview_for_recordlist,null,false) as TextView
        val m_str = if(minute<10) "0$minute" else "$minute"
        val s_str = if(second<10) "0$second" else "$second"
        val mil_str = if(milli_second<10) "0$milli_second" else "$milli_second"
        row_record.setText("${rank}\t${m_str}:${s_str}.${mil_str}")
        rank++
        record_list.addView(row_record)

        scrollview.post {
            scrollview.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }
}



