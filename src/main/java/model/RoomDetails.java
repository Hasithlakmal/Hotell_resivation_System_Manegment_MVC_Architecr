package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomDetails {

    private long roomNumber;
    private String roomType;
    private double pricePerNight;
    private String description;
    private String roomStatus;
}
