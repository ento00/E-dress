package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DAO.DAOProdotto;
import model.bean.Cart;
import model.bean.Prodotto;

/**
 * Servlet implementation class Carrello
 */
@WebServlet("/Carrello")
public class Carrello extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Carrello() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session=request.getSession();
		Cart cart=(Cart)session.getAttribute("cart");

		if(cart==null)
		{
			cart=new Cart();
			session.setAttribute("cart",cart);
		}
		try 
		{
			String bottone=request.getParameter("action");
			if(bottone!=null) {
				if(bottone.equalsIgnoreCase("addC"))
				{
					String codiceProdotto=request.getParameter("codice");
					String size=request.getParameter("size");
					int pezzi= 1 /*Integer.parseInt(request.getParameter("quantità"))*/;
					Prodotto prod= DAOProdotto.viewProduct("codice",codiceProdotto,"size",size).get(0);
					prod.setPezzi(pezzi);
					cart.addProduct(prod);
					session.setAttribute("cart",cart);
					response.sendRedirect("carrello.jsp");
				}
				if(bottone.equalsIgnoreCase("rimuovi")){
					String codiceProdotto=request.getParameter("codice");
					String size=request.getParameter("size");
					Prodotto prod=new Prodotto();
					prod.setSize(size);
					prod.setCodice(codiceProdotto);
					cart.deleteProduc(prod);
					session.setAttribute("cart",cart);
					response.sendRedirect("carrello.jsp");
				}
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
