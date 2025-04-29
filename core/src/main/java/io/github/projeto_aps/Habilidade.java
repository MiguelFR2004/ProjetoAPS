package io.github.projeto_aps;

public class Habilidade 
{
    // Interface usada para manipular funções como variáveis. (possibilita colocar as habilidades em um array)
     private static interface Tipo 
     {
        // Falta colocar os parâmetros (Personagem alvo, Personagem fonte, mais qualquer informação necessária)
        void Executar();
     }

     // Usado para definir o comportamento da habilidade quando ela for criada.
     public static enum HabilidadeID
     {
        ATAQUE_BASICO,
        CURA_BASICA,
        DEFESA_BASICA;
     }

    private Tipo tipo;
    private String nome;
    private String descricao;
    private int indice;
    private int posX;
    private int posY;

    // Criar váriavel para caso a habilidade não tenha avlo único ou auto-afetante (NÃO NECESSÁRIO)

    public Habilidade(int indiceNoArray, HabilidadeID tipoDeHabilidade)
    {
        // indice da váriavel dentro da array habilidades[]
        indice = indiceNoArray;

        posX = (indice % 2) * 400 + 70;

        posY = ((indice + 2) / 2) * -50 + 280;

        switch (tipoDeHabilidade)
        {
            case ATAQUE_BASICO: 
                tipo = Habilidade::AtaqueBasico;
                nome = " Ataque Básico";
                descricao = "Atacar inimigo por x dano";
                break;
            case CURA_BASICA:   
                tipo = Habilidade::CuraBasica;
                nome = " Cura Básica";
                descricao = "Curar a si próprio";
                break;
            case DEFESA_BASICA: 
                tipo = Habilidade::DefesaBasica;
                nome = " Defesa Básica";
                descricao = "Defender a si próprio";
                break;
        }
    }

    // .Aplicar() pode ser usado em qualquer uma das habilidades Ex: habilidades[5].Aplicar( parâmetros ) funciona.
    // Wrapper para a função definida na interface.
    public void Aplicar()
    {
        tipo.Executar();
    }

    public int GetIndice()
    {
        return indice;
    }

    public int GetPosX()
    {
        return posX;
    }

    public int GetPosY()
    {
        return posY;
    }

    public String GetNome()
    {
        return nome;
    }

    // As habilidades que os personagems podem usar.
    // Detalhe: Se o jogador não tiver energia o suficiente, 
    // não cobre a energia e não faça nada, talvez mande algum feedback como um som.
    private static void AtaqueBasico()
    {
        System.out.println("Ataque feito"); // DEBUG
    }

    private static void CuraBasica()
    {
        System.out.println("Cura feita"); // DEBUG
    }

    private static void DefesaBasica()
    {
        System.out.println("Defesa feita"); // DEBUG
    }
}