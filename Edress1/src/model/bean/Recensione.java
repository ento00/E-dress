package model.bean;

public class Recensione 
{
	private User user;
	private String Nstelle;
	private String commento;
	private Prodotto prodotto;
	
	public Recensione()
	{}
	
	public User getUser() 
	{
		return user;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public String getNstelle() 
	{
		return Nstelle;
	}
	
	public void setNstelle(String nstelle) 
	{
		Nstelle = nstelle;
	}
	
	public String getCommento() 
	{
		return commento;
	}
	
	public void setCommento(String commento) 
	{
		this.commento = commento;
	}
	
	public Prodotto getProdotto() 
	{
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto)
	{
		this.prodotto = prodotto;		
	}

	@Override
	public String toString() {
		return "Recensione [user=" + user + ", Nstelle=" + Nstelle + ", commento=" + commento + ", prodotto=" + prodotto
				+ "]";
	}	
}
