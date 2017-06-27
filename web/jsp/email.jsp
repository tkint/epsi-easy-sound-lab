<%@page import="model.Mail"%>
<%@page import="dao.MailDAO"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.GsonBuilder"%>

<%
    Gson gson = new GsonBuilder().create();
    Mail mail = new Mail();
    String s = request.getParameter("id");
    if (s != null && !s.trim().equals("")) {
        try {
            MailDAO mailDAO = MailDAO.getInstance();
            mail = mailDAO.getEntityById(Integer.parseInt(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    out.print(gson.toJson(mail));
%>  