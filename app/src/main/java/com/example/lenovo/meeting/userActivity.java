package com.example.lenovo.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hmh.Meeting;
import hmh.MeetingRoom;
import hmh.meetingAdapter;
import hmh.meetingRoomAdapter;

public class userActivity extends AppCompatActivity {

   // private TextView mTextMessage;
    private LinearLayout usermeetinglayout;
    private LinearLayout usermeetingroomlayout;
    private ListView meetingListView;
    private ListView meetingRoomListView;
    private Button userApplyMeetingButton;
    private Button userRoomShareButton;
    private List<Meeting>meetingList=new ArrayList<>();
    private List<MeetingRoom>meetingRoomList=new ArrayList<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    usermeetinglayout.setVisibility(View.VISIBLE);
                    usermeetingroomlayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:

                    usermeetinglayout.setVisibility(View.GONE);
                    usermeetingroomlayout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_notifications:

                    usermeetinglayout.setVisibility(View.GONE);
                    usermeetingroomlayout.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

       //布局
        usermeetinglayout=(LinearLayout)findViewById(R.id.userMeetinglayout);
        usermeetingroomlayout=(LinearLayout)findViewById(R.id.userMeetingRoomlayout);

        //按钮
        userApplyMeetingButton=(Button)findViewById(R.id.UserMetingApplyButton);
        userRoomShareButton=(Button)findViewById(R.id.UserRoomShareButton);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //取得listview
        meetingListView=(ListView)findViewById(R.id.meetingList);
        meetingRoomListView=(ListView)findViewById(R.id.meetingRoomList);

        //适配器
        initmeetinglist();
        meetingAdapter meetingadapter=new meetingAdapter(userActivity.this,R.layout.user_meeting,meetingList);
        meetingListView.setAdapter(meetingadapter);
        initmeetingroomlist();
        meetingRoomAdapter meetingroomadapter=new meetingRoomAdapter(userActivity.this,R.layout.user_meetingroom,meetingRoomList);
        meetingRoomListView.setAdapter(meetingroomadapter);

        //按钮监听
        userApplyMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meetingApply();
            }
        });
        userRoomShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomShare();
            }
        });


    }
    private void initmeetinglist(){
        Meeting meeting1=new Meeting("后勤预算工作专题会","2019/10/15","1:00pm-3:00pm","已完成");
        Meeting meeting2=new Meeting("专题教育学习","2019/10/26","8:00am-9:00am","已分配会议室");
        Meeting meeting3=new Meeting("后勤综改专题会","2019/10/27","8:30am-10:00am","申请中...");
        Meeting meeting4=new Meeting("沙河校区建设专题会","2019/10/27","2:00pm-5:00pm","申请中...");

        meetingList.add(meeting4);
        meetingList.add(meeting3);
        meetingList.add(meeting2);
        meetingList.add(meeting1);


    }
    private void initmeetingroomlist(){
        MeetingRoom meetingroom1=new MeetingRoom("科研楼610","2019/10/15","1:00pm-3:00pm");
        MeetingRoom meetingroom2=new MeetingRoom("办510","2019/10/15","1:00pm-3:00pm");
        MeetingRoom meetingroom3=new MeetingRoom("学生发展中心","2019/10/15","1:00pm-3:00pm");
        MeetingRoom meetingroom4=new MeetingRoom("教三楼一层大厅","2019/10/15","1:00pm-3:00pm");

        meetingRoomList.add(meetingroom1);
        meetingRoomList.add(meetingroom2);
        meetingRoomList.add(meetingroom3);
        meetingRoomList.add(meetingroom4);



    }
    private void meetingApply(){
        Intent intent = new Intent(userActivity.this, meetingApply.class);
        startActivity(intent);

    }
    private void roomShare(){
        Intent intent = new Intent(userActivity.this, userRoomShare.class);
        startActivity(intent);

    }

}
