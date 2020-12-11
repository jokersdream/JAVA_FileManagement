import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;

public class DataProcessing {
    private static final String url="jdbc:mysql://localhost:3306/document?serverTimezone=Asia/Shanghai";
    private static final String username="root";
    private static final String password="1234qwer";
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static boolean connectedToDatabase = false;
    
    public static void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        connectedToDatabase = true;
    }
    
    public static void disconnectFromDatabase() {
        if (connectedToDatabase) {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                connectedToDatabase = false;
            }
        }
    }
    
    public static Doc searchDoc(String fileName_) throws SQLException {
        Doc temp = null;
        if (!connectedToDatabase) {
            throw new SQLException ("Not Connected to Database");
        }
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM doc_info WHERE filename = '" + fileName_ + "';";
        resultSet = statement.executeQuery(sql);
        
        if (resultSet.next()) {
            String ID = resultSet.getString("Id");
            String creator = resultSet.getString("creator");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");
            String description = resultSet.getString("description");
            String filename = resultSet.getString("filename");
            temp = new Doc(ID, creator, timestamp, description, filename);
        }
        return temp;
    }
    
    public static boolean deleteUser(String username_) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException ("Not Connected to Database");
        }
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "DELETE FROM user_info WHERE username = '" + username_ + "';";
        return statement.executeUpdate(sql) > 0;
    }
    
    public static Enumeration<Doc> getAllDocs() throws SQLException {
        Enumeration<Doc> docs;
        Vector docList = new Vector();
        Doc temp = null;
        if (!connectedToDatabase) {
            throw new SQLException ("Not Connected to Database");
        }
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM doc_info;";
        resultSet = statement.executeQuery(sql);
        
        while (resultSet.next()) {
            String ID = resultSet.getString("Id");
            String creator = resultSet.getString("creator");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");
            String description = resultSet.getString("description");
            String filename = resultSet.getString("filename");
            temp = new Doc(ID, creator, timestamp, description, filename);
            docList.addElement(temp);
        }
        docs = docList.elements();
        return docs;
    }
    
    public static Enumeration<User> getAllUser() throws SQLException {
        Enumeration<User> users;
        Vector userList = new Vector();
        User temp = null;
        if (!connectedToDatabase) {
            throw new SQLException ("Not Connected to Database");
        }
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM user_info;";
        resultSet = statement.executeQuery(sql);
        
        while (resultSet.next()) {
            String userName = resultSet.getString("username");
            String passWord = resultSet.getString("password");
            String role = resultSet.getString("role");
            switch (role) {
                case "Administrator" :
                    temp = new Administrator(userName, passWord, role);
                    break;
                case "Operator" :
                    temp = new Operator(userName, passWord, role);
                    break;
                case "Browser" :
                    temp = new Browser(userName, passWord, role);
                    break;
            }
            userList.addElement(temp);
        }
        users = userList.elements();
        return users;
    }
    
    public static boolean insertDoc(String Id_, String creator_, Timestamp timestamp_, String description_, String filename_) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException ("Not Connected to Database");
        }
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "INSERT INTO doc_info VALUES ('" + Id_ + "', '" + creator_ + "', '" + timestamp_ + "', '" + description_ + "', '" + filename_ + "');";
        return statement.executeUpdate(sql) > 0;
    }
    
    public static boolean insertUser(String username_, String password_, String role_) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException ("Not Connected to Database");
        }
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "INSERT INTO user_info VALUES ('" + username_ + "', '" + password_ + "', '" + role_ + "');";
        return statement.executeUpdate(sql) > 0;
    }
    
    public static boolean updateUser(String username_, String password_, String role_) throws SQLException {
        if (!connectedToDatabase) {
            throw new SQLException ("Not Connected to Database");
        }
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql1 = "UPDATE user_info SET password = '" + password_ + "' WHERE username = '" + username_ + "';";
        String sql2 = "UPDATE user_info SET role = '" + role_ + "' WHERE username = '" + username_ + "';";
        statement.executeUpdate(sql1);
        return statement.executeUpdate(sql2) > 0;
    }
    
    public static User searchUser(String username_, String password_) throws SQLException {
        User temp = null;
        if (!connectedToDatabase) {
            throw new SQLException ("Not Connected to Database");
        }
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql = "SELECT * FROM user_info WHERE username = '" + username_ + "';";
        resultSet = statement.executeQuery(sql);
    
        if (resultSet.next()) {
            String userName = resultSet.getString("username");
            String passWord = resultSet.getString("password");
            String role = resultSet.getString("role");
            if (passWord.equals(password_)) {
                switch (role) {
                    case "Administrator":
                        temp = new Administrator(userName, passWord, role);
                        break;
                        case "Operator":
                        temp = new Operator(userName, passWord, role);
                        break;
                    case "Browser":
                        temp = new Browser(userName, passWord, role);
                        break;
                }
            }
        }
        return temp;
    }
}