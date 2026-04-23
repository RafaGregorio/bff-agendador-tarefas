package com.rafael.bffagendadortarefas.infrastructure.client;

import com.rafael.bffagendadortarefas.business.dto.EnderecoDTO;
import com.rafael.bffagendadortarefas.business.dto.TelefoneDTO;
import com.rafael.bffagendadortarefas.business.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    // Get Methods
    @GetMapping
    UsuarioDTO buscaUsuarioPorEmail(@RequestParam("email") String email,
                                    @RequestHeader("Authorization") String token);

    // Post Methods
    @PostMapping
    UsuarioDTO salvaUsuario(@RequestBody UsuarioDTO usuarioDTO);

    @PostMapping("/login")
    String login(@RequestBody UsuarioDTO usuarioDTO);

    @PostMapping("/endereco")
    EnderecoDTO cadastraEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                        @RequestHeader("Authorization") String token);

    @PostMapping("/telefone")
    TelefoneDTO cadastraTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                 @RequestHeader("Authorization") String token);

    // Update Methods
    @PutMapping
    UsuarioDTO atualizaDadosUsuario(@RequestBody UsuarioDTO dto,
                                    @RequestHeader("Authorization")  String token);

    @PutMapping("/endereco")
    EnderecoDTO atualizaEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                 @RequestParam("id") Long id,
                                 @RequestHeader("Authorization")  String token);

    @PutMapping("/telefone")
    TelefoneDTO atualizaTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                 @RequestParam("id") Long id,
                                 @RequestHeader("Authorization")  String token);

    // Delete Methods
    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail(@PathVariable String email,
                               @RequestHeader("Authorization")  String token);
}
