package com.expense.model;

import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "role")
public class Role implements GrantedAuthority {

    @Id
    @JsonView({View.Main.class})
    private String id;

    @NonNull
    @Indexed(unique = true)
    @JsonView({View.Main.class})
    private String name;

    @Override
    public String getAuthority() {
        return this.getName();
    }

}
