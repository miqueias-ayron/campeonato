package com.campeonato.campeonato.time;
import com.campeonato.campeonato.jogador.Jogador;

import java.util.ArrayList;
import java.util.UUID;

public class Time {
    private final String id;
    private String nome;
    private String cidade;
    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private int pontuacao = 0;

    public Time(String nome, String cidade){
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.cidade = cidade;

    }

    public String getId(){

        return this.id;
    }

    public String getNome(){

        return this.nome;
    }

    public String getCidade(){

        return this.cidade;
    }

    public void adicionarJogador(Jogador jogador){
        jogadores.add(jogador);
        jogador.setTime(this);
    }

    public void removerJogador(Jogador jogador){
        jogadores.remove(jogador);
    }

    public ArrayList<Jogador> listarJogadores() {
        return this.jogadores;
    }
    public int getPontuacao(){
        return this.pontuacao;
    }
    public void setPontuacao(int pontos){
        if (pontos < 0){
            System.out.println("Você não pode reduzir a pontuação!");
        }
        else {
            this.pontuacao += pontos;
        }
    }
}
