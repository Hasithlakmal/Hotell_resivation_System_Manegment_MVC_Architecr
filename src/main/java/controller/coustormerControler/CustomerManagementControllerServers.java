package controller.coustormerControler;

import javafx.collections.ObservableList;
import model.CustormerDetails;

public interface CustomerManagementControllerServers {

    int addCustormerDetails(CustormerDetails custormerDetails);

    int updateCustormerDetails(CustormerDetails custormerDetails);

    int deleteCustormerDetails(String PhoneNumber ,String name,String txtEmail);

    ObservableList<CustormerDetails> setViweDetails();
}
