package model;

import java.sql.Connection;
import java.sql.SQLException;

import data.DAOException;
import data.DAOUtils;

public class DBModel {
	
	private static final Connection connection = DAOUtils.localMySQLConnection("chessorg", "root", "");
	
	public static Connection getConnection() {
		return DBModel.connection;
	}
	
	public static void closeConnection() {
		try {
			DBModel.connection.close();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
}
