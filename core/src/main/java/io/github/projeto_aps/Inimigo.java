package io.github.projeto_aps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inimigo extends Personagem
{
	// Energia é usada para aplicar habilidades e habilidades segura todas as habilidades do personagem.
    // protected int vida;					(em Personagem)
    // protected int energia;				(em Personagem)
    // protected Habilidade habilidades[];	(em Personagem)

	public enum Animacao
	{
		NENHUM,
		SPAWN,
		MORTE,
		ATACANDO,
		ATACADO;
	}

	// Essa variável mantém conta da animação sendo tocada atualmente
	private Animacao animacaoAtual;

	// Variável para o índice do inimigo na array inimigos[]
	private int indice;

	// Variável que mantém conta de quantos segundos para trocar o frame em uma animação
	private float temporizadorAnimacao;

	// Sprites do inimigo e de efeitos adicionais (quando o inimigo é criado ou morre)
	private Texture sprite1;
	private Texture sprite2;
	private Texture spriteAtual;
	private Texture efeitoSprite;

	// Usados para certas animações
	private float rgb;
	private float alfa;

	// posição do inimigo
	private int posX;
	private int posY;

	public Inimigo(int indicePassado)
	{
        sprite1 = new Texture("inimigo1.png");
		sprite2 = new Texture("inimigo2.png");
		spriteAtual = sprite1;

		indice = indicePassado;

		animacaoAtual = Animacao.SPAWN;

		rgb = 0;
		alfa = 0;

		temporizadorAnimacao = 0;
		this.setAnimacaoAtual(Animacao.SPAWN);
		
		vidaMax = 100;   // DEBUG
		vidaAtual = 100; // DEBUG

		// A posição do inmigo depende da sua posição no array inimigos[]
		posX = (800 / 4) * indice + 100;
        posY = 400;	
	}

	// Função para o inimigo decidir qual habilidade usar.
	public void JogarRodada() // TODO
	{
		// Sugestão: Cada inimigo criado têm um TIPO, dependendo do seu TIPO uma cadeia lógica diferente é usada.

		// Para declarar habiliades é só escrever Habilidade.NomeDaHabilidade(parametros necessários);
		// Isso só se aplica aos inimigos, o jogador têm um array que segura as habilidades.
	}

	// Método para controlar a animação do inimigo.
	public void setAnimacaoAtual(Animacao novaAnimacao)
	{
		animacaoAtual = novaAnimacao;

		switch (animacaoAtual)
		{
			case NENHUM: temporizadorAnimacao = 1; break;
			case SPAWN: temporizadorAnimacao = 4; break;
		}
	}

	// Função para atualizar o inimigo dependendo da sua animação.
	public void AtualizarAnimacao(SpriteBatch batch) // TODO
	{
		switch (animacaoAtual)
		{
			case NENHUM: 	AnimacaoNenhum();		break;
			case SPAWN: 	AnimacaoSpawn(batch); 	break;
			case MORTE: 	AnimacaoMorte(); 		break;
			case ATACANDO: 	AnimacaoAtacando(); 	break;
			case ATACADO: 	AnimacaoAtacado();	 	break;
		}
	}

	// Desenhar o inimigo e sua vida.
    public void Desenhar(SpriteBatch batch, BitmapFont font)
    {
		batch.setColor(rgb, rgb, rgb, alfa);
        batch.draw(spriteAtual, posX, posY);
		batch.setColor(1, 1, 1, 1);

		// batch.draw(efeitoSprite, posX, posY);

		font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		font.draw(batch, vidaAtual + " / " + vidaMax, posX, posY - 10);
    }

	// Métodos para cada das animações dos inimigos.
	private void AnimacaoNenhum()
	{
		System.out.println(temporizadorAnimacao);

		temporizadorAnimacao = temporizadorAnimacao - Gdx.graphics.getDeltaTime();

		if (temporizadorAnimacao < 0)
		{
			if (spriteAtual == sprite1)
			{
				spriteAtual = sprite2;
			}
			else if (spriteAtual == sprite2)
			{
				spriteAtual = sprite1;
			}
			
			temporizadorAnimacao = 1;
		}
	}

	private void AnimacaoSpawn(SpriteBatch batch)
	{
		float tempoDelta = Gdx.graphics.getDeltaTime();

		temporizadorAnimacao = temporizadorAnimacao - tempoDelta;

		alfa = alfa + tempoDelta / 2;

		if (temporizadorAnimacao < 2)
		{
			alfa = 1;

			rgb = rgb + tempoDelta;

			if (temporizadorAnimacao < 0)
			{
				rgb = 1;

				setAnimacaoAtual(Animacao.NENHUM);
			}
		}
	}

	private void AnimacaoMorte()
	{

	}
	
	private void AnimacaoAtacando()
	{

	}

	private void AnimacaoAtacado()
	{

	}
}