package com.example.tacocloud.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Data
public class Taco {
    @NotBlank(message = "Must include name")
    private String name;

    @NotNull(message="You must have at least 2 ingredients to make a taco")
    @Size(min=2, message= "Pick a minimum of two ingredients")
    private List<String> ingredients;

    private Long id;
    private Date createdAt;
}
