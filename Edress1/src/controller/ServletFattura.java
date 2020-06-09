package controller;

import model.DAO.DAOOrdine;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/fattura")
public class ServletFattura extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
      String codice=req.getParameter("codice");
        String indirizzo="";

      if(codice!=null)
      {
          try
          {
              req.setAttribute("ordine", DAOOrdine.searchOrdineById(Integer.parseInt(codice)));
              indirizzo="/fattura.jsp";
          }
          catch (SQLException e)
          {
              e.printStackTrace();
          }
      }
      else
          indirizzo="/index.jsp";
        RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher(indirizzo);
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doGet(req, resp);
    }
}
