package com.taihao.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.taihao.aidlserver.Book;
import com.taihao.aidlserver.BookController;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "client";
    private BookController bookController;
    private boolean connected;
    private List<Book> bookList;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            bookController = BookController.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            connected = false;
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_getBookList:
                    Log.d(TAG, String.valueOf(connected));
                    if(connected) {
                        try {
                            bookList = bookController.getBookList();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        log();
                    }
                    break;
                case R.id.btn_addBook_inOut:
                    if(connected) {
                        Book book = new Book("这是一本新书 InOut");
                        try {
                            bookController.addBookInOut(book);
                            Log.d(TAG, "向服务器以InOut方式添加了一本新书");
                            Log.d(TAG, "新书名：" + book.getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case R.id.btn_addBook_in:
                    if(connected) {
                        Book book = new Book("这是一本新书 In");
                        try {
                            bookController.addBookIn(book);
                            Log.d(TAG, "向服务器以In方式添加了一本新书");
                            Log.d(TAG, "新书名：" + book.getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case R.id.btn_addBook_out:
                    if(connected) {
                        Book book = new Book("这是一本新书 Out");
                        try {
                            bookController.addBookOut(book);
                            Log.d(TAG, "向服务器以Out方式添加了一本新书");
                            Log.d(TAG, "新书名：" + book.getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_addBook_inOut).setOnClickListener(clickListener);
        findViewById(R.id.btn_getBookList).setOnClickListener(clickListener);
        findViewById(R.id.btn_addBook_in).setOnClickListener(clickListener);
        findViewById(R.id.btn_addBook_out).setOnClickListener(clickListener);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.taihao.aidlserver.action");
        intent.setPackage("com.taihao.aidlserver");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connected) {
            unbindService(serviceConnection);
        }
    }

    private void log() {
        for (Book book : bookList) {
            Log.d(TAG, book.toString());
        }
    }
}
