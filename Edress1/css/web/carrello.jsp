<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ page import="model.DAO.DAOProdotto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.bean.Prodotto" %>
<%@ page import="model.bean.Cart" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<head>
    <title>Carrello</title>
    <link rel="icon" href="./img/logo_1.png"/>

    <meta name="viewport" content="width=device-width, initial-scale=1">
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>--%>
    <script src="js/jquery.min.js"></script>
    <script src="js/carrello.js"></script>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/shoppingCart.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
</head>

<body>
<%@ include file="header.jsp" %>

<main class="container">
    <% Cart c = (Cart) request.getSession().getAttribute("cart");
        double totale = 0;
    %>

    <div class="basket">

        <% if (c != null && c.getProdotti().size() > 0) {
            totale = c.getTotale();
            DecimalFormat df = new DecimalFormat("#.##");
            String prezzoString="";



            ArrayList<Prodotto> prodotti = c.getProdotti();
            for (int i = 0; i < prodotti.size(); i++) {
                Prodotto p = prodotti.get(i);

                double prezzo=0;
                prezzoString=df.format((p.getPrezzo()));
                if(p.getSconto()!=0)
                {
                    prezzo=p.getPrezzo()*p.getSconto()/100;
                    prezzo=p.getPrezzo()-prezzo;
                    prezzoString=df.format(prezzo);
                }
        %>
        <input type="hidden" id="numeroP" value="<%=prodotti.size()%>">
        <div class="basket-product">
            <div class="item">
                <div class="product-image">
                    <a href="ServletProduct?codice=<%=p.getCodice()%>">
                        <img src="./img/prodotti/<%=p.getGenere()%>/<%=p.getCategoria()%><%=p.getImg()%>" alt=" Immagine"
                         class="product-frame"></a>
                </div>
                <div class="product-details">
                    <h1 class="head_title"><strong><span class="item-quantity"></span> <%=p.getBrand()%>
                    </strong> <%=p.getNome()%>
                    </h1>
                    <p class="s_txt"><strong><%=p.getCategoria()%>, Taglia <%=p.getSize()%>
                    </strong></p>
                    <p class="s_txt">Codice Prodotto: <%=p.getCodice()%>
                    </p>
                    <div class="remove">
                        <form action="Carrello" method="get">
                            <input type="hidden" value="<%=p.getCodice()%>" id="codice" name="codice">
                            <input type="hidden" value="<%=p.getSize()%>" id="size" name="size">
                            <button name="action" value="rimuovi">Rimuovi</button>
                        </form>
                    </div>
                </div>
            </div>

            <div class="item_price">
                <div class="quatity-div">
                        <input type="number" value="<%= p.getPezzi()%>" id="quantità" min="1" class="quantity-field"
                            onChange="cambiaquantita(this)"
                               <%--onkeydown="return false"--%>>
                        <input type="hidden" value="<%=p.getCodice()%>" id="codiceVariazione" name="codiceVariazione">
                        <input type="hidden" value="<%=p.getSize()%>" id="tagliaVariazione" name="tagliaVariazione">
                        <span class="err" id="err"></span>
                    <input type="hidden" value="<%=p.getPezzi()%>">
                </div>
                <div id="prezzo" class="price-field"><%=prezzoString%>€</div>
            </div>

        </div>


        <%
            }%>
    </div>
    <aside class="pirate">
        <div class="summary">
            <form action="acquisto" method="post" onsubmit="if(verificaOrdine(this.form)==false){return false;}">
                <div class="summary-total-items"><span class="total-items"></span> Prodotti nel carrello</div>
                <div class="summary-subtotal">
                    <div class="subtotal-title">Subtotal</div>
                    <div class="subtotal-value final-value" id="tot"> <%=df.format(totale)%> </div>


                </div>
                <div class="summary-delivery">
                    <select name="delivery-collection" class="summary-delivery-selection" id="spedizione" onchange="setSpedizione()">
                        <option value="0" selected="selected">Seleziona Spedizione</option>
                        <option value="collection">Veloce</option>
                        <option value="first-class">Standard</option>
                    </select>
                </div>
                <div class="summary-subtotal">
                    <div class="subtotal-title">Spedizione</div>
                    <div class="subtotal-value final-value" >
                        <span id="basket-spedizione"></span>
                    </div>

                </div>
                <div class="summary-total">
                    <div class="total-title">Totale</div>
                    <input type="hidden" value="64.0" id="totaleInteroCarrello" name="totaleInteroCarrello">
                    <div class="total-value final-value" id="basket-total"><%=df.format(totale)%>
                    </div>

                </div>
                <div class="summary-checkout">
                    <button class="checkout-cta" type="submit">Procedi e Ordina</button>
                    <p class="err" id="risultato"></p>
                </div>
            </form>
        </div>
    </aside>
    <%} else {
    %>
    <div class="empty_container">
        <div class="empty_cart">
            <img id="empty_cart" src="img/cart_empty.png">
            <h6 class="noRes">Nessun elemento aggiunto al carrello fino ad ora.</h6>
        </div>
    </div>
    <% } %>
</main>
<%@ include file="footer.jsp" %>
</body>
</html>
