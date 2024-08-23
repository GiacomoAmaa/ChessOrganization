package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * an utility wrapper to build statements safely.
 */
public final class DAOUtils {

    private DAOUtils() {
    }

    /**
     * Establishes a connection to a MySQL daemon running locally at port 3306.
     * @param database
     * @param username
     * @param password
     * @return the connection
     */
    public static Connection localMySQLConnection(final String database, final String username,
            final String password) {
        try {
            var host = "localhost";
            var port = "3306";
            var connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;
            return DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }


    /**
     * We must always prepare a statement to make sure we do not fall victim to SQL injection:
     * https://owasp.org/www-community/attacks/SQL_Injection.
     * This is a helper that prepares the statement with all the values we give it:
     * @param connection
     * @param query
     * @param values
     * @return the prepared statement
     * @throws SQLException
     */
    public static PreparedStatement prepare(final Connection connection, final String query,
            final Object... values) throws SQLException {
        PreparedStatement statement = null;
        try {
            // Statement.RETURN_GENERATED_KEYS might be the problem!!!
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement;
        } catch (Exception e) {
            if (statement != null) {
                statement.close();
            }
            throw e;
        }
    }
}
