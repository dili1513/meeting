package com.example.lenovo.meeting;

import android.content.Intent;
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
import hmh.confereeAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class meetingApply extends AppCompatActivity {

    private ListView confereeListView;
    private Button updateConfereeButton;
    private Button submitConfereeButton;
    private Button submitApplyButton;
    private Button query;
    private List<Conferee> confereeList = new ArrayList<>();
    private boolean isUpDate;

    private String res;
    private String applyres;
    private int flag = 0;

    private String ConfereeString = new String("");
    private String ConfereeID = new String("");
    private String MeetingString = new String("");

    private EditText Editname;
    private EditText Editsponsor;
    private EditText EditTime;
    private EditText Editlength;
    private EditText Editmicrophone;
    private EditText Editprojector;
    private EditText Editnum;
    private EditText Editdep;
    private Httpclient httpurl = new Httpclient();


    private String adminId;
    private String URLceshi;

    private Meeting meetingApply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_apply);

        Intent intent = getIntent();
        adminId = intent.getStringExtra("name");

        meetingApply = new Meeting(intent.getStringExtra("Name"),
                intent.getStringExtra("Date"),
                intent.getStringExtra("Time"),
                intent.getStringExtra("Shenqingzhuangtai"),
                intent.getStringExtra("ID"));



        confereeListView = (ListView) findViewById(R.id.confereeListView);
        updateConfereeButton = (Button) findViewById(R.id.updateConfereeButton);
        submitApplyButton = (Button) findViewById(R.id.submitApplyButton);
        final EditText Editname = (EditText) findViewById(R.id.editText10);
        final EditText Editsponsor = (EditText) findViewById(R.id.editText11);
        final EditText EditTime = (EditText) findViewById(R.id.editText12);
        final EditText Editlength = (EditText) findViewById(R.id.editText13);
        final EditText Editmicrophone = (EditText) findViewById(R.id.editText14);
        final EditText Editprojector = (EditText) findViewById(R.id.editText15);
        final EditText Editnum = (EditText) findViewById(R.id.editText16);
        query = (Button) findViewById(R.id.query);
        Editdep = (EditText) findViewById(R.id.editTextquery);

        Editname.setText(meetingApply.getName());
        EditTime.setText(meetingApply.getName());

        // initconfereelist();
//        confereeAdapter confereeAdapter=new confereeAdapter(meetingApply.this,R.layout.confereelayout,confereeList);
//        confereeListView.setAdapter(confereeAdapter);

        confereeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conferee conferee = confereeList.get(position);
                Toast.makeText(meetingApply.this, conferee.getName() + "添加成功", Toast.LENGTH_SHORT).show();
                ConfereeString = ConfereeString + conferee.getName() + ";";
                ConfereeID = ConfereeID + conferee.getId() + ";";
            }
        });
        updateConfereeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(meetingApply.this, "请重新选择参会名单", Toast.LENGTH_SHORT).show();
                ConfereeString = "";
                ConfereeID = "";
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                confereeAdapter confereeAdapter = new confereeAdapter(meetingApply.this, R.layout.confereelayout, confereeList);
                confereeListView.setAdapter(null);
                confereeListView.setAdapter(confereeAdapter);

                confereeAdapter.clear();
                confereeAdapter.notifyDataSetChanged();

                String que = Editdep.getText().toString();
