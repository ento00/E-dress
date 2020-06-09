package controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import model.beans.CodiceBean;
//import model.beans.Preferiti;
//import model.beans.ProductBean;
//import model.beans.Utente;
//import model.DAOS.PreferitiDAO;
//import model.DAOS.ProductModelDM;
//import model.DAOS.UtenteDAO;
//
//import database.Conn;
//import database.Record;
//import database.Table;
//import model.beans.Preferiti;
import model.DAO.DAOProdotto;
import model.DAO.DAORecensione;
import model.bean.Prodotto;
import model.bean.Recensione;


@WebServlet("/ServletProduct")
public class ServletProduct extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        Prodotto prod=null;
        ArrayList<Prodotto> simili=null;
        ArrayList<Recensione> recensioni=null;
        int i;
        String codice="";
        try {
            codice=request.getParameter("codice").trim();
            String codice2=(String)request.getServletContext().getAttribute("codice");
            if(request.getAttribute("codice")!=null)
                prod = DAOProdotto.viewProduct("codice", (String)request.getAttribute("codice")).get(0);
            if(codice!=null)
                prod = DAOProdotto.viewProduct("codice", codice).get(0);
            else
                if(codice2!=null)
                    prod = DAOProdotto.viewProduct("codice", codice2).get(0);
            if(prod==null)
                response.sendRedirect("index.jsp");
            String categoria=prod.getCategoria();
            simili=(ArrayList<Prodotto>) DAOProdotto.viewProduct("categoria", categoria);

            for(i=0;i<simili.size();i++)
            {
                if (simili.get(i).getCodice().equalsIgnoreCase(codice))
                    simili.remove(i);
            }
            recensioni= DAORecensione.showRecensione(prod);
        }
        catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
        request.setAttribute("commenti",recensioni);
        request.setAttribute("prodotto", prod);
        request.setAttribute("prodotti", simili);
        try
        {
            request.setAttribute("media", DAORecensione.media(codice));
        }
        catch (SQLException sq)
        {
            sq.printStackTrace();
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/productView.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
       doGet(request,response);
    }
}

