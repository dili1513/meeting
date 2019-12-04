package com.example.lenovo.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hmh.Conferee;
import hmh.Httpclient;
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
    private String roomres;
    private String meetingres;
    private String userID;
    private Httpclient httpurl=new Httpclient();

    private  int flag=0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    initmeetinglist();
                    usermeetinglayout.setVisibility(View.VISIBLE);
                    usermeetingroomlayout.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
                    initmeetingroomlist();
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

        //获取ID
        Intent intent=getIntent();
        userID=intent.getStringExtra("userID");

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
//        meetingAdapter meetingadapter=new meetingAdapter(userActivity.this,R.layout.user_meeting,meetingList);
//        meetingListView.setAdapter(meetingadapter);
//
//        meetingRoomAdapter meetingroomadapter=new meetingRoomAdapter(userActivity.this,R.layout.user_meetingroom,meetingRoomList);
//        meetingRoomListView.setAdapter(meetingroomadapter);
        initmeetinglist();
        initmeetingroomlist();
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
        sendRequest1("");
//        Meeting meeting1=new Meeting("后勤预算工作专题会","2019/10/15","1:00pm-3:00pm","已完成");
//        Meeting meeting2=new Meeting("专题教育学习","2019/10/26","8:00am-9:00am","已分配会议室");
//        Meeting meeting3=new Meeting("后勤综改专题会","2019/10/27","8:30am-10:00am","申请中...");
//        Meeting meeting4=new Meeting("沙河校区建设专题会","2019/10/27","2:00pm-5:00pm","申请中...");
//
//        meetingList.add(meeting4);
//        meetingList.add(meeting3);
//        meetingList.add(meeting2);
//        meetingList.add(meeting1);
    }
    private void initmeetingroomlist(){
          sendRequest("");
//        MeetingRoom meetingroom1=new MeetingRoom(res,"2019/10/15","1:00pm-3:00pm");
//        MeetingRoom meetingroom2=new MeetingRoom("办510","2019/10/15","1:00pm-3:00pm");
//        MeetingRoom meetingroom3=new MeetingRoom("学生发展中心","2019/10/15","1:00pm-3:00pm");
//        MeetingRoom meetingroom4=new MeetingRoom("教三楼一层大厅","2019/10/15","1:00pm-3:00pm");
//
//        meetingRoomList.add(meetingroom1);
//        meetingRoomList.add(meetingroom2);
//        meetingRoomList.add(meetingroom3);
//        meetingRoomList.add(meetingroom4);
    }
    private void meetingApply(){
        Intent intent = new Intent(userActivity.this, meetingApply.class);
        intent.putExtra("userID",userID);
        startActivity(intent);

    }
    private void roomShare(){
        Intent intent = new Intent(userActivity.this, userRoomShare.class);
        intent.putExtra("userID",userID);
        startActivity(intent);

    }
    private void sendRequest(final String email){
        new Thread(new Runnable(){
            @Override
            public void run(){
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL urlroom = new URL(httpurl.httpurl+":8080/MeetingRoom/ListMeetingRoomFromUser?username="+userID );
                    connection = (HttpURLConnection) urlroom.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    final StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    roomres=response.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                meetingRoomAdapter meetingroomadapter=new meetingRoomAdapter(userActivity.this,R.layout.user_meetingroom,meetingRoomList);
                                meetingRoomListView.setAdapter(meetingroomadapter);
                                if(!roomres.equals("WTFnull")&&!roomres.equals("")){
                                    roomres=roomres+"|";
                                    String[]nameList=roomres.split("\\|");
                                    for(int i=0;i<nameList.length;i++){
                                        String listitem[]=nameList[i].split(";");
                                        MeetingRoom meetingroom=new MeetingRoom("名称："+listitem[1],"容量:"+listitem[2]+"  投影:"+listitem[3]+"  麦克风:"+listitem[4],listitem[0]);
                                        meetingRoomList.add(meetingroom);
                                    }
                                }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }
    private void sendRequest1(final String email){
        new Thread(new Runnable(){
            @Override
            public void run(){
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL urlmeeting = new URL(httpurl.httpurl+":8080/meeting/findByUID?UID="+userID);
                    connection = (HttpURLConnection) urlmeeting.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    final StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    meetingres=response.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                meetingAdapter meetingadapter=new meetingAdapter(userActivity.this,R.layout.user_meeting,meetingList);
                                meetingListView.setAdapter(meetingadapter);
                                if(!meetingres.equals("WTFnull")&&!roomres.equals("")){
                                    meetingres=meetingres+"|";
                                    String[]nameList=meetingres.split("\\|");

                                    for(int i=0;i<nameList.length;i++){
                                        String listitem[]=nameList[i].split(";");
                                        Meeting meeting1=new Meeting(listitem[0],listitem[1],listitem[2],listitem[3],0+"");
                                        meetingList.add(meeting1);
                                    }
                                }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
