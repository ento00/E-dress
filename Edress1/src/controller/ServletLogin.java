package controller;

import model.DAO.DAOUser;
import model.bean.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.*;
import java.io.IOException;


@WebServlet("/login")

public class  ServletLogin extends HttpServlet
{

	protected  void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{

		HttpSession ssn = request.getSession();

		synchronized (ssn) // SINCRONIZZO LA SESSIONE
		{
			User userB = getEmailPsw(request);
			if (userB.getEmail() == null || userB.getPassword() == null)
				request.setAttribute("denied", "true");
			String address = "";
			try
			{
				boolean ub = DAOUser.checkLogin(userB.getEmail(), userB.getPassword());
				if (!ub)
				{
					request.setAttribute("denied", "true");
					ssn.setAttribute("accesso", null);
					address = "register.jsp";//gestire errore ecexione o segnalare con mess
				}
				else
				{
					User u=new User();
					u=DAOUser.showAccount(userB.getEmail());
					u.setPassword("");
					ssn.setAttribute("userBean", u); // l'output ha bisogno di queste informazioni
					ssn.setAttribute("accesso", "true");
					String provenienza=(String)request.getSession().getAttribute("provenienza");
					System.out.println(provenienza);
					if(provenienza!=null && provenienza.equalsIgnoreCase("acquisto")) {
						address = "carrello.jsp";
						request.getSession().removeAttribute("provenienza");
					}
					else
						address = "index.jsp";

				}
			}
			catch (Exception ex)
			{ex.printStackTrace();}
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	private User getEmailPsw(HttpServletRequest request) {
		User ub = null;
		String email = null, psw = null;

		if (email == null || psw == null) { // se recupero tramite cookie fallisce, allora tramite parametri
			String temp;
			temp = request.getParameter("email").trim();
			if (temp != null) {
				email = temp;
				temp = request.getParameter("psw");
				if (temp != null) {
					psw = temp;
				}
			}
		}
		if (email != null && psw != null) // se recupero ha avuto successo riempio model.bean
			ub = new User();
			ub.setEmail(email);
			ub.setPassword(psw);
		return ub;
	}


}
