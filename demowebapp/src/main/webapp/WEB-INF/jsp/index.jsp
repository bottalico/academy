<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="java.util.*"%>
<html>
  <%@ include file="start.jsp" %>
  <body>
    <div>
      <form action="<%= application.getContextPath() %>/hello" method="post">
        <label for="name">Nome: </label>
        <input id="name" type="text" name="name" />
        <input type="submit" value="Invia" />
      </form>
    </div>
    <% String w = (String)session.getAttribute("welcome"); if (w != null) { %>
    <h1>Hello ${welcome}!!!</h1>
    <% } %>
    <%@ include file="javascript.jsp" %>
  </body>
</html>
