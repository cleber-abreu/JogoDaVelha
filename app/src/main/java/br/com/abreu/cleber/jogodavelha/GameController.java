package br.com.abreu.cleber.jogodavelha;

/**
 * Created by Cleber on 30/12/2015.
 *
 */

public class GameController {
    public static int[][] tabuleiro = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
    public static int[] placar = {0,0};
    public static boolean jogadorAi = false;
    public static int vezDoJogador;

    public static void novaPartida(boolean Ia, int pontos1, int pontos2, int vez) {
        jogadorAi = Ia;
        placar[0] = pontos1;
        placar[1] = pontos2;
        vezDoJogador = vez;
        tabuleiro = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    }

    public static boolean realizaJogada(int i, int j) {
        if (tabuleiro[i][j] == 0) {
            tabuleiro[i][j] = (vezDoJogador==1)?1:2;
            return true;
        }
        return false;
    }

    public static boolean tabuleiroCheio() {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (tabuleiro[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    public static int[][] jogadaVencedora() {
        int[][] jogada = new int[2][3];
        jogada[0][0] = -1;
        for (int i=0; i<3; i++) {

            //  Horizontal
            if (tabuleiro[i][0] == vezDoJogador
                    && tabuleiro[i][1] == vezDoJogador
                    && tabuleiro[i][2] == vezDoJogador){
                jogada[0][0] = i;
                jogada[0][1] = i;
                jogada[0][2] = i;
                jogada[1][0] = 0;
                jogada[1][1] = 1;
                jogada[1][2] = 2;
                return jogada;
            }

            //  Vertical
            else if (tabuleiro[0][i] == vezDoJogador
                    && tabuleiro[1][i] == vezDoJogador
                    && tabuleiro[2][i] == vezDoJogador){
                jogada[0][0] = 0;
                jogada[0][1] = 1;
                jogada[0][2] = 2;
                jogada[1][0] = i;
                jogada[1][1] = i;
                jogada[1][2] = i;
                return jogada;
            }
        }

        // Diagonais
        if (tabuleiro[0][0] == vezDoJogador
                && tabuleiro[1][1] == vezDoJogador
                && tabuleiro[2][2] == vezDoJogador) {
            jogada[0][0] = 0;
            jogada[0][1] = 1;
            jogada[0][2] = 2;
            jogada[1][0] = 0;
            jogada[1][1] = 1;
            jogada[1][2] = 2;
            return jogada;
        }

        else if (tabuleiro[0][2] == vezDoJogador
                && tabuleiro[1][1] == vezDoJogador
                && tabuleiro[2][0] == vezDoJogador) {
            jogada[0][0] = 2;
            jogada[0][1] = 1;
            jogada[0][2] = 0;
            jogada[1][0] = 0;
            jogada[1][1] = 1;
            jogada[1][2] = 2;
            return jogada;
        }

        return jogada;
    }
}
