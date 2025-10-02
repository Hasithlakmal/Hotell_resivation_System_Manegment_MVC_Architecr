package controller.restaurentControler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.RestaurantDetails;

import javax.swing.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class restaurantFormControler implements Initializable {

    private String Date;

    private restaurantFormControlerServices controler =new restaurantControler();

    ObservableList<RestaurantDetails> list = FXCollections.observableArrayList();

    ObservableList<RestaurantDetails> addpriceToTable = FXCollections.observableArrayList();


    @FXML
    private JFXComboBox<String> ComboItemCode;

    @FXML
    private Label LabelTotal;

    @FXML
    private JFXButton btnADD;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnPint;

    @FXML
    private TableColumn<?, ?> coiumDesc;

    @FXML
    private TableColumn<?, ?> columDate;

    @FXML
    private TableColumn<?, ?> columItem;

    @FXML
    private TableColumn<?, ?> columPrice;

    @FXML
    private TableColumn<?, ?> columQuty;

    @FXML
    private TableView<RestaurantDetails> tableRest;

    @FXML
    private JFXTextField txtFeildDES;

    @FXML
    private JFXTextField txtFilldQty;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    void AddONAction(ActionEvent event) {


        if (txtFilldQty.getLength() != 0) {


            RestaurantDetails restaurantDetails;
            int updatQyt = Integer.parseInt(txtFilldQty.getText());
         //   double updatePrice = updatQyt * Double.parseDouble(txtPrice.getText());


            Double AItemOfPrice =0.0;

                for (RestaurantDetails details : list) {

                        if (details.getItemcode().equals(ComboItemCode.getValue())){


                                AItemOfPrice=details.getPrice();
                        }

                }

                double updatePrice = updatQyt * AItemOfPrice;

            for (RestaurantDetails res : addpriceToTable) {

                if (res.getItemcode().equals(ComboItemCode.getValue())) {


                    updatQyt += res.getQty();
                    //updatePrice = Double.parseDouble(txtPrice.getText()) * updatQyt;
                    updatePrice = Double.parseDouble(String.valueOf(AItemOfPrice * updatQyt));
                    restaurantDetails = new RestaurantDetails(

                            ComboItemCode.getValue(),
                            updatQyt,
                            txtFeildDES.getText(),
                            updatePrice,
                            Date

                    );


                    addpriceToTable.remove(res);


                    break;

                }


            }


            restaurantDetails = new RestaurantDetails(

                    ComboItemCode.getValue(),
                    updatQyt,
                    txtFeildDES.getText(),
                    updatePrice,
                    Date

            );
            addpriceToTable.add(restaurantDetails);

            addTabelItem();

            setTotal();

            ComboItemCode.setValue(null);
            txtPrice.setText("");
            txtFilldQty.setText("");
            txtFeildDES.setText("");

            restaurantDetails=null;

        } else {

            JOptionPane.showMessageDialog(null, "Qyt Is Empty , Please Enter the Qyt   !!!!");

        }

    }

    @FXML
    void Clear(ActionEvent event) {


        ComboItemCode.setValue(null);
        txtPrice.setText("");
        txtFilldQty.setText("");
        txtFeildDES.setText("");
        addpriceToTable.clear();
        setTotal();

    }

    RestaurantDetails deletobject;

    @FXML
    void Delete(ActionEvent event) {

        addpriceToTable.remove(deletobject);


//        for (RestaurantDetails res : addpriceToTable) {
//
//            if (res.getItemcode().equals(ComboItemCode.getValue())) {
//
//
//                addpriceToTable.remove(res);
//                setTotal();
//                break;
//            }
//
//        }


    }

    @FXML
    void ComboBoxOnAction(ActionEvent event) {

        for (RestaurantDetails details : list) {

            if (details.getItemcode().equals(ComboItemCode.getValue())) {

                txtFeildDES.setText(details.getDescription());
                txtPrice.setText(String.valueOf(details.getPrice()));

            }

            txtFilldQty.setText("1");

        }

    }


    @FXML
    void PrintBil(ActionEvent event) {

        int length=addpriceToTable.size();
        int i=0;

        for (RestaurantDetails res : addpriceToTable) {

           i += controler.addBill(res);

        }

        if (i==length){


            ComboItemCode.setValue(null);
            txtPrice.setText("");
            txtFilldQty.setText("");
            txtFeildDES.setText("");
            addpriceToTable.clear();
            setTotal();


        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println(Date);

        // String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String time = new SimpleDateFormat("hh:mm:ss a").format(new Date());

        System.out.println(time);


        loadComboItam();
        comboBoxSetDetailsLOad();


    }


    public void loadComboItam() {

        ObservableList<String> combo = FXCollections.observableArrayList();

        ResultSet resultSet = null;
        try {
            resultSet = controler.getRestaurantInfo();
            while (resultSet.next()) {

                combo.add(resultSet.getString("item_code"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ComboItemCode.setItems(combo);

    }

    public void comboBoxSetDetailsLOad() {

        try {
            ResultSet resultSet = controler.getRestaurantInfo();

            while (resultSet.next()) {

                list.add(new RestaurantDetails(

                                resultSet.getString("item_code"),
                                resultSet.getInt("qyt"),
                                resultSet.getString("description"),
                                resultSet.getDouble("price"),
                                resultSet.getString("date")

                        )

                );

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addTabelItem() {

        columItem.setCellValueFactory(new PropertyValueFactory<>("itemcode"));
        columQuty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        coiumDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        columPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableRest.setItems(addpriceToTable);


        tableRest.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {


            if (newValue != null) {

                //tableSelected(newValue);
                deletobject=newValue;

            }

        });

    }

//    private void tableSelected(RestaurantDetails newValue) {
//
////       // ComboItemCode.setValue(newValue.getItemcode());
////        txtPrice.setText(String.valueOf(newValue.getPrice()));
////        txtFilldQty.setText(String.valueOf(newValue.getQty()));
////        txtFeildDES.setText(newValue.getDescription());
//
//
//    }


    public void setTotal() {

        double tot = 0;

        for (RestaurantDetails data : addpriceToTable) {

            tot += data.getPrice();


        }

        LabelTotal.setText(String.valueOf(tot));

    }

        public void qytTaype(KeyEvent keyEvent) {

                //   Type iter + Tab/Enter for a for-each loop snippet.

                for (RestaurantDetails res : list) {


                        if (res.getItemcode().equals(ComboItemCode.getValue()))


                                txtPrice.setText(String.valueOf(Double.valueOf(res.getPrice()) * Double.valueOf(txtFilldQty.getText())));


                }


        }




/*
        show databases;
        use hotel_reservation;
        show tables;
        select * from restaurent;


 */
}


