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
    private enum Estado
    {
        INICIO,
        HABILIDADES,
        ALVOS,
        MENSAGEM;
    }
    
    // Variáveis de desenho
    private Texture sprite;

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
    private Estado estadoDaJanela;
    private boolean rodadaTerminou;

    // Referência ao objeto do protagonista
    private Protagonista protagonista;

    // Variável usado para a seleção dos items na janela.
    private int indiceSelecao;

    // Métodos Públicos
    public JanelaUI(Protagonista protagonistaAtivo)
    {
        protagonista = protagonistaAtivo;

        sprite = new Texture("menu.png");

        estadoDaJanela = Estado.INICIO;
        rodadaTerminou = false;
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
        batch.draw(sprite, 0, 0);

        switch (estadoDaJanela)
        {
            case INICIO:
                DesenharInicio(batch, font);
                break;
            case HABILIDADES:
                DesenharHabilidades(batch, font, protagonista.habilidades);
                break;
            case ALVOS:
                DesenharAlvos();
                break;
            case MENSAGEM:
                DesenharMensagem(batch, font);
                break;
        }
    }

    // Permite mudar o estado da janela fora da clsse JanelaUI.
    // Deve escrever em uma string com tudo maísculo.
    public void SetEstadoDaJanela(String estadoAtual)
    {
        if ("INICIO".equals(estadoAtual))
        {
            rodadaTerminou = false;
            indiceSelecao = 0;
            estadoDaJanela = Estado.INICIO;
        }
        else if ("MENSAGEM".equals(estadoAtual))
        {
            estadoDaJanela = Estado.MENSAGEM;
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
                estadoDaJanela = Estado.HABILIDADES;
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
            protagonista.habilidades[indiceSelecao].Aplicar(); // DEBUG
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

        }
        if (Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DPAD_DOWN))
        {

        }
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) || Gdx.input.isKeyJustPressed(Keys.ENTER))
        {

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
    private void DesenharAlvos()
    {

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
