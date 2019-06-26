// BookController.aidl
package com.taihao.aidlserver;

import com.taihao.aidlserver.Book;

// Declare any non-default types here with import statements

interface BookController {
    List<Book> getBookList();
    void addBookInOut (inout Book book);
    void addBookIn(in Book book);
    void addBookOut(out Book book);
}
