package io.github.projeto_aps;

// Classes do libGDX
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SistemaJogo
{
	// SistemaJogo processa as mecânicas do jogo.
	// Responsável por controlar as rodadas e manipular os personagems (inimigos e protagonista).

	// TODO:
	// Adicionar opção para voltar ao INICIO (JanelaUI)
	// Criar vária telas em HABILIDADES para segurar muitas habilidades (JanelaUI)
	// Mostrar vida do jogador (JanelaUI, função AtualizarInicio() )
	// Mostrar rodada atual (JanelaUI, função AtualizarInicio() )
	// Implementar seleção de inimigos (JanelaUI, função AtualizarAlvos() )
	// Mostrar vida do inimigos (Inimigo, Desenhar() )
	// Criar ataques que funcionam 
	// Implementar sistema de energia para ataques (JanelaUI, Protagonista )
	// Criar inimigos a cada x rodadas (SistemaJogo, AplicarRodada(), ProcessarRodada() )
	// Fazer inimigos atacarem (Inimigo, JogarRodada() )
	// Implementar som
	// Implementar música
	// Criar método para sacudir a tela (SistemaJogo)
	// Impolementar animações de inimigos (Inimigo, Desenhar()) [João Miguel]

	// Enumerador para controlar o estado do jogo. (Máquina de estados finita)
	// Cada estado chama uma função que controla o comportamento do jogo.
	private enum Estado
	{
		APLICAR_RODADA,
		PROCESSAR_RODADA,
		APLICAR_JOGADOR,
		PROCESSAR_JOGADOR,
		APLICAR_INIMIGOS,
		PROCESSAR_INIMIGOS;
	}

	// Variável para segurar o estado atual do jogo.
	private Estado estadoDoJogo;
	// Mantém conta da rodada atual.
	private int rodada;
	// Relógio do jogo.
	private float tempo;

	private Protagonista protagonista;
	private Inimigo[] inimigos;

	// Classe que controla o UI do jogo.
	private JanelaUI janelaUI;

	private SpriteBatch batch;
	private BitmapFont font;

	// Variáveis para debugging e teste.
	private String texto;		  // DEBUG
	private float tempoFaltando1; // DEBUG
	private float tempoFaltando2; // DEBUG
	
	public SistemaJogo(final Main jogo)
	{
		// Classes do jogo
		inimigos = new Inimigo[4];
		protagonista = new Protagonista();
		janelaUI = new JanelaUI(protagonista);

		// Objetos para desenho
		batch = jogo.getSpriteBatch();
		font = jogo.getFont();

		// Variáveis de controle das rodadas.
		estadoDoJogo = Estado.APLICAR_RODADA;
		rodada = 0;
		tempo = 0;
		
		tempoFaltando1 = 5;  // DEBUG
		tempoFaltando2 = 10; // DEBUG
	}

    public void Atualizar()
    {
		// Aperte K para criar inimigos
		// Aperte L para mudar estado do jogo
		ATUALIZAR_DEBUG();
		// Usado para testar os timers, estado do jogo e criar inimigos

		
		for (Inimigo inimigo : inimigos)
		{
			if (inimigo != null)
			{
				inimigo.AtualizarAnimacao(batch);
			}
		}

		// Controle da máquina de estados:
		// Os estados APLICAR lidam mais com lógica, qualquer coisa que precisa ser processado só uma vez (Ex: Aplicar Ataque)
		// Os estados PROCESSAR lidam com processos que acontecem ao longo de vários frames como animações e input.
		switch (estadoDoJogo)
		{
			case APLICAR_RODADA: 		AplicarRodada(); 		break; 
			case PROCESSAR_RODADA: 		ProcessarRodada(); 		break;
			case APLICAR_JOGADOR: 		AplicarJogador(); 		break;
			case PROCESSAR_JOGADOR: 	ProcessarJogador(); 	break;
			case APLICAR_INIMIGOS: 		AplicarInimigos(); 		break;
			case PROCESSAR_INIMIGOS: 	ProcessarInimigos(); 	break;
		}
    }

	public void Desenhar()
	{
		DESENHAR_DEBUG();



		// Desenhar janela
		janelaUI.Desenhar(batch, font);

		// Desenhar inimigos
		for (Inimigo inimigo : inimigos)
		{
			if (inimigo != null) { inimigo.Desenhar(batch, font); }
		}
	}

	// --- Métodos para o estado do jogo. -----------------------------------------------------------------------------------------------------------

	private void AplicarRodada()
	{
		// Começo da nova rodada.
		rodada++;

		// Preparar janela para mostrar uma mensagem.
		janelaUI.SetEstadoDaJanela("MENSAGEM");

		// Iniciar processamento da rodada.
		estadoDoJogo = Estado.PROCESSAR_RODADA;
		
		System.out.println("Nova rodada começada"); // DEBUG
		tempoFaltando1 = 5; // DEBUG
	}

	private void ProcessarRodada()
	{
		janelaUI.MostrarMensagem("Começando rodada " + rodada);

		// DEBUG
		tempoFaltando1 = tempoFaltando1 - Gdx.graphics.getDeltaTime();

		if (tempoFaltando1 < 0)
		{
			estadoDoJogo = Estado.APLICAR_JOGADOR;
		}
		// DEBUG
	}

	private void AplicarJogador()
	{
		// Preparar janela para receber input.
		janelaUI.SetEstadoDaJanela("INICIO");

		// Iniciar processamento da vez do jogador.
		estadoDoJogo = Estado.PROCESSAR_JOGADOR;
	}

	private void ProcessarJogador()
	{
		// Atualizar a rodada do jogador.
		janelaUI.Atualizar();

		// Se o jogador terminou a rodada, começar vez dos inimigos.
		if (janelaUI.checarSeRodadaTerminou())
		{
			estadoDoJogo = Estado.APLICAR_INIMIGOS;
		}
	}

	private void AplicarInimigos()
	{
		// Preparar janela para mostrar uma mensagem.
		janelaUI.SetEstadoDaJanela("MENSAGEM");

		// Iniciar processamento da vez dos inimigos.
		estadoDoJogo = Estado.PROCESSAR_INIMIGOS;

		tempoFaltando2 = 10; // DEBUG
	}

	private void ProcessarInimigos()
	{
		janelaUI.MostrarMensagem("Rodada dos inimigos...");

		// DEBUG
		tempoFaltando2 = tempoFaltando2 - Gdx.graphics.getDeltaTime();

		if (tempoFaltando2 < 0)
		{
			estadoDoJogo = Estado.APLICAR_RODADA;
		}
		// DEBUG
	}

	// Métodos Auxiliares
	private void CriarInimigo()
	{
		for (int i = 0; i < inimigos.length; i++)
		{
			if (inimigos[i] == null)
			{
				inimigos[i] = new Inimigo(i);
				break;
			}
		}
	}

	private void ATUALIZAR_DEBUG()
	{
		// Atualizar o tempo
		tempo = tempo + Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyJustPressed(Input.Keys.K)) // DEBUG
		{
			CriarInimigo();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.L)) // DEBUG
		{
			switch (estadoDoJogo)
			{
				case APLICAR_RODADA: estadoDoJogo = Estado.PROCESSAR_RODADA; break;
				case PROCESSAR_RODADA: estadoDoJogo = Estado.APLICAR_JOGADOR; break;
				case APLICAR_JOGADOR: estadoDoJogo = Estado.PROCESSAR_JOGADOR; break;
				case PROCESSAR_JOGADOR: estadoDoJogo = Estado.APLICAR_INIMIGOS; break;
				case APLICAR_INIMIGOS: estadoDoJogo = Estado.PROCESSAR_INIMIGOS; break;
				case PROCESSAR_INIMIGOS: estadoDoJogo = Estado.APLICAR_RODADA; break;
			}
		}

		switch (estadoDoJogo) // DEBUG
		{
			case APLICAR_RODADA: texto = "APLICAR_RODADA"; break;
			case PROCESSAR_RODADA: texto = "PROCESSAR_RODADA"; break;
			case PROCESSAR_JOGADOR: texto = "PROCESSAR_JOGADOR"; break;
			case APLICAR_INIMIGOS: texto = "APLICAR_INIMIGOS"; break;
			case PROCESSAR_INIMIGOS: texto = "PROCESSAR_INIMIGOS"; break;
		}
	}

	private void DESENHAR_DEBUG()
	{
		font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		font.draw(batch, texto, 10, 590); 				// DEBUG
		font.draw(batch, "Tempo: " + tempo, 10, 560); 	// DEBUG

		if (estadoDoJogo == Estado.PROCESSAR_RODADA)
		{
			font.setColor(com.badlogic.gdx.graphics.Color.GREEN);
			font.draw(batch, "Faltando: " + tempoFaltando1, 10, 530); 	// DEBUG
		}
		else if (estadoDoJogo == Estado.PROCESSAR_INIMIGOS)
		{
			font.setColor(com.badlogic.gdx.graphics.Color.RED);
			font.draw(batch, "Faltando: " + tempoFaltando2, 10, 530); 	// DEBUG
		}
	}
}