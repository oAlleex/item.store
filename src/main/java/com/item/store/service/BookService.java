package com.item.store.service;

import com.item.store.dto.BookDto;
import com.item.store.entity.Book;
import com.item.store.mapper.BookMapper;
import com.item.store.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public void saveBook(BookDto bookDto, MultipartFile file) throws IOException {
        Book book = bookMapper.bookMapper(bookDto,file);
        bookRepository.save(book);
    }
}
