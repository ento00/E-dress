package controller;

import model.DAO.DAOPreferiti;
import model.DAO.DAOProdotto;
import model.DAO.DAOUser;
import model.bean.PreferitiBeen;
import model.bean.Prodotto;
import model.bean.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/servletPreferiti")
public class servletPreferiti extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String bottone=req.getParameter("action");
        boolean flag=false;
        if(bottone!=null)
        {
            if (bottone.equalsIgnoreCase("add"))
            {
                String codice=req.getParameter("value");
                try
                {
                    User user= DAOUser.showAccount(((User)req.getSession().getAttribute("userBean")).getEmail());
                    Prodotto prod= DAOProdotto.viewProduct("codice",codice).get(0);
                    HttpSession ssn=req.getSession();
                    synchronized (ssn)
                    {
                        if(ssn.getAttribute("pref")!=null)
                        {
                            for(int i=0;i<((PreferitiBeen)ssn.getAttribute("pref")).getProd().size() && !flag ;i++)
                                if(((PreferitiBeen)ssn.getAttribute("pref")).getProd().get(i).getCodice().equalsIgnoreCase(prod.getCodice()))
                                    flag=true;
                            if(flag==false)
                            {
                                PreferitiBeen preferitiBeen=(PreferitiBeen)ssn.getAttribute("pref");
                                preferitiBeen.setProd(prod);
                                DAOPreferiti.addPreferiti(prod,user);
                                ssn.setAttribute("pref",preferitiBeen);
                            }
                        }
                        else
                        {
                            PreferitiBeen preferitiBeen=new PreferitiBeen();
                            preferitiBeen.setProd(prod);
                            ssn.setAttribute("pref",preferitiBeen);
                        }
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
            else
            if (bottone.equalsIgnoreCase("visualizza"))
            {
                PreferitiBeen pref= null;
                try
                {
                    if (req.getSession().getAttribute("pref") == null)
                    {
                        if (req.getSession().getAttribute("userBean") != null)
                            pref = DAOPreferiti.visualizzaPreferiti((User) req.getSession().getAttribute("userBean"));
                        req.getSession().setAttribute("pref", pref);
                    }
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                RequestDispatcher requestDispatcher=req.getRequestDispatcher("preferiti.jsp");
                requestDispatcher.forward(req,resp);
            }
            else
            if (bottone.equalsIgnoreCase("rimuovi"))
            {
                PreferitiBeen pref= null;
                try
                {
                    String codice=req.getParameter("codice");
                    Prodotto prod=DAOProdotto.viewProduct("codice",codice).get(0);
                    User user=(User)req.getSession().getAttribute("userBean");
                    DAOPreferiti.removePreferiti(prod,user);
                    PreferitiBeen preferitiBeen= (PreferitiBeen) req.getSession().getAttribute("pref");
                    for(int i=0;i<preferitiBeen.getProd().size();i++)
                        if(preferitiBeen.getProd().get(i).getCodice().equalsIgnoreCase(codice))
                        {
                            preferitiBeen.getProd().remove(i);
                            break;
                        }
                    req.getSession().setAttribute("pref",preferitiBeen);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                RequestDispatcher requestDispatcher=req.getRequestDispatcher("preferiti.jsp");
                requestDispatcher.forward(req,resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doGet(req, resp);
    }
}
