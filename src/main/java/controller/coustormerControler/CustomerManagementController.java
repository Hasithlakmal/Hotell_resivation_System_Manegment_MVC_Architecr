package controller.coustormerControler;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CustormerDetails;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerManagementController implements CustomerManagementControllerServers {

    @Override
    public int addCustormerDetails(CustormerDetails custormerDetails) {

        try {
            Connection connection = DBConnection.getDbConnection_instence().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("insert into customers (name,email,phone,address) values(?,?,?,?)");

            preparedStatement.setObject(1, custormerDetails.getName());
            preparedStatement.setObject(2, custormerDetails.getEmail());
            preparedStatement.setObject(3, custormerDetails.getPhone());
            preparedStatement.setObject(4, custormerDetails.getAddress());

            return preparedStatement.executeUpdate();


        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null,"mix it ");
            throw new RuntimeException(e);
        }


    }

    @Override
    public int updateCustormerDetails(CustormerDetails custormerDetails) {

        try {
            Connection connection = DBConnection.getDbConnection_instence().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("update customers set name='" + custormerDetails.getName() + "', email='" + custormerDetails.getEmail() + "',address='" + custormerDetails.getAddress() + "' where phone='" + custormerDetails.getPhone() + "'");
            int i = preparedStatement.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteCustormerDetails(String PhoneNumber, String name, String email) {

        try {

            Connection connection = DBConnection.getDbConnection_instence().getConnection();
            PreparedStatement preparedStatement = null;

            String where = "";
            if (PhoneNumber.length() != 0) {

                preparedStatement = connection.prepareStatement("delete from customers where phone='" + PhoneNumber + "'");

            }
            if (name.length() != 0 && email.length() == 0 && PhoneNumber.length() == 0) {

                preparedStatement = connection.prepareStatement("delete from customers where name='" + name + "'");


            }
            if (email.length() == 0 && name.length() != 0 && PhoneNumber.length() != 0) {

                preparedStatement = connection.prepareStatement("delete from customers where phone='" + PhoneNumber + "'");


            }
            if (email.length() != 0 && name.length() != 0 && PhoneNumber.length() == 0) {

                preparedStatement = connection.prepareStatement("delete from customers where email='" + email + "'");


            }
            if (email.length() != 0 && PhoneNumber.length() != 0 && name.length() != 0) {

                preparedStatement = connection.prepareStatement("delete from customers where phone='" + PhoneNumber + "'");


            }


            int i = preparedStatement.executeUpdate();
            return i;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<CustormerDetails> setViweDetails() {

        ObservableList<CustormerDetails> custormerDetails = FXCollections.observableArrayList();

        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection_instence().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                CustormerDetails custormerinfo = new CustormerDetails(

                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")

                );

                custormerDetails.add(custormerinfo);
            }
            return custormerDetails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
