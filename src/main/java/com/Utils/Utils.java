package com.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.Base.base;

public class Utils extends base{

	public ResultSet get_data_from_dataBase(String table, String query) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://honasa-mamaearth-aurora-cluster.cluster-cnfyh20enzgz.ap-south-1.rds.amazonaws.com:3306/"+table, "udit", "udnmde7382n4djsbd");
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(query);
		return result;
	}
}
