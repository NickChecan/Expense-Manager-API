package com.expense.model;

import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "group")
public class Group {

    @Id
    @JsonView({View.Main.class})
    private String id;

    @Indexed(unique = true)
    @JsonView({View.Main.class})
    private String name;

    @JsonView({View.Main.class})
    private String description;

    @DBRef
    private User user;

}
