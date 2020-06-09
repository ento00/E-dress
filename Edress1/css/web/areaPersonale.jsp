<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Area Personale</title>
    <link rel="icon" href="./img/logo_1.png"/>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/areaPersonale.css">
    <link rel="stylesheet" href="css/footer.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>


</head>
<%@ include file="header.jsp" %>
<div class="container_p wrapper">
    <%if(ssn.getAttribute("accesso") != null && ssn.getAttribute("userBean")!=null)
    {
        user = (User) ssn.getAttribute("userBean");

    %>
    <div class="title_profile">
<%--        <h1>Benvenuto nella tua Area Personale</h1>--%>
        <h2>Ciao <%= user.getNome()%>!</h2>
    </div>
    <form action="userController" method="post">
        <ul class="ul_p">
            <li class="li_p">
                <button class="button_p" name="action" value="visualizzaDati">
                    <img class="img-p" alt="Dati personali" src="./img/profile/profile.png">
                    <span class="titolo">Profilo</span>
                </button>
            </li>
            <li class="li_p">
                <button  class="button_p" name="action" value="modificaDati">
                    <img class="img-p" alt="Modifica dati" src="./img/profile/setting_gear.png">
                    <span class="titolo">Modifica profilo</span>
                </button>
            </li>
            <% if(user.getTipo().equalsIgnoreCase("user"))
                {%>
            <li class="li_p">
                <button  class="button_p" name="action" value="visualizzaOrdini">
                    <img  class="img-p" alt="I miei ordini" src="./img/profile/box.png">
                    <span class="titolo">I Miei Ordini</span>
                </button>
            </li>
                <%}%>
            </form>
            <%if(user.getTipo().equalsIgnoreCase("admin")){%>
            <form action="admin" method="post">
                <li class="li_p">
                <button  class="button_p" name="action" value="transitoAddProd">
                    <img  class="img-p" alt="Aggiungi prodotto" src="./img/profile/add_prod.png">
                    <span class="titolo">Aggiunta Prodotto</span>
                </button>
                </li>
                <li class="li_p">
                <button  class="button_p" name="action" value="visualizzaOrdini">
                    <img  class="img-p" alt="Tutti gli ordini" src="./img/profile/order_a.png">
                    <span class="titolo">Ordini Complessivi</span>
                </button>
                </li>
                <li class="li_p">
                    <button  class="button_p" name="action" value="transitoDate">
                        <img  class="img-p" alt="Ordini Data" src="./img/profile/order_data.png">
                        <span class="titolo">Ordini Data a Data</span>
                    </button>
                </li>
            <%}%>
            <li class="li_p">
                <a class="button_p" value="home" href="index.jsp">
                    <img class="img-p" alt="Home" src="./img/profile/home_b.png">
                    <span class="titolo">Torna alla Home</span>
                </a>
            </li>
        </ul>
    </form>
    <%}else
        response.sendRedirect("./index.jsp");
    %>
</div>

</body>
<%@ include file="footer.jsp" %>

</html>
