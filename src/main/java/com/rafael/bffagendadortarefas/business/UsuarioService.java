package com.rafael.bffagendadortarefas.business;

import com.rafael.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.rafael.bffagendadortarefas.business.dto.in.LoginDTORequest;
import com.rafael.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.rafael.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.rafael.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.rafael.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.rafael.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.rafael.bffagendadortarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient client;

    public UsuarioDTOResponse salvaUsuario(UsuarioDTORequest usuarioDTO) {
        return client.salvaUsuario(usuarioDTO);
    }

    public String loginUsuario(LoginDTORequest loginDTORequest) {
        return client.login(loginDTORequest);
    }

    public UsuarioDTOResponse buscaUsuarioPorEmail(String email, String token) {
        return client.buscaUsuarioPorEmail(email, token);
    }

    public void deletaUsuarioPorEmail(String email, String token) {
        client.deletaUsuarioPorEmail(email, token);
    }

    // Metodos de Update
    public UsuarioDTOResponse atualizaDadosUsuario(String token, UsuarioDTORequest dto) {
        return client.atualizaDadosUsuario(dto, token);
    }

    public EnderecoDTOResponse atualizaEndereco(Long idEndereco, EnderecoDTORequest enderecoDTO, String token) {
        return client.atualizaEndereco(enderecoDTO, idEndereco, token);
    }

    public TelefoneDTOResponse atualizaTelefone(Long idTelefone, TelefoneDTORequest telefoneDTO, String token) {
        return client.atualizaTelefone(telefoneDTO, idTelefone, token);
    }

    public EnderecoDTOResponse cadastraEndereco(String token, EnderecoDTORequest dto) {
        return client.cadastraEndereco(dto, token);
    }

    public TelefoneDTOResponse cadastraTelefone(String token, TelefoneDTORequest dto) {
        return  client.cadastraTelefone(dto, token);
    }
}