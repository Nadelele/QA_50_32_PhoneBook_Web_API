package dto;
import lombok.*;
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Contact {
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String description;

    public Contact(String data) {
        String[] textData = data.split("\\R");
        String[] nameParts = textData[0].split("\\s", 2);
        this.name = nameParts[0];
        this.lastName = nameParts[1];
        this.email = textData[2];
        this.phone = textData[1];
        this.address = textData[3];
        this.description = textData[4];
    }
}


