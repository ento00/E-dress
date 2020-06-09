package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Ordine;
import model.bean.Prodotto;
import model.bean.User;
import model.connection.ConnectionPool;

public class DAOOrdine
{
	public static ArrayList<Ordine> viewOrdine(String startDate,String endDate) throws SQLException
	{
		ArrayList<Ordine> ordini=new ArrayList<Ordine>();
		User user=null;
		Prodotto prod=null;
		Ordine order=null;
		int precedente=0;
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(viewOrdine);
			statement.setString(1,startDate);
			statement.setString(2,endDate);
			set=statement.executeQuery();
			while(set.next())
			{
				prod=new Prodotto();
				prod.setImg(set.getString(8));
				prod.setNome(set.getString(9));
				prod.setGenere(set.getString("genere"));
				prod.setCategoria(set.getString("categoria"));
				prod.setSize(set.getString("size"));
				prod.setBrand(set.getString("brand"));
				prod.setSconto(set.getInt("sconto"));
				prod.setCodice(set.getString("codice"));

				if(set.getInt(4)==precedente)
				{
					order.setProdotti(prod);
					order.setQuantità(set.getInt(10));
					order.setPrezzo(set.getFloat(11));
					order.setPrezzo(set.getDouble("prezzo"));
				}
				else
				{
					order=new Ordine();
					user=new User();

					user.setEmail(set.getString(1));
					user.setNome(set.getString(2));
					user.setCognome(set.getString(3));
					order.setNumeroOrdine(set.getInt(4));
					order.setData(set.getString(5));
					order.setTotale(set.getFloat(6));
					order.setQuantità(set.getInt(10));
					order.setPrezzo(set.getFloat(11));
					order.setPrezzo(set.getDouble("prezzo"));
					order.setMetodoPagamento(set.getString(7));
					order.setNumeroOrdine(Integer.parseInt(set.getString("id")));
					order.setUser(user);
					order.setProdotti(prod);
					ordini.add(order);
				}
				precedente=order.getNumeroOrdine();
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
		return ordini;
	}

	public static ArrayList<Ordine> viewOrdineById(String Email) throws SQLException
	{
		ArrayList<Ordine> ordini=new ArrayList<Ordine>();
		User user=null;
		Prodotto prod=null;
		Ordine order=null;
		String precedente="";
		String precedente2="";
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(viewOrdineById);
			statement.setString(1,Email+"%");
			set=statement.executeQuery();
			while(set.next())
			{
				prod=new Prodotto();

				prod.setImg(set.getString(8));
				prod.setNome(set.getString(9));
                prod.setGenere(set.getString("genere"));
                prod.setCategoria(set.getString("categoria"));
                prod.setSize(set.getString("size"));
                prod.setBrand(set.getString("brand"));
                prod.setSconto(set.getInt("sconto"));
				prod.setCodice(set.getString("codice"));
				if(set.getString(4).equalsIgnoreCase(precedente) && precedente2.equalsIgnoreCase(set.getString("numOrdine")))
				{
					order.setProdotti(prod);
					order.setQuantità(set.getInt(10));
					order.setPrezzo(set.getDouble("prezzo"));
				}
				else
				{
					order=new Ordine();
					user=new User();

					user.setEmail(set.getString(1));
					user.setNome(set.getString(2));
					user.setCognome(set.getString(3));
					order.setNumeroOrdine(set.getInt(4));
					order.setData(set.getString(5));
					order.setTotale(set.getFloat(6));
					order.setQuantità(set.getInt(10));
                    order.setPrezzo(set.getDouble("prezzo"));
					order.setMetodoPagamento(set.getString(7));
					order.setNumeroOrdine(Integer.parseInt(set.getString("id")));
					order.setUser(user);
					order.setProdotti(prod);
					ordini.add(order);
				}
				precedente=String.valueOf(order.getNumeroOrdine());
				precedente2=String.valueOf(order.getNumeroOrdine());
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
		return ordini;
	}

	public static boolean addOrder(Ordine order) throws SQLException
	{
		boolean flag=false;
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(addOrdine);
			statement.setDouble(1,order.getTotale());
			statement.setString(2,order.getMetodoPagamento());
			statement.setString(3,order.getUser().getEmail());
			flag=statement.executeUpdate()>0;
			for(int i=0;i<order.getProdotti().size();i++)
			{
				Prodotto prod=order.getProdotti().get(i);
				Prodotto app=DAOProdotto.viewProduct("codice",prod.getCodice(),"size",prod.getSize()).get(0);
				DAOProdotto.modifyProduct("pezzi",String.valueOf(app.getPezzi()-prod.getPezzi()),"codice",prod.getCodice(),"size",prod.getSize());
			}
			con.commit();
			addComposizione(order);
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

	private static boolean addComposizione(Ordine order) throws SQLException
	{
		boolean flag=false;
		String insertComposizione="insert into composizione (iva,prezzo,quantità,id,size,codiceProd) values (?,?,?,?,?,?)";
		String numeroOrdine="SELECT max(numOrdine) FROM edress.ordine;";
		int i=0;

		con=ConnectionPool.getConnection();
		statement=con.prepareStatement(numeroOrdine);
		set=statement.executeQuery();
		if(set.next())
			numeroOrdine=set.getString(1);
		order.setNumeroOrdine(Integer.parseInt(numeroOrdine));
		statement=con.prepareStatement(insertComposizione);
		while(i<order.getProdotti().size())
		{
			statement.setInt(1,order.getProdotti().get(i).getIva());
			statement.setDouble(2,order.getPrezzo().get(i));
			statement.setInt(3,order.getQuantità().get(i));
			statement.setInt(4,order.getNumeroOrdine());
			statement.setString(5,order.getProdotti().get(i).getSize());
			statement.setString(6,order.getProdotti().get(i).getCodice());
			i+=1;
			flag=statement.executeUpdate()>0;
		}
		con.commit();
		return flag;
	}

	public static ArrayList<Ordine> viewOrdine() throws SQLException
	{
		ArrayList<Ordine> ordini=new ArrayList<Ordine>();
		User user=null;
		Prodotto prod=null;
		Ordine order=null;
		int precedente=0;

		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(viewAll);
			set=statement.executeQuery();
			while(set.next())
			{
				prod=new Prodotto();
				prod.setImg(set.getString(8));
				prod.setNome(set.getString(9));
				if(set.getInt(4)==precedente)
				{
					order.setProdotti(prod);
					order.setQuantità(set.getInt(10));
					order.setPrezzo(set.getFloat(11));
				}
				else
				{
					order=new Ordine();
					user=new User();

					user.setEmail(set.getString(1));
					user.setNome(set.getString(2));
					user.setCognome(set.getString(3));
					user.setSesso(set.getString("sesso"));
					order.setNumeroOrdine(set.getInt(4));
					order.setData(set.getString(5));
					order.setTotale(set.getFloat(6));
					order.setQuantità(set.getInt(10));
					order.setPrezzo(set.getFloat(11));
					order.setMetodoPagamento(set.getString(7));
					order.setUser(user);
					order.setProdotti(prod);
					ordini.add(order);
				}
				precedente=order.getNumeroOrdine();
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
		return ordini;
	}

	public static Ordine searchOrdineById(int identificativo) throws SQLException
	{
		Ordine order=null;;
		User user=null;
		Prodotto prod=null;
		try
		{
			con=ConnectionPool.getConnection();
			statement=con.prepareStatement(viewOrdineByNOrdine);
			statement.setInt(1,identificativo);
			set=statement.executeQuery();
			while(set.next())
			{
				prod=new Prodotto();

				if(user==null)
				{
					user=new User();
					order=new Ordine();

					user.setEmail(set.getString(1));
					user.setNome(set.getString(2));
					user.setCognome(set.getString(3));
					order.setNumeroOrdine(set.getInt(4));
					order.setData(set.getString(5));
					order.setTotale(set.getFloat(6));
					order.setMetodoPagamento(set.getString(7));
					order.setUser(user);
				}
				prod.setImg(set.getString(8));
				prod.setNome(set.getString(9));
				order.setQuantità(set.getInt(10));
				order.setPrezzo(set.getFloat(11));
				prod.setCodice(set.getString("codice"));
				prod.setGenere(set.getString("genere"));
				prod.setCategoria(set.getString("categoria"));
				prod.setSize(set.getString("size"));
				prod.setBrand(set.getString("brand"));
                prod.setSconto(set.getInt("sconto"));
				order.setProdotti(prod);
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
		return order;
	}

	private static Connection con=null;
	private static PreparedStatement statement=null;
	private static 	ResultSet set=null;
	private static String viewOrdine;
	private static String viewOrdineById;
	private static String addOrdine;
	private static String viewAll;
	private static String viewOrdineByNOrdine;

	static 
	{
		viewOrdine="select table1.email,table1.nome,table1.cognome,table1.numOrdine,table1.data,table1.costoTot,table1.metodoPagamento,table2.immagine,table2.nome,table2.quantità,table2.prezzo,table2.id,table2.genere,table2.categoria,table2.size,table2.codice,table2.brand,table2.sconto\r\n" +
				"from (select utente.email,nome,cognome,numOrdine,data,costoTot,metodoPagamento\r\n" + 
				"	  from utente \r\n" + 
				"      inner join ordine\r\n" + 
				"      on utente.email=ordine.email\r\n" + 
				"		where data between ? and ?) as table1 \r\n" + 
				"	  inner join \r\n" + 
				"	(select immagine,nome,composizione.quantità,composizione.prezzo,id,genere,categoria,prodotto.size,prodotto.codice,brand,sconto\r\n" +
				"from prodotto inner join composizione\r\n" + 
				"on prodotto.codice=composizione.codiceProd and prodotto.size=composizione.size) as table2\r\n" + 
				" on table1.numOrdine=table2.id";
		viewOrdineById="select table1.email,table1.nome,table1.cognome,table1.numOrdine,table1.data,table1.costoTot,table1.metodoPagamento,table2.immagine,table2.nome,table2.quantità,table2.prezzo,table2.id,table2.genere,table2.categoria,table2.size,table2.codice,table2.size,table2.brand,table2.sconto\r\n" +
				"from (select utente.email,nome,cognome,numOrdine,data,costoTot,metodoPagamento\r\n" + 
				"	  from utente \r\n" + 
				"      inner join ordine\r\n" + 
				"      on utente.email=ordine.email\r\n" + 
				"		where utente.email like ?) as table1 \r\n" + 
				"	  inner join \r\n" + 
				"	(select immagine,nome,composizione.quantità,composizione.prezzo,id,genere,categoria,prodotto.size,prodotto.codice,brand,sconto\r\n" +
				"from prodotto inner join composizione\r\n" + 
				"on prodotto.codice=composizione.codiceProd and prodotto.size=composizione.size) as table2\r\n" + 
				" on table1.numOrdine=table2.id";
		addOrdine="insert into ordine (data,costoTot,metodoPagamento,email)values (current_date(),?,?,?)";
		viewAll="select table1.email,table1.nome,table1.cognome,table1.numOrdine,table1.data,table1.costoTot,table1.metodoPagamento,table2.immagine,table2.nome,table2.quantità,table2.prezzo,table2.id,table2.genere,table2.categoria,table2.size,table2.codice,table2.brand,table2.sconto,table1.sesso\r\n" +
				"from (select utente.email,nome,cognome,numOrdine,data,costoTot,metodoPagamento,sesso\r\n" +
				"	  from utente \r\n" + 
				"      inner join ordine\r\n" + 
				"      on utente.email=ordine.email) as table1 \r\n" + 
				"	  inner join \r\n" +
                "    (select immagine,nome,composizione.quantità,composizione.prezzo,id,genere,categoria,prodotto.size,codice,brand,sconto\r\n" +
				"from prodotto inner join composizione\r\n" + 
				"on prodotto.codice=composizione.codiceProd and prodotto.size=composizione.size) as table2\r\n" + 
				" on table1.numOrdine=table2.id";
		viewOrdineByNOrdine="select table1.email,table1.nome,table1.cognome,table1.numOrdine,table1.data,table1.costoTot,table1.metodoPagamento,table2.immagine,table2.nome,table2.quantità,table2.prezzo,table2.id,table2.genere,table2.categoria,table2.size,table2.codice,table2.size,table2.brand,table2.sconto\r\n" +
			"from (select utente.email,nome,cognome,numOrdine,data,costoTot,metodoPagamento\r\n" +
			"	  from utente \r\n" +
			"      inner join ordine\r\n" +
			"      on utente.email=ordine.email\r\n" +
			"		where numOrdine=?) as table1 \r\n" +
			"	  inner join \r\n" +
			"	(select immagine,nome,composizione.quantità,composizione.prezzo,id,genere,categoria,prodotto.size,prodotto.codice,brand,sconto\r\n" +
			"from prodotto inner join composizione\r\n" +
			"on prodotto.codice=composizione.codiceProd and prodotto.size=composizione.size) as table2\r\n" +
			" on table1.numOrdine=table2.id";
	}
}