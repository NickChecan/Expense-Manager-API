package com.expense.model;

import com.expense.setting.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Document(collection = "expense")
public class Expense {

    @Id
    @JsonView({View.Main.class})
    private String id;

    @JsonView({View.Main.class})
    private String title;

    @JsonView({View.Main.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;

    @JsonView({View.Main.class})
    private Double value;

    @JsonView({View.Main.class})
    private String description;

    @DBRef
    @JsonView({View.Main.class})
    private Group group;

    @DBRef
    private User user;

}
