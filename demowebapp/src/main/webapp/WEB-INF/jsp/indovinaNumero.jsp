<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.demowebapp.model.*" %>

<%
    pageContext.setAttribute("cp", application.getContextPath());
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Indovina il numero</title>
    <!-- Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
      crossorigin="anonymous"
    />
    <link href="${cp}/styles/indovinaNumero.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
      <div class="container-fluid">
        <a class="navbar-brand" href="${cp}">Esempi JSP</a>
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarCollapse"
          aria-controls="navbarCollapse"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav me-auto mb-2 mb-md-0">
            <li class="nav-item">
              <a class="nav-link" href="${cp}">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link active"  aria-current="page" href="${cp}/indovina">Indovina il numero</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <main class="container">
      <div class="bg-light p-5 rounded">
        <form action="<%= application.getContextPath() %>/indovina" method="post" autocomplete="off">
          <h1>Indovina il numero</h1>
          <div>Indovina un numero fra 100 e 200</div>
          <%
            String controlsDisabled = ((Boolean)session.getAttribute("found")) ? "disabled":"";
          %>
          <input name="number" type="text" <%= controlsDisabled %> />
          <input type="submit" value="Indovina" <%= controlsDisabled %> />
          <%
            String resetDisplay = ((Boolean)session.getAttribute("found")) ? "inline" : "none";
            String resetDisabled = ((Boolean)session.getAttribute("found")) ? "" : "disabled";
          %>
          <input id="reset" name="reset" type="submit" value="Nuovo numero" style="display: <%= resetDisplay %>" <%= resetDisabled %> >
        </form>
        <ol id="messages">
<%
            List<Result> results = (List<Result>)session.getAttribute("results");
            if (results != null) {
                for(Result result: results) {
                    pageContext.setAttribute("current", result);
%>
                    <li class="${current.cssMessageClass}">Tentato ${current.number} (${current.message})</li>
<%
                }
            }
%>
        </ol>
      </div>
    </main>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
      crossorigin="anonymous"
    ></script>
    <script>
        window.addEventListener('load', () => {
            document.querySelector('form input:enabled').focus()
        })
    </script>
  </body>
</html>
