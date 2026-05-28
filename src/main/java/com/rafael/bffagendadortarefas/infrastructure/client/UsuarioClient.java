package com.rafael.bffagendadortarefas.infrastructure.client;

import com.rafael.bffagendadortarefas.business.dto.in.EnderecoDTORequest;
import com.rafael.bffagendadortarefas.business.dto.in.LoginDTORequest;
import com.rafael.bffagendadortarefas.business.dto.in.TelefoneDTORequest;
import com.rafael.bffagendadortarefas.business.dto.in.UsuarioDTORequest;
import com.rafael.bffagendadortarefas.business.dto.out.EnderecoDTOResponse;
import com.rafael.bffagendadortarefas.business.dto.out.TelefoneDTOResponse;
import com.rafael.bffagendadortarefas.business.dto.out.UsuarioDTOResponse;
import com.rafael.bffagendadortarefas.business.dto.out.ViaCepDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    // Get Methods
    @GetMapping
    UsuarioDTOResponse buscaUsuarioPorEmail(@RequestParam("email") String email,
                                            @RequestHeader("Authorization") String token);

    // Post Methods
    @PostMapping
    UsuarioDTOResponse salvaUsuario(@RequestBody UsuarioDTORequest usuarioDTO);

    @PostMapping("/login")
    String login(@RequestBody LoginDTORequest loginDTORequest);

    @PostMapping("/endereco")
    EnderecoDTOResponse cadastraEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                         @RequestHeader("Authorization") String token);

    @PostMapping("/telefone")
    TelefoneDTOResponse cadastraTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                         @RequestHeader("Authorization") String token);

    // Update Methods
    @PutMapping
    UsuarioDTOResponse atualizaDadosUsuario(@RequestBody UsuarioDTORequest dto,
                                            @RequestHeader("Authorization")  String token);

    @PutMapping("/endereco")
    EnderecoDTOResponse atualizaEndereco(@RequestBody EnderecoDTORequest enderecoDTO,
                                         @RequestParam("id") Long id,
                                         @RequestHeader("Authorization")  String token);

    @PutMapping("/telefone")
    TelefoneDTOResponse atualizaTelefone(@RequestBody TelefoneDTORequest telefoneDTO,
                                         @RequestParam("id") Long id,
                                         @RequestHeader("Authorization")  String token);

    // Delete Methods
    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail(@PathVariable String email,
                               @RequestHeader("Authorization")  String token);

    // ViaCep Method
    @GetMapping("/endereco/{cep}")
    ViaCepDTOResponse buscarDadosCep(@PathVariable("cep") String cep);

}
