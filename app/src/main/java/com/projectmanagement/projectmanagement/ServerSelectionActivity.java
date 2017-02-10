package com.projectmanagement.projectmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projectmanagement.projectmanagement.Models.ServerInfo;
import com.thoughtworks.xstream.XStream;

import org.w3c.dom.Text;

import java.util.List;

public class ServerSelectionActivity extends AppCompatActivity implements DeserializeCallback{

    private LinearLayout serverInfoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_selection);

        serverInfoContainer = (LinearLayout) findViewById(R.id.server_info_container);

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        HttpManager httpManager = new HttpManager(this);
        httpManager.pulldata(this, new String[]{"action", "username", "password"}, new String[]{"getServerInfo", username, password});
    }

    @Override
    public void onSuccess(String response) {
        if (!response.equals("")) {
            XStream xStream = new XStream();
            xStream.alias("ServerInfo", ServerInfo.class);
            List<ServerInfo> serverInfoList = (List<ServerInfo>)xStream.fromXML(response);

            LayoutInflater layoutInflater = getLayoutInflater();
            for (ServerInfo serverInfo : serverInfoList) {
                FrameLayout server_info_layout = (FrameLayout) layoutInflater.inflate(R.layout.server_info_row, serverInfoContainer, false);

                LinearLayout textview_holders = (LinearLayout) server_info_layout.getChildAt(0);
                TextView server_name = (TextView)textview_holders.getChildAt(0);
                TextView server_status = (TextView)textview_holders.getChildAt(1);

                server_name.setText(serverInfo.name);
                server_status.setText(serverInfo.status);

                serverInfoContainer.addView(server_info_layout);
            }
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
}
