package com.campeonato.campeonato.campeonato;

import com.campeonato.campeonato.partida.Partida;
import com.campeonato.campeonato.time.Time;

import java.util.*;
import java.util.stream.Collectors;

public class Campeonato {
    private String id;
    private String nome;
    private int ano;
    private ArrayList<Time> times;
    private ArrayList<Partida> partidas;

    public Campeonato(String nome, int ano, ArrayList<Time> times, ArrayList<Partida> partidas){
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.ano = ano;
        this.times = times != null ? times : new ArrayList<>();
        this.partidas = partidas != null ? partidas : new ArrayList<>();
    }

    public String getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public int getAno(){
        return this.ano;
    }

    public void adicionarTime(Time time){

        times.add(time);
    }

    public void adicionarPartida(Partida partida){

        partidas.add(partida);
    }

    public ArrayList<Time> listarTimes(){

        return this.times;
    }

    public ArrayList<Partida> listarPartidas(){

        return this.partidas;
    }

    public ArrayList<Time> getTimes(){

        return this.times;
    }

    public Time buscarTime(String nome){
        for (Time time: times){
            if (time.getNome().equals(nome)) {
                return time;
            }
        }
        return null;
    }

    public Map<String, Integer> exibirClassificacao(){
        Map<String, Integer> classificacao = new HashMap<>();

        for (Time time : this.times) {
            classificacao.put(time.getNome(), time.getPontuacao());
        }

        Map<String, Integer> classificacaoOrdenada = classificacao.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (valorExistente, novoValor) -> valorExistente,
                        LinkedHashMap::new
                ));
        return classificacaoOrdenada;
    }
}
