package com.campeonato.campeonato.time.controller;

import com.campeonato.campeonato.jogador.Jogador;
import com.campeonato.campeonato.time.Time;
import com.campeonato.campeonato.time.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class TimeController {

    @Autowired
    private TimeService timeService;

    public record CriarTimeRequest(String nome, String cidade) {}

    public record JogadorResponse(String id, String nome, Integer numero, String posicao, int gols) {
        static JogadorResponse from(Jogador jogador){
            return new JogadorResponse(jogador.getId(), jogador.getNome(), jogador.getNumero(), jogador.getPosicao(), jogador.getGols());
        }
    }

    public record TimeDetalheResponse(String id, String nome, String cidade, int pontuacao, List<JogadorResponse> jogadores) {}

    @PostMapping("/times")
    @ResponseStatus(HttpStatus.CREATED)
    public Time criar(@RequestBody CriarTimeRequest request){
        return timeService.criar(request.nome(), request.cidade());
    }

    @GetMapping("/times")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Time> listar(){
        return timeService.listar();
    }

    @GetMapping("/times/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TimeDetalheResponse buscar(@PathVariable String id){
        Time time = timeService.buscar(id);
        if (time == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Time não encontrado");
        }
        return toDetalhe(time);
    }

    @PostMapping("/times/{idTime}/jogadores/{idJogador}")
    @ResponseStatus(HttpStatus.OK)
    public TimeDetalheResponse adicionarJogador(@PathVariable String idTime, @PathVariable String idJogador){
        Jogador jogador = timeService.adicionarJogador(idTime, idJogador);
        if (jogador == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Time ou jogador não encontrado");
        }
        return toDetalhe(timeService.buscar(idTime));
    }

    private TimeDetalheResponse toDetalhe(Time time){
        List<JogadorResponse> jogadores = new ArrayList<>();
        for (Jogador jogador : time.listarJogadores()){
            jogadores.add(JogadorResponse.from(jogador));
        }
        return new TimeDetalheResponse(time.getId(), time.getNome(), time.getCidade(), time.getPontuacao(), jogadores);
    }
}
