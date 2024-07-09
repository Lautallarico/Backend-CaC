package infraestructure.entity.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.models.User;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/users")
public class UserController extends HttpServlet {

    private ObjectMapper mapper;
    private UserService userService;

    public UserController() {
        this.mapper = new ObjectMapper();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null) {
            User user = userService.findById(id);
            if (user != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                resp.getWriter().write(mapper.writeValueAsString(user));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("User not found \n");
                resp.getWriter().write("Users in database: \n");
                List<User> allUsers = userService.getAllUsers();
                String jsonResponse = mapper.writeValueAsString(allUsers);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonResponse);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = mapper.readValue(req.getInputStream(), User.class);
        userService.saveUser(user);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("User saved");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        if (id != null) {
            User user = userService.findById(id);

            if (user != null) {
                userService.deleteUser(id);

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("User delete succesful");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("User not found");
            }


        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = mapper.readValue(req.getInputStream(), User.class);
        User userFound = userService.findById(id);

        if (userFound == null){
            resp.getWriter().write("User cant found");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (id != null) {
            user.setId(Integer.parseInt(id));
        }

        userService.updateUser(user);

        resp.setContentType("application/json");
        resp.getWriter().write("User updated");
        resp.setStatus(HttpServletResponse.SC_OK);

    }
}
