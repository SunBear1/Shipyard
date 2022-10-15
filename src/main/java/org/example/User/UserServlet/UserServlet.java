package org.example.User.UserServlet;

import org.example.Servlet.ServletUtility;
import org.example.User.DTO.GetUserResponse;
import org.example.User.DTO.GetUsersResponse;
import org.example.User.Service.UserService;
import org.example.User.User;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@WebServlet(urlPatterns = {
        UserServlet.Paths.USER + "/*",
        UserServlet.Paths.USERS,
})

public class UserServlet extends HttpServlet {
    public static class Paths {

        public static final String USER = "/api/user";
        public static final String USERS = "/api/users";

    }


    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        if (Paths.USER.equals(servletPath)) {
            getUser(request, response);
        } else if (Paths.USERS.equals(servletPath)) {
            getUsers(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService service = (UserService) request.getServletContext().getAttribute("userService");
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
        UserService service = (UserService) request.getServletContext().getAttribute("userService");
        response.setContentType("application/json");
        response.getWriter().write(jsonb.toJson(GetUsersResponse.entityToDtoMapper().apply(service.findAll())));
        response.setStatus(HttpServletResponse.SC_OK);
    }


}
