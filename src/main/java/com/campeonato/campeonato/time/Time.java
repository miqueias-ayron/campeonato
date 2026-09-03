package com.campeonato.campeonato.time;
import com.campeonato.campeonato.jogador.Jogador;

import java.util.ArrayList;

public class Time {
    private String nome;
    private String cidade;
    private ArrayList<Jogador> jogadores = new ArrayList<>();
    private int pontuacao = 0;

    public Time(String nome, String cidade){
        this.nome = nome;
        this.cidade = cidade;

    }

    public String getNome(){
        return this.nome;
    }

    public void adicionarJogador(Jogador jogador){
        jogadores.add(jogador);
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
