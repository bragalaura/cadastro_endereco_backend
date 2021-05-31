package com.laurabraga.exemplo.rest.api.controllers;

import com.laurabraga.exemplo.rest.api.entities.Endereco;
import com.laurabraga.exemplo.rest.api.repositories.EnderecoRepository;
import com.laurabraga.exemplo.rest.api.services.EnderecoService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

@WebMvcTest
public class EnderecoControllerTest {

    @Autowired
    private EnderecoController enderecoController;

    @MockBean
    private EnderecoService enderecoService;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.enderecoController);
    }

    @Test
    public void deveRetornarSucesso_QuandoBuscarEndereco() {

        Mockito.when(this.enderecoService.obterPorId(1L))
            .thenReturn(new Endereco(1L, "Rua Quetúnia", 5, "",
                        "Esperança", "Ipatinga", "MG", "Brasil",
                        35160000, null, null ));

        given()
            .accept(ContentType.JSON)
        .when()
            .get("/enderecos/{id}", 1L)
        .then()
            .statusCode(HttpStatus.OK.value());

    }

    @Test
    public void deveRetornarNaoEncontrado_QuandoBuscarEndereco() {

        Mockito.when(this.enderecoService.obterPorId(2L))
            .thenReturn(null);

        given()
            .accept(ContentType.JSON)
        .when()
            .get("/enderecos/{id}", 2L)
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
