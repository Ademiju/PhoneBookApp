package africa.semicolon.phoneBookApp.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean isSuccessful;
    private String message;
}
