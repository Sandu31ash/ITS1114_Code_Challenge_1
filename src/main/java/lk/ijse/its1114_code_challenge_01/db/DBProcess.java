package lk.ijse.its1114_code_challenge_01.db;

//import lk.ijse.its1114_code_challenge_01.dto.CustomerDTO;
import lk.ijse.its1114_code_challenge_01.dto.CustomerDTO;
import lk.ijse.its1114_code_challenge_01.dto.ItemDTO;
//import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBProcess {

    private static final String SAVE_CUSTOMER_DATA = "INSERT INTO customer (ID,NAME,CONTACT,ADDRESS) VALUES (?,?,?,?)";
    private static final String GET_CUSTOMER_DATA = "SELECT * FROM customer WHERE id = ?";

    final static Logger logger = LoggerFactory.getLogger(DBProcess.class);

    private static final String SAVE_ITEM_DATA = "INSERT INTO item (CODE,DES,PRICE,QTY) VALUES (?,?,?,?)";

    public void saveCustomerData(CustomerDTO customerDTO, Connection connection){
//        String customItemId = "IT "+UUID.randomUUID();
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER_DATA);
            ps.setString(1, customerDTO.getId());
            ps.setString(2, customerDTO.getName());
            ps.setString(3, customerDTO.getContact());
            ps.setString(4, customerDTO.getAddress());

            if (ps.executeUpdate() != 0) {
                logger.info("Data saved");
                System.out.println("Data saved");
            } else {
                logger.error("Failed to save");
                System.out.println("Failed to save");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<String> getCustomerData(String id, Connection connection) {
        //get data
        List<String> selectedCustomer = new ArrayList<>();
        try {
            var ps = connection.prepareStatement(GET_CUSTOMER_DATA);
            ps.setInt(1, Integer.parseInt(id));
            var rs = ps.executeQuery();

            while (rs.next()) {
                String id1 = rs.getString("id");
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                String address = rs.getString("address");

                System.out.println("You got this -> "+id1+" , "+name+" , "+contact+" , "+address);

                selectedCustomer.add(id1);
                selectedCustomer.add(name);
                selectedCustomer.add(contact);
                selectedCustomer.add(address);
            }
            return selectedCustomer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //    public void saveItem(List<ItemDTO> items,Connection connection){
////          String customItemId = "IT "+UUID.randomUUID();
//            for(ItemDTO itemData : items){
//                try {
//                    var ps = connection.prepareStatement(SAVE_ITEM_DATA);
//                    ps.setString(1, itemData.getCode());
//                    ps.setString(2, String.valueOf(itemData.getDes()));
//                    ps.setDouble(3, itemData.getPrice());
//                    ps.setInt(4, itemData.getQty());
//
//                    if (ps.executeUpdate() != 0) {
//
//                        System.out.println("Data saved");
//                    } else {
//
//                        System.out.println("Failed to save");
//
//                    }
//
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//    }

    public void saveItemOne(ItemDTO items, Connection connection){
//        String customItemId = "IT "+UUID.randomUUID();
        try {
            var ps = connection.prepareStatement(SAVE_ITEM_DATA);
            ps.setString(1, items.getCode());
            ps.setString(2, String.valueOf(items.getDes()));
            ps.setDouble(3, items.getPrice());
            ps.setInt(4, items.getQty());

            if (ps.executeUpdate() != 0) {
                logger.info("Data saved");
                System.out.println("Data saved");
            } else {
                logger.error("Failed to save");
                System.out.println("Failed to save");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
