package controller;

import com.google.gson.Gson;
import model.DAO.DAOProdotto;
import model.bean.Cart;
import model.bean.Prodotto;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ServletVariaQuantita")
public class ServletVariaQuantita extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codice = request.getParameter("codice");
        String size = request.getParameter("size");
        String quantita = request.getParameter("quantia");
        Gson g = new Gson();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            Prodotto p = DAOProdotto.viewProduct("codice", codice, "size", size).get(0);
            if (Integer.parseInt(quantita) > p.getPezzi() || Integer.parseInt(quantita)<=0) {
                response.getWriter().write(g.toJson("error"));
                return;
            }
            else
            {
                if (request.getSession().getAttribute("cart") != null)
                {
                    Cart cart = (Cart) request.getSession().getAttribute("cart");
                    for (int i = 0; i < cart.getProdotti().size(); i++)
                    {
                        if (cart.getProdotti().get(i).getCodice().equalsIgnoreCase(p.getCodice()) && cart.getProdotti().get(i).getSize().equalsIgnoreCase(p.getSize()))
                        {
                            cart.getProdotti().remove(i);
                            p.setPezzi(Integer.parseInt(quantita));
                            cart.addProduct(p);
                        }
                    }
                    request.getSession().setAttribute("cart", cart);
                    response.getWriter().write(g.toJson(cart.getTotale()));
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        return;
    }

}