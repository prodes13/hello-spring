package ro.siit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.siit.domain.Book;
import ro.siit.model.BookDto;
import ro.siit.repository.AuthorRepository;
import ro.siit.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public List<BookDto> findByAuthor(final Long authorId) {
        final List<Book> books = authorRepository.findById(authorId)
                .map(a -> bookRepository.findByAuthor(a))
                .orElse(new ArrayList<>());
        return books.stream()
                .map(b -> new BookDto(b.getId(), b.getTitle()))
                .collect(Collectors.toList());
    }
}
