package io.github.projeto_aps;

public abstract class Personagem
{
    // Classe abstrata para Protagonista e Inimigo, usado principalmente para generalizar uso de habilidades.
    // (Personagem -> Personagem invés de Inimigo -> Protagonista ou Protagonista -> Inimigo)

    protected int vidaMax;
    protected int vidaAtual;
    protected int energia;
    protected Habilidade habilidades[];
}
