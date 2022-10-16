package org.example.User.UserServlet;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;

//@WebServlet(urlPatterns = PortraitServlet.Paths.PORTRAITS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class UserAvatarServlet extends HttpServlet {
    public static class Paths {

        public static final String AVATARS = "/api/portraits";

    }

}
