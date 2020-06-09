package model.bean;

public class User implements Cloneable
{
	public User() 
	{}

	public User(String email, String nome, String cognome, String password, String sesso) 
	{
		this.email = email;
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.sesso = sesso;
	}

	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public String getCognome()
	{
		return cognome;
	}
	
	public void setCognome(String cognome) 
	{
		this.cognome = cognome;
	}

	public String getCittà()
	{
		return città;
	}
	
	public void setCittà(String città)
	{
		this.città = città;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getSesso() 
	{
		return sesso;
	}
	
	public void setSesso(String sesso) 
	{
		this.sesso = sesso;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public String getTipo()
	{
		return tipo;
	}
	
	public int getNumeroCivico() 
	{
		return numeroCivico;
	}
	
	public void setNumeroCivico(int numeroCivico) 
	{
		this.numeroCivico = numeroCivico;
	}
	
	public String getVia()
	{
		return via;
	}
	
	@Override
	public String toString() {
		return "User [email=" + email + ", nome=" + nome + ", cognome=" + cognome + ", citt�=" + città + ", password="
				+ password + ", sesso=" + sesso + ", tipo=" + tipo + ", numeroCivico=" + numeroCivico + ", via=" + via
				+ "]\n";
	}

	public void setVia(String via) 
	{
		this.via = via;
	}

	public int getCap()
	{
		return cap;
	}

	public void setCap(int cap)
	{
		this.cap = cap;
	}

	@Override
	public User clone() throws CloneNotSupportedException
	{
		return (User)super.clone();
	}

	private String email;
	private String nome;
	private String cognome;
	private String città;
	private String password;
	private String sesso;
	private String tipo;
	private int numeroCivico;
	private String via;
	private int cap;

}
