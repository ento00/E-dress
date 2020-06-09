package controller;
import model.DAO.DAORecensione;
import model.bean.Prodotto;
import model.bean.Recensione;
import model.bean.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/addRecensione")
public class addRecensione extends HttpServlet {

    public addRecensione() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numeroStelle=request.getParameter("stelle");
        String commento=request.getParameter("commento");
        String prodotto= request.getParameter("codice");
        Recensione r= new Recensione();
        Prodotto p= new Prodotto();
        User u=(User)request.getSession().getAttribute("userBean");

        p.setCodice(prodotto);
        r.setUser(u);
        r.setProdotto(p);
        r.setNstelle(numeroStelle);
        r.setCommento(commento);
        try {
            if(DAORecensione.addRecensione(r))
            {
                request.setAttribute("acquistato", true);
            }
            else
                request.setAttribute("acquistato", false);
            request.setAttribute("codice",prodotto);
            RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher("/ServletProduct");
            requestDispatcher.forward(request,response);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doGet(request,response);
    }


}
