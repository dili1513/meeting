package com.example.lenovo.meeting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hmh.MeetingRoom;

public class userRoomShare extends AppCompatActivity {
    public Button addTimeButton;
    public Button submitButton;
    public Button deleteButton;
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
                MeetingRoom meetingroom1=new MeetingRoom(roomName.getText().toString(),
                                               roomAddress.getText().toString(),
                                                ShareTime,
                                                   roomCapacity.getText().toString(),
                                                    roomProject.getText().toString(),
                                                      roomMicrophone.getText().toString());
                Toast.makeText(userRoomShare.this,
                        "会议室名称 : "+meetingroom1.getName()+
                                "\n会议室地址 : "+meetingroom1.getAddress()+
                                "\n会议室容量 : "+meetingroom1.getNum()+
                                "\n麦克风 : "+meetingroom1.getMicrophone()+
                                "\n投影仪 : "+meetingroom1.getProjector()+
                                "\n共享时间 : \n"+ShareTime+"\n\n\n会议室共享提交成功",Toast.LENGTH_LONG).show();

            }
        });


    }
}
