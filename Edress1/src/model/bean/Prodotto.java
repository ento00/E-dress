package model.bean;

public class Prodotto
{
	public Prodotto() 
	{
		size="";
		codice = "";
		brand="";
		descrizione = "";
		img="";
		categoria="";
		nome = "";
		genere="";
		sconto=0;
		pezzi = 0;
		prezzo=0;
		iva=22;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public String getCodice() 
	{
		return codice;
	}

	public void setCodice(String codice)
	{
		this.codice = codice;
	}

	public String getBrand()
	{
		return brand;
	}

	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	public String getCategoria()
	{
		return categoria;
	}

	public void setCategoria(String categoria)
	{
		this.categoria = categoria;
	}

	public String getDescrizione() 
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione) 
	{
		this.descrizione = descrizione;
	}

	public String getImg() 
	{
		return img;
	}

	public void setImg(String img) 
	{
		this.img = img;
	}

	public String getGenere() 
	{
		return genere;
	}

	public void setGenere(String genere) 
	{
		this.genere = genere;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome) 
	{
		this.nome = nome;
	}

	public int getSconto()
	{
		return sconto;
	}

	public void setSconto(int sconto) 
	{
		this.sconto = sconto;
	}

	public int getPezzi() 
	{
		return pezzi;
	}

	public void setPezzi(int pezzi)
	{
		this.pezzi = pezzi;
	}

	public double getPrezzo() 
	{
		return prezzo;
	}

	public void setPrezzo(double prezzo) 
	{
		this.prezzo = prezzo;
	}


	public int getIva() 
	{
		return iva;
	}

	public void setIva(int iva)
	{
		this.iva = iva;
	}
	
	@Override
	public String toString() {
		return "Prodotto [size=" + size + ", codice=" + codice + ", brand=" + brand + ", categoria=" + categoria
				+ ", descrizione=" + descrizione + ", img=" + img + ", genere=" + genere + ", nome=" + nome
				+ ", sconto=" + sconto + ", pezzi=" + pezzi + ", prezzo=" + prezzo +"]\n";
	}

	private String size;
	private String codice;
	private String brand;
	private String categoria;
	private String descrizione;
	private String img;
	private String genere;
	private String nome;
	private int sconto;
	private int pezzi;
	private double prezzo;
	private int iva;
}
