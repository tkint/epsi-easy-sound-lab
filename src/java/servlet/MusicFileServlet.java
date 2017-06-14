/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import bean.FolderBean;
import bean.MusicFileBean;
import bean.UserBean;
import dao.MusicFileDAO;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.faces.context.FacesContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import model.MusicFile;

/**
 * The File servlet for serving from absolute path.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/07/fileservlet.html
 */
@MultipartConfig
public class MusicFileServlet extends HttpServlet {

    // Constants ----------------------------------------------------------------------------------
    private static final int DEFAULT_BUFFER_SIZE = 1024; // 10KB.

    // Properties ---------------------------------------------------------------------------------
    private static String parentFilePath = "/easysoundlab";
    private static String subFilePath = "musicfiles";

    private MusicFileDAO musicFileDAO;

    // Actions ------------------------------------------------------------------------------------
    public void init() throws ServletException {

        this.musicFileDAO = new MusicFileDAO();

        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\files".
        // In UNIX, it is just straightforward "/files".
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get requested file by path info.
        String requestedFile = request.getPathInfo();

        // Check if file is actually supplied to the request URI.
        if (requestedFile == null) {
            // Do your thing if the file is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Decode the file name (might contain spaces and on) and prepare file object.
        File file = new File(parentFilePath + "/" + subFilePath, URLDecoder.decode(requestedFile, "UTF-8"));

        // Check if file actually exists in filesystem.
        if (!file.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Get content type by filename.
        String contentType = getServletContext().getMimeType(file.getName());

        // If content type is unknown, then set the default value.
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        // To add new content types, add new mime-mapping entry in web.xml.
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);
        FolderBean folderBean = context.getApplication().evaluateExpressionGet(context, "#{folderBean}", FolderBean.class);

        String sharedParam = request.getParameter("shared");
        boolean shared = sharedParam != null;
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        FileInputStream fileContent = (FileInputStream) filePart.getInputStream();

        byte[] buffer = new byte[fileContent.available()];
        fileContent.read(buffer);

        String musicFileName = fileName.substring(0, fileName.length() - 4);
        String musicFileExtension = fileName.substring(fileName.length() - 4);

        if (musicFileExtension.equals(".wav")) {

            MusicFile musicFile = new MusicFile(folderBean.getCurrentFolder().id, musicFileName, musicFileExtension, 0, shared);
            folderBean.addMusicFile(musicFile);
            musicFile = musicFileDAO.createEntity(musicFile);

            String userDir = createDir(String.valueOf(userBean.getCurrentUser().id));
            String folderDir = createDir(userDir, String.valueOf(folderBean.getCurrentFolder().id));
            String fileDir = createDir(folderDir, String.valueOf(musicFile.id));
            String filePath = fileDir + "/" + musicFile.id + musicFile.extension;

            musicFile.absolutePath = "c:" + filePath;
            musicFile.path = "c:" + fileDir;

            File file = new File(filePath);

            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(buffer);

            try {
                File fichier = new File(file.getAbsolutePath());

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(fichier);
                AudioFormat format = audioStream.getFormat();
                long frames = audioStream.getFrameLength();
                double duration = (frames + 0.0) / format.getFrameRate();

                musicFile.duration = (int) duration;

            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }

            musicFileDAO.updateEntity(musicFile);
        }

        response.sendRedirect(request.getContextPath() + "/faces/folder.xhtml");
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

    public static String createDir(String subDir) {
        String path = createDir(parentFilePath, subFilePath) + "/" + subDir;
        File fullDir = new File(path);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }
        return path;
    }

    public static String createDir(String parent, String subDir) {
        File parentDir = new File(parent);
        if (!parentDir.exists()) {
            parentDir.mkdir();
        }
        String path = parent + "/" + subDir;
        File fullDir = new File(path);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }
        return path;
    }

    public static boolean deleteMusicFile() {
        boolean deleted = false;
        FacesContext context = FacesContext.getCurrentInstance();
        MusicFileBean musicFileBean = context.getApplication().evaluateExpressionGet(context, "#{musicFileBean}", MusicFileBean.class);

        File file = new File(musicFileBean.getCurrentMusicFile().absolutePath);

        if (file.exists()) {
            deleted = file.delete();
        }
        
        File fileDir = new File(musicFileBean.getCurrentMusicFile().path);
        if (fileDir.exists()) {
            deleted = fileDir.delete();
        }

        return deleted;
    }
    
    public static boolean deleteFolder() {
        boolean deleted = false;
        FacesContext context = FacesContext.getCurrentInstance();
        UserBean userBean = context.getApplication().evaluateExpressionGet(context, "#{userBean}", UserBean.class);
        FolderBean folderBean = context.getApplication().evaluateExpressionGet(context, "#{folderBean}", FolderBean.class);

        File file = new File("c:" + parentFilePath + "/" + subFilePath + "/" + userBean.getCurrentUser().id + "/" + folderBean.getCurrentFolder().id);

        if (file.exists()) {
            deleted = file.delete();
        }

        return deleted;
    }
}
