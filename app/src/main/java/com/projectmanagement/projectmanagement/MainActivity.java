package com.projectmanagement.projectmanagement;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.projectmanagement.projectmanagement.Models.ServerInfo;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DeserializeCallback{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText passwordEditText = (EditText) findViewById(R.id.password_et);
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());

        final Activity activity = this;

        Button login = (Button) findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpManager httpManager = new HttpManager(activity);

                EditText username = (EditText) findViewById(R.id.username_et);
                EditText password = (EditText) findViewById(R.id.password_et);

                httpManager.pulldata((DeserializeCallback)activity, new String[]{"action", "username", "password"}, new String[]{"login", username.getText().toString(), password.getText().toString()});
            }
        });

        ServerInfo serverInfo = new ServerInfo();
        serverInfo.name = "WHU";
        serverInfo.status = "DOWN";
        List<ServerInfo> serverInfoList = new ArrayList<ServerInfo>();
        serverInfoList.add(serverInfo);
        XStream xStream = new XStream();
        xStream.alias("ServerInfo", ServerInfo.class);
        Log.e("OKK", String.valueOf(xStream.toXML(serverInfoList)));
    }

    @Override
    public void onSuccess(String response) {
        if (response.equals("success")) {
            Intent intent = new Intent(MainActivity.this, ServerSelectionActivity.class);

            EditText username = (EditText) findViewById(R.id.username_et);
            EditText password = (EditText) findViewById(R.id.password_et);

            intent.putExtra("username", username.getText().toString());
            intent.putExtra("password", password.getText().toString());
            startActivity(intent);
//            finish();
        } else {
            Toast.makeText(this, "Wrong Login Credentials", Toast.LENGTH_LONG).show();
        }
    }
}
