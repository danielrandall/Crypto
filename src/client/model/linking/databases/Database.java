package client.model.linking.databases;

import java.sql.Connection;

public interface Database {
	
	Connection getConnection();

}
