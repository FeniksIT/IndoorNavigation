package by.grsu.ftf.activity;

import android.os.AsyncTask;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import by.grsu.ftf.beacon.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    String serIpAddress;       // адрес сервера
    int port = 10000;           // порт
    String msg;                 // Сообщение
    final byte codeMsg = 1;     // Оправить сообщение
    final byte codeRotate = 2;  // Повернуть экран
    final byte codePoff = 3;    // Выключить компьютер
    byte codeCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startService(new Intent(MainActivity.this, BeaconSimulation.class));
    }

    public void onClick(View view) {
        serIpAddress = String.valueOf("192.168.100.5");
        if (serIpAddress.isEmpty()){
            Toast msgToast = Toast.makeText(this, "Введите ip адрес", Toast.LENGTH_SHORT);
            msgToast.show();
            return;
        }
        msg ="QQQQQQ";
        SenderThread sender = new SenderThread();
        codeCommand = codeMsg;
        sender.execute();
    }
    class SenderThread extends AsyncTask <Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                InetAddress ipAddress = InetAddress.getByName(serIpAddress);
                Socket socket = new Socket(ipAddress, port);
                //InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outputStream);
                ///DataInputStream in = new DataInputStream(inputStream);
                byte[] outMsg = msg.getBytes("UTF8");
                out.write(outMsg);
                out.flush();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }
}
