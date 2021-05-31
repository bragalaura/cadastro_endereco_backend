package com.laurabraga.exemplo.rest.api.controllers;

import com.laurabraga.exemplo.rest.api.entities.Endereco;
import com.laurabraga.exemplo.rest.api.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> obterPorId(@PathVariable Long id) {

        Endereco endereco = this.enderecoService.obterPorId(id);

        if(endereco == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(endereco);

    }

    @GetMapping("/cep/{zipcode}")
    public List<Endereco> obterPorZipcode(@PathVariable Integer zipcode) {
        return this.enderecoService.obterPorZipcode(zipcode);
    }

    @GetMapping("/")
    public List<Endereco> obterTodos() {
        return this.enderecoService.obterTodos();
    }

    @PostMapping("/")
    public Endereco inserir(@Valid @RequestBody Endereco endereco) {
        return this.enderecoService.inserir(endereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @Valid @RequestBody Endereco novoEndereco) {

        Endereco endereco = this.enderecoService.atualizar(id, novoEndereco);

        if(endereco == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(endereco);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
         boolean enderecoApagado = this.enderecoService.deletar(id);

         if (enderecoApagado) {
             return ResponseEntity.ok().build();
         }

        return ResponseEntity.notFound().build();

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
