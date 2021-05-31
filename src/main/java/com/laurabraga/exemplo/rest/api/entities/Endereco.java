package com.laurabraga.exemplo.rest.api.entities;

//import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Street Name é obrigatório.")
    private String streetName;

    @Column(nullable = false)
    @NotNull(message = "Number é obrigatório.")
    private Integer number;

    private String complement;

    @Column(nullable = false)
    @NotEmpty(message = "Neighbourhood é obrigatório.")
    private String neighbourhood;

    @Column(nullable = false)
    @NotEmpty(message = "City é obrigatório.")
    private String city;

    @Column(nullable = false)
    @NotEmpty(message = "State é obrigatório.")
    private String state;

    @Column(nullable = false)
    @NotEmpty(message = "Country é obrigatório.")
    private String country;

    @Column(nullable = false)
    @NotNull(message = "Zip Code é obrigatório.")
    private Integer zipcode;

    private Double latitude;

    private Double longitude;

}
