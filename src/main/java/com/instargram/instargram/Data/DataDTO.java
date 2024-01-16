package com.instargram.instargram.Data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataDTO {
    Integer type;

    String name;

    Long id;

    public DataDTO(Integer type, String name, Long id)
    {
        this.type = type;
        this.name = name;
        this.id = id;
    }
}
