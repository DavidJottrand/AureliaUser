package dj.aurelia.aureliauser.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Entity(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {

    @Id
    @Column(unique = true, nullable = false)
    @Type(type="uuid-char")
    private UUID uuid;

    @Column(name = "USERNAME", unique = true, nullable = false)
    @NotNull
    @Size(min = 4, max = 100)
    private String username;

    @Column(name = "PASSWORD", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "ENABLED")
    @NotNull
    private Boolean enabled;

    @Column(name = "LASTPASSWORDRESETDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;
}
