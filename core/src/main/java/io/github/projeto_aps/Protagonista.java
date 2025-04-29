package io.github.projeto_aps;

import io.github.projeto_aps.Habilidade.HabilidadeID;

public class Protagonista extends Personagem
{
	// Energia é usada para aplicar habilidades e habilidades segura todas as habilidades do personagem.
    // protected int vida;					(em Personagem)
    // protected int energia;				(em Personagem)
    // protected Habilidade habilidades[];	(em Personagem)

    // Mantém conta de quantas habilidades o protagonista têm. Usado para controlar a seleção de habilidades na JanelaUI.
    private int quantidadeHabilidades;

    public Protagonista()
    {
        energia = 3;
        vidaMax = 100;
        vidaAtual = 100;
        quantidadeHabilidades = 0;

        // Estabelece o tamanaho máximo de habilidades que o personagem pode ter e inicializa algumas habilidades.
        habilidades = new Habilidade[8];
        habilidades[0] = new Habilidade(0, HabilidadeID.CURA_BASICA);   // DEBUG
        habilidades[1] = new Habilidade(1, HabilidadeID.ATAQUE_BASICO); // DEBUG
        habilidades[2] = new Habilidade(2, HabilidadeID.DEFESA_BASICA); // DEBUG
        habilidades[3] = new Habilidade(3, HabilidadeID.CURA_BASICA);   // DEBUG
        habilidades[4] = new Habilidade(4, HabilidadeID.ATAQUE_BASICO); // DEBUG
        habilidades[5] = new Habilidade(5, HabilidadeID.DEFESA_BASICA); // DEBUG
        habilidades[6] = new Habilidade(6, HabilidadeID.CURA_BASICA);   // DEBUG
        habilidades[7] = new Habilidade(7, HabilidadeID.ATAQUE_BASICO); // DEBUG

        quantidadeHabilidades = 8; // DEBUG
    }

    public int GetQuantidadeHabilidades()
    {
        return quantidadeHabilidades;
    }
}
