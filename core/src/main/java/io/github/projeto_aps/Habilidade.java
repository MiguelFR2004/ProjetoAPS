package io.github.projeto_aps;

public class Habilidade 
{
    // Interface usada para manipular funções como variáveis. (possibilita colocar as habilidades em um array)
    private static interface ReferenciaFuncao 
    {
       // Falta colocar os parâmetros (Personagem alvo, Personagem fonte, mais qualquer informação necessária)
      void Executar(Personagem responsavel, Personagem alvo);
    }
 
    // Usado para definir o comportamento da habilidade quando ela for criada.
    public static enum HabilidadeID
    {
       ATAQUE_BASICO,
       CURA_BASICA,
       DEFESA_BASICA;
    }

    public static enum TipoDeAlvo
    {
        UNICO,
        TODOS,
        AUTO;
    }

    // Variável que segura uma referência a uma função. Deixa agente colocar funções dentro de um array no java.
    private ReferenciaFuncao funcao;

    // Informações sobre a instância da habilidade.
    private String nome;
    private String descricao;

    // Configura quais alvos a habilidade pode afetar. (Um só alvo, todos os alvos, o próprio responsável)
    private TipoDeAlvo tipoDeAlvo;

    // Variável que segura o índice dessa habilidade no array habilidades[]
    private int indice;

    // Usado para desenhar o texto no estado HABILIDADES em JanelaUI
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
                funcao = Habilidade::AtaqueBasico;
                nome = " Ataque Básico";
                descricao = "Atacar inimigo por x dano";
                tipoDeAlvo = TipoDeAlvo.UNICO;
                break;
            case CURA_BASICA:   
                funcao = Habilidade::CuraBasica;
                nome = " Cura Básica";
                descricao = "Curar a si próprio";
                tipoDeAlvo = TipoDeAlvo.AUTO;
                break;
            case DEFESA_BASICA: 
                funcao = Habilidade::DefesaBasica;
                nome = " Defesa Básica";
                descricao = "Defender a si próprio";
                tipoDeAlvo = TipoDeAlvo.AUTO;
                break;
        }
    }

    // .Aplicar() pode ser usado em qualquer uma das habilidades Ex: habilidades[5].Aplicar( parâmetros ) funciona.
    // Wrapper para a função definida na interface.
    public void Aplicar(Personagem responsavel, Personagem alvo)
    {
        funcao.Executar(responsavel, alvo);
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

    public TipoDeAlvo GetTipoDeAlvo()
    {
        return tipoDeAlvo;
    }

    // As habilidades que os personagems podem usar.
    // Detalhe: Se o jogador não tiver energia o suficiente, 
    // não cobre a energia e não faça nada, talvez mande algum feedback como um som.
    private static void AtaqueBasico(Personagem responsavel, Personagem alvo)
    {
        
        System.out.println("Ataque feito"); // DEBUG
    }

    private static void CuraBasica(Personagem responsavel, Personagem alvo)
    {
        System.out.println("Cura feita"); // DEBUG
    }

    private static void DefesaBasica(Personagem responsavel, Personagem alvo)
    {
        System.out.println("Defesa feita"); // DEBUG
    }
}