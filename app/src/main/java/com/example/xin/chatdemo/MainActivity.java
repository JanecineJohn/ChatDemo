package com.example.xin.chatdemo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView show;
    EditText msg;
    Button send;
    Handler handler;
    Socket client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new MyHandler();
        show = (TextView) findViewById(R.id.show);
        msg = (EditText) findViewById(R.id.msg);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = msg.getText().toString();
                new EchoThread(MainActivity.this,message).start();
            }
        });
    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msge) {
            switch (msge.what){
                case 1:
                    Toast.makeText(MainActivity.this,"建立连接失败",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String message = (String) msge.obj;
                    show.append(message + "\n");
                    break;
            }
        }
    }

    private class EchoThread extends Thread{
        private Context context;
        private String msg;
        EchoThread(Context context,String msg){
            this.context = context;
            this.msg = msg;
        }

        @Override
        public void run() {
            if (client == null){
                //如果是初次接入服务器，即无socket，就创建一个
                try {
                    client = new Socket("192.168.56.1",4700);
                } catch (IOException e) {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
            try {
                BufferedReader in;
                PrintWriter out;
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream());
                String line = msg;
                out.println(line);
                out.flush();
                Message message = new Message();
                message.obj = in.readLine();
                message.what = 2;
                handler.sendMessage(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
