package com.example.lenovo.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hmh.Conferee;
import hmh.Meeting;
import hmh.MeetingRoom;
import hmh.meetingAdapter;
import hmh.meetingRoomAdapter;

public class adminActivity extends AppCompatActivity {

    private LinearLayout adminmeetinglayout;
    private LinearLayout adminmeetingroomlayout;
    private LinearLayout adminpilianglayout;
    private ListView meetingListView;
    private ListView meetingRoomListView;
    private Button adminApplyMeetingButton;
    private Button adminRoomShareButton;
    private List<Meeting> meetingList=new ArrayList<>();
    private List<MeetingRoom>meetingRoomList=new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    adminmeetinglayout.setVisibility(View.VISIBLE);
                    adminmeetingroomlayout.setVisibility(View.GONE);
                    adminpilianglayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    adminmeetinglayout.setVisibility(View.GONE);
                    adminmeetingroomlayout.setVisibility(View.VISIBLE);
                    adminpilianglayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_notifications:
                    adminmeetinglayout.setVisibility(View.GONE);
                    adminmeetingroomlayout.setVisibility(View.GONE);
                    adminpilianglayout.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);



        //布局
        adminmeetinglayout=(LinearLayout)findViewById(R.id.adminMeetinglayout);
        adminmeetingroomlayout=(LinearLayout)findViewById(R.id.adminMeetingRoomlayout);
        adminpilianglayout=(LinearLayout)findViewById(R.id.adminPiliangLayout);

        //按钮
        adminApplyMeetingButton=(Button)findViewById(R.id.adminMetingApplyButton);
        adminRoomShareButton=(Button)findViewById(R.id.adminRoomShareButton);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.adminnavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //取得listview
        meetingListView=(ListView)findViewById(R.id.meetingList);
        meetingRoomListView=(ListView)findViewById(R.id.meetingRoomList);

        //适配器
        initmeetinglist();
        meetingAdapter meetingadapter=new meetingAdapter(adminActivity.this,R.layout.user_meeting,meetingList);
        meetingListView.setAdapter(meetingadapter);
        initmeetingroomlist();
        meetingRoomAdapter meetingroomadapter=new meetingRoomAdapter(adminActivity.this,R.layout.user_meetingroom,meetingRoomList);
        meetingRoomListView.setAdapter(meetingroomadapter);

        //按钮监听
        adminApplyMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meetingApply();
            }
        });
        adminRoomShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomShare();
            }
        });
        meetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meeting meeting=meetingList.get(position);
                Toast.makeText(adminActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(adminActivity.this,meetingApply.class);
        startActivity(intent);

    }
    private void roomShare(){
        Intent intent = new Intent(adminActivity.this, userRoomShare.class);
        startActivity(intent);

    }

}
