package com.sbq.tools;

import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Connection;

/**
 * 
 * @author LiuHui
 */
public class SqlUtil
{

	/**
	 * 
	 * Description:创建Clob或者Blob
	 * 
	 * @param conn
	 * @param lobClassName
	 *            oracle.sql.CLOB或者oracle.sql.BLOB
	 * @return oracle.sql.CLOB或者oracle.sql.BLOB对象
	 * @throws Exception
	 */
	public static Object createOracleLob(Connection conn, String lobClassName) throws Exception
	{
		Class lobClass = conn.getClass().getClassLoader().loadClass(lobClassName);
		final Integer DURATION_SESSION = new Integer(lobClass.getField("DURATION_SESSION").getInt(null));
		final Integer MODE_READWRITE = new Integer(lobClass.getField("MODE_READWRITE").getInt(null));
		Method createTemporary = lobClass.getMethod("createTemporary", new Class[]
		{
				Connection.class, boolean.class, int.class
		});
		Object lob = createTemporary.invoke(null, new Object[]
		{
				conn, false, DURATION_SESSION
		});
		Method open = lobClass.getMethod("open", new Class[]
		{
			int.class
		});
		open.invoke(lob, new Object[]
		{
			MODE_READWRITE
		});
		return lob;
	}

	/**
	 * 
	 * Description:将Clob对象转换为String对象,Blob处理方式与此相同
	 * 
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String oracleClob2Str(Clob clob) throws Exception
	{
		return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
	}

	/**
	 * 
	 * Description:将string对象转换为Clob对象,Blob处理方式与此相同
	 * 
	 * @param str
	 * @param lob
	 * @return
	 * @throws Exception
	 */
	public static Clob oracleStr2Clob(String str, Clob lob) throws Exception
	{
		Method methodToInvoke = lob.getClass().getMethod("getCharacterOutputStream", (Class[]) null);
		Writer writer = (Writer) methodToInvoke.invoke(lob, (Object[]) null);
		writer.write(str);
		writer.close();

		return lob;
	}
}
