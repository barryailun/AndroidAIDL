package com.taihao.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenwei
 * @date 2019/6/25
 * description：
 */
public class AIDLService extends Service {

    private final String TAG = "Server";
    private List<Book> books;

    public AIDLService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        books = new ArrayList<>();
        initData();
    }

    private void initData() {
        Book book1 = new Book("活着");
        Book book2 = new Book("或者");
        Book book3 = new Book("创造属于你的美丽。");
        Book book4 = new Book("https://github.com/leavesC");
        Book book5 = new Book("http://www.jianshu.com/u/9df45b87cfdf");
        Book book6 = new Book("http://blog.csdn.net/new_one_object");
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
    }

    private final BookController.Stub stub = new BookController.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return books;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            if(book != null) {
                book.setName("服务器改了新书的名字 InOut");
                books.add(book);
            } else {
                Log.e(TAG, "接收到了一个空对象 InOut");
            }
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            if (book != null) {
                book.setName("服务器改了新书的名字 In");
                books.add(book);
            } else {
                Log.e(TAG, "接收到了一个空对象 In");
            }
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            if (book != null) {
                Log.e(TAG, "客户端传来的书的名字：" + book.getName());
                book.setName("服务器改了新书的名字 Out");
                books.add(book);
            } else {
                Log.e(TAG, "接收到了一个空对象 Out");
            }
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
