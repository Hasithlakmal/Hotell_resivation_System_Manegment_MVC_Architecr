package controller.restaurentControler;

import db.DBConnection;
import model.RestaurantDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class restaurantControler implements restaurantFormControlerServices {


    public ResultSet getRestaurantInfo() throws SQLException {

        Connection connection = DBConnection.getDbConnection_instence().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from restaurent");
        return preparedStatement.executeQuery();

    }

    public int addBill(RestaurantDetails res) {

        try {
            Connection connection = DBConnection.getDbConnection_instence().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into bill values (?,?,?,?,?)");

            preparedStatement.setObject(1,res.getItemcode());
            preparedStatement.setObject(2,res.getQty());
            preparedStatement.setObject(3,res.getDescription());
            preparedStatement.setObject(4,res.getPrice());
            preparedStatement.setObject(5,res.getDate());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
