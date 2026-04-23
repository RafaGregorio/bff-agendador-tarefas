package com.rafael.bffagendadortarefas.infrastructure.client;

import com.rafael.bffagendadortarefas.business.dto.in.TarefasDTORequest;
import com.rafael.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.rafael.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefas.url}")
public interface TarefasClient {

    // Get Methods
    @GetMapping("/eventos")
    List<TarefasDTOResponse> buscaListaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader("Authorization") String token);

    @GetMapping
    List<TarefasDTOResponse> buscaTarefaPorEmail(@RequestHeader("Authorization") String token);

    // Post Methods
    @PostMapping
    TarefasDTOResponse gravarTarefas(@RequestBody TarefasDTORequest dto,
                                     @RequestHeader("Authorization") String token);

    // Update Methods
    @PatchMapping
    TarefasDTOResponse alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                               @RequestParam("id") String id,
                                               @RequestHeader("Authorization") String token);
    @PutMapping
    TarefasDTOResponse updateTarefas(@RequestBody TarefasDTORequest dto, @RequestParam("id") String id, @RequestHeader("Authorization") String token);

    // Delete Methods
    @DeleteMapping
    void deletaTarefaPorId(@RequestParam("id") String id, @RequestHeader("Authorization") String token);
}
