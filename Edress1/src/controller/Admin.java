package controller;

import model.DAO.DAOOrdine;
import model.DAO.DAOProdotto;
import model.bean.Ordine;
import model.bean.PreferitiBeen;
import model.bean.Prodotto;
import model.bean.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/admin")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
      maxFileSize = 1024 * 1024 * 5,
      maxRequestSize = 1024 * 1024 * 5 * 5)

public class Admin extends HttpServlet
{
    private static final String UPLOAD_DIRECTORY ="img\\prodotti\\";

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String bottone=req.getParameter("action");
        ServletContext snx=req.getServletContext();


        if(bottone!=null)
        {
            String indirizzo="";
            if(bottone.equalsIgnoreCase("rimuovi"))
            {
                String codice=req.getParameter("codice");
                if(codice!=null)
                {
                    try
                    {
                        boolean flag=DAOProdotto.cancelProduct(codice);
                        if(flag)
                            indirizzo="/index.jsp";
                        PreferitiBeen preferitiBeen= (PreferitiBeen) req.getSession().getAttribute("pref");
                        if(preferitiBeen!=null)
                        {
                            for (int i = 0; i < preferitiBeen.getProd().size(); i++)
                                if (preferitiBeen.getProd().get(i).getCodice().equalsIgnoreCase(codice))
                                {
                                    preferitiBeen.getProd().remove(i);
                                    break;
                                }
                            req.getSession().setAttribute("pref", preferitiBeen);
                        }
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(indirizzo);
                dispatcher.forward(req, resp);
            }
            else
                if(bottone.equalsIgnoreCase("visualizzaOrdini"))
                {
                    try
                    {
                        String Email=req.getParameter("user");
                        ArrayList<Ordine> ordini=DAOOrdine.viewOrdine();

                        if(Email==null)
                        {
                            ArrayList<User> user=new ArrayList<>();
                            String precendente="";
                            for(Ordine o:ordini)
                            {
                                if (precendente.indexOf(o.getUser().getEmail()) == -1)
                                {
                                    user.add(o.getUser());
                                    precendente+=" "+o.getUser().getEmail();
                                }
                            }
                            req.setAttribute("user",user);
                            indirizzo="visualOrderAdmin.jsp";
                        }
                        else
                        {
                            req.setAttribute("ordini",DAOOrdine.viewOrdineById(Email));
                            indirizzo="visualizzaOrdini.jsp";
                        }
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                    RequestDispatcher requestDispatcher=req.getRequestDispatcher(indirizzo);
                    requestDispatcher.forward(req,resp);
                }
                else
                    if(bottone.equalsIgnoreCase("carica"))
                    {
                        String codice= req.getParameter("codice").trim();
                        String nome=req.getParameter("nome").trim();
                        String genere=req.getParameter("genere").trim();
                        String prezzo=req.getParameter("prezzo").trim();
                        String descrizione=req.getParameter("descrizione").trim();
                        String categoria=req.getParameter("categoria").trim();
                        String brand=req.getParameter("brand").trim();
                        String sconto=req.getParameter("sconto").trim();
                        String pezzi=req.getParameter("pezzi").trim();
                        String s=req.getParameter("S");
                        String m=req.getParameter("M");
                        String l=req.getParameter("L");
                        String xl=req.getParameter("XL");
                        String precedente="";
                        boolean flag=true;
                        // inizio crea dir
                        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY+genere+"\\"+categoria+"\\";
                        File uploadDir = new File(uploadPath);
                        uploadDir.setWritable(true);
                        if (!uploadDir.exists())
                            uploadDir.mkdir();
                        // fine creazione dir
                        String fileName="";
                        try{
                            for (Part part : req.getParts()) {
                                fileName = getFileName(part);
                                if(fileName.equalsIgnoreCase(""))
                                    continue;
                                else
                                    precedente=fileName;
                                part.write(uploadPath + precedente);
                                part.write("C:/Users/marco/OneDrive/Desktop/edress/web/img/prodotti/"+genere+"/"+categoria+"/"+precedente);
                            }
                            Prodotto prod=new Prodotto();
                            prod.setCodice(codice);
                            prod.setNome(nome);
                            prod.setGenere(genere);
                            prod.setPrezzo(Double.parseDouble(prezzo));
                            prod.setDescrizione(descrizione);
                            prod.setCategoria(categoria);
                            prod.setBrand(brand);
                            prod.setSconto(Integer.parseInt(sconto));
                            prod.setPezzi(Integer.parseInt(pezzi));
                            prod.setImg("/"+precedente);
                            if(s!=null && flag)
                            {
                                if(!(DAOProdotto.viewProduct("codice",prod.getCodice(),"size",s).size()>0)) {
                                    prod.setSize(s.toUpperCase());
                                    flag = DAOProdotto.addProduct(prod);
                                }
                            }
                            if(m!=null && flag)
                            {
                                if(!(DAOProdotto.viewProduct("codice",prod.getCodice(),"size",s).size()>0)) {
                                prod.setSize(m.toUpperCase());
                                flag=DAOProdotto.addProduct(prod);
                                }
                            }
                            if(l!=null && flag)
                            {
                                if(!(DAOProdotto.viewProduct("codice",prod.getCodice(),"size",s).size()>0)) {
                                prod.setSize(l.toUpperCase());
                                flag=DAOProdotto.addProduct(prod);
                                }
                            }
                            if(xl!=null && flag)
                            {
                                if(!(DAOProdotto.viewProduct("codice",prod.getCodice(),"size",s).size()>0)) {
                                prod.setSize(xl.toUpperCase());
                                flag=DAOProdotto.addProduct(prod);
                                }
                            }
                            if(flag)
                                snx.setAttribute("successo", "true");
                            else
                                snx.setAttribute("successo","false");
                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ServletProduct");
                            dispatcher.forward(req, resp);
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                    else
                        if(bottone.equalsIgnoreCase("visualizzaPerData"))
                        {
                            String startDate = req.getParameter("startDate");
                            String endDate = req.getParameter("endDate");
                            if (startDate != null && endDate != null)
                            {
                                try
                                {
                                    req.setAttribute("ordini", DAOOrdine.viewOrdine(startDate,endDate));
                                    indirizzo="/visualizzaOrdini.jsp";
                                }
                                catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }
                                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(indirizzo);
                                dispatcher.forward(req, resp);
                            }
                        }
                        else
                            if(bottone.equalsIgnoreCase("transitoAddProd"))
                            {
                                indirizzo="/addProduct.jsp";
                                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(indirizzo);
                                dispatcher.forward(req, resp);
                            }
                            else
                                if(bottone.equalsIgnoreCase("transitoDate"))
                                {
                                    indirizzo="/orderDate.jsp";
                                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(indirizzo);
                                    dispatcher.forward(req, resp);
                                }
                                else
                                    if(bottone.equalsIgnoreCase("forClient"))
                                    {
                                        try
                                        {
                                            ArrayList<Ordine> ordini = DAOOrdine.viewOrdine();
                                            ArrayList<User> user = new ArrayList<>();
                                            String precendente = "";

                                            for (Ordine o : ordini)
                                                if (precendente.indexOf(o.getUser().getEmail()) == -1)
                                                {
                                                    user.add(o.getUser());
                                                    precendente += " " + o.getUser().getEmail();
                                                }
                                            req.setAttribute("user", user);
                                            indirizzo = "/orderForClient.jsp";
                                            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(indirizzo);
                                            dispatcher.forward(req, resp);
                                        }
                                        catch (SQLException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                    else
                                        if(bottone.equalsIgnoreCase("modifica"))
                                        {
                                            try
                                            {
                                                req.setAttribute("prod",DAOProdotto.viewProduct("codice",(String)req.getParameter("codice")));
                                                indirizzo = "/modifcaProd.jsp";
                                                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(indirizzo);
                                                dispatcher.forward(req, resp);
                                            }
                                            catch (SQLException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                        else
                                            if(bottone.equalsIgnoreCase("modificaDati"))
                                            {
                                                String codice= req.getParameter("codice").trim();
                                                String nome=req.getParameter("nome").trim();
                                                String genere=req.getParameter("genere").trim();
                                                String prezzo=req.getParameter("prezzo").trim();
                                                String descrizione=req.getParameter("descrizione").trim();
                                                String categoria=req.getParameter("categoria").trim();
                                                String brand=req.getParameter("brand").trim();
                                                String sconto=req.getParameter("sconto").trim();
                                                String pezzi=req.getParameter("pezzi").trim();
                                                String s=req.getParameter("S").trim();
                                                String m=req.getParameter("M").trim();
                                                String l=req.getParameter("L").trim();
                                                String xl=req.getParameter("XL").trim();
                                                String precedente="";
                                                boolean flag=true;
                                                // inizio crea dir
                                                String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY+genere+"\\"+categoria+"\\";
                                                File uploadDir = new File(uploadPath);
                                                uploadDir.setWritable(true);
                                                if (!uploadDir.exists())
                                                    uploadDir.mkdir();
                                                // fine creazione dir
                                                String fileName="";
                                                try{
                                                    for (Part part : req.getParts()) {
                                                        fileName = getFileName(part);
                                                        if(fileName.equalsIgnoreCase(""))
                                                            continue;
                                                        else
                                                            precedente=fileName;
                                                        part.write(uploadPath + precedente);
                                                        part.write("C:/Users/marco/OneDrive/Desktop/edress/web/img/prodotti/"+genere+"/"+categoria+"/"+precedente);
                                                    }
                                                    Prodotto prod=new Prodotto();
                                                    prod.setCodice(codice);
                                                    prod.setNome(nome);
                                                    prod.setGenere(genere);
                                                    prod.setPrezzo(Double.parseDouble(prezzo));
                                                    prod.setDescrizione(descrizione);
                                                    prod.setCategoria(categoria);
                                                    prod.setBrand(brand);
                                                    prod.setSconto(Integer.parseInt(sconto));
                                                    if(!precedente.equalsIgnoreCase(""))
                                                        prod.setImg("/"+precedente);
                                                    else
                                                        prod.setImg(req.getParameter("img"));

                                                    DAOProdotto.modifyProduct("nome",prod.getNome(),"immagine",prod.getImg(),"genere",prod.getGenere(),"prezzo", String.valueOf(prod.getPrezzo()),"descrizione",prod.getDescrizione(),"categoria",prod.getCategoria(),"brand",prod.getBrand(),"sconto", String.valueOf( prod.getSconto()),"codice",prod.getCodice(),"size","M");
                                                    DAOProdotto.modifyProduct("nome",prod.getNome(),"immagine",prod.getImg(),"genere",prod.getGenere(),"prezzo", String.valueOf(prod.getPrezzo()),"descrizione",prod.getDescrizione(),"categoria",prod.getCategoria(),"brand",prod.getBrand(),"sconto", String.valueOf( prod.getSconto()),"codice",prod.getCodice(),"size","S");
                                                    DAOProdotto.modifyProduct("nome",prod.getNome(),"immagine",prod.getImg(),"genere",prod.getGenere(),"prezzo", String.valueOf(prod.getPrezzo()),"descrizione",prod.getDescrizione(),"categoria",prod.getCategoria(),"brand",prod.getBrand(),"sconto", String.valueOf( prod.getSconto()),"codice",prod.getCodice(),"size","L");
                                                    DAOProdotto.modifyProduct("nome",prod.getNome(),"immagine",prod.getImg(),"genere",prod.getGenere(),"prezzo", String.valueOf(prod.getPrezzo()),"descrizione",prod.getDescrizione(),"categoria",prod.getCategoria(),"brand",prod.getBrand(),"sconto", String.valueOf( prod.getSconto()),"codice",prod.getCodice(),"size","XL");

                                                    if(s!=null && (pezzi!=null && !pezzi.equalsIgnoreCase("")))
                                                    {
                                                        prod.setSize(s.toUpperCase());
                                                        flag=DAOProdotto.modifyProduct("nome",prod.getNome(),"immagine",prod.getImg(),"genere",prod.getGenere(),"prezzo", String.valueOf(prod.getPrezzo()),"descrizione",prod.getDescrizione(),"categoria",prod.getCategoria(),"brand",prod.getBrand(),"sconto", String.valueOf( prod.getSconto()),"pezzi",pezzi,"codice",prod.getCodice(),"size",prod.getSize());
                                                    }
                                                    if(m!=null && flag && (pezzi!=null && !pezzi.equalsIgnoreCase("")))
                                                    {
                                                        prod.setSize(m.toUpperCase());
                                                        flag=DAOProdotto.modifyProduct("nome",prod.getNome(),"immagine",prod.getImg(),"genere",prod.getGenere(),"prezzo", String.valueOf(prod.getPrezzo()),"descrizione",prod.getDescrizione(),"categoria",prod.getCategoria(),"brand",prod.getBrand(),"sconto", String.valueOf( prod.getSconto()),"pezzi",pezzi,"codice",prod.getCodice(),"size",prod.getSize());

                                                    }
                                                    if(l!=null && flag && (pezzi!=null && !pezzi.equalsIgnoreCase("")))
                                                    {
                                                        prod.setSize(l.toUpperCase());
                                                        flag=DAOProdotto.modifyProduct("nome",prod.getNome(),"immagine",prod.getImg(),"genere",prod.getGenere(),"prezzo", String.valueOf(prod.getPrezzo()),"descrizione",prod.getDescrizione(),"categoria",prod.getCategoria(),"brand",prod.getBrand(),"sconto", String.valueOf( prod.getSconto()),"pezzi",pezzi,"codice",prod.getCodice(),"size",prod.getSize());
                                                    }
                                                    if(xl!=null && flag && (pezzi!=null && !pezzi.equalsIgnoreCase("")))
                                                    {
                                                        prod.setSize(xl.toUpperCase());
                                                        flag=DAOProdotto.modifyProduct("nome",prod.getNome(),"immagine",prod.getImg(),"genere",prod.getGenere(),"prezzo", String.valueOf(prod.getPrezzo()),"descrizione",prod.getDescrizione(),"categoria",prod.getCategoria(),"brand",prod.getBrand(),"sconto", String.valueOf( prod.getSconto()),"pezzi",pezzi,"codice",prod.getCodice(),"size",prod.getSize());
                                                    }
                                                    if(flag)
                                                        snx.setAttribute("modifica", "true");
                                                    else
                                                        snx.setAttribute("modifica","false");
                                                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ServletProduct");
                                                    dispatcher.forward(req, resp);
                                                }
                                                catch (Exception ex)
                                                {
                                                    ex.printStackTrace();
                                                }
                                            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doGet(req, resp);
    }
}