package controller.roomControler;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RoomDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomManagementControler implements RoomManagementControlerServers {



    //public int addRoomDetails(int roomNumber, String roomType , double pricePerNight , String description , String  roomStatus  ){
    @Override
    public int addRoomDetails(RoomDetails roomDetails) {


    String SQL = "INSERT INTO rooms(room_number, room_type, price_per_night, description, room_status) VALUES(?,?,?,?,?);";

    try {
        Connection connection =DBConnection.getDbConnection_instence().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        preparedStatement.setObject(1,roomDetails.getRoomNumber());
        preparedStatement.setObject(2,roomDetails.getRoomType());
        preparedStatement.setObject(3,roomDetails.getPricePerNight());
        preparedStatement.setObject(4,roomDetails.getDescription());
        preparedStatement.setObject(5,roomDetails.getRoomStatus());

        int i = preparedStatement.executeUpdate();

        return i;



    } catch (SQLException e) {
        throw new RuntimeException(e);
    }



}

    @Override
public int deleteRoomDetails (String txtRoomNumber) {


    try {
        Connection connection =DBConnection.getDbConnection_instence().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("  delete from rooms  where room_number ='" + txtRoomNumber + "' ");
        return preparedStatement.executeUpdate();


    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }

    @Override
    public int updateRoomDetails(int roomNumber, String roomType , double pricePerNight , String description , String  roomStatus ){


        try {
            Connection connection =DBConnection.getDbConnection_instence().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("  update rooms set room_type='" + roomType + "',price_per_night='" +pricePerNight + "',description='" + description + "',room_status='" + roomStatus + "' where room_number='" + roomNumber + "'  ");
            return preparedStatement.  executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }



        }


        @Override
    public ObservableList<RoomDetails> setLoadRoomDetails() {

        ObservableList roomDetails= FXCollections.observableArrayList();

        try {
            Connection connection =DBConnection.getDbConnection_instence().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Select * FROM rooms;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomDetails.add(new RoomDetails(
                                resultSet.getInt("room_number"),
                                resultSet.getString("room_type"),
                                resultSet.getDouble("price_per_night"),
                                resultSet.getString("description"),
                                resultSet.getString("room_status")
                        )
                );
            }



//            System.out.println(roomDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return roomDetails;


    }


}
