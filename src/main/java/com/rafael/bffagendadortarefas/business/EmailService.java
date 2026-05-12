package com.rafael.bffagendadortarefas.business;

import com.rafael.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.rafael.bffagendadortarefas.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient client;


    // Post Methods
    public void enviaEmail(TarefasDTOResponse dto) {
        client.enviarEmail(dto);
    }
}
