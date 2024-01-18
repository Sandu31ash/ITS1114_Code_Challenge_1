package lk.ijse.its1114_code_challenge_01.api;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lk.ijse.its1114_code_challenge_01.db.DBProcess;
import lk.ijse.its1114_code_challenge_01.dto.ItemDTO;
//import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Item extends HttpServlet {
    final static Logger logger = LoggerFactory.getLogger(Item.class);
    Connection connection;
    public void init() throws ServletException {
        logger.info("Init the Item Servlet");

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
            var itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            var dbProcess = new DBProcess();
            dbProcess.saveItemOne(itemDTO,connection);

//            List<ItemDTO> itemList= jsonb.fromJson(req.getReader(),new ArrayList<ItemDTO>(){
//            }.getClass().getGenericSuperclass());
//            var dbProcess = new DBProcess();
//            dbProcess.saveItem(itemList,connection);
//            //itemList.forEach(System.out::println);
//            jsonb.toJson(itemList,resp.getWriter());
        }

    }
}
