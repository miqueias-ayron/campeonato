package com.campeonato.campeonato.campeonato.controller;

import com.campeonato.campeonato.campeonato.Campeonato;
import com.campeonato.campeonato.campeonato.service.CampeonatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    public record CriarCampeonatoRequest(String nome, Integer ano, List<String> timeIds) {}

    @PostMapping("/campeonatos")
    @ResponseStatus(HttpStatus.CREATED)
    public Campeonato createCampeonato(@RequestBody CriarCampeonatoRequest request){
        int ano = request.ano() != null ? request.ano() : 0;
        return campeonatoService.criar(request.nome(), ano, request.timeIds());
    }

    @GetMapping("/campeonatos/{id}/classificacao")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> getClassificacao(@PathVariable String id){
        return campeonatoService.exibirClassificacao(id);
    }
}
