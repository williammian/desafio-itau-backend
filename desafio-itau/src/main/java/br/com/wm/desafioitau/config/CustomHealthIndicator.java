package br.com.wm.desafioitau.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // lógica customizada aqui (ex: verificar se o banco está OK)
        boolean tudoOk = true; // simulação

        if (tudoOk) {
            return Health.up().withDetail("customCheck", "Tudo OK!").build();
        } else {
            return Health.down().withDetail("customCheck", "Deu ruim!").build();
        }
    }
}
