package com.rafael.bffagendadortarefas.business;

import com.rafael.bffagendadortarefas.business.dto.in.LoginDTORequest;
import com.rafael.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.rafael.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CronService {

    private final TarefasService tarefasService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;
    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void buscaTarefasProximaHora() {
        String token = login(converterParaDtoResquest());

        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);
        LocalDateTime horaFuturaMaisCinco = LocalDateTime.now().plusHours(1).plusMinutes(5);

        List<TarefasDTOResponse> listaTarefas = tarefasService.buscaTarefasAgendadasPorPeriodo(horaFutura,
                horaFuturaMaisCinco, token);

        listaTarefas.forEach(tarefa -> { emailService.enviaEmail(tarefa);
            tarefasService.alteraStatus(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(),
                    token );});
    };

    public String login(LoginDTORequest dto){
       return usuarioService.loginUsuario(dto);
    }

    public LoginDTORequest converterParaDtoResquest(){
        return LoginDTORequest.builder()
                .email(email)
                .senha(senha)
                .build();
    };
}
