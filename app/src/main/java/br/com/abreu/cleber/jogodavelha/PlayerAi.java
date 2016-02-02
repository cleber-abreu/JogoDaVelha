package br.com.abreu.cleber.jogodavelha;

import java.util.Random;

/**
 * Created by Cleber on 04/01/2016.
 *
 */

public class PlayerAi {
    private static int x, y;
    protected static int nivel = 2;

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    // Verifica se falta uma jogada para vencer
    private static boolean quaseVencendo(int p) {
        for (int i = 0; i < 3; i++) {

            // Horizontais
            if (GameController.tabuleiro[i][0] == 0
                    && GameController.tabuleiro[i][1] == p
                    && GameController.tabuleiro[i][2] == p) {
                x = i;
                y = 0;
                GameController.realizaJogada(i, 0);
                return true;
            } else if (GameController.tabuleiro[i][0] == p
                    && GameController.tabuleiro[i][1] == 0
                    && GameController.tabuleiro[i][2] == p) {
                x = i;
                y = 1;
                GameController.realizaJogada(i, 1);
                return true;
            } else if (GameController.tabuleiro[i][0] == p
                    && GameController.tabuleiro[i][1] == p
                    && GameController.tabuleiro[i][2] == 0) {
                x = i;
                y = 2;
                GameController.realizaJogada(i, 2);
                return true;
            }

            // Verticais
            else if (GameController.tabuleiro[0][i] == 0
                    && GameController.tabuleiro[1][i] == p
                    && GameController.tabuleiro[2][i] == p) {
                x = 0;
                y = i;
                GameController.realizaJogada(0, i);
                return true;
            } else if (GameController.tabuleiro[0][i] == p
                    && GameController.tabuleiro[1][i] == 0
                    && GameController.tabuleiro[2][i] == p) {
                x = 1;
                y = i;
                GameController.realizaJogada(1, i);
                return true;
            } else if (GameController.tabuleiro[0][i] == p
                    && GameController.tabuleiro[1][i] == p
                    && GameController.tabuleiro[2][i] == 0) {
                x = 2;
                y = i;
                GameController.realizaJogada(2, i);
                return true;
            }
        }

        // Diagonais
        if (GameController.tabuleiro[1][1] == 0) {

            if (GameController.tabuleiro[0][0] == p
                    && GameController.tabuleiro[2][2] == p) {
                x = 1;
                y = 1;
                GameController.realizaJogada(1, 1);
                return true;
            } else if (GameController.tabuleiro[0][2] == p
                    && GameController.tabuleiro[2][0] == p) {
                x = 1;
                y = 1;
                GameController.realizaJogada(1, 1);
                return true;
            }
        } else if (GameController.tabuleiro[1][1] == p) {

            if (GameController.tabuleiro[0][0] == 0
                    && GameController.tabuleiro[2][2] == p) {
                x = 0;
                y = 0;
                GameController.realizaJogada(0, 0);
                return true;
            } else if (GameController.tabuleiro[0][0] == p
                    && GameController.tabuleiro[2][2] == 0) {
                x = 2;
                y = 2;
                GameController.realizaJogada(2, 2);
                return true;
            } else if (GameController.tabuleiro[0][2] == 0
                    && GameController.tabuleiro[2][0] == p) {
                x = 0;
                y = 2;
                GameController.realizaJogada(0, 2);
                return true;
            } else if (GameController.tabuleiro[0][2] == p
                    && GameController.tabuleiro[2][0] == 0) {
                x = 2;
                y = 0;
                GameController.realizaJogada(2, 0);
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    public static void realizaJogada() {

//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Verifica se pode vencer
        if (!quaseVencendo(2)) {

            // verifica se o adversario esta perto de ganhar
            if (!quaseVencendo(1)) {

                // Nível medio
                if (nivel > 0) {

                    // joga no meio
                    if (GameController.realizaJogada(1, 1)) {
                        x = 1; y = 1;
                        return;
                    }

                    //joga nos cantos
                    if (GameController.tabuleiro[0][0] == 0
                            || GameController.tabuleiro[0][2] == 0
                            || GameController.tabuleiro[2][0] == 0
                            || GameController.tabuleiro[2][2] == 0) {
                        while (true) {
                            if (nivel > 1) {
                                if ((GameController.tabuleiro[0][0] == 1 && GameController.tabuleiro[1][2] == 1)
                                        || (GameController.tabuleiro[2][0] == 1
                                        && GameController.tabuleiro[0][2] == 1)) {
                                    break;
                                }
                            }
                            Random sort = new Random();
                            switch (sort.nextInt(4)) {
                                case 0:
                                    x = 0; y = 0;
                                    break;
                                case 1:
                                    x = 0; y = 2;
                                    break;
                                case 2:
                                    x = 2; y = 0;
                                    break;
                                case 3:
                                    x = 2; y = 2;
                                    break;
                            }
                            if (GameController.realizaJogada(x, y)) {
                                return;
                            }
                        }
                    }

                    while (true) {
                        // Joga nos meios
                        Random sort = new Random();
                        switch (sort.nextInt(4)) {
                            case 0:
                                x = 1; y = 0;
                                break;
                            case 1:
                                x = 0; y = 1;
                                break;
                            case 2:
                                x = 2; y = 1;
                                break;
                            case 3:
                                x = 1; y = 2;
                                break;
                        }
                        if (GameController.realizaJogada(x, y)) {
                            return;
                        }
                    }
                } else { // Fácil
                    Random sort = new Random();
                    while (true) {
                        x = sort.nextInt(3);
                        y = sort.nextInt(3);
                        if (GameController.realizaJogada(x, y)) {
                            break;
                        }
                    }
                }
            }
        }
    }
}
