package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL="jdbc:mysql://localhost/";
    private static final String USER ="root";
    private static final String PASSWORD ="root";
    private static final int TIMEOUT=5;

    private Connection connection;

    public JDBConnectionWrapper(String schema)
    {
        try{
            Class.forName(JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL+schema,USER,PASSWORD);
            //createTables();

        }catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private void createTables() throws SQLException
    {
        Statement statement= connection.createStatement();
        String sql = "create table if not exists book(" +
                "id bigint not null auto_increment," +
                "author varchar(500) not null," +
                "title varchar(500) not null,"+
                "publishedDate datetime default null,"+
                "primary key(id)," +
                "unique key id_unique(id)" +
                ") engine=InnoDB auto_increment=0 default charset=utf8;";
    statement.execute(sql);
    }
     public boolean testConnection() throws SQLException
     {
         return connection.isValid(TIMEOUT);
     }
    public Connection getConnection()
    {
        return connection;
    }

}
