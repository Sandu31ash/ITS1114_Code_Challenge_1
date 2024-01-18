package lk.ijse.its1114_code_challenge_01.db;

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

    private static final String SAVE_DATA = "INSERT INTO customer (ID,NAME,CONTACT,ADDRESS) VALUES (?,?,?,?)";
    private static final String GET_DATA = "SELECT * FROM customer WHERE id = ?";

    final static Logger logger = LoggerFactory.getLogger(DBProcess.class);

    private static final String SAVE_ITEM_DATA = "INSERT INTO ItemNew (CODE,DES,PRICE,QTY) VALUES (?,?,?,?)";

    public String saveCustomerData(String id, String name, String contact, String address, Connection connection) {
        // save / manipulate data
        try {
            var ps = connection.prepareStatement(SAVE_DATA);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, contact);
            ps.setString(4, address);

            if (ps.executeUpdate() != 0) {
                return "Data saved";
            } else {
                return "Failed to save data";
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getCustomerData(String id, Connection connection) {
        //get data
        List<String> selectedCustomer = new ArrayList<>();
        try {
            var ps = connection.prepareStatement(GET_DATA);
            ps.setInt(1, Integer.parseInt(id));
            var rs = ps.executeQuery();

            while (rs.next()) {
                int custId = rs.getInt("id");
                String name = rs.getString("name");
                String city = rs.getString("city");
                String email = rs.getString("email");

                selectedCustomer.add(String.valueOf(custId));
                selectedCustomer.add(name);
                selectedCustomer.add(city);
                selectedCustomer.add(email);
            }
            return selectedCustomer;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveItem(List<ItemDTO> items,Connection connection){
          String customItemId = "IT "+UUID.randomUUID();
            for(ItemDTO itemData : items){
                try {
                    var ps = connection.prepareStatement(SAVE_ITEM_DATA);
                    ps.setString(1, customItemId);
                    ps.setString(2, String.valueOf(itemData.getDes()));
                    ps.setDouble(3, itemData.getPrice());
                    ps.setInt(4, itemData.getQty());

                    if (ps.executeUpdate() != 0) {

                        System.out.println("Data saved");
                    } else {

                        System.out.println("Failed to save");

                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
    }

    public void saveItemOne(ItemDTO items, Connection connection){
        String customItemId = "IT "+UUID.randomUUID();
            try {
                var ps = connection.prepareStatement(SAVE_ITEM_DATA);
                ps.setString(1, customItemId);
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
