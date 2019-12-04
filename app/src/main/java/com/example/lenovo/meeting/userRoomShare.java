package com.example.lenovo.meeting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import hmh.Httpclient;
import hmh.MeetingRoom;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class userRoomShare extends AppCompatActivity {
    public Button addTimeButton;
    public Button submitButton;
    public Button deleteButton;
    private Intent intent;
    private boolean isAdmin;
    private String name;
    private Httpclient url = new Httpclient();
    EditText roomName;
    EditText roomAddress;
    EditText roomCapacity;
    EditText roomMicrophone;
    EditText roomProject;
    EditText startTime;
    EditText endTime;
    String ShareTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_room_share);

        intent = getIntent();
        ShareTime="";
        addTimeButton=(Button)findViewById(R.id.button3);
        submitButton=(Button)findViewById(R.id.button4);
        deleteButton=(Button)findViewById(R.id.button2);
        roomName=(EditText)findViewById(R.id.RoomNameEditText);
        roomAddress=(EditText)findViewById(R.id.RoomAddressEditText);
        roomCapacity=(EditText)findViewById(R.id.RoomCapacityEditText);
        roomMicrophone=(EditText)findViewById(R.id.editText14);
        roomProject=(EditText)findViewById(R.id.editText15);
        startTime=(EditText)findViewById(R.id.StartTime);
        endTime=(EditText)findViewById(R.id.EndTime);

        addTimeButton=(Button) findViewById(R.id.button3);
        submitButton=(Button) findViewById(R.id.button4);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ShareTime="";
            }
        });
        isAdmin = intent.getBooleanExtra("isAdmin",false);
        name = intent.getStringExtra("name");

        addTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareTime=ShareTime+startTime.getText().toString()+"-"+endTime.getText().toString()+"|";

                Toast.makeText(userRoomShare.this,
                        "起始时间 : "+startTime.getText().toString()+
                                "\n终止时间 : "+endTime.getText().toString()+
                                "\n添加成功",Toast.LENGTH_LONG).show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MeetingRoom meetingroom1=new MeetingRoom(roomName.getText().toString(),
                                               roomAddress.getText().toString(),
                                                ShareTime,
                                                   roomCapacity.getText().toString(),
                                                    roomProject.getText().toString(),
                                                      roomMicrophone.getText().toString());

                //8080/MeetingRoom/insertMeetingRoom?Address=1125&Name=期中总结会议6&Capacity=10&Projector=1&Microphone=10&Admname=hanning

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        String nameType;
                        if (isAdmin)nameType = "Admname";
                        else nameType = "Username";
                        OkHttpClient client = new OkHttpClient();
                        //Log.d("nameType", name);
                        Request request = new Request.Builder()
                                .url(url.httpurl+":8080//MeetingRoom/insertMeetingRoom?" +
                                        "Address="+ meetingroom1.getAddress()+
                                        "&Name="+ meetingroom1.getName()+
                                        "&Capacity="+ meetingroom1.getNum()+
                                        "&Projector="+ meetingroom1.getProjector()+
                                        "&Microphone="+ meetingroom1.getMicrophone()+
                                        "&"+nameType+"="+ name)
                                .method("GET", null)
                                .build();
                            Log.d("request1", request.toString());
                            Response response = client.newCall(request).execute();
                            String s = response.body().string();
                            if (s.equals(""))toast("插入失败");
                            else toast(s);

                        } catch (Exception e) {
                            toast("插入失败");
                            e.printStackTrace();
                        }


                    }
                }).start();
                /*
                Toast.makeText(userRoomShare.this,
                        "会议室名称 : "+meetingroom1.getName()+
                                "\n会议室地址 : "+meetingroom1.getAddress()+
                                "\n会议室容量 : "+meetingroom1.getNum()+
                                "\n麦克风 : "+meetingroom1.getMicrophone()+
                                "\n投影仪 : "+meetingroom1.getProjector()+
                                "\n共享时间 : \n"+ShareTime+"\n\n\n会议室共享提交成功",Toast.LENGTH_LONG).show();

                 */

            }
        });
    }

    private void toast(final String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(userRoomShare.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
