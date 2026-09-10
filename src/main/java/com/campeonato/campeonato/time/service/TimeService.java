package com.campeonato.campeonato.time.service;

import com.campeonato.campeonato.jogador.Jogador;
import com.campeonato.campeonato.jogador.service.JogadorService;
import com.campeonato.campeonato.time.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TimeService {

    private final Map<String, Time> times = new LinkedHashMap<>();

    @Autowired
    private JogadorService jogadorService;

    public Time criar(String nome, String cidade){
        Time time = new Time(nome, cidade);
        times.put(time.getId(), time);
        return time;
    }

    public Collection<Time> listar(){
        return new ArrayList<>(times.values());
    }

    public Time buscar(String id){
        return times.get(id);
    }

    public Jogador adicionarJogador(String idTime, String idJogador){
        Time time = times.get(idTime);
        Jogador jogador = jogadorService.buscar(idJogador);
        if (time == null || jogador == null){
            return null;
        }
        time.adicionarJogador(jogador);
        return jogador;
    }
}
