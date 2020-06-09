<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Aiuto</title>
    <link rel="icon" href="./img/logo_1.png"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/areaPersonale.css">
    <link rel="stylesheet" href="css/footer.css">
</head>

<%@ include file="header.jsp" %>
<div class="container_p wrapper">

    <div class="title_profile"><h2>Hai bisogno di aiuto?</h2></div>

    <ul class="ul_p">
        <li class="li_p2">
            <a class="button_p" href="generale.jsp">
                <img class="img-p" alt="" src="./img/faq.png">
                <span class="titolo">Generali</span>
            </a>
        </li>
        <li class="li_p2">
            <a class="button_p" href="infoOrder.jsp">
                <img class="img-p" alt="" src="./img/profile/box.png">
                <span class="titolo">Ordini</span>
            </a>
        </li>
        <li class="li_p2">
            <a class="button_p" href="pagamenti.jsp">
                <img class="img-p" alt="" src="./img/pagamento.png">
                <span class="titolo">Pagamento</span>
            </a>
        </li>
        <li class="li_p2">
            <a class="button_p" href="reso.jsp">
                <img class="img-p" alt="Dati personali" src="./img/resoH.png">
                <span class="titolo">Reso</span>
            </a>
        </li>
        <li class="li_p2">
            <a class="button_p" href="spedizione.jsp">
                <img class="img-p" alt="" src="./img/spedizioneH.png">
                <span class="titolo">Spedizione</span>
            </a>
        </li>
        <li class="li_p2">
            <a class="button_p" href="index.jsp">
                <img class="img-p" alt="" src="./img/profile/home_b.png">
                <span class="titolo">Home</span>
            </a>
        </li>
    </ul>
</div>

</body>
<%@ include file="footer.jsp" %>
</html>
