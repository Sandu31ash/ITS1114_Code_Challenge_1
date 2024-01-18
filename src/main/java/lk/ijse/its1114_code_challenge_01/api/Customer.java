package lk.ijse.its1114_code_challenge_01.api;

import lk.ijse.its1114_code_challenge_01.db.DBProcess;
//import lombok.var;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "customer", urlPatterns = "/customer",
        initParams = {
                @WebInitParam(name = "db-user", value = "root"),
                @WebInitParam(name = "db-pw", value = "1234"),
                @WebInitParam(name = "db-url", value = "jdbc:mysql://localhost:3306/lavazza"),
                @WebInitParam(name = "db-class", value = "com.mysql.cj.jdbc.Driver"),
        }
        ,loadOnStartup = 2
)

public class Customer extends HttpServlet {

    Connection connection;
    private static final String SAVE_DATA = "INSERT INTO customer (ID, NAME, CONTACT, ADDRESS) VALUES (?, ?, ?, ?)";

    private static final String GET_DATA = "SELECT * FROM customer WHERE ID = ?";

    @Override
    public void init() throws ServletException {

        try{
            //        var initParameter = getServletContext().getInitParameter("db-user");
            var user = getServletConfig().getInitParameter("db-user");
            var password = getServletConfig().getInitParameter("db-pw");
            var url = getServletConfig().getInitParameter("db-url");
            Class.forName( getServletConfig().getInitParameter("db-class"));
            this.connection = DriverManager.getConnection(url,user,password);

            System.out.println(user);
            System.out.println(password);
            System.out.println(url);

        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // param catch
        var id = req.getParameter("id");
        var name = req.getParameter("name");
        var contact = req.getParameter("contact");
        var address = req.getParameter("address");

        var writer = resp.getWriter();
        resp.setContentType("text/html");
        var data = new DBProcess();
        writer.println(data.saveCustomerData(id,name,contact,address,connection));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        if (req.getParameter("id") == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        var id = req.getParameter("id");

        var writer = resp.getWriter();
        resp.setContentType("text/html");

        // get data
        try {
            var ps = connection.prepareStatement(GET_DATA);

            ps.setString(1,id);
            var resultSet = ps.executeQuery();

            if(resultSet.next()){
                String id1 = resultSet.getString(1);
                String name1 = resultSet.getString(2);
                String contact1 = resultSet.getString(3);
                String address1 = resultSet.getString(4);

                System.out.println("ID : "+id1+" Name : "+name1+" Contact : "+contact1+" Address : "+address1);

            }else {
                System.out.println("Not Found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
