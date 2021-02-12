package co.com.ceiba.mobile.pruebadeingreso.model;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Parcel
@Data @AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;

}
