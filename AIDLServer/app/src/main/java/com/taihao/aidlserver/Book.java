package com.taihao.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author chenwei
 * @date 2019/6/25
 * description：
 */
public class Book implements Parcelable {

    private String name;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    protected Book(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                '}';
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
    }

}
