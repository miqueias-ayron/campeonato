package com.campeonato.campeonato.partida.controller;

import com.campeonato.campeonato.cartao.Cartao;
import com.campeonato.campeonato.partida.Partida;
import com.campeonato.campeonato.partida.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RestController
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    public record CriarPartidaRequest(LocalDate data, String idMandante, String idVisitante) {}

    public record PlacarRequest(Integer golMandante, Integer golVisitante) {}

    public record CartaoRequest(String idJogador, String tipo, Integer minuto) {}

    @PostMapping("/partidas")
    @ResponseStatus(HttpStatus.CREATED)
    public Partida criar(@RequestBody CriarPartidaRequest request){
        return partidaService.criar(request.data(), request.idMandante(), request.idVisitante());
    }

    @GetMapping("/partidas")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Partida> listar(){
        return partidaService.listar();
    }

    @GetMapping("/partidas/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Partida buscar(@PathVariable String id){
        return partidaService.buscar(id);
    }

    @PatchMapping("/partidas/{id}/placar")
    @ResponseStatus(HttpStatus.OK)
    public Partida atualizarPlacar(@PathVariable String id, @RequestBody PlacarRequest request){
        return partidaService.atualizarPlacar(id, request.golMandante(), request.golVisitante());
    }

    @PostMapping("/partidas/{id}/cartoes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cartao registrarCartao(@PathVariable String id, @RequestBody CartaoRequest request){
        return partidaService.registrarCartao(id, request.idJogador(), request.tipo(), request.minuto());
    }
}
