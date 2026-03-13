package dto;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor

public class ErrorMessage {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
