package com.campeonato.campeonato.jogador;

import com.campeonato.campeonato.time.Time;

import java.util.UUID;

public class Jogador {
    private final String id;
    private String nome;
    private Integer numero;
    private String posicao;
    private Time time;
    private int gols = 0;

    public Jogador(String nome, Integer numero, String posicao, Time time){
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.numero = numero;
        this.posicao = posicao;
        this.time = time;
    }

    public String getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public Integer getNumero(){
        return this.numero;
    }

    public String getPosicao(){
        return this.posicao;
    }

    public Time getTime(){

        return this.time;
    }

    public void setTime(Time time){
        this.time = time;
    }

    public int getGols(){
        return this.gols;
    }

    public void marcarGol(){
        this.gols++;
    }
}
