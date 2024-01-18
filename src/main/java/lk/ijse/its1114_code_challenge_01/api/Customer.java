package lk.ijse.its1114_code_challenge_01.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.its1114_code_challenge_01.db.DBProcess;
import lk.ijse.its1114_code_challenge_01.dto.CustomerDTO;
import lk.ijse.its1114_code_challenge_01.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "customer",urlPatterns = "/customer"
//        initParams = {
//                @WebInitParam(name = "db-user",value = "root"),
//                @WebInitParam(name = "db-pw",value = "mysql"),
//                @WebInitParam(name = "db-url",value = "jdbc:mysql://localhost:3306/gdse65JavaEE?createDatabaseIfNotExist=true"),
//                @WebInitParam(name = "db-class",value = "com.mysql.cj.jdbc.Driver")
//        }
)
public class Customer extends HttpServlet {
    final static Logger logger = LoggerFactory.getLogger(Customer.class);

    private static final String GET_CUSTOMER_DATA = "SELECT * FROM customer WHERE id = ?";

    Connection connection;
    public void init() throws ServletException {
        logger.info("Init the Customer Servlet");

        try {
//            var user = getServletConfig().getInitParameter("db-user");
//            var password = getServletConfig().getInitParameter("db-pw");
//            var url = getServletConfig().getInitParameter("db-url");
//            Class.forName(getServletConfig().getInitParameter("db-class"));
//            this.connection = DriverManager.getConnection(url, user, password);
            InitialContext ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/lavazza");
            System.out.println(pool);
            this.connection = pool.getConnection();

        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        if(req.getContentType() == null ||
                !req.getContentType().toLowerCase().startsWith("application/json")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }else {
            Jsonb jsonb = JsonbBuilder.create();
            var customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            var dbProcess = new DBProcess();
            dbProcess.saveCustomerData(customerDTO,connection);

//            List<ItemDTO> itemList= jsonb.fromJson(req.getReader(),new ArrayList<ItemDTO>(){
//            }.getClass().getGenericSuperclass());
//            var dbProcess = new DBProcess();
//            dbProcess.saveItem(itemList,connection);
//            //itemList.forEach(System.out::println);
//            jsonb.toJson(itemList,resp.getWriter());
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var writer = resp.getWriter();
        String id = req.getParameter("id");

        System.out.println(" *ID : "+id);

        if(id == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }else {

            resp.setContentType("text/html");
            var data = new DBProcess();
            var getData = data.getCustomerData(id, connection);
            for (String eachData : getData){
                writer.println(eachData+"\n");
            }

        }

//        if (req.getParameter("id") == null){
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        }
//
//        var id = req.getParameter("id");
//
//        var writer = resp.getWriter();
//        resp.setContentType("text/html");
//
//        // get data
//        try {
//            var ps = connection.prepareStatement(GET_CUSTOMER_DATA);
//
//            ps.setString(1,id);
//            var resultSet = ps.executeQuery();
//
//            if(resultSet.next()){
//                String id1 = resultSet.getString(1);
//                String name1 = resultSet.getString(2);
//                String contact1 = resultSet.getString(3);
//                String address1 = resultSet.getString(4);
//
//                System.out.println("ID : "+id1+" Name : "+name1+" Contact : "+contact1+" Address : "+address1);
//
//            }else {
//                System.out.println("Not Found");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

}
