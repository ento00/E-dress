package controller;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import model.DAO.DAOOrdine;
import model.DAO.DAOProdotto;
import model.DAO.DAOUser;
import model.bean.Ordine;
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
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet("/userController")
public class userController extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession ssn=request.getSession();
        User user=(User)ssn.getAttribute("userBean");
        String addres=null;
        String bottone=request.getParameter("action");
        try
        {
            if(ssn.getAttribute("accesso") != null && ssn.getAttribute("userBean")!=null)
            {
                if (bottone != null && bottone.equalsIgnoreCase("visualizzaOrdini"))
                {
                    ArrayList<Ordine> ordini = DAOOrdine.viewOrdineById(user.getEmail());
                    request.setAttribute("ordini", ordini);
                    addres = "visualizzaOrdini.jsp";
                }
                else
                if (bottone.equalsIgnoreCase("visualizzaDati"))
                {
                    user = DAOUser.showAccount(user.getEmail());
                    request.setAttribute("user",user);
                    request.setAttribute("prov","visual");
                    addres="modifica.jsp";   //modificare con visualizza se necessario
                }
                else
                if (bottone.equalsIgnoreCase("modificaDati"))
                {
                    //sendRedirect  e memorizzaare att in sessione
                    if(request.getParameter("hidden")==null)
                        request.setAttribute("provenienza","servlet");
                    else
                    {
                        String nome=request.getParameter("nome").trim();
                        String cognome=request.getParameter("cognome").trim();
                        String citta=request.getParameter("citta").trim();
                        String via=request.getParameter("via").trim();
                        String numeroCivico=request.getParameter("numeroCivico").trim();
                        String cap=request.getParameter("cap").trim();
//                        String sesso=request.getParameter("sesso");
                        String password=request.getParameter("password").trim();
                        if(DAOUser.modificaAccount("nome",nome,"cognome",cognome,"citta",citta,"via",via,"numeroCivico",numeroCivico,"cap",cap,"password",password,"email",((User)request.getSession().getAttribute("userBean")).getEmail()))
                            request.setAttribute("modifica","true");
                        else
                            request.setAttribute("modifica","false");
                    }
                    user = DAOUser.showAccount(user.getEmail());
                    request.setAttribute("provenienza","servlet");
                    request.setAttribute("user",user);
                    User user1=user.clone();
                    user1.setPassword("");
                    request.getSession().setAttribute("userBean",user1);
                    addres="modifica.jsp";
                }
            }
            else
            {
                addres="index.jsp";
            }
        }
        catch (SQLException | CloneNotSupportedException e)
        {   // da cambiare con la pagina d'errore
            addres = "index.jsp";
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher=request.getRequestDispatcher(addres);
        requestDispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request,response);
    }
}
