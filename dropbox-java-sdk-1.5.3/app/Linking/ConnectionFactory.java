package Linking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
        private String driverClassName;
        private static final String dbUser = "sa";
        private static final String dbPwd = "";

        private static ConnectionFactory connectionFactory = null;

        private ConnectionFactory() {
        	
            try {
            	Class.forName(driverClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public Connection getConnection(String connection) throws SQLException {
        	Connection conn = null;
            conn = DriverManager.getConnection(connection, dbUser, dbPwd);
            return conn;
        }

        public static ConnectionFactory getInstance() {
            if (connectionFactory == null) {
            	connectionFactory = new ConnectionFactory();
            }
            return connectionFactory;
        }
}