<%@page import="dao.PlaylistDAO"%>
<%@page import="model.Playlist"%>
<%@page import="dao.UserDAO"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.GsonBuilder"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%
    Gson gson = new GsonBuilder().create();
    List<Playlist> playlists = new ArrayList();
    String idUser = request.getParameter("idUser");
    String value = request.getParameter("value");
    if (value != null && !value.trim().equals("")) {
        try {
            PlaylistDAO playlistDAO = PlaylistDAO.getInstance();
            playlists = playlistDAO.getPlaylistsByName(Integer.parseInt(idUser), value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    out.print(gson.toJson(playlists));
%>  