package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Date;
import java.io.IOException;
import java.time.LocalDate;

import model.DAO.DAOOrdine;
import model.bean.User;
import model.bean.Cart;
import model.bean.Ordine;
import model.bean.Prodotto;

@WebServlet("/acquisto")
public class Acquisto extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ssn=request.getSession();
        Cart car=(Cart)ssn.getAttribute("cart");
        Ordine order=new Ordine();

        if(ssn.getAttribute("userBean")==null){
            request.getSession().setAttribute("provenienza","acquisto");

            RequestDispatcher requestDispatcher=request.getRequestDispatcher("register.jsp");
            requestDispatcher.forward(request,response);
        }

        else{
        for(Prodotto p:car.getProdotti())
        {
            order.setMetodoPagamento("Visa");
            order.setPrezzo(p.getPrezzo());
            order.setProdotti(p);
            order.setQuantità(p.getPezzi());
            if(request.getParameter("delivery-collection") !=null && request.getParameter("delivery-collection").equalsIgnoreCase("collection"))
                order.setTotale(car.getTotale()+20);
            else
                if(request.getParameter("delivery-collection") != null && request.getParameter("delivery-collection").equalsIgnoreCase("first-class"))
                    order.setTotale(car.getTotale()+10);
                else
                    order.setTotale(car.getTotale());

                order.setUser((User)request.getSession().getAttribute("userBean"));
        }
        try
        {
            if(DAOOrdine.addOrder(order))
            {
                request.getSession().removeAttribute("cart");
                request.setAttribute("cart",new Cart());
                response.sendRedirect("index.jsp");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }}
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
     doGet(request,response);
    }
}
