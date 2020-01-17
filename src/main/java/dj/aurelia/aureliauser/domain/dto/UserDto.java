package dj.aurelia.aureliauser.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID uuid;
    private String username;
    private String email;
    private Boolean enabled;
}
