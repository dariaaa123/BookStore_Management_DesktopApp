package mapper;

import model.Book;
import model.builder.BookBuilder;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDTO convertBookToBookDTO(Book book)
    {
        return new BookDTOBuilder().setTitle(book.getTitle()).setAuthor(book.getAuthor())
                .setStock(book.getStock()).setPrice(book.getPrice()).build();
    }
    public static Book convertBookDTOToBook(BookDTO bookDTO)
    {
        return new BookBuilder().setTitle(bookDTO.getTitle()).setAuthor(bookDTO.getAuthor()).setPublishedDate(LocalDate.of(2010,1,1))
                .setStock(bookDTO.getStock()).setPrice(bookDTO.getPrice()).build();
    }

    public static List<BookDTO> convertBookListToBookDTOList(List<Book> books)
    {
        return books.parallelStream().map(BookMapper::convertBookToBookDTO).collect(Collectors.toList());
    }

    public static List<Book> convertBookDTOToList(List<BookDTO> bookDTOS)
    {
        return bookDTOS.parallelStream().map(BookMapper::convertBookDTOToBook).collect(Collectors.toList());
    }
}
