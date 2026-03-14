package dto;
import lombok.*;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor

public class ErrorMessage {
    private String timestamp;
    private int status;
    private String error;
    //private Map<String, String> message;
    //private String message;
    private Object message;
    private String path;
}
