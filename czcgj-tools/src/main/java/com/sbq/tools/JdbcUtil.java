package com.sbq.tools;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * jdbc 工具类
 * 
 * @version 1.0 2010-12-07
 * @author wuyy
 */
public class JdbcUtil
{
	private static DataSource dataSource = null;
	private static DataSource dataSourceCtais = null;

	// private static String user = "wxgs";
	// private static String password = "wxgs";
	// private static String url = "jdbc:oracle:thin:@112.21.191.72:8082:ORCL";

	public static Connection getOracleConnection() throws Exception
	{
		InputStream inputStream = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties p = new Properties();
		p.load(inputStream);
		inputStream.close();

		String url = p.getProperty("jdbc.url");
		String user = p.getProperty("jdbc.username");
		String password = p.getProperty("jdbc.password");
		
		Connection con = null;
		try
		{
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, user, password);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return con;
	}

	public static Connection getConnection()
	{
		if (dataSource == null)
		{
			dataSource = (DataSource) SpringContext.getBean("dataSource");
		}
		Connection con = null;
		try
		{
			con = dataSource.getConnection();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return con;
	}

	public static Connection getConnectionCtais()
	{
		if (dataSourceCtais == null)
		{
			dataSourceCtais = (DataSource) SpringContext.getBean("dataSourceCtais");
		}
		Connection con = null;
		try
		{
			con = dataSourceCtais.getConnection();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return con;
	}

	public static long nextId(String sequence, Connection con) throws SQLException
	{
		long nextId = -1;
		Statement st = null;
		ResultSet rs = null;
		try
		{
			st = con.createStatement();
			rs = st.executeQuery("select " + sequence + ".nextval from dual");
			rs.next();
			nextId = rs.getLong(1);
		}
		finally
		{
			JdbcUtil.close(rs, st);
		}
		return nextId;
	}

	public static void close(ResultSet rs, Statement st, Connection con)
	{
		close(rs);
		close(st);
		close(con);
	}

	public static void close(ResultSet rs, PreparedStatement pst, Connection con)
	{
		close(rs);
		close(pst);
		close(con);
	}

	public static void close(ResultSet rs, Statement st)
	{
		close(rs);
		close(st);
	}

	public static void close(ResultSet rs, PreparedStatement pst)
	{
		close(rs);
		close(pst);
	}

	public static void close(Statement st, Connection con)
	{
		close(st);
		close(con);
	}

	public static void close(PreparedStatement pst, Connection con)
	{
		close(pst);
		close(con);
	}

	public static void close(ResultSet rs)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void close(Statement st)
	{
		try
		{
			if (st != null)
			{
				st.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pst)
	{
		try
		{
			if (pst != null)
			{
				pst.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void close(Connection con)
	{
		try
		{
			if (con != null)
			{
				con.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public static void rollback(Connection con)
	{
		try
		{
			if (con != null)
			{
				con.rollback();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}
