package com.campeonato.campeonato.jogador.controller;

import com.campeonato.campeonato.jogador.Jogador;
import com.campeonato.campeonato.jogador.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    public record CriarJogadorRequest(String nome, Integer numero, String posicao) {}

    @PostMapping("/jogadores")
    @ResponseStatus(HttpStatus.CREATED)
    public Jogador criar(@RequestBody CriarJogadorRequest request){
        return jogadorService.criar(request.nome(), request.numero(), request.posicao());
    }

    @GetMapping("/jogadores")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Jogador> listar(){
        return jogadorService.listar();
    }

    @GetMapping("/jogadores/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Jogador buscar(@PathVariable String id){
        Jogador jogador = jogadorService.buscar(id);
        if (jogador == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não encontrado");
        }
        return jogador;
    }
}
