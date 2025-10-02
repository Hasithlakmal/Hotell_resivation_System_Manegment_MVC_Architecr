package controller.coustormerControler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustormerDetails;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerManagementFormController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableView<CustormerDetails> tbleCustormerDetails;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPhoneNumber;

    CustomerManagementControllerServers customerManagementControllerServers = new CustomerManagementController();

    public boolean isname() {

        return txtName.getLength() != 0 ? true : false;

    }

    public boolean isPhoneNumber() {

        return txtPhoneNumber.getLength() != 0 ? true : false;

    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        if (isname()) {

            if (isPhoneNumber()) {

                if (isIntegeerChacker(txtPhoneNumber.getText())) {

                    CustormerDetails getcustormerDetails = new CustormerDetails(

                            txtName.getText(),
                            txtEmail.getText(),
                            txtPhoneNumber.getText(),
                            txtAddress.getText()

                    );

                    int i = customerManagementControllerServers.addCustormerDetails(getcustormerDetails);

                    if (i > 0) {

                        LoadCustormerToTable();
                        txtName.setText("");
                        txtEmail.setText("");
                        txtPhoneNumber.setText("");
                        txtAddress.setText("");


                    }

                } else {

                    JOptionPane.showMessageDialog(null, "!! Please Phone Number  Enter only Numbers , You can't Enter Letters !!!");
                }
            } else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Your Contact Number is Empty ? , Are you Sure , Do you want to   Ignor your Contact Numbee ?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Confirm");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.YES) {

                    CustormerDetails getcustormerDetails = new CustormerDetails(

                            txtName.getText(),
                            txtEmail.getText(),
                            txtPhoneNumber.getText(),
                            txtAddress.getText()

                    );

                    int i = customerManagementControllerServers.addCustormerDetails(getcustormerDetails);

                    if (i > 0) {

                        LoadCustormerToTable();
                        txtName.setText("");
                        txtEmail.setText("");
                        txtPhoneNumber.setText("");
                        txtAddress.setText("");


                    }

                } else {


                    return;

                }

            }
        } else {

            JOptionPane.showMessageDialog(null, "Name is empty , Please Enter your name !!!");

        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {


        LoadCustormerToTable();
        txtName.setText("");
        txtEmail.setText("");
        txtPhoneNumber.setText("");
        txtAddress.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {


        int i = customerManagementControllerServers.deleteCustormerDetails(txtPhoneNumber.getText(), txtName.getText(), txtEmail.getText());


        if (i > 0) {


            LoadCustormerToTable();
            txtName.setText("");
            txtEmail.setText("");
            txtPhoneNumber.setText("");
            txtAddress.setText("");

        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        CustormerDetails getcustormerDetails = new CustormerDetails(

                txtName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), txtAddress.getText()

        );

        int i = customerManagementControllerServers.updateCustormerDetails(getcustormerDetails);
        if (i > 0) {


            LoadCustormerToTable();
            txtName.setText("");
            txtEmail.setText("");
            txtPhoneNumber.setText("");
            txtAddress.setText("");

        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LoadCustormerToTable();

    }

    public void LoadCustormerToTable() {


        ObservableList<CustormerDetails> custormerDetails = customerManagementControllerServers.setViweDetails();
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));


        tbleCustormerDetails.setItems(custormerDetails);

        tbleCustormerDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {

                setSelectedCustormerDetails(newValue);

            }


        });


    }

    public void setSelectedCustormerDetails(CustormerDetails newValue) {

        txtName.setText(newValue.getName());
        txtEmail.setText(newValue.getEmail());
        txtPhoneNumber.setText(newValue.getPhone());
        txtAddress.setText(newValue.getAddress());

    }

    public boolean isIntegeerChacker(String data) {

        try {
            Integer.parseInt(data);
            return true;
        } catch (NumberFormatException e) {

            return false;
        }

    }

}
