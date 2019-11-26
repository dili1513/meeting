package com.example.lenovo.meeting;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hmh.Conferee;
import hmh.Meeting;
import hmh.MeetingRoom;
import hmh.confereeAdapter;
import hmh.meetingAdapter;

public class meetingApply extends AppCompatActivity {

    private ListView confereeListView;
    private Button updateConfereeButton;
    private Button submitConfereeButton;
    private Button submitApplyButton;
    private List<Conferee> confereeList=new ArrayList<>();

    private String res;
    private int flag=0;

    private String ConfereeString=new String("");
    private String ConfereeID=new String("");
    private String MeetingString=new String("");

    private EditText Editname;
    private EditText Editsponsor;
    private EditText EditTime;
    private EditText Editlength;
    private EditText Editmicrophone;
    private EditText Editprojector;
    private EditText Editnum;

    private TextView ceshi;
    public static final int UP=1;
    String i="";

    private Handler handler=new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UP:
                    ceshi.setText("dianl");

                    break;
                default:
                    break;
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_apply);

        confereeListView=(ListView)findViewById(R.id.confereeListView);
        updateConfereeButton=(Button)findViewById(R.id.updateConfereeButton);
        submitApplyButton=(Button)findViewById(R.id.submitApplyButton);
        final EditText Editname=(EditText)findViewById(R.id.editText10);
        final EditText Editsponsor=(EditText)findViewById(R.id.editText11);
        final EditText EditTime=(EditText)findViewById(R.id.editText12);
        final EditText Editlength=(EditText)findViewById(R.id.editText13);
        final EditText Editmicrophone=(EditText)findViewById(R.id.editText14);
        final EditText Editprojector=(EditText)findViewById(R.id.editText15);
        final EditText Editnum=(EditText)findViewById(R.id.editText16);

        final TextView ceshi=(TextView)findViewById(R.id.test);





        initconfereelist();
        confereeAdapter confereeAdapter=new confereeAdapter(meetingApply.this,R.layout.confereelayout,confereeList);
        confereeListView.setAdapter(confereeAdapter);

        confereeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conferee conferee=confereeList.get(position);
                Toast.makeText(meetingApply.this,conferee.getName()+"添加成功",Toast.LENGTH_SHORT).show();
                ConfereeString=ConfereeString+conferee.getName()+" ";
                ConfereeID=ConfereeID+conferee.getId()+" ";
            }
        });
        updateConfereeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(meetingApply.this,"请重新选择参会名单",Toast.LENGTH_SHORT).show();
                ConfereeString="";
                ConfereeID="";
            }
        });
        submitApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendRequest();
                while(flag==0){}

                    final Meeting meetingApply=new Meeting(Editname.getText().toString(),
                            Editsponsor.getText().toString(),
                            EditTime.getText().toString(),
                            EditTime.getText().toString(),
                            Editlength.getText().toString(),
                            Editmicrophone.getText().toString(),
                            Editprojector.getText().toString(),
                            Editnum.getText().toString());
                    Toast.makeText(meetingApply.this,
                            "会议名称 : "+meetingApply.getName()+
                                    "\n主办单位 : "+meetingApply.getMeetingsponsor()+
                                    "\n会议时间 : "+meetingApply.getDate()+
                                    "\n会议时长 : "+meetingApply.getMeetinglength()+
                                    "\n麦克风 : "+meetingApply.getMicrophone()+
                                    "\n投影仪 : "+meetingApply.getProjector()+
                                    "\n人数 : "+ meetingApply.getNum()+
                                    "\n参会人员 :\n"+ConfereeString+"\n\n\n会议提交成功",Toast.LENGTH_LONG).show();
                    //在这里给服务器传会议信息和参会名单confereeId
                    ceshi.setText(res);

            }
        });
    }
    private void initconfereelist(){
        Conferee conferee1=new Conferee("2019865423","王玉平");
        Conferee conferee2=new Conferee("2019652354","陈云会");
        Conferee conferee3=new Conferee("2019632584","殷霄寒");
        Conferee conferee4=new Conferee("2019658423","阎瑞波");
        Conferee conferee5=new Conferee("2019156115","韩宁");
        Conferee conferee6=new Conferee("2019658156","林晴雯");
        Conferee conferee7=new Conferee("2019615464","刘戎");
        Conferee conferee8=new Conferee("2019684651","王天娇");


        confereeList.add(conferee1);
        confereeList.add(conferee2);
        confereeList.add(conferee3);
        confereeList.add(conferee4);
        confereeList.add(conferee5);
        confereeList.add(conferee6);
        confereeList.add(conferee7);
        confereeList.add(conferee8);
        confereeList.add(conferee1);
        confereeList.add(conferee2);
        confereeList.add(conferee3);
        confereeList.add(conferee4);
        confereeList.add(conferee5);
        confereeList.add(conferee6);
        confereeList.add(conferee7);
        confereeList.add(conferee8);
    }

    private void sendRequest(){
        new Thread(new Runnable(){
            @Override
            public void run(){


                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://10.128.199.207:8080/CRUD/ListUser");
                    connection = (HttpURLConnection) url.openConnection();
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

                  res=response.toString();
                    flag=1;

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

