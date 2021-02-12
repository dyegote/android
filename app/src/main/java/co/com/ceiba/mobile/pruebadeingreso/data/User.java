package co.com.ceiba.mobile.pruebadeingreso.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @PrimaryKey
    public int id;
    public String name;
    public String username;
    public String email;
    public String phone;

}
