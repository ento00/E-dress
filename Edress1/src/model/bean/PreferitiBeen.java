package model.bean;

import java.util.ArrayList;

public class PreferitiBeen
{
    private User user;
    private ArrayList<Prodotto> prod;

    public PreferitiBeen()
    {
        user=new User();
        prod=new ArrayList<Prodotto>();
    }

    public ArrayList<Prodotto> getProd()
    {
        return prod;
    }

    public void setProd(Prodotto prod)
    {
        this.prod.add(prod);
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "user:   "+user+"\n prod:   "+prod;
    }
}
