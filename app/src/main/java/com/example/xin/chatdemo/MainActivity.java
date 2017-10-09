package com.example.xin.chatdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView show;
    EditText message;
    Button send;
    Socket socket;//声明套接字对象
    Thread thread;//声明线程对象
    Handler handler;
    DataInputStream dataInputStream;//客户端的输入流
    DataOutputStream dataOutputStream;//客户端的输出流
    public static final int PORT = 4800;
    public static final String IPADDRESS = "192.168.56.1";
    private String newMessage;//接收服务端发送过来的消息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏聊天界面的标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //将接收到的新消息显示到客户端的TextView中
                show.append(newMessage + '\n');
                super.handleMessage(msg);
            }
        };
        new EchoThread(MainActivity.this).start();
        init();//初始化各种组件

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        show = (TextView) findViewById(R.id.showTv);
        show.setText("");
        message = (EditText) findViewById(R.id.messageEt);
        send = (Button) findViewById(R.id.sendBt);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myMessage = message.getText().toString();
                if (myMessage != null){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dataOutputStream.writeUTF(myMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

            }
        });
    }

    private class EchoThread extends Thread{
        private Context context;
        EchoThread(Context context){
            this.context = context;
        }

        @Override
        public void run() {
            if (socket == null){
                try {
                    socket = new Socket(IPADDRESS,PORT);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF("有人上线了");
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            while (true){
                try {
                    newMessage = dataInputStream.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(handler.obtainMessage());
            }
        }
    }

}
