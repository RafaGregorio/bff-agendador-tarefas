package com.rafael.bffagendadortarefas.business;

import com.rafael.bffagendadortarefas.business.dto.in.TarefasDTORequest;
import com.rafael.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.rafael.bffagendadortarefas.infrastructure.client.TarefasClient;
import com.rafael.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasClient client;


    // Post Metodos
    public TarefasDTOResponse gravarTarefa(String token, TarefasDTORequest dto) {
        return client.gravarTarefas(dto, token);
    }

    // Get Metodos
    public List<TarefasDTOResponse> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token) {
        return client.buscaListaTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    public List<TarefasDTOResponse> buscaTarefasPorEmail(String token) {
        return client.buscaTarefaPorEmail(token);
    }

    // Update Metodos
    public TarefasDTOResponse alteraStatus(StatusNotificacaoEnum status, String id, String token) {
        return client.alteraStatusNotificacao(status, id, token);
    }

    public TarefasDTOResponse updateTarefas(TarefasDTORequest dto, String id, String token) {
        return client.updateTarefas(dto, id, token);
    }

    // Delete Metodos
    public void deletaTarefaPorId(String id, String token) {
        client.deletaTarefaPorId(id, token);
    }
}
