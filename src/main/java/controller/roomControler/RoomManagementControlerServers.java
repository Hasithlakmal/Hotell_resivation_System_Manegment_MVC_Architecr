package controller.roomControler;

import javafx.collections.ObservableList;
import model.RoomDetails;


public interface RoomManagementControlerServers {

    int addRoomDetails(RoomDetails roomDetails);


    int deleteRoomDetails(String txtRoomNumber);


    int updateRoomDetails(int roomNumber, String roomType, double pricePerNight, String description, String roomStatus);

    ObservableList<RoomDetails> setLoadRoomDetails();


}
