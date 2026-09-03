package com.campeonato.campeonato.partida;
import com.campeonato.campeonato.time.Time;
import com.campeonato.campeonato.campeonato.Campeonato;
import com.campeonato.campeonato.cartao.Cartao;
import com.campeonato.campeonato.jogador.Jogador;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Partida {
    private LocalDate data;
    private Time timeMandante;
    private Time timeVisitante;
    private Integer golsMandante;
    private Integer golsVisitante;
    private Map<String, Cartao> cartoes = new HashMap<>();
    Campeonato campeonato;

    public Partida(LocalDate data, Campeonato campeonato){
        this.data = data;
        this.campeonato = campeonato;
        this.golsMandante = 0;
        this.golsVisitante = 0;
    }
    public void registrarTimes(Time timeMandante, Time timeVisitante){
        this.timeMandante = timeMandante;
        this.timeVisitante = timeVisitante;
    }
    public void registrarGol(int golMandante, int golVisitante){
        this.golsMandante = golMandante;
        this.golsVisitante = golVisitante;
    }
    public void registrarGol(Jogador jogador){
        jogador.marcarGol();
        if (jogador.getTime().getNome().equals(this.timeMandante.getNome())){
            this.golsMandante++;
        } else {
            this.golsVisitante++;
        }
    }
    public void registrarCartao(Jogador jogador, Cartao cartao){
        cartoes.put(jogador.getNome(), cartao);
    }

    public Time buscarVencedor(){
        if (this.golsVisitante > this.golsMandante){
            this.timeVisitante.setPontuacao(3);
            return this.timeVisitante;
        }
        if (this.golsVisitante < this.golsMandante){
            this.timeMandante.setPontuacao(3);
            return this.timeMandante;
        }
        this.timeVisitante.setPontuacao(1);
        this.timeMandante.setPontuacao(1);
        return null;
    }
    public String exibirPlacar() {
        return String.format("%s %d x %d %s", this.timeMandante.getNome(), this.golsMandante, this.golsVisitante, this.timeVisitante.getNome());
    }
}
