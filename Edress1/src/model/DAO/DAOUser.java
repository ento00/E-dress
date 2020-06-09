package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.bean.User;
import model.connection.ConnectionPool;
import utility.Utilites;

public class DAOUser
{
	public static boolean regUtente(User utente) throws SQLException 
	{
		boolean flag=false;

		try 
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(addUtente);
			statement.setString(1,utente.getEmail());
			statement.setString(2,utente.getPassword());
			statement.setString(3,utente.getNome());
			statement.setString(4,utente.getCognome());
			statement.setString(5,utente.getSesso());
			statement.setString(6,utente.getCittà());
			statement.setString(7,utente.getVia());
			statement.setInt(8,utente.getNumeroCivico());
			statement.setInt(9,utente.getCap());
			flag=statement.executeUpdate()>0;
			con.commit();
		}
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				ConnectionPool.rilasciaConnessione(con);
			}
		}
		return flag;
	}
	
	public static boolean checkEmail(String Email) throws SQLException
	{
		boolean flag=false;

		try 
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(checkEmail);
			statement.setString(1,Email);
			set=statement.executeQuery();
			set.last();
			flag=(set.getRow()>0);
		} 		
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				ConnectionPool.rilasciaConnessione(con);
			}
		}
		return flag;	
	}
	
	public static boolean checkLogin(String Email,String password) throws SQLException
	{
		boolean flag=false;

		try 
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(checkLogin);
			statement.setString(1,Email);
			statement.setString(2,password);
			set=statement.executeQuery();
			flag=set.next();
		} 		
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				ConnectionPool.rilasciaConnessione(con);
			}
		}
		return flag;	
	}
	
	public static boolean modificaAccount(String ...vars) throws SQLException
	{
		int j=1;
		boolean flag=false;
		modifyAccount="UPDATE utente SET";

		if(vars.length<0 || !Utilites.fieldOk(vars))
			throw new SQLException("parametri inseriti errati");
		for(int i=0;i<vars.length;i+=2)
		{
			if(i!=vars.length-4)
				modifyAccount+=" "+vars[i]+"=?,";
			else 
			{
				modifyAccount+=" "+vars[i]+"=? WHERE "+vars[vars.length-2]+"=?";
				break;
			}
		}
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(modifyAccount);
			for(int i=1;i<vars.length;i+=2,j++) 
				statement.setString(j,vars[i]);
			flag=statement.executeUpdate()>0;
			con.commit();
		}
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				ConnectionPool.rilasciaConnessione(con);
			}
		}
		return flag;
	}
	
	public static User showAccount(String Email) throws SQLException
	{
		User user=null;

		try 
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(showAccount);
			statement.setString(1,Email);
			set=statement.executeQuery();
			while(set.next())
			{	//potrei utilizzare un costruttore riducendo le righe di codice ,complessit� invariata
				user=new User();

				user.setEmail(set.getString(1));
				user.setPassword(set.getString(2));
				user.setNome(set.getString(3));
				user.setCognome(set.getString(4));
				user.setCittà(set.getString(5));
				user.setVia(set.getString(6));
				user.setNumeroCivico(set.getInt(7));
				user.setCap(set.getInt(8));
				user.setSesso(set.getString(9));
				user.setTipo(set.getString("tipo"));
			}
		} 		
		finally
		{
			try
			{
				if(statement!=null)
					statement.close();
			}
			finally
			{
				ConnectionPool.rilasciaConnessione(con);
			}
		}
		return user;	
	}

	
	private static Connection con;
	private static PreparedStatement statement;
	private static ResultSet set;
	private static String addUtente;
	private static String modifyAccount;
	private static String checkEmail;
	private static String showAccount;
	private static String checkLogin;

	static
	{
		addUtente="INSERT INTO utente(Email,Password,Nome,Cognome,sesso,citta,via,numeroCivico,cap) values(?,?,?,?,?,?,?,?,?)";
		checkEmail="SELECT nome FROM utente where Email=?";
		showAccount="SELECT * FROM utente where Email=?";
		checkLogin="SELECT nome FROM utente where Email=? AND Password=?";
	}
}
