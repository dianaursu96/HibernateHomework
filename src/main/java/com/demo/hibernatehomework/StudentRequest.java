package com.demo.hibernatehomework;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
    public class StudentRequest {
        private String nume;
        private String prenume;
        private String cnp;
        private AdresaRequest adresa;

    }

