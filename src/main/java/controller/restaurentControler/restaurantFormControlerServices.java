package controller.restaurentControler;

import model.RestaurantDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface restaurantFormControlerServices {

     ResultSet getRestaurantInfo() throws SQLException;

    int addBill(RestaurantDetails res);


}
