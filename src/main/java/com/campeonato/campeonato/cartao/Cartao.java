package com.campeonato.campeonato.cartao;

import com.campeonato.campeonato.jogador.Jogador;
import com.campeonato.campeonato.partida.Partida;

public class Cartao {
    private Jogador jogador;
    private int minuto;
    private String tipo;
    private Partida partida;

    public Cartao( Jogador jogador, int minuto, String tipo, Partida partida){
        this.jogador = jogador;
        this.minuto = minuto;
        this.tipo = tipo;
        this.partida = partida;
    }

}
