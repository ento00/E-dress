package model.DAO;

import model.connection.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Prodotto;
import utility.Utilites;


public class DAOProdotto
{
	public static boolean addProduct(Prodotto prod) throws SQLException
	{
		boolean flag=false;

		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(addProduct);
			statement.setString(1,prod.getCodice());
			statement.setString(2,prod.getNome());
			statement.setString(3,prod.getImg());
			statement.setString(4,prod.getGenere());
			statement.setDouble(5,prod.getPrezzo());
			statement.setString(6,prod.getDescrizione());
			statement.setString(7,prod.getCategoria());
			statement.setString(8,prod.getBrand());
			statement.setInt(9,prod.getSconto());
			statement.setInt(10,prod.getPezzi());
			statement.setString(11,prod.getSize());
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

	public static boolean modifyProduct(String ...vars) throws SQLException /*Da fare piu veloce*/
	{
		boolean flag=false;
		int j=1;
		String modifyProduct="UPDATE prodotto SET";

		if(vars.length<0 || !Utilites.fieldOk(vars))
			return flag;
		for(int i=0;i<vars.length;i+=2)
			if(i!=vars.length-6 && i<vars.length-6)
				modifyProduct+=" "+vars[i]+"=?,";
			else 
			{
				modifyProduct+=" "+vars[i]+"=? WHERE visualizzabile=true";
				if(!Utilites.sizeIn(vars))
					modifyProduct+=" AND "+vars[vars.length-4]+"=? AND "+vars[vars.length-2]+"=?";
				else
					modifyProduct+=" AND "+vars[vars.length-2]+"=? ";
				break;	
			}
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(modifyProduct);
			for(int i=1;i<vars.length;i+=2) 
			{
				statement.setString(j,vars[i]);
				j++;
			}
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

	public static ArrayList<Prodotto> viewDiscountProduct(String genere) throws SQLException
	{
		ArrayList<Prodotto> prodotti=new ArrayList<Prodotto>();
		String viewProduct="SELECT * FROM prodotto where visualizzabile=true AND sconto>0";
		try
		{
			con = ConnectionPool.getConnection();
			con.commit();
			con.close();
			con=ConnectionPool.getConnection();
			if(genere.equalsIgnoreCase("m") || genere.equalsIgnoreCase("f"))
				viewProduct+=" AND genere='"+genere+"'";
			statement=con.prepareStatement(viewProduct);
			set=statement.executeQuery();
			while(set.next())
			{
				Prodotto prod=new Prodotto();
				prod.setCodice(set.getString(1));
				prod.setNome(set.getString(2));
				prod.setImg(set.getString(3));
				prod.setGenere(set.getString(4));
				prod.setPrezzo(set.getDouble(5));
				prod.setDescrizione(set.getString(6));
				prod.setCategoria(set.getString(7));
				prod.setBrand(set.getString(8));
				prod.setSconto(set.getInt(9));
				prod.setPezzi(set.getInt(10));
				prod.setSize(set.getString(12));
				prodotti.add(prod);
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
		return prodotti;
	}

	public static ArrayList<Prodotto> viewProduct(String ...strings) throws SQLException
	{
		ArrayList<Prodotto> prodotti=new ArrayList<Prodotto>();
		int j=1;
		String viewProduct="SELECT * FROM prodotto";;

		if(strings.length>0)
		{
			if(!Utilites.fieldOk(strings))
				throw new SQLException("parametri di ricerca errati");
			viewProduct+=" WHERE visualizzabile=true AND ";
			for(int i=0;i<strings.length;i+=2)				
				if(i!=strings.length-2)
					viewProduct+=strings[i]+"=? AND ";
				else
					viewProduct+=strings[i]+"=?;";
		}
		try 
		{
			con = ConnectionPool.getConnection();
			con.commit();
			con.close();
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(viewProduct);
			for(int i=1;i<strings.length;i+=2,j++) 
				statement.setString(j,strings[i]);
			set=statement.executeQuery();
			while(set.next())
			{
				Prodotto prod=new Prodotto();
				prod.setCodice(set.getString(1));
				prod.setNome(set.getString(2));
				prod.setImg(set.getString(3));
				prod.setGenere(set.getString(4));
				prod.setPrezzo(set.getDouble(5));
				prod.setDescrizione(set.getString(6));
				prod.setCategoria(set.getString(7));
				prod.setBrand(set.getString(8));
				prod.setSconto(set.getInt(9));
				prod.setPezzi(set.getInt(10));
				prod.setSize(set.getString(12));
				prodotti.add(prod);
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
		return prodotti;
	}

	public static boolean cancelProduct(String idProdotto) throws SQLException
	{
		boolean flag=false;
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(deleteProduct);
			statement.setString(1,idProdotto);
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

	private static Connection con=null;
	private static PreparedStatement statement=null;
	private static 	ResultSet set=null;
	private static String addProduct;
	private static String deleteProduct;

	static
	{
		addProduct="INSERT INTO prodotto(codice,nome,immagine,genere,prezzo,descrizione,categoria,brand,sconto,pezzi,size) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
		deleteProduct="UPDATE prodotto SET Visualizzabile=false WHERE codice=?";
	}
}