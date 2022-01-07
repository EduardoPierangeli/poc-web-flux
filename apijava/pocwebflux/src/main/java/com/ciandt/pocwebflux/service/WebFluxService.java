package com.ciandt.pocwebflux.service;

import br.com.bradesco.cinv.comum.consulta.pacl.Consolidado;
import br.com.bradesco.investimento.comum.DadosObjetoTopicoKafka;
import com.ciandt.pocwebflux.util.Constants;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class WebFluxService {

    protected WebClient getWebClient() {return WebClient.create();}


    public DadosObjetoTopicoKafka<String> casoSemWebFlux() {
        String message = LocalDateTime.now() +
                bateRotaInstantanea().getMensagem() +
                LocalDateTime.now() +
                bateRotaAssincrona().getMensagem() +
                LocalDateTime.now() +
                bateRotaInstantanea().getMensagem() +
                LocalDateTime.now() +
                bateRotaAssincrona().getMensagem() +
                LocalDateTime.now() +
                bateRotaInstantanea().getMensagem() +
                LocalDateTime.now();
        DadosObjetoTopicoKafka<String> res = new DadosObjetoTopicoKafka<>();
        res.setMensagem(message);
        return res;
    }

    public DadosObjetoTopicoKafka<String> casoComWebFluxSucesso() {

        Mono<DadosObjetoTopicoKafka<String>> response = getWebClient()
                .get()
                .uri(Constants.URL_BASE + Constants.RES_ASSINC)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, res -> Mono.error(new RuntimeException("Erro ao processar request")))
                .bodyToMono(new ParameterizedTypeReference<DadosObjetoTopicoKafka<String>>(){}).

        return response.onErrorReturn(RuntimeException.class, new DadosObjetoTopicoKafka<>()).block();
    }

    public DadosObjetoTopicoKafka<String> casoSemWebFluxErro() {

    }

    public DadosObjetoTopicoKafka<String> casoComWebFluxErro() {

    }

    private DadosObjetoTopicoKafka<String> bateRotaInstantanea() {
        return new DadosObjetoTopicoKafka<String>();
    }

    private DadosObjetoTopicoKafka<String> bateRotaAssincrona() {
        return new DadosObjetoTopicoKafka<String>();
    }

    private DadosObjetoTopicoKafka<String> bateRotaErro() {
        return new DadosObjetoTopicoKafka<String>();
    }


}
