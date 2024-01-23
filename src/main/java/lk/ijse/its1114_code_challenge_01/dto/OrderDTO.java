package lk.ijse.its1114_code_challenge_01.dto;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO implements Serializable {
    private String code;
    private Date date;
    private String id;
    private String name;
    private double tot;
    private double disc;
    private double stot;
    private double cash;
    private double bal;

}

