package lk.ijse.its1114_code_challenge_01.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.its1114_code_challenge_01.db.DBProcess;
import lk.ijse.its1114_code_challenge_01.dto.CustomerDTO;
import lk.ijse.its1114_code_challenge_01.dto.ItemDTO;
import lk.ijse.its1114_code_challenge_01.dto.OrderDTO;
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


@WebServlet(name = "order",urlPatterns = "/order")

public class Order extends HttpServlet {
    final static Logger logger = LoggerFactory.getLogger(Order.class);
    Connection connection;
    public void init() throws ServletException {
        logger.info("Init the Order Servlet");

        try {
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
            var orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);

            System.out.println(orderDTO.getCode()+","+orderDTO.getName());
            System.out.println(orderDTO);

            var dbProcess = new DBProcess();
            dbProcess.saveOrder(orderDTO,connection);
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var writer = resp.getWriter();
        String code = req.getParameter("code");

        System.out.println("OrderCode : "+code);

        if(code == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }else {

            resp.setContentType("text/html");
            var data = new DBProcess();
            var getData = data.getOrderData(code, connection);
            for (String eachData : getData){
                writer.println(eachData+"\n");
            }

        }
    }


}

//
//
//{
//        "oCode" : "O001",
//        "date" : "2024-01-22",
//        "id" : "C001",
//        "name" : "Ash",
//        "tot" : "1000",
//        "disc" : "5",
//        "sTot" : "950",
//        "cash" : "5000",
//        "bal" : "4050"
//        }

//{
//        "id" : "C001",
//        "name" : "Ash",
//        "contact" : "525",
//        "address" : "Rome"
//        }