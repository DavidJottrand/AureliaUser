package dj.aurelia.aureliauser.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
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
    private Date lastPasswordResetDate;
    private List<UUID> characters;
    private List<Long> authorities;
}
