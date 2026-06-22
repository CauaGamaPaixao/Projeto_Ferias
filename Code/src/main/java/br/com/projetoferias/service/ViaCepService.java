package br.com.projetoferias.service;

import br.com.projetoferias.model.CheckoutAddress;
import br.com.projetoferias.model.ViaCepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Service
public class ViaCepService {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("https://viacep.com.br/ws")
            .build();

    public Optional<CheckoutAddress> findAddress(String cep) {
        String digitsOnly = cep == null ? "" : cep.replaceAll("\\D", "");
        if (digitsOnly.length() != 8) {
            return Optional.empty();
        }

        try {
            ViaCepResponse response = restClient.get()
                    .uri("/{cep}/json/", digitsOnly)
                    .retrieve()
                    .body(ViaCepResponse.class);

            if (response == null || response.notFound()) {
                return Optional.empty();
            }

            return Optional.of(new CheckoutAddress(
                    response.cep(),
                    response.logradouro(),
                    response.bairro(),
                    response.localidade(),
                    response.uf(),
                    response.complemento()
            ));
        } catch (RestClientException exception) {
            return Optional.empty();
        }
    }
}
