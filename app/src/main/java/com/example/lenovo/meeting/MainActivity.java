//package com.example.lenovo.meeting;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.ListView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import hmh.Meeting;
//import hmh.meetingAdapter;
//
//public class MainActivity extends AppCompatActivity {
//    private ListView meetingListView;
//    private List<Meeting> meetingList=new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        initmeetinglist();
//        meetingListView=(ListView)findViewById(R.id.meetingList);
//        meetingAdapter meetingadapter=new meetingAdapter(MainActivity.this,R.layout.user_meeting,meetingList);
//        meetingListView.setAdapter(meetingAdapter);
//
//
//    }
//    private void initmeetinglist(){
//        Meeting meeting1=new Meeting("北邮创新大会","2019/8/7","8:am-9:am");
//        Meeting meeting2=new Meeting("北邮创新大会","2019/8/7","8:am-9:am");
//        meetingList.add(meeting1);
//        meetingList.add(meeting2);
//
//    }
//
//}
