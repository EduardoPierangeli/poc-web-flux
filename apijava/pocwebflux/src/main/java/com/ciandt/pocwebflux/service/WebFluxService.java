package com.ciandt.pocwebflux.service;

import br.com.bradesco.investimento.comum.DadosObjetoTopicoKafka;
import com.ciandt.pocwebflux.util.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class WebFluxService {

    protected WebClient getWebClient() {
        return WebClient.create();
    }

    RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {

        if (Objects.isNull(this.restTemplate)) {
            this.restTemplate = new RestTemplate();
        }

        return this.restTemplate;
    }

    public DadosObjetoTopicoKafka<String> casoSemWebFlux() {

        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            stringBuilder.append(bateRotaAssincrona().getMensagem() + LocalDateTime.now());
        }
        DadosObjetoTopicoKafka<String> res = new DadosObjetoTopicoKafka<>();
        res.setMensagem(stringBuilder.toString());
        return res;
    }

    public List<String> casoComWebFluxSucesso() {
        final Mono<List<String>> listMono = Flux.range(0, 10).flatMap(e -> {
                    return getWebClient()
                            .get()
                            .uri(Constants.URL_BASE + Constants.RES_ASSINC)
                            .retrieve()
                            .bodyToMono(String.class);
                })
                .map(e -> e + " " + LocalDateTime.now())
                .collectList();

        return listMono.onErrorReturn(RuntimeException.class, Collections.singletonList("")).block();

    }

    private DadosObjetoTopicoKafka<String> bateRotaInstantanea() {

        final String forObject = this.getRestTemplate().getForObject(Constants.URL_BASE + Constants.RES_INST, String.class);
        final DadosObjetoTopicoKafka<String> stringDadosObjetoTopicoKafka = new DadosObjetoTopicoKafka<>();
        stringDadosObjetoTopicoKafka.setMensagem(forObject);
        return stringDadosObjetoTopicoKafka;
    }

    private DadosObjetoTopicoKafka<String> bateRotaAssincrona() {

        final String forObject = this.getRestTemplate().getForObject(Constants.URL_BASE + Constants.RES_ASSINC, String.class);
        final DadosObjetoTopicoKafka<String> stringDadosObjetoTopicoKafka = new DadosObjetoTopicoKafka<>();
        stringDadosObjetoTopicoKafka.setMensagem(forObject);
        return stringDadosObjetoTopicoKafka;
    }

    private DadosObjetoTopicoKafka<String> bateRotaErro() {
        final String forObject = this.getRestTemplate().getForObject(Constants.URL_BASE + Constants.RES_ERR, String.class);
        final DadosObjetoTopicoKafka<String> stringDadosObjetoTopicoKafka = new DadosObjetoTopicoKafka<>();
        stringDadosObjetoTopicoKafka.setMensagem(forObject);
        return stringDadosObjetoTopicoKafka;
    }


}
