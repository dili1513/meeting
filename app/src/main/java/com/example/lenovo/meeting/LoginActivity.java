package com.example.lenovo.meeting;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hmh.Httpclient;
import hmh.applicant;
import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    //一个账号密码数组
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    // private UserLoginTask mAuthTask = null;
    private applicant user = null;

    // UI references.
    private TextView tiaoshi;//调试
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private CheckBox administratorCheckbox;
    private CheckBox userCheckbox;
    private boolean administrator;
    private int identity=0;

    private String res;
    private int flag=0;

    private String email="";
    private String password="";
    private Httpclient httpurl=new Httpclient();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        Intent intent = new Intent(LoginActivity.this, userActivity.class);
        intent.putExtra("adminID",email);
        startActivity(intent);
        finish();

         */


        // Set up the login form.
        tiaoshi = (TextView) findViewById(R.id.textView);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        administratorCheckbox=(CheckBox)findViewById(R.id.administrator);
        userCheckbox=(CheckBox)findViewById(R.id.user);
        userCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    administratorCheckbox.setChecked(false);
                    administrator=true;
                    identity=0;
                }
            }
        });
        administratorCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    userCheckbox.setChecked(false);
                    administrator=false;
                    identity=1;
                }
            }
        });

        administratorCheckbox.setChecked(true);



        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mEmailView.setText("songlinming");
        mPasswordView.setText("159123");

    }


    private void attemptLogin() {

        // 错误提示去除
        mEmailView.setError(null);
        mPasswordView.setError(null);



        // 取得输入
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        //第一次
        tiaoshi.setText("第一次" + "email " + email + " password " + password);

        boolean cancel = false;
        View focusView = null;

        // 检查密码
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 检查邮件
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //这里的输入合法了
            //第二次
            user = new applicant(email, password);
            if (administratorCheckbox.isChecked()) {
                sendRequest(email);
            }
            if (userCheckbox.isChecked()) {
                sendRequest(email);



            }
        }

        //判断账号密码是否合法

    }private boolean isEmailValid (String email){
        //TODO: Replace this with your own logic
        //return email.contains("@");
        return true;
    }

    private boolean isPasswordValid (String password){
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private void sendRequest(final String email){
        new Thread(new Runnable(){
            @Override
            public void run(){


                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    final URL urluser = new URL(httpurl.httpurl+":8080/CRUD/ListUserByname?name=" + email);
                    final URL urladmin = new URL(httpurl.httpurl+":8080/CRUD/ListAdminByname?name=" + email);
                    Log.d(TAG, "run: " + email);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tiaoshi.setText(urluser.toString());
                        }
                    });

                    if (identity==0)
                        connection = (HttpURLConnection) urluser.openConnection();
                    if (identity==1)
                        connection = (HttpURLConnection) urladmin.openConnection();
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tiaoshi.setText(urluser.toString());
                            if (identity==0)
                            {
                                if (password.equals(res)) {
                                    tiaoshi.setText("用户" + "email " + email + " password " + password+" res "+res);
                                    Log.d("result", "对比成功");
                                    Intent intent = new Intent(LoginActivity.this, userActivity.class);
                                    intent.putExtra("userID",email);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                                    mPasswordView.requestFocus();
                                }}

                            if (identity==1){

                                if (password.equals(res)) {
                                    Log.d(TAG, "run: " + email);
                                    Log.d("result", "对比成功");
                                    Intent intent = new Intent(LoginActivity.this, adminActivity.class);
                                    intent.putExtra("adminID",email);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                                    mPasswordView.requestFocus();
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

