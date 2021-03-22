package jdbc;

public class ConnexionBDD 
{
	private static String driver ="mysql",
			driverClassName = "com.mysql.cj.jdbc.Driver",
			host = "localhost", 
			port ="3306",
			database ="MDL",
			user = "root",
			password = "rootroot24",
			TimeZone = "?zeroDataTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
	
	static String getUrl() 
	{
		return "jdbc:" + driver + "://" + host + "/" + database + TimeZone;
	}
	
	static String getDriverClassName()
	{
		return driverClassName;
	}
	
	static String getUser() 
	{
		return user;
	}

	static String getPassword() 
	{
		return password;
	}
}
