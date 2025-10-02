package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class RestaurantDetails {

    private String itemcode;
    private int qty;
    private String description;
    private double price;
    private String date;


}
