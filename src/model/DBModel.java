package model;

import java.sql.Connection;
import java.util.Objects;

public class DBModel {
	
	private final Connection connection;

	public DBModel(final Connection connection) {
		Objects.requireNonNull(connection, "Trying to load DB with null connection");
		this.connection = connection;
	}
	
	
	
	
}
