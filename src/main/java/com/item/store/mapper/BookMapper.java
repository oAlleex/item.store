package com.item.store.mapper;

import com.item.store.dto.BookDto;
import com.item.store.entity.Book;
import com.item.store.entity.FileCover;
import com.item.store.repository.FileCoverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class BookMapper {

    private static String DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @Autowired
    private FileCoverRepository fileCoverRepository;

    public Book bookMapper(BookDto bookDto, MultipartFile file) throws IOException {
        Path fileName = Paths.get(DIRECTORY);
        FileCover fileCover = new FileCover();
        fileCover.setPath(fileName.toFile().getPath());

        FileCover fileSaved = fileCoverRepository.save(fileCover);

        final String fileExtension = Optional.ofNullable(file.getOriginalFilename())
                .flatMap(BookMapper::getFileExtension)
                .orElse("");

        final String targetFileName = fileSaved.getId() + "." + fileExtension;
        final Path targetPath = fileName.resolve(targetFileName);

        File f = new File(String.valueOf(targetPath));
        file.transferTo(f);

        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setPrice(Double.parseDouble(bookDto.getPrice()));
        book.setQuantity(Integer.parseInt(bookDto.getQuantity()));
        book.setFileCover(fileSaved);

        return book;
    }

    private static Optional<String> getFileExtension(String fileName) {
        final int indexOfLastDot = fileName.lastIndexOf('.');

        if (indexOfLastDot == -1) {
            return Optional.empty();
        } else {
            return Optional.of(fileName.substring(indexOfLastDot + 1));
        }
    }
}
