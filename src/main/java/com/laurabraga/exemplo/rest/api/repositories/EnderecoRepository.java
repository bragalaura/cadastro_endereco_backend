package com.laurabraga.exemplo.rest.api.repositories;

import com.laurabraga.exemplo.rest.api.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("SELECT u From Endereco u where CAST(u.zipcode as text) like CONCAT(:zipcode, '%') ")
    List<Endereco> findByZipcode(@Param("zipcode") Integer zipcode);

}
