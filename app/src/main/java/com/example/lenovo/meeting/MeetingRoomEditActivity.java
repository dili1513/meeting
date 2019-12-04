package com.example.lenovo.meeting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hmh.Httpclient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MeetingRoomEditActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_name,edit_id;
    private Button btn_edit,btn_delete;
    private TextView is_Admin;
    private Intent intent;
    private boolean isAdmin;
    private String name;
    private Httpclient url = new Httpclient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_edit);
        intent = getIntent();
        edit_id = findViewById(R.id.edit_id);
        edit_name = findViewById(R.id.edit_name);
        btn_edit = findViewById(R.id.btn_edit);
        btn_delete = findViewById(R.id.btn_delete);
        is_Admin = findViewById(R.id.text_is_admin);
        edit_id.setText(intent.getStringExtra("ID"));
        edit_name.setText(intent.getStringExtra("Name"));
        btn_edit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        isAdmin = intent.getBooleanExtra("isAdmin",false);
        if (isAdmin){
            is_Admin.setText("由管理员添加");
        }else {
            is_Admin.setText("由用户添加");
        }
        name = intent.getStringExtra("name");
       // Log.d("name", name);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete:
                //btn_edit.setOnClickListener(null);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String nameType;
                        if (isAdmin)nameType = "Admname=";
                        else nameType = "Username=";
                        OkHttpClient client = new OkHttpClient();
                            Log.d("nameType", name);
                        Request request = new Request.Builder()
                                .url(url.httpurl+":8080/MeetingRoom/deleteAdminMeetingRoom?" +
                                        "Name=" + intent.getStringExtra("Name")+
                                        "&"+
                                        nameType +name)
                                .method("GET", null)
                                .build();
                            Log.d("request", request.toString());
                            Response response = client.newCall(request).execute();
                            String s = response.body().string();
                            if (!s.equals(""))toast(s);
                            else toast("删除失败");
                        } catch (Exception e) {
                            e.printStackTrace();
                            toast("删除失败");
                        }
                        finish();
                        btn_edit.setOnClickListener(MeetingRoomEditActivity.this);
                    }
                }).start();
                break;
            case R.id.btn_edit:
                //btn_delete.setOnClickListener(null);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            String url1 = url.httpurl+":8080/MeetingRoom/updateMeetingRoom?" +
                                    "Name=" + edit_name.getText()+
                                    "&"+
                                    "ID=" +intent.getStringExtra("ID");
                            Log.d("request", url1 );
                            Request request = new Request.Builder()
                                    .url(url1)
                                    .method("GET", null)
                                    .build();
                            Response response = client.newCall(request).execute();
                            String s = response.body().string();
                            if (!s.equals(""))toast(s);
                            else toast("修改失败");
                        } catch (Exception e) {
                            e.printStackTrace();
                            toast("修改失败");
                        }
                        finish();
                        btn_delete.setOnClickListener(MeetingRoomEditActivity.this);
                    }
                }).start();
                break;
            default:break;


        }
    }


    private void toast(final String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MeetingRoomEditActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
