package model.bean;

import java.util.ArrayList;

public class Ordine 
{

	public Ordine() {
		quantità=new ArrayList<Integer>();
		prezzo=new ArrayList<Double>();
		prodotti=new ArrayList<Prodotto>();
	}

	public ArrayList<Integer> getQuantità()
	{
		return quantità;
	}
	
	public void setQuantità(int quantità)
	{
		this.quantità.add(quantità);
	}
	
	public ArrayList<Double> getPrezzo() 
	{
		return prezzo;
	}
	
	public void setPrezzo(double prezzo) 
	{
		this.prezzo.add(prezzo);
	}
	
	public ArrayList<Prodotto> getProdotti()
	{
		return prodotti;
	}
	
	public void setProdotti(Prodotto prodotto)
	{
		this.prodotti.add(prodotto);
	}
	
	public double getTotale() 
	{
		return totale;
	}
	
	public void setTotale(double totale)
	{
		this.totale = totale;
	}
	
	public int getNumeroOrdine()
	{
		return numeroOrdine;
	}
	
	public void setNumeroOrdine(int numeroOrdine) 
	{
		this.numeroOrdine = numeroOrdine;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(User user) 
	{
		this.user = user;
	}
	
	public String getData() 
	{
		return data;
	}
	
	public void setData(String data)
	{
		this.data = data;
	}
	
	public String getMetodoPagamento() 
	{
		return metodoPagamento;
	}
	
	public void setMetodoPagamento(String metodoPagamento) 
	{
		this.metodoPagamento = metodoPagamento;
	}
	
	
	
	@Override
	public String toString() {
		return "Ordine [quantità=" + quantità + ", prezzo=" + prezzo + ", prodotti=" + prodotti + ", totale=" + totale
				+ ", numeroOrdine=" + numeroOrdine + ", user=" + user + ", data=" + data + ", metodoPagamento="
				+ metodoPagamento + "]\n";
	}



	private ArrayList<Integer> quantità;
	private ArrayList<Double> prezzo;
	private ArrayList<Prodotto> prodotti;
	private double totale;
	private int numeroOrdine;
	private User user;
	private String data;
	private String metodoPagamento;
}
