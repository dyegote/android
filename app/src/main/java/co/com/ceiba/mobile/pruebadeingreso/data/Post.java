package co.com.ceiba.mobile.pruebadeingreso.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE))
@Data @AllArgsConstructor
@NoArgsConstructor
public class Post {

    @PrimaryKey
    public int id;
    @ColumnInfo(name = "userId", index = true)
    public int userId;
    public String body;
    public String title;

}
