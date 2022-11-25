package com.example.mobile;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class connect {
    boolean connected = false;
    EditText ip_edit;
    TextView show_text;
    String return_data = null;
    // about socket
    private Handler mHandler;
    private Socket socket;

    private DataOutputStream outstream;
    private DataInputStream instream;
    private String newip = "192.168.219.160";
    private int port = 8000;

    void connect(){

        mHandler = new Handler(Looper.getMainLooper());
        Log.w("connect","연결 하는중");

        Thread checkUpdate = new Thread(){
            public void run(){

                // Access server
                try{
                    socket = new Socket(newip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                }catch (IOException e1){
                    Log.w("서버 접속 못함", "서버 접속 못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ","안드로이드에서 서버로 연결 요청");

                try{
                    outstream = new DataOutputStream(socket.getOutputStream());
                    instream = new DataInputStream(socket.getInputStream());
                    outstream.writeUTF("안드로이드에서 서버로 연결 요청");
                }catch(IOException e){
                    e.printStackTrace();
                    Log.w("버퍼","버퍼 생성 잘못 됨");
                }
                Log.w("버퍼","버퍼 생성 잘 됨");
                connected = true;
                try{
                    while(true){
                        String msg = "java test message - ";
                        if (connected == true){
                            byte[] data = msg.getBytes();
                            ByteBuffer b1 = ByteBuffer.allocate(4);
                            b1.order(ByteOrder.LITTLE_ENDIAN);
                            b1.putInt(data.length);
                            outstream.write(b1.array(),0,4);
                            outstream.write(data);

                            data = new byte[4];
                            instream.read(data,0,4);
                            ByteBuffer b2 = ByteBuffer.wrap(data);
                            b2.order(ByteOrder.LITTLE_ENDIAN);
                            int length = b2.getInt();
                            data = new byte[length];
                            instream.read(data,0,length);
                            msg = new String(data,"UTF-8");

                            //msg = instream.read(data);
                            return_data= msg;
                            //show_text.setText(msg);
                            connected = false;
                        }
                    }
                }catch(Exception e){

                }
            }
        };
        checkUpdate.start();
    }
}
