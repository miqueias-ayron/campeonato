package com.campeonato.campeonato.jogador.service;

import com.campeonato.campeonato.jogador.Jogador;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JogadorService {

    private final Map<String, Jogador> jogadores = new LinkedHashMap<>();

    public Jogador criar(String nome, Integer numero, String posicao){
        Jogador jogador = new Jogador(nome, numero, posicao, null);
        jogadores.put(jogador.getId(), jogador);
        return jogador;
    }

    public Collection<Jogador> listar(){
        return new ArrayList<>(jogadores.values());
    }

    public Jogador buscar(String id){
        return jogadores.get(id);
    }
}
