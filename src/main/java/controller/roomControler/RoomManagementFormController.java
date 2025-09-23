package controller.roomControler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RoomDetails;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomManagementFormController implements Initializable {

    ObservableList <RoomDetails> roomDetails = FXCollections.observableArrayList();

    @FXML
    private JFXRadioButton availableRadio;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPricePerNight;

    @FXML
    private TableColumn<?, ?> colRoomNumber;

    @FXML
    private TableColumn<?, ?> colRoomStatus;

    @FXML
    private TableColumn<?, ?> colRoomType;

    @FXML
    private JFXComboBox<String> roomTypeCombo;

    @FXML
    private TableView<RoomDetails> tblRoomDetails;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private JFXTextField txtPricePerNight;

    @FXML
    private JFXTextField txtRoomNumber;

    @FXML
    public JFXRadioButton maintenanceRadio;

    @FXML
    private JFXRadioButton unavailableRadio;


    //private RoomManagementControler roomManagementControler = new RoomManagementControler();

    RoomManagementControlerServers roomManagementControler = new RoomManagementControler();

    public boolean isRoomNumber(){

        return txtRoomNumber.getLength()!=0   ? true : false;
    }

    public boolean isRoomType(){

        return  roomTypeCombo.getValue()!=null ? true : false;
    }

    public boolean isPerNight(){

        return txtPricePerNight.getLength()!=0 ? true : false ;

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

//        int roomNumber = Integer.parseInt(txtRoomNumber.getText());
//        String roomType = roomTypeCombo.getValue();
//        double pricePerNight = Double.parseDouble(txtPricePerNight.getText());
//        String description = txtDescription.getText();
//        String roomStatus = checkRoomStatus();




        if (isRoomNumber()) {

            if (isRoomType()) {

                if (isPerNight()) {



                    RoomDetails roomDetails1 = new RoomDetails(

                            Integer.parseInt(txtRoomNumber.getText()),
                            roomTypeCombo.getValue(),
                            Double.parseDouble(txtPricePerNight.getText()),
                            txtDescription.getText(),
                            checkRoomStatus()

                    );

                    //int i = roomManagementControler.addRoomDetails(roomNumber, roomType, pricePerNight, description, roomStatus);
                    int i = roomManagementControler.addRoomDetails(roomDetails1);


                    if (i > 0) { //my chaing

                        loadRoomDetails();
                        txtRoomNumber.setText(null);
                        roomTypeCombo.getSelectionModel().clearSelection();
                        txtPricePerNight.setText(null);
                        txtDescription.setText(null);
                        availableRadio.setSelected(false);
                        unavailableRadio.setSelected(false);
                        maintenanceRadio.setSelected(false);


                    }


                } else {

                    JOptionPane.showMessageDialog(null, "Prices Per Night   is Empty , Please Enter your Room Type  !!! ");

                }
            } else {

                JOptionPane.showMessageDialog(null, "Room Type  is Empty , Please Enter your Room Type  !!! ");
            }


        } else {

            JOptionPane.showMessageDialog(null, "Name is Empty , Please Enter your Name !!! ");

        }


    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        System.out.println(checkRoomStatus());
        txtRoomNumber.setText(null);
        roomTypeCombo.getSelectionModel().clearSelection();
        txtPricePerNight.setText(null);
        txtDescription.setText(null);
        availableRadio.setSelected(false);
        unavailableRadio.setSelected(false);
        maintenanceRadio.setSelected(false);


    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) { //my chaing

        int i = roomManagementControler.deleteRoomDetails(txtRoomNumber.getText());


        if (i>0){

            loadRoomDetails();

            txtRoomNumber.setText(null);
            roomTypeCombo.getSelectionModel().clearSelection();
            txtPricePerNight.setText(null);
            txtDescription.setText(null);
            availableRadio.setSelected(false);
            unavailableRadio.setSelected(false);
            maintenanceRadio.setSelected(false);


        }





    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) { // my chaing

        int roomNumber = Integer.parseInt(txtRoomNumber.getText());
        String roomType = roomTypeCombo.getValue();
        double pricePerNight = Double.parseDouble(txtPricePerNight.getText());
        String description = txtDescription.getText();
        String roomStatus = checkRoomStatus();
        int i = roomManagementControler.updateRoomDetails(roomNumber, roomType, pricePerNight, description, roomStatus);


        if (i > 0) {

            loadRoomDetails();

            txtRoomNumber.setText(null);
            roomTypeCombo.getSelectionModel().clearSelection();
            txtPricePerNight.setText(null);
            txtDescription.setText(null);
            availableRadio.setSelected(false);
            unavailableRadio.setSelected(false);
            maintenanceRadio.setSelected(false);


        }




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        ------initialize roomTypeCombo-------
        ObservableList<String> roomTypes = FXCollections.observableArrayList(
                "Single",
                "Double",
                "Suite"
        );
        roomTypeCombo.setItems(roomTypes);
//        ----------------------------------------

//        ------set toggleGroup for radiobuttons------
        ToggleGroup roomStstusToggleGroup = new ToggleGroup();

        availableRadio.setToggleGroup(roomStstusToggleGroup);
        unavailableRadio.setToggleGroup(roomStstusToggleGroup);
        maintenanceRadio.setToggleGroup(roomStstusToggleGroup); //my chaing
//        --------------------------------------------

//        ------set table details----------
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colPricePerNight.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRoomStatus.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));

        loadRoomDetails();

//        ------------------------------------

        tblRoomDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue!=null) {
                setSelectedValues(newValue);
            }


        });


    }

    private void loadRoomDetails(){

        roomDetails.clear();//my chaing

        roomDetails=roomManagementControler.setLoadRoomDetails();

        tblRoomDetails.setItems(roomDetails);

    }

    private String checkRoomStatus(){
        if(availableRadio.isSelected()){
            return "Available";
        }else if(maintenanceRadio.isSelected()){
            return "Maintenance";
        }
        return "UnAvailable";
    }

    private void setRoomStatus(String data){

        if (data.equals("Available")){

            availableRadio.setSelected(true);
        } else if (data.equals("Maintenance")) {

            maintenanceRadio.setSelected(true);

        }else {

            unavailableRadio.setSelected(true);
        }

    }


    private  void setSelectedValues(RoomDetails newValue){

        txtRoomNumber.setText(String.valueOf(newValue.getRoomNumber()));
        roomTypeCombo.setValue(newValue.getRoomType());
        txtPricePerNight.setText(String.valueOf(newValue.getPricePerNight()));
        txtDescription.setText(newValue.getDescription());
        setRoomStatus(newValue.getRoomStatus());

    }


}
