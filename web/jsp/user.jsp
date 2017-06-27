<%@page import="model.User"%>
<%@page import="dao.UserDAO"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.GsonBuilder"%>

<%
    Gson gson = new GsonBuilder().create();
    User user = new User();
    String s = request.getParameter("id");
    if (s != null && !s.trim().equals("")) {
        try {
            UserDAO userDAO = UserDAO.getInstance();
            user = userDAO.getEntityById(Integer.parseInt(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    out.print(gson.toJson(user));
%>  