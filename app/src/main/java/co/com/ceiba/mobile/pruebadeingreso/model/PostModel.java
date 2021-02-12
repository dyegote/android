package co.com.ceiba.mobile.pruebadeingreso.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostModel {

    public int id;
    public int userId;
    public String body;
    public String title;

}
