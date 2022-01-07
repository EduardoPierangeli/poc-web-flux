package com.ciandt.pocwebflux;

import br.com.bradesco.investimento.comum.DadosObjetoTopicoKafka;
import com.ciandt.pocwebflux.service.WebFluxService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class PocwebfluxApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext container = SpringApplication.run(PocwebfluxApplication.class, args);
        final WebFluxService webFluxService = container.getBean("webFluxService", WebFluxService.class);

        final LocalDateTime startSemWeb = LocalDateTime.now();
        final DadosObjetoTopicoKafka<String> semWebFlux = webFluxService.casoSemWebFlux();
        final LocalDateTime endSemWeb = LocalDateTime.now();

        final long durationSemWeb = ChronoUnit.SECONDS.between(startSemWeb, endSemWeb);
        System.out.println("Tempo total - chamada assíncrona SEM o uso de webflux: " + durationSemWeb + "s.");

        final LocalDateTime startComWeb = LocalDateTime.now();
        final List<String> strings = webFluxService.casoComWebFluxSucesso();
        final LocalDateTime endComWeb = LocalDateTime.now();


        final long durationComWeb = ChronoUnit.SECONDS.between(startComWeb, endComWeb);
        System.out.println("Tempo total - chamada assíncrona COM o uso de webflux: " + durationComWeb + "s. Total de itens requisitados: " + strings.size() + " itens");

        container.close();
    }


}
