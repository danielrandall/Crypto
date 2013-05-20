package server.databases;

import java.sql.Connection;

public interface Database {
	
	Connection getConnection();

}
