package dto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor

public class Contacts {
    private List<Contact> contacts;
}