//                final TextView ceshi = (TextView) findViewById(R.id.test);
//                ceshi.setText(que);
                sendRequest(que);
                while (flag == 0) {
                }
                if (!res.equals("WTFnull")) {
                    String[] nameList = res.split("\\|");

                    for (int i = 0; i < nameList.length; i++) {
                        String listitem[] = nameList[i].split(";");
                        Conferee conferee1 = new Conferee(listitem[0], listitem[1]);
                        confereeList.add(conferee1);
                    }
                }
                flag = 0;

            }

        });
        submitApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meeting meetingApply = new Meeting(Editname.getText().toString(),
                        Editsponsor.getText().toString(),
                        EditTime.getText().toString(),
                        EditTime.getText().toString(),
                        Editlength.getText().toString(),
                        Editmicrophone.getText().toString(),
                        Editprojector.getText().toString(),
                        Editnum.getText().toString());
                //http://10.128.199.207:8080/meeting/insert?
                // Name=AI讨论会
                // &Sponsor=计算机学院
                // &StartTime=201911241400
                // &EndTime=201911241800
                // &Projector=2
                // &Headcount=100
                // &Microphone=3
                // &adminId=liming
                // &peoples=2019140576;2019140577;2019140578;2019140579
                final String http = (httpurl.httpurl + ":8080/meeting/insert?Name=" + meetingApply.getName() +
                        "&Sponsor=" + meetingApply.getMeetingsponsor() +
                        "&StartTime=" + meetingApply.getTime() +
                        "&EndTime=" + meetingApply.getMeetinglength() +
                        "&Projector=" + meetingApply.getProjector() +
                        "&Headcount=" + meetingApply.getNum() +
                        "&Microphone=" + meetingApply.getMicrophone() +
                        "&AdmName=" + adminId +
                        "&peoples=2019140576;2019140577;2019140578;2019140579");
                        //"&peoples=" + ConfereeID);
                final TextView ceshi = (TextView) findViewById(R.id.test);
                //ceshi.setText(http);

                //sendRequest1(http);
                //while (flag == 0) { }

                /*
                Toast.makeText(meetingApply.this,
                        "会议名称 : " + meetingApply.getName() +
                                "\n主办单位 : " + meetingApply.getMeetingsponsor() +
                                "\n会议时间 : " + meetingApply.getDate() +
                                "\n会议时长 : " + meetingApply.getMeetinglength() +
                                "\n麦克风 : " + meetingApply.getMicrophone() +
                                "\n投影仪 : " + meetingApply.getProjector() +
                                "\n人数 : " + meetingApply.getNum() +
                                "\n参会人员 :\n" + ConfereeString + "\n\n\n会议提交成功", Toast.LENGTH_LONG).show();
                //在这里给服务器传会议信息和参会名单confereeId
                //ceshi.setText(URLceshi);

                 */
                //flag = 0;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(http)
                                .method("GET", null)
                                .build();
                        Response response = null;
                        response = client.newCall(request).execute();
                        String s = response.body().string();
                        if (!s.equals(""))toast(s);
                        else toast("添加失败");
                        } catch (IOException e) {
                            toast("添加失败");
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    private void initconfereelist() {
//        Conferee conferee1=new Conferee("2019865423","王玉平");
//        Conferee conferee2=new Conferee("2019652354","陈云会");
//        Conferee conferee3=new Conferee("2019632584","殷霄寒");
//        Conferee conferee4=new Conferee("2019658423","阎瑞波");
//        Conferee conferee5=new Conferee("2019156115","韩宁");
//        Conferee conferee6=new Conferee("2019658156","林晴雯");
//        Conferee conferee7=new Conferee("2019615464","刘戎");
//        Conferee conferee8=new Conferee("2019684651","王天娇");
//
//
//        confereeList.add(conferee1);
//        confereeList.add(conferee2);
//        confereeList.add(conferee3);
//        confereeList.add(conferee4);
//        confereeList.add(conferee5);
//        confereeList.add(conferee6);
//        confereeList.add(conferee7);
//        confereeList.add(conferee8);
//        confereeList.add(conferee1);
//        confereeList.add(conferee2);
//        confereeList.add(conferee3);
//        confereeList.add(conferee4);
//        confereeList.add(conferee5);
//        confereeList.add(conferee6);
//        confereeList.add(conferee7);
//        confereeList.add(conferee8);
    }

    private void sendRequest(final String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL urlquery = new URL(httpurl.httpurl + ":8080/nameList/findByDpt?department=" + query);
                    connection = (HttpURLConnection) urlquery.openConnection();
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
                    res = response.toString();
                    flag = 1;
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

    private void sendRequest1(final String http) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {

                    URL url= new URL(http);
                    URLceshi = http;
                    //flag = 1;
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
                    applyres = response.toString();
                    flag = 1;
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
    private void toast(final String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(meetingApply.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}




