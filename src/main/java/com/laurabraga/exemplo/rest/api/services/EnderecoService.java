package com.laurabraga.exemplo.rest.api.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.laurabraga.exemplo.rest.api.entities.Endereco;
import com.laurabraga.exemplo.rest.api.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;


    public Endereco obterPorId(Long id) {
        return this.enderecoRepository.findById(id).orElse(null);
    }

    public List<Endereco> obterPorZipcode(Integer zipcode) {
        return this.enderecoRepository.findByZipcode(zipcode);
    }

    public List<Endereco> obterTodos() {
        return this.enderecoRepository.findAll();
    }

    public Endereco inserir(Endereco endereco) {

        inserirLatLong(endereco);

        return this.enderecoRepository.save(endereco);
    }


    public Endereco atualizar(Long id, Endereco novoEndereco) {

        inserirLatLong(novoEndereco);

        Endereco enderecoAtualizado = this.enderecoRepository.findById(id)
                .map(endereco -> {
                    endereco.setStreetName(novoEndereco.getStreetName());
                    endereco.setNumber(novoEndereco.getNumber());
                    endereco.setComplement(novoEndereco.getComplement());
                    endereco.setNeighbourhood(novoEndereco.getNeighbourhood());
                    endereco.setCity(novoEndereco.getCity());
                    endereco.setState(novoEndereco.getState());
                    endereco.setCountry(novoEndereco.getCountry());
                    endereco.setZipcode(novoEndereco.getZipcode());
                    endereco.setLatitude(novoEndereco.getLatitude());
                    endereco.setLongitude(novoEndereco.getLongitude());

                    return enderecoRepository.save(endereco);

                })
                .orElse(null);

        return enderecoAtualizado;
    }

    public boolean deletar(Long id) {
        Optional<Endereco> enderecoEncontrado =this.enderecoRepository.findById(id);

        if (enderecoEncontrado.isPresent()) {
            enderecoRepository.deleteById(id);
            return true;
        }

        return false;

    }

    /*
        Consulta Geocoding API do Google para buscar latitude e longitude se não informadas.
     */
    private void inserirLatLong(Endereco endereco) {
        if (endereco.getLatitude() == null || endereco.getLongitude() == null) {

            StringBuilder address = new StringBuilder();
                address.append(endereco.getStreetName())
                        .append(" ")
                        .append(endereco.getNumber())
                        .append(" ")
                        .append(endereco.getNeighbourhood())
                        .append(" ")
                        .append(endereco.getCity())
                        .append(",")
                        .append(endereco.getState())
                        .append(" ")
                        .append(endereco.getZipcode());

            System.out.println("Address => " + address);

            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw")
                    .build();

            GeocodingResult[] results = new GeocodingResult[0];
            try {
                results = GeocodingApi.geocode(context,
                        address.toString()).await();
            } catch (ApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println(gson.toJson(results[0].geometry));

            endereco.setLatitude(results[0].geometry.location.lat);
            endereco.setLongitude(results[0].geometry.location.lng);
        }
    }


}
