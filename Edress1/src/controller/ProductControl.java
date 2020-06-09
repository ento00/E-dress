package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.DAO.DAOProdotto;
import model.bean.Prodotto;

@WebServlet("/ProductControl")
public class ProductControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductControl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        String token = request.getParameter("token");
        ServletContext snt=getServletContext();
        String token2=(String)snt.getAttribute("search");


        try {
            if(token!=null) {
                if (token.equalsIgnoreCase("offerte")) {
                    String precedente = "";
                    ArrayList<Prodotto> list;
                    if(request.getParameter("genere")==null)
                        list=DAOProdotto.viewDiscountProduct("");
                    else
                        list=DAOProdotto.viewDiscountProduct(request.getParameter("genere"));
                    for (int i = 0; i < list.size(); i++) {
                        if (!precedente.equalsIgnoreCase(list.get(i).getCodice()))
                            precedente = list.get(i).getCodice();
                        else {
                            list.remove(i);
                            i -= 1;
                        }
                    }
                    request.setAttribute("prodotti",list);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/listProduct.jsp");
                    dispatcher.forward(request, response);

                } else {
                    String brand = request.getParameter("brand");
                    if (token.equalsIgnoreCase("SearchBrand")) {
                        ArrayList<Prodotto> list = null;
                        if (brand.equalsIgnoreCase("tutti"))
                            list = (DAOProdotto.viewProduct("1", "1"));
                        else
                            list = (DAOProdotto.viewProduct("brand", brand, "genere", request.getParameter("genere")));
                        String precedente = "";
                        for (int i = 0; i < list.size(); i++) {
                            if (!precedente.equalsIgnoreCase(list.get(i).getCodice()))
                                precedente = list.get(i).getCodice();
                            else {
                                list.remove(i);
                                i -= 1;
                            }
                        }
                        request.setAttribute("prodotti", list);
                        request.setAttribute("marca", brand);
                        request.setAttribute("token", token);
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/listProduct.jsp");
                        dispatcher.forward(request, response);
                    } else if (token.equalsIgnoreCase("SearchCategoria")) {
                        String categoria = request.getParameter("categoria");
                        ArrayList<Prodotto> list = (DAOProdotto.viewProduct("categoria", categoria, "genere", request.getParameter("genere")));
                        String precedente = "";
                        for (int i = 0; i < list.size(); i++)
                            if (!precedente.equalsIgnoreCase(list.get(i).getCodice()))
                                precedente = list.get(i).getCodice();
                            else {
                                list.remove(i);
                                i -= 1;
                            }

                        request.setAttribute("prodotti", list);
                        request.setAttribute("marca", brand);
                        request.setAttribute("cat", categoria);
                        request.setAttribute("token", token);
                        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/listProduct.jsp");
                        dispatcher.forward(request, response);
                    }
                }
            }
            if(token2!=null)
            {
                String paramentroRicerca=(String)snt.getAttribute("parametro");

                if(token2.equalsIgnoreCase("Brand"))
                {
                    ArrayList<Prodotto> list=(DAOProdotto.viewProduct("brand",paramentroRicerca));
                    String precedente="";
                    for(int i=0;i<list.size();i++)
                        if(!precedente.equalsIgnoreCase(list.get(i).getCodice()))
                            precedente=list.get(i).getCodice();
                        else
                        {
                            list.remove(i);
                            i-=1;
                        }
                    request.setAttribute("prodotti", list);
                }
                else
                    if(token2.equalsIgnoreCase("Nome"))
                    {
                        ArrayList<Prodotto> list=(DAOProdotto.viewProduct("nome",paramentroRicerca));
                        String precedente="";
                        for(int i=0;i<list.size();i++)
                            if(!precedente.equalsIgnoreCase(list.get(i).getCodice()))
                                precedente = list.get(i).getCodice();
                            else
                            {
                                list.remove(i);
                                i-=1;
                            }
                        request.setAttribute("prodotti", list);
                    }
                    else
                        if(token2.equalsIgnoreCase("fail"))
                            request.setAttribute("prodotti",null);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/listProduct.jsp");
                dispatcher.forward(request, response);
            }
        }
        catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}