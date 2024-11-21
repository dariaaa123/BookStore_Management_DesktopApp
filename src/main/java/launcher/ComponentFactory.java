package launcher;

import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.BookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public final class ComponentFactory {
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static volatile ComponentFactory instance;

    public static ComponentFactory getInstance(Boolean componentsForTest, Stage primaryStage)

    {
        if(instance == null)
        {
            synchronized (ComponentFactory.class)
            {
                if (instance == null)
                {
                    instance = new ComponentFactory(componentsForTest,primaryStage);
                }
            }
        }
        return instance;

    }

    private  ComponentFactory(Boolean componentsForTest, Stage primaryStage)
    {
        Connection connection = DatabaseConnectionFactory.getConncetionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository);
        List<BookDTO> booksDTOs= BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage, booksDTOs);
        this.bookController = new BookController(bookView,bookService);

    }
    public BookView getBookView()
    {
        return bookView;
    }
    public BookController getBookController()
    {
        return bookController;

    }
    public BookRepository getBookRepository()
    {
        return bookRepository;
    }
    public BookService getBookService()
    {
        return bookService;
    }
}
