package launcher;

import controller.BookController;
import controller.LoginController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.user.AuthentificationService;
import service.user.AuthentificationServiceImpl;
import view.BookView;
import view.LoginView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public final class ComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthentificationService authentificationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepository bookRepository;
    private final BookView bookView;
   private final BookController bookController;
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

        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        this.authentificationService = new AuthentificationServiceImpl(userRepository,rightsRolesRepository);
        this.loginView = new LoginView(primaryStage);
        this.loginController = new LoginController(loginView,authentificationService);


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
    public AuthentificationService getAuthentificationService()
    {
        return authentificationService;
    }
    public UserRepository getUserRepository()
    {
        return userRepository;
    }
    public RightsRolesRepository getRightsRolesRepository()
    {
        return rightsRolesRepository;
    }
    public LoginView getLoginView()
    {
        return loginView;
    }

  /*  public static Stage getStage()
    {
        return stage;
    }

    public static Boolean getComponentsForTest()
    {
        return getComponentsForTest()
    }*/
}
