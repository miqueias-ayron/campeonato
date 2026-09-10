package com.campeonato.campeonato.campeonato.service;

import com.campeonato.campeonato.campeonato.Campeonato;
import com.campeonato.campeonato.time.Time;
import com.campeonato.campeonato.time.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampeonatoService {

    private final Map<String, Campeonato> campeonatos = new LinkedHashMap<>();

    @Autowired
    private TimeService timeService;

    public Campeonato criar(String nome, int ano, List<String> timeIds){
        ArrayList<Time> times = new ArrayList<>();
        if (timeIds != null){
            for (String idTime : timeIds){
                Time time = timeService.buscar(idTime);
                if (time == null){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Time não encontrado: " + idTime);
                }
                times.add(time);
            }
        }
        Campeonato campeonato = new Campeonato(nome, ano, times, new ArrayList<>());
        campeonatos.put(campeonato.getId(), campeonato);
        return campeonato;
    }

    public Collection<Campeonato> listar(){
        return new ArrayList<>(campeonatos.values());
    }

    public Campeonato buscar(String id){
        Campeonato campeonato = campeonatos.get(id);
        if (campeonato == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Campeonato não encontrado");
        }
        return campeonato;
    }

    public Map<String, Integer> exibirClassificacao(String id){
        return buscar(id).exibirClassificacao();
    }
}
