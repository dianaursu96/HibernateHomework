package com.demo.hibernatehomework;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AdresaRequest {
    private String strada;
    private String numar;
    private String localitate;
}

