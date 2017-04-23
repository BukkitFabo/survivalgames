package de.debitorlp.server.survivalgames.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Georg
 *         <a href="mailto:georgsteinmetz@web.de">georgsteinmetz@web.de</a>
 */
public class Driver extends com.mysql.jdbc.Driver {
    private final java.sql.Driver delegateDriver;

    public Driver() throws ClassNotFoundException, SQLException {
        delegateDriver = new com.mysql.jdbc.Driver();
        DriverManager.registerDriver(this);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url.startsWith("jdbc:db:");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegateDriver.getParentLogger();
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        String mysqlUrl = url.replace("jdbc:db", "jdbc:mysql");
        return delegateDriver.connect(mysqlUrl, info);
    }
}
