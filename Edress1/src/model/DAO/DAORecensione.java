package model.DAO;

import java.sql.*;
import java.util.ArrayList;

import model.bean.Prodotto;
import model.bean.Recensione;
import model.bean.User;
import model.connection.ConnectionPool;

public class DAORecensione
{
	public static ArrayList<Integer> media(String codice) throws SQLException
	{
		ArrayList<Integer> val=new ArrayList<>();
		try
		{
			con = ConnectionPool.getConnection();
			statement=con.prepareStatement(avg);
			statement.setString(1,codice);
			set=statement.executeQuery();
			while (set.next())
			{
				val.add(set.getInt("media"));
				val.add(set.getInt("numero"));
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
		return val;
	}

	public static boolean addRecensione(Recensione rec) throws SQLException
	{
		String controlloAquisto="select *\r\n" + 
				"from(select nome,cognome,utente.email,numOrdine\r\n" + 
				"from utente inner join ordine \r\n" + 
				"on utente.email=ordine.email\r\n" + 
				"where utente.email=?) as table1 inner join \r\n" + 
				"	composizione on table1.numOrdine=composizione.id\r\n" + 
				"	where composizione.codiceProd=?;";
		boolean flag=false;
		
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(controlloAquisto);
			statement.setString(1,rec.getUser().getEmail());
			statement.setString(2,rec.getProdotto().getCodice());
			set=statement.executeQuery();
			set.last();
			if(flag=(set.getRow()>0))
			{
				statement=con.prepareStatement(addRec);
				statement.setString(1,rec.getNstelle());
				statement.setString(2,rec.getCommento());
				statement.setString(3,rec.getUser().getEmail());
				statement.setString(4,rec.getProdotto().getCodice());
				flag=statement.executeUpdate()>0;
				con.commit();
				con.close();
			}
		}
        catch (SQLException ex)
        {
            if(flag)
            {
                statement=con.prepareStatement(updateRec);
                statement.setString(1,rec.getNstelle());
                statement.setString(2,rec.getCommento());
                statement.setString(3,rec.getUser().getEmail());
                statement.setString(4,rec.getProdotto().getCodice());
                flag=statement.executeUpdate()>0;
                con.commit();
                con.close();
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

		return flag;
	}

	public static ArrayList<Recensione> showRecensione(Prodotto prod) throws SQLException
	{
		ArrayList<Recensione> rec=null;
		Recensione recensione=null;
		User user=null;
		Prodotto prodotto=null;

		try
		{
			con = ConnectionPool.getConnection();
			con.commit();
			con.close();
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(showRec);
			statement.setString(1,prod.getCodice());
			set=statement.executeQuery();
			rec=new ArrayList<Recensione>();
			while(set.next())
			{
				recensione=new Recensione();
				prodotto=new Prodotto();
				user=new User();

				recensione.setNstelle(set.getString(1));
				recensione.setCommento(set.getString(2));
				user.setEmail(set.getString(3));
				user.setNome(set.getString(5));
				user.setCognome(set.getString(6));
				prodotto.setCodice(set.getString(4));
				recensione.setUser(user);
				recensione.setProdotto(prodotto);
				rec.add(recensione);
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
		return rec;
	}
	
	public static int numberRecensione(User utente,Prodotto prod) throws SQLException
	{
		String controlloAquisto="select *\r\n" + 
				"from(select nome,cognome,utente.email,numOrdine\r\n" + 
				"from utente inner join ordine \r\n" + 
				"on utente.email=ordine.email\r\n" + 
				"where utente.email=?) as table1 inner join \r\n" + 
				"	composizione on table1.numOrdine=composizione.id\r\n" + 
				"	where composizione.codiceProd=?;";
		int flag=0;
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(controlloAquisto);
			statement.setString(1,utente.getEmail());
			statement.setString(2,prod.getCodice());
			set=statement.executeQuery();
			set.last();
			flag=set.getRow();
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

	private static Connection con=null;
	private static PreparedStatement statement=null;
	private static 	ResultSet set=null;
	private static String addRec="insert into recensione (valutazione,commento,email,id) values(?,?,?,?)";
	private static String showRec="select valutazione,commento,utente.email,id,nome,cognome from recensione inner join utente on utente.email=recensione.email where id=?";
	private static String updateRec="UPDATE recensione SET valutazione=?,commento=? WHERE email=? && id=?";
	private static String avg="select count(*) as numero,avg(valutazione) as media\n" +
		"from recensione\n" +
		"where id=?;";
}
