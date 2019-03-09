package com.expense.model;

import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    @JsonView({View.Main.class})
    private String id;

    @Indexed(unique = true)
    @JsonView({View.Main.class})
    private String email;

    @JsonView({View.Main.class})
    private String name;

    private String password;

    @DBRef
    @JsonView({View.All.class})
    private List<Role> roles;

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

}
