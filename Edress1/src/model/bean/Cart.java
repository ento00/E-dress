package model.bean;

import java.sql.SQLException;
import java.util.ArrayList;

import model.DAO.DAOProdotto;

public class Cart
{
	private ArrayList<Prodotto> prodotti;
	private double totale;

	public Cart()
	{
		prodotti=new ArrayList<Prodotto>();
		totale=0;
	}
	
	public boolean addProduct(Prodotto prod) throws SQLException
	{
		Prodotto product=DAOProdotto.viewProduct("codice",prod.getCodice(),"size",prod.getSize()).get(0);
		if(prod.getPezzi()<=product.getPezzi())
		{
			if(!presente(prod))
				prodotti.add(prod);
			aggiornaTotale();
			return true;
		}
		else
			return false;
	}


	private boolean presente(Prodotto prod)
	{
		for(Prodotto product:prodotti)
			if(product.getCodice().equalsIgnoreCase(prod.getCodice()) && product.getSize().equalsIgnoreCase(prod.getSize()))
			{
				prod.setPezzi(prod.getPezzi()+product.getPezzi());
				return true;
			}
		return false;
	}

	public boolean deleteProduc(Prodotto prod)
	{
		for(int i=0;i<prodotti.size();i++)
			if(prodotti.get(i).getCodice().equalsIgnoreCase(prod.getCodice()) && prodotti.get(i).getSize().equalsIgnoreCase(prod.getSize()))
				prodotti.remove(i);
		aggiornaTotale();
		return true;
	}
	
	private void aggiornaTotale()
	{
		double totale=0;
		for(int i=0;i<prodotti.size();i++)
			if(prodotti.get(i).getSconto()>0)
				totale+=(prodotti.get(i).getPrezzo()-(prodotti.get(i).getPrezzo()*prodotti.get(i).getSconto()/100))*prodotti.get(i).getPezzi();
			else
				totale+=prodotti.get(i).getPrezzo()*prodotti.get(i).getPezzi();
		setTotale(totale);
	}
	
	public ArrayList<Prodotto> getProdotti() 
	{
		return prodotti;
	}
	public void setProdotti(ArrayList<Prodotto> prodotti) 
	{
		this.prodotti = prodotti;
	}

	public double getTotale() 
	{
		return totale;
	}
	public void setTotale(double totale)
	{
		this.totale = totale;
	}


}
