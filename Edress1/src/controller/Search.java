package controller;

import model.DAO.DAOProdotto;
import model.bean.Prodotto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/search")
public class Search extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String paramentroRicerca=request.getParameter("search").trim();
        String indirizzo="";
        ServletContext cnt=request.getServletContext();
        ArrayList<Prodotto> prod;

        if(paramentroRicerca!=null)
            if(paramentroRicerca.equalsIgnoreCase(""))
                indirizzo="index.jsp"; //da sostituire con una pagina d'errore se non ci sono prodotti
            else
            {
                try
                { /* viene fatta cosi per non modificare la query si puo fare di meglio*/
                    prod =DAOProdotto.viewProduct();
                    for(int i=0;i<prod.size();i++)
                        if(prod.get(i).getBrand().equalsIgnoreCase(paramentroRicerca))
                        {
                           cnt.setAttribute("search","Brand");
                            break;
                        }
                        else
                            if(prod.get(i).getNome().equalsIgnoreCase(paramentroRicerca))
                            {
                                cnt.setAttribute("search","Nome");
                                break;
                            }
                     if(cnt.getAttribute("search")==null)
                         cnt.setAttribute("search","fail");
                     cnt.setAttribute("parametro",paramentroRicerca);
                     indirizzo="ProductControl";
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                RequestDispatcher requestDispatcher=request.getRequestDispatcher(indirizzo);
                requestDispatcher.forward(request,response);
            }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
