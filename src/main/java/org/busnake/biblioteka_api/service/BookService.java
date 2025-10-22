package org.busnake.biblioteka_api.service;

import org.busnake.biblioteka_api.model.entities.Author;
import org.busnake.biblioteka_api.model.entities.Book;
import org.busnake.biblioteka_api.model.entities.Genre;
import org.busnake.biblioteka_api.repository.AuthorRepository;
import org.busnake.biblioteka_api.repository.BookRepository;
import org.busnake.biblioteka_api.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public Book saveBook(String title, String authorName, String genreName) {
        Author author = authorRepository.findByName(authorName)
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    newAuthor.setDateCreated(LocalDate.now());
                    return authorRepository.save(newAuthor);
                });

        Genre genre = genreRepository.findByName(genreName)
                .orElseGet(() -> {
                    Genre newGenre = new Genre();
                    newGenre.setName(genreName);
                    newGenre.setDateCreated(LocalDate.now());
                    return genreRepository.save(newGenre);
                });

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setDateCreated(LocalDate.now());
        book.setIsAvailable(true);

        return bookRepository.save(book);
    }
}