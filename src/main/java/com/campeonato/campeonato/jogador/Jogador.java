package com.campeonato.campeonato.jogador;

import com.campeonato.campeonato.time.Time;

public class Jogador {
    private String nome;
    private Integer numero;
    private String posicao;
    private Time time;
    private int gols = 0;

    public Jogador(String nome, Integer numero, String posicao, Time time){
        this.nome = nome;
        this.numero = numero;
        this.posicao = posicao;
        this.time = time;
    }

    public String getNome(){
        return this.nome;
    }

    public Time getTime(){
        return this.time;
    }

    public int getGols(){
        return this.gols;
    }

    public void marcarGol(){
        this.gols++;
    }
}
