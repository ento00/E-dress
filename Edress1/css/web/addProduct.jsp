<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>AddProduct</title>
    <link rel="icon" href="./img/logo_1.png" type="image/x-icon"/>
    <link rel="shortcut" href="./img/logo_1.png" type="image/x-icon"/>
    <meta charset="UTF-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="js/jquery.min.js"></script>
    <script src="js/Mprodotto.js"></script>
    <link rel="stylesheet" href="css/form.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
<%@ include file="header.jsp" %>
<%
    if (ssn.getAttribute("accesso") != null && ssn.getAttribute("userBean") != null) {
        user = (User) ssn.getAttribute("userBean");
        if (user != null && user.getTipo().equalsIgnoreCase("admin")) {
%>
<form class="container_mod" action="admin" method="POST" enctype="multipart/form-data"
      onsubmit="if(carica()==false){return false;}">
    <div class="fild">
        <div class="d_fild">
            <label class="fild_l">Immagine</label><input type="file" class="input_mod" name="file" required>
        </div>
        <div class="d_fild">
            <label class="fild_l">Codice:</label><input type="text" class="input_mod" name="codice" id="codice">
            <span id="errcodice" class="form-validation__text"></span>
        </div>
        <div class="d_fild">
            <label class="fild_l">Nome:</label><input type="text" class="input_mod" name="nome" id="nome">
            <span id="errnome" class="form-validation__text"></span>
        </div>
        <div class="d_fild">
            <label class="fild_l">Genere:</label><input type="text" class="input_mod" name="genere" id="genere">
            <span id="errgenere" class="form-validation__text"></span>
        </div>
        <div class="d_fild">
            <label class="fild_l">Prezzo:</label><input type="text" class="input_mod" name="prezzo" id="prezzo">
            <span id="errprezzo" class="form-validation__text"></span>
        </div>
        <div class="d_fild">
            <label class="fild_l"> Descrizione:</label><textarea class="input_mod" name="descrizione"></textarea>
        </div>
        <div class="d_fild">
            <label class="fild_l">Categoria:</label><input type="text" class="input_mod" name="categoria"
                                                           id="categoria">
            <span id="errcategoria" class="form-validation__text"></span>
        </div>
        <div class="d_fild">
            <label class="fild_l">Brand:</label><input type="text" class="input_mod" name="brand" id="brand">
            <span id="errbrand" class="form-validation__text"></span>
        </div>
        <div class="d_fild">
            <label class="fild_l"> Sconto:</label><input type="text" class="input_mod" name="sconto" id="sconto">
            <span id="errsconto" class="form-validation__text"></span>
        </div>
        <div class="d_fild">
            <label class="fild_l"> Pezzi:</label><input type="text" class="input_mod" name="pezzi" id="pezzi">
            <span id="errpezzi" class="form-validation__text"></span>
        </div>
        <div class="d_fild">
            <label class="fild_l" id="taglie">Size:<input type="checkbox" name="S" value="S" id="S"> S
                <input type="checkbox" name="M" value="M" id="M"> M
                <input type="checkbox" name="L" value="L" id="L"> L
                <input type="checkbox" name="XL" value="XL" id="XL"> XL
            </label>
            <span id="errsize" class="form-validation__text"></span>
        </div>
        <button class="but_mod" type="submit" name="action" value="Carica">Carica</button>
    </div>

    <label class="msg"></label>
</form>
<%
        } else {
            response.sendRedirect("index.jsp");
        }
    }
%>
<%@ include file="footer.jsp"%>
</body>
</html>
