package com.example.lenovo.meeting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hmh.AdminMeetingRoomAdapter;
import hmh.Httpclient;
import hmh.Meeting;
import hmh.MeetingRoomBean;
import hmh.meetingAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class adminActivity extends AppCompatActivity {
    private static final String TAG = "adminActivity";

    private LinearLayout adminmeetinglayout;
    private LinearLayout adminmeetingroomlayout;
    private LinearLayout adminpilianglayout;
    private ListView meetingListView;
    private ListView meetingRoomListView;
    private Button adminApplyMeetingButton;
    private Button adminRoomShareButton;
    private List<Meeting> meetingList=new ArrayList<>();
    //private List<MeetingRoomBean>meetingRoomList=new ArrayList<>();
    private String meetingres;
    private String roomres="sggsg";
    private String adminID;
    private Httpclient httpurl=new Httpclient();

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


        //获取ID
        Intent intent=getIntent();
        adminID=intent.getStringExtra("adminID");

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
        meetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meeting meeting = meetingList.get(position);
                Intent intent1 = new Intent(adminActivity.this,meetingApply.class);
                intent1.putExtra("Name",meeting.getName());
                intent1.putExtra("Date",meeting.getDate());
                intent1.putExtra("Time",meeting.getTime());
                intent1.putExtra("Shenqingzhuangtai",meeting.getShenqingzhuangtai());
                intent1.putExtra("ID",meeting.getName());
                startActivity(intent1);
            }
        });

        initmeetingroomlist();
        //AdminMeetingRoomAdapter meetingroomadapter=new AdminMeetingRoomAdapter(adminActivity.this,R.layout.user_meetingroom,meetingRoomList);
        //meetingRoomListView.setAdapter(meetingroomadapter);

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
        /*meetingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meeting meeting=meetingList.get(position);
                Toast.makeText(adminActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
            }
        });

         */
    }
    private void initmeetinglist(){
        /*
        Meeting meeting1=new Meeting("后勤预算工作专题会","2019/10/15","1:00pm-3:00pm","已完成");
        Meeting meeting2=new Meeting("专题教育学习","2019/10/26","8:00am-9:00am","已分配会议室");
        Meeting meeting3=new Meeting("后勤综改专题会","2019/10/27","8:30am-10:00am","申请中...");
        Meeting meeting4=new Meeting("沙河校区建设专题会","2019/10/27","2:00pm-5:00pm","申请中...");

        meetingList.add(meeting4);
        meetingList.add(meeting3);
        meetingList.add(meeting2);
        meetingList.add(meeting1);

         */
        new Thread(new Runnable(){
            @Override
            public void run(){
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL urlmeeting = new URL(httpurl.httpurl+":8080/meeting/findByAID?AID=" + adminID);
                            //+adminID);
                    Log.d(TAG, "run: " + urlmeeting);
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
                    //Log.d(TAG, "run: " + meetingres);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Meeting> meetings = new ArrayList<>();
                            if(!meetingres.equals("WTFnull")&&!roomres.equals("")){
                                meetingres=meetingres+"|";
                                String[] nameList=meetingres.split("\\|");
                                for(int i=0;i<nameList.length;i++){
                                    Log.d(TAG, "run: " + nameList[i]);
                                    String[] listitem = nameList[i].split(";");
                                    if (listitem.length>=4){
                                        Meeting meeting1=new Meeting(listitem[0],listitem[1],listitem[2],listitem[3],0+"");
                                        meetings.add(meeting1);
                                    }
                                }
                            }
                            meetingAdapter meetingadapter = new meetingAdapter(adminActivity.this, R.layout.user_meeting, meetings);
                            meetingListView.setAdapter(meetingadapter);
                            meetingList.clear();
                            meetingList.addAll(meetings);
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
    private void initmeetingroomlist(){
        /*
        MeetingRoom meetingroom1=new MeetingRoom("科研楼610","2019/10/15","1:00pm-3:00pm");
        MeetingRoom meetingroom2=new MeetingRoom("办510","2019/10/15","1:00pm-3:00pm");
        MeetingRoom meetingroom3=new MeetingRoom("学生发展中心","2019/10/15","1:00pm-3:00pm");
        MeetingRoom meetingroom4=new MeetingRoom("教三楼一层大厅","2019/10/15","1:00pm-3:00pm");

        meetingRoomList.add(meetingroom1);
        meetingRoomList.add(meetingroom2);
        meetingRoomList.add(meetingroom3);
        meetingRoomList.add(meetingroom4);

         */
        final List<MeetingRoomBean> meetingRoomList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(httpurl.httpurl+":8080/MeetingRoom/ListMeetingRoom")
                        .method("GET", null)
                        .build();
                Response response = client.newCall(request).execute();
                String meetingrooms = response.body().string();
                 //Log.d(TAG, "run: "+meetingrooms);
                JSONArray jsonArray = new JSONArray(meetingrooms);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MeetingRoomBean meetingRoom = new Gson().fromJson(jsonArray.get(i).toString(), MeetingRoomBean.class);
                        meetingRoomList.add(meetingRoom);
                    }

                } catch (Exception e) {
                    toast("连接失败");
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AdminMeetingRoomAdapter meetingRoomAdapter=new AdminMeetingRoomAdapter(adminActivity.this,
                                R.layout.user_meetingroom,
                                meetingRoomList);
                        meetingRoomAdapter.setListener(new AdminMeetingRoomAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(MeetingRoomBean meetingRoom) {
                                edit(meetingRoom);
                            }
                        });
                        meetingRoomListView.setAdapter(meetingRoomAdapter);
                    }
                });

            }
        }
        ).start();


    }
    private void meetingApply(){
        Intent intent = new Intent(adminActivity.this,meetingApply.class);
        intent.putExtra("name",adminID);
        startActivity(intent);

    }
    private void roomShare(){
        Intent intent = new Intent(adminActivity.this, userRoomShare.class);
        intent.putExtra("name",adminID);
        intent.putExtra("isAdmin", true);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initmeetingroomlist();
        initmeetinglist();
    }

    private void toast(final String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(adminActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void edit(MeetingRoomBean meetingRoomBean){

        Intent intent = new Intent(this, MeetingRoomEditActivity.class);
        intent.putExtra("Name", meetingRoomBean.getName());
        intent.putExtra("ID", meetingRoomBean.getId()+"");
        if (meetingRoomBean.getAdmname()!=null) {
            Log.d(TAG, "edit: " +meetingRoomBean.getAdmname());
            intent.putExtra("isAdmin", true);
            intent.putExtra("name", meetingRoomBean.getAdmname());
        }
        if (meetingRoomBean.getUsername()!=null) {
            Log.d(TAG, "edit: " +meetingRoomBean.getUsername());
            intent.putExtra("isAdmin", false);
            intent.putExtra("name", meetingRoomBean.getUsername());
        }
        startActivity(intent);
    }

}
