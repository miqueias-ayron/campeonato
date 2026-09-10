package com.campeonato.campeonato.partida.service;

import com.campeonato.campeonato.cartao.Cartao;
import com.campeonato.campeonato.jogador.Jogador;
import com.campeonato.campeonato.jogador.service.JogadorService;
import com.campeonato.campeonato.partida.Partida;
import com.campeonato.campeonato.time.Time;
import com.campeonato.campeonato.time.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Service
public class PartidaService {

    private static final Set<String> TIPOS_CARTAO = Set.of("AMARELO", "VERMELHO");

    private final Map<String, Partida> partidas = new LinkedHashMap<>();

    @Autowired
    private TimeService timeService;

    @Autowired
    private JogadorService jogadorService;

    public Partida criar(LocalDate data, String idMandante, String idVisitante){
        if (data == null || idMandante == null || idVisitante == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data, idMandante e idVisitante são obrigatórios");
        }
        if (idMandante.equals(idVisitante)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O time mandante e o visitante não podem ser o mesmo time");
        }
        Time mandante = timeService.buscar(idMandante);
        Time visitante = timeService.buscar(idVisitante);
        if (mandante == null || visitante == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Time mandante ou visitante não encontrado");
        }
        Partida partida = new Partida(data);
        partida.registrarTimes(mandante, visitante);
        partidas.put(partida.getId(), partida);
        return partida;
    }

    public Collection<Partida> listar(){
        return new ArrayList<>(partidas.values());
    }

    public Partida buscar(String id){
        Partida partida = partidas.get(id);
        if (partida == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada");
        }
        return partida;
    }

    public Partida atualizarPlacar(String id, Integer golMandante, Integer golVisitante){
        if (golMandante == null || golVisitante == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "golMandante e golVisitante são obrigatórios");
        }
        if (golMandante < 0 || golVisitante < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O placar não pode ser negativo");
        }
        Partida partida = buscar(id);
        partida.registrarGol(golMandante, golVisitante);
        partida.contabilizarPontuacao();
        return partida;
    }

    public Cartao registrarCartao(String id, String idJogador, String tipo, Integer minuto){
        Partida partida = buscar(id);
        String tipoNormalizado = tipo == null ? null : tipo.trim().toUpperCase();
        if (!TIPOS_CARTAO.contains(tipoNormalizado)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O cartão deve ser AMARELO ou VERMELHO");
        }
        if (minuto == null || minuto < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "minuto é obrigatório e não pode ser negativo");
        }
        Jogador jogador = jogadorService.buscar(idJogador);
        if (jogador == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogador não encontrado");
        }
        Cartao cartao = new Cartao(jogador, minuto, tipoNormalizado, partida);
        partida.registrarCartao(jogador, cartao);
        return cartao;
    }
}
