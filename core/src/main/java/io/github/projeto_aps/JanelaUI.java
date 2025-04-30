package io.github.projeto_aps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JanelaUI 
{
    // Enumerador para controlar o estado da janela. (Máquina de estados finita)
	// Cada estado chama uma função que controla o comportamento dos controles do jogo.
    // INICIO: Mostra as ações possíveis (Habilidades e terminar rodada), e os stats do personagem (vida, energia)
    // HABILIDADES: Mostra as habilidades disponíveis para uso.
    // ALVOS: Deixa o jogador escolher qual inimigo vai ser atacado.
    // MENSAGEM: Mostra texto na tela enquanto o jogador não está em controle.
    public enum EstadoDaJanela
    {
        INICIO,
        HABILIDADES,
        ALVOS,
        MENSAGEM;
    }
    
    // Variáveis de desenho
    private Texture spriteJanela;
    private Texture spriteAlvo;

    // iconeSelecionado, o ícone que gira no item selecionado
    private char iconeSelecionado;
    private float temporizadorSelecionado;
    private float temporizadorConstante;
    private final char iconeNaoSelecionado;

    // Texto usado para desenho
    private String textoHabilidades;
    private String textoTerminar;
    private String textoMensagem;

    // Variável de controle do estado da janela
    private EstadoDaJanela estadoDaJanela;
    private boolean rodadaTerminou;

    // Referência ao objeto do protagonista
    private Protagonista protagonista;
    private Inimigo[] inimigos;

    // Variável usado para a seleção dos items na janela.
    private int indiceSelecao;
    
    // Variável para segurar o índice da habilidade selecionada. 
    // (usado em AtualizarAlvos. protagonista.habilidades[indiceHabilidadeSelecionada].Aplicar();)
    private int indiceHabilidadeSelecionada;

    // Métodos Públicos
    public JanelaUI(Protagonista protagonistaAtivo, Inimigo[] inimigosAtivos)
    {
        protagonista = protagonistaAtivo;
        inimigos = inimigosAtivos;

        spriteJanela = new Texture("menu.png");
        spriteAlvo   = new Texture("alvo1.png");

        estadoDaJanela = EstadoDaJanela.INICIO;
        rodadaTerminou = false;
        indiceHabilidadeSelecionada = 0;
        indiceSelecao = 0;
        // Habilidades     = indiceSeleçao de 0
        // Terminar Rodada = indiceSelecao de 1

        iconeSelecionado = '-';
        iconeNaoSelecionado = '>';
        temporizadorConstante = 0.1f;
        temporizadorSelecionado = temporizadorConstante;

        textoMensagem = "";
        textoHabilidades = " Habilidades";
        textoTerminar = " Terminar Rodada";
    }

    // Atualizar estado da janela.
    public void Atualizar()
    {
        temporizadorSelecionado();

        switch (estadoDaJanela)
        {
            case INICIO:
                AtualizarInicio();
                break;
            case HABILIDADES:
                AtualizarHabilidades();
                break;
            case ALVOS:
                AtualizarAlvos();
                break;
            case MENSAGEM:
                AtualizarMensagem();
                break;
        }

    }

    // Desenhar estado da janela
    public void Desenhar(SpriteBatch batch, BitmapFont font)
    {
        batch.draw(spriteJanela, 0, 0);

        switch (estadoDaJanela)
        {
            case INICIO:
                DesenharInicio(batch, font);
                break;
            case HABILIDADES:
                DesenharHabilidades(batch, font, protagonista.habilidades);
                break;
            case ALVOS:
                DesenharAlvos(batch, font);
                break;
            case MENSAGEM:
                DesenharMensagem(batch, font);
                break;
        }
    }

    // Permite mudar o estado da janela fora da clsse JanelaUI.
    // Deve escrever em uma string com tudo maísculo.
    public void SetEstadoDaJanela(EstadoDaJanela estadoAtual)
    {
        if (estadoAtual == EstadoDaJanela.INICIO)
        {
            rodadaTerminou = false;
            indiceSelecao = 0;
            estadoDaJanela = EstadoDaJanela.INICIO;
        }
        else if (estadoAtual == EstadoDaJanela.MENSAGEM)
        {
            estadoDaJanela = EstadoDaJanela.MENSAGEM;
        }
        else
        {
            System.out.println("Erro: valor inválido no método SetEstadoDaJanela.");
        }
    }

    // Mostra se o jogador terminou sua rodada. Usado na lógica do SistemaJogo.
    public boolean checarSeRodadaTerminou()
    {
        return rodadaTerminou;
    }

    // Método para escrever texto na janela. Usado na lógica do SistemaJogo.
    public void MostrarMensagem(String texto)
    {
        textoMensagem = texto;
    }

    // Métodos Privados
    // Métodos de atualização dos estado
    private void AtualizarInicio()
    {
        // Atualizar o índice da seleção dependendo do input do jogador.
        // Math.floorMod(valor, limite) faz o valor voltar a zero se superar o limite, e ir ao limite se descer ao negativo,
        // criando um loop. Exemplo: (8 + 1, 8) = 0 / (8 + 5, 8) = 4 / (0 - 1, 8) = 8 / (0 - 4, 8) = 5
        // Isso é usado para criar um loop nos items selecionaveis na janela.
        if (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.DPAD_UP))
        {
            indiceSelecao = Math.floorMod(indiceSelecao - 1, 2);
        }
        if (Gdx.input.isKeyJustPressed(Keys.A) || Gdx.input.isKeyJustPressed(Keys.DPAD_LEFT))
        {
            indiceSelecao = Math.floorMod(indiceSelecao - 1, 2);
        }
        if (Gdx.input.isKeyJustPressed(Keys.D) || Gdx.input.isKeyJustPressed(Keys.DPAD_RIGHT))
        {
            indiceSelecao = Math.floorMod(indiceSelecao + 1, 2);
        }
        if (Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DPAD_DOWN))
        {
            indiceSelecao = Math.floorMod(indiceSelecao + 1, 2);
        }
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.ENTER))
        {
            if (indiceSelecao == 0)
            {
                estadoDaJanela = EstadoDaJanela.HABILIDADES;
            }
            if (indiceSelecao == 1)
            {
                rodadaTerminou = true;
            }
        }
    }

    private void AtualizarHabilidades()
    {
        // Atualizar o índice da seleção dependendo do input do jogador.
        // Math.floorMod(valor, limite) faz o valor voltar a zero se superar o limite, e ir ao limite se descer ao negativo,
        // criando um loop. Exemplo: (8 + 1, 8) = 0 / (8 + 5, 8) = 4 / (0 - 1, 8) = 8 / (0 - 4, 8) = 5
        // Isso é usado para criar um loop nos items selecionaveis na janela.
        if (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.DPAD_UP))
        {
            indiceSelecao = Math.floorMod(indiceSelecao - 2, protagonista.GetQuantidadeHabilidades());
        }
        if (Gdx.input.isKeyJustPressed(Keys.A) || Gdx.input.isKeyJustPressed(Keys.DPAD_LEFT))
        {
            indiceSelecao = Math.floorMod(indiceSelecao - 1, protagonista.GetQuantidadeHabilidades());
        }
        if (Gdx.input.isKeyJustPressed(Keys.D) || Gdx.input.isKeyJustPressed(Keys.DPAD_RIGHT))
        {
            indiceSelecao = Math.floorMod(indiceSelecao + 1, protagonista.GetQuantidadeHabilidades());
        }
        if (Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DPAD_DOWN))
        {
            indiceSelecao = Math.floorMod(indiceSelecao + 2, protagonista.GetQuantidadeHabilidades());
        }
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.ENTER))
        {
            // Se existirem inimigos vivos, verifique qual é o tipo de alvo da habilidade e processe o resultado.
            if (Inimigo.inimigosAtivos != 0)
            {
                // Se for UNICO, guarde o indice da habilidade selecionada para ela ser aplicada depois.
                // Se for TODOS, repita a habilidade em todos os alvos ativos.
                // Se for AUTO, o alvo também é o protagonista.
                switch (protagonista.habilidades[indiceSelecao].GetTipoDeAlvo())
                {
                    case UNICO: 
                        indiceHabilidadeSelecionada = indiceSelecao;
                        indiceSelecao = 0;
                        estadoDaJanela = EstadoDaJanela.ALVOS;
                        do
                        {
                            indiceSelecao = Math.floorMod(indiceSelecao + 1, Inimigo.inimigosAtivos);
                        } 
                        while (inimigos[indiceSelecao] == null);
                        break;
                    case TODOS: 
                        for (Inimigo inimigo : inimigos)
                        {
                            if (inimigo != null)
                            {
                                protagonista.habilidades[indiceSelecao].Aplicar(protagonista, inimigo);
                            }
                        }
                        rodadaTerminou = true;
                        break;
                    case AUTO: 
                        protagonista.habilidades[indiceSelecao].Aplicar(protagonista, protagonista);
                        rodadaTerminou = true;
                        break;
                }
            }
            else
            {
                if (protagonista.habilidades[indiceSelecao].GetTipoDeAlvo() == Habilidade.TipoDeAlvo.AUTO)
                {
                    protagonista.habilidades[indiceSelecao].Aplicar(protagonista, protagonista);
                }
                else
                {
                    System.out.println("Nenhum alvo válido");
                    // Mostrar uma mensagem falando que não existem alvos possíveis.
                }
            }
        }
    }

    private void AtualizarAlvos()
    {
        // Atualizar o índice da seleção dependendo do input do jogador.
        // Math.floorMod(valor, limite) faz o valor voltar a zero se superar o limite, e ir ao limite se descer ao negativo,
        // criando um loop. Exemplo: (8 + 1, 8) = 0 / (8 + 5, 8) = 4 / (0 - 1, 8) = 8 / (0 - 4, 8) = 5
        // Isso é usado para criar um loop nos items selecionaveis na janela.
        if (Gdx.input.isKeyJustPressed(Keys.D) || Gdx.input.isKeyJustPressed(Keys.DPAD_RIGHT))
        {
            // Cada índice está associado a um inimigo, já que cada inimigo segure uma variável "indice".
            // Se o índice atual representar um inimigo que não está vivo, pule para o próximo até achar um que esteje.
            do
            {
                indiceSelecao = Math.floorMod(indiceSelecao + 1, Inimigo.inimigosAtivos);
            } 
            while (inimigos[indiceSelecao] == null);
        }
        if (Gdx.input.isKeyJustPressed(Keys.A) || Gdx.input.isKeyJustPressed(Keys.DPAD_LEFT))
        {
            // Cada índice está associado a um inimigo, já que cada inimigo segure uma variável "indice".
            // Se o índice atual representar um inimigo que não está vivo, pule para o próximo até achar um que esteje.
            do
            {
                indiceSelecao = Math.floorMod(indiceSelecao - 1, Inimigo.inimigosAtivos);
            } 
            while (inimigos[indiceSelecao] == null);
        }
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.ENTER))
        {
            protagonista.habilidades[indiceHabilidadeSelecionada].Aplicar(protagonista, inimigos[indiceSelecao]);
        }
    }

    // Usado para escrever dinamicamente o texto da mensagem, letra por letra. (NÃO ESSENCIAL)
    private void AtualizarMensagem()
    {

    }

    // Métodos de desenho dos estado
    // Desenhar Janela em estado INICIO
    private void DesenharInicio(SpriteBatch batch, BitmapFont font)
    {
        font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        font.draw(batch, "VIDA: " + protagonista.vidaAtual + " / " + protagonista.vidaMax, 470, 230);

        // Se o ínidce é 0, desenhar "Habilidades" em seleção e "Terminar Rodada" desativado. Vice-Versa se for 1.
        if (indiceSelecao == 0)
        {
            font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
            font.draw(batch, iconeSelecionado + textoHabilidades, 70, 230);
            font.setColor(com.badlogic.gdx.graphics.Color.GRAY);
            font.draw(batch, iconeNaoSelecionado + textoTerminar, 70, 180);
        }
        if (indiceSelecao == 1)
        {
            font.setColor(com.badlogic.gdx.graphics.Color.GRAY);
            font.draw(batch, iconeNaoSelecionado + textoHabilidades, 70, 230);
            font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
            font.draw(batch, iconeSelecionado + textoTerminar, 70, 180);
        }
    }

    // Desenhar estado em HABILIDADES
    private void DesenharHabilidades(SpriteBatch batch, BitmapFont font, Habilidade[] habilidades)
    {
        for (Habilidade habilidade : habilidades)
        {
            // System.out.println(habilidade.GetNome() + ": " + habilidade.GetPosX() + " " + habilidade.GetPosY());
            
            if (habilidade.GetIndice() == indiceSelecao)
            {
                font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
                font.draw(batch, iconeSelecionado + habilidade.GetNome(), habilidade.GetPosX(), habilidade.GetPosY());
            }
            else
            {
                font.setColor(com.badlogic.gdx.graphics.Color.GRAY);
                font.draw(batch, iconeNaoSelecionado + habilidade.GetNome(), habilidade.GetPosX(), habilidade.GetPosY());
            }
        }
    }

    // Desenhar estado em ALVOS
    private void DesenharAlvos(SpriteBatch batch, BitmapFont font)
    {
        font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        font.draw(batch, "Vida: " + inimigos[indiceSelecao].vidaAtual + " / " + inimigos[indiceSelecao].vidaMax,70, 220);

        batch.draw(spriteAlvo, inimigos[indiceSelecao].GetPosX(), inimigos[indiceSelecao].GetPosY());
    }

    // Desenhar estado em MENSAGEM
    private void DesenharMensagem(SpriteBatch batch, BitmapFont font)
    {
        font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        font.draw(batch, textoMensagem, 70, 220);
    }

    // Atualizar o temporizadorSelecionado a cada x segundos, onde x é igual a temporizadorConstante.
    private void temporizadorSelecionado()
    {
        temporizadorSelecionado = temporizadorSelecionado - Gdx.graphics.getDeltaTime();

        if (temporizadorSelecionado < 0)
        {
            switch (iconeSelecionado)
            {
                case '-': 
                    iconeSelecionado = '\\';
                    break;
                case '\\': 
                    iconeSelecionado = '|';
                    break;
                case '|': 
                    iconeSelecionado = '/';
                    break;
                case '/': 
                    iconeSelecionado = '-';
                    break;
            }

            temporizadorSelecionado = temporizadorConstante;
        }
    }
}
