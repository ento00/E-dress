package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.DAO.DAOUser;
import model.bean.User;

@WebServlet("/register")

public class ServletRegister extends HttpServlet 
{


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String indirizzo="index.jsp";
		try
		{
			User ub = new User();

			String email = request.getParameter("email").trim();
			String psw = request.getParameter("password").trim();
			String nome = request.getParameter("nome").trim();
			String cognome = request.getParameter("cognome").trim();
			String sesso =request.getParameter("sesso").trim();
			String citta=request.getParameter("citta").trim();
			String via=request.getParameter("via").trim();
			String numeroCivico=request.getParameter("numeroCivico").trim();
			String cap=request.getParameter("cap").trim();

			ub.setCap(Integer.parseInt(cap));
			ub.setNumeroCivico(Integer.parseInt(numeroCivico));
			ub.setCittà(citta);
			ub.setEmail(email);
			ub.setPassword(psw);
			ub.setNome(nome);
			ub.setCognome(cognome);
			ub.setSesso(sesso);
			ub.setVia(via);
			if(DAOUser.checkEmail(ub.getEmail()))
			{
				request.setAttribute("email","usata");
				/* le seguinti istruzione devono essere implementate ma solo per una questione di velocità"*/
//				ub.setEmail("");
//				request.setAttribute("user",ub);
				indirizzo="register.jsp";
			}
			else
				DAOUser.regUtente(ub);
			RequestDispatcher requestDispatcher=request.getRequestDispatcher(indirizzo);
			requestDispatcher.forward(request,response);
		}
		catch (Exception ex) {
				ex.printStackTrace();
				request.setAttribute("exception", ex);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("view/exception.jsp");
				requestDispatcher.forward(request, response);
		}

	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
