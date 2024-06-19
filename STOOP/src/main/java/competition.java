import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "competition", value = "/competition")
public class competition extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //получаем параметры запроса
        try {
            int points = Integer.parseInt(req.getParameter("points"));
        } catch (NumberFormatException ex) { // обрабатываем исключение

            //получаем объект для записи данных на страницу
            PrintWriter out = resp.getWriter();

            try {
                //создаем подключение к СУБД
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT id, name, points from competition WHERE points >= 0");

                while (rs.next()) {
                    out.println(rs.getString("id") + " " + rs.getString("name") + " " + rs.getString("points"));
                }
            } catch (SQLException | ClassNotFoundException e) {
                out.print(e);
            }
            out.close();
        }
    }
}




