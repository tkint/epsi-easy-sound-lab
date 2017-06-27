<%@page import="model.User"%>
<%@page import="dao.UserDAO"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%
    Gson gson = new GsonBuilder().create();
    List<User> users = new ArrayList();
    String s = request.getParameter("value");
    if (s != null && !s.trim().equals("")) {
        try {
            UserDAO userDAO = UserDAO.getInstance();
            users = userDAO.getUsersByEmailPseudoName(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    out.print(gson.toJson(users));
%>  