package com.rafael.bffagendadortarefas.infrastructure.client;

import com.rafael.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificacao", url = "${notificacao.url}")
public interface EmailClient {

    // Post Methods
    @PostMapping
    void enviarEmail(@RequestBody TarefasDTOResponse dto);
}
