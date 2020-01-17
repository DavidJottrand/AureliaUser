package dj.aurelia.aureliauser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {

    private String username;
    private String password;
    private String email;

    public boolean check(){
        return (username != null && password != null && email != null);
    }
}
