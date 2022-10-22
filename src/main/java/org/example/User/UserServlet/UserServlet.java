package org.example.User.UserServlet;

import org.example.Servlet.ServletUtility;
import org.example.User.DTO.GetUserResponse;
import org.example.User.DTO.GetUsersResponse;
import org.example.User.Service.UserService;
import org.example.User.User;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;


@WebServlet(urlPatterns = {
        UserServlet.Paths.USER + "/*",
        UserServlet.Paths.USERS,
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class UserServlet extends HttpServlet {

    private UserService service;

    public static class Paths {

        public static final String USER = "/api/user";
        public static final String USERS = "/api/users";
        public static final String AVATAR = "avatar";

    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public UserServlet(UserService service){
        this.service = service;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();

        if (Paths.USER.equals(servletPath)) {
            if (isAvatarInPath(request, response)) {
                getUserAvatar(request, response);
            } else {
                getUser(request, response);
            }
        } else if (Paths.USERS.equals(servletPath)) {
            getUsers(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String servletPath = request.getServletPath();

        if (Paths.USER.equals(servletPath)) {
            if (isAvatarInPath(request, response)) {
                putUserAvatar(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();

        if (Paths.USER.equals(servletPath)) {
            if (isAvatarInPath(request, response)) {
                deleteUserAvatar(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // UserService service = (UserService) request.getServletContext().getAttribute("userService");
        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<User> user = service.find(login);
        if (user.isPresent()) {
            response.setContentType("application/json");
            response.getWriter()
                    .write(jsonb.toJson(GetUserResponse.entityToDtoMapper().apply(user.get())));
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter()
                    .write("User not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // UserService service = (UserService) request.getServletContext().getAttribute("userService");
        response.setContentType("application/json");
        response.getWriter().write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(service.findAll())));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void getUserAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // UserService service = (UserService) request.getServletContext().getAttribute("userService");
        String[] urlParts = request.getPathInfo().split("/");
        String login = urlParts[1];
        Optional<User> user = service.find(login);
        if (user.isPresent()) {
            try {
                response.setContentLength(user.get().getAvatar().length);
                response.getOutputStream().write(user.get().getAvatar());
                response.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (NullPointerException ex) {
                response.getWriter().write("User does not have avatar");
                response.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putUserAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // UserService service = (UserService) request.getServletContext().getAttribute("userService");
        String[] urlParts = request.getPathInfo().split("/");
        String login = urlParts[1];
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            Part avatar = request.getPart("avatar");
            if (avatar != null) {
                if (user.get().getAvatar() == null) {
                    response.setStatus(HttpServletResponse.SC_CREATED);
                } else {
                    response.setStatus(HttpServletResponse.SC_CREATED);
                }
                service.updateAvatar(login, avatar.getInputStream());
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteUserAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // UserService service = (UserService) request.getServletContext().getAttribute("userService");
        String[] urlParts = request.getPathInfo().split("/");
        String login = urlParts[1];
        Optional<User> user = service.find(login);

        if (user.isPresent()) {
            if (user.get().getAvatar() != null) {
                service.deleteAvatar(login);
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.getWriter().write("User does not have avatar");
                response.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private Boolean isAvatarInPath(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] urlParts = request.getPathInfo().split("/");
        if (urlParts.length == 3) {
            if (Paths.AVATAR.equals(urlParts[2])) {
                return true;
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return false;
            }
        } else if (urlParts.length == 2) {
            return false;
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
    }
}
