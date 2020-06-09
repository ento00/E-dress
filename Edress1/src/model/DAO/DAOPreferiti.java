package model.DAO;

import model.bean.PreferitiBeen;
import model.bean.Prodotto;
import model.bean.User;
import model.connection.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOPreferiti
{
    public static synchronized boolean addPreferiti(Prodotto prod, User user) throws SQLException
    {
        boolean flag=false;
        try
        {
            con=ConnectionPool.getConnection();
            statement=con.prepareStatement(addPreferiti);
            statement.setString(1,user.getEmail());
            statement.setString(2,prod.getCodice());
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

    public static synchronized boolean removePreferiti(Prodotto prod, User user) throws SQLException
    {
        boolean flag=false;
        try
        {
            con=ConnectionPool.getConnection();
            statement=con.prepareStatement(deletePreferiti);
            statement.setString(1,user.getEmail());
            statement.setString(2,prod.getCodice());
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

    public static synchronized PreferitiBeen visualizzaPreferiti(User user) throws SQLException
    {
        PreferitiBeen preferiti=new PreferitiBeen();
        try
        {
            con = ConnectionPool.getConnection();
            con.close();
            con=ConnectionPool.getConnection();
            statement = con.prepareStatement(visualizzaPreferiti);
            statement.setString(1, user.getEmail());
            set=statement.executeQuery();
            preferiti.setUser(DAOUser.showAccount(user.getEmail()));
            String precedente="";
            while (set.next())
            {
                if(!precedente.equalsIgnoreCase(set.getString("codiceProdotto")))
                {
                    String codice = set.getString("codiceProdotto");
                    preferiti.setProd(DAOProdotto.viewProduct("codice", codice).get(0));
                    precedente = codice;
                }
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
        return preferiti;
    }

    private static Connection con=null;
    private static PreparedStatement statement=null;
    private static ResultSet set=null;
    private static String addPreferiti;
    private static String deletePreferiti;
    private static String visualizzaPreferiti;

    static
    {
        addPreferiti="insert into preferiti value(?,?)";
        deletePreferiti="DELETE FROM preferiti WHERE utente=? and codiceProdotto=?;";
        visualizzaPreferiti="select * from preferiti inner join prodotto on codiceProdotto=codice \n" +
              "where utente=? and visualizzabile=true;";
    }
}
