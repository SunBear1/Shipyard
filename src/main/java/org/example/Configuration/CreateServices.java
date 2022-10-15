package org.example.Configuration;

import org.example.DataStore.DataStore;
import org.example.User.Repository.UserRepository;
import org.example.User.Service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataStore dataSource = (DataStore) sce.getServletContext().getAttribute("datasource");
        sce.getServletContext().setAttribute("userService", new UserService(new UserRepository(dataSource)));
    }

}
