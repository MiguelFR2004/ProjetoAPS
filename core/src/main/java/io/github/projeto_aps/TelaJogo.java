package io.github.projeto_aps;

// Classes do libGDX
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

// Tela que mostra o jogo principal
public class TelaJogo implements Screen{

    // TelaJogo tem a função de segurar tudo que acontece no jogo de rpg em si.
    
    // Referência da instância Main, usada para acessar batch e font nas outras classes.
    final Main jogo;

    // usado para desenhar sprites. batch.draw()
    private SpriteBatch batch;
    // usado para desenhar texto. font.draw()
    private BitmapFont font;

    // usado para manter a resolução do jogo
    private Viewport viewport;
    private Camera camera;

    // Usado para as bordas rolantes
    private int scroll;
    private Texture fundo;

    // usado para calculara a lógica do mundo jogo.
    SistemaJogo sistemaJogo;

    public TelaJogo(final Main jogo)
    {
        // Variáveis estabelecidas em Main. 
        // Todas as variáveis SpriteBatch e BitMapFont devem referênciar o mesmo objeto criado no Main.
        this.jogo = jogo;
        batch = this.jogo.getSpriteBatch();
        font  = this.jogo.getFont();
        viewport = this.jogo.getViewport();
        camera = this.jogo.getCamera();
        viewport.apply();

        // Inicializar as bordas rolantes.
        scroll = 0;
        fundo = new Texture("Grama.png");
        fundo.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    
        // Lógica do jogo acontece dentro daqui
        sistemaJogo = new SistemaJogo(this.jogo);
    }

    // Loop do jogo acontece na função render() todo frame.
    @Override
    public void render(float delta)
    {
        // Atualizar a lógica do jogo.
        Atualizar();
        // Iniciar SpriteBatch batch e desenhar os sprites.
        Desenhar();
    }

    // Lógica do jogo
    private void Atualizar()
    {
        sistemaJogo.Atualizar();

        // Pseudocódigo para possíveis menus, não obrigatório.
        // Uma imagem de fundo e um botão que mande para a TelaMenu seria o suficiente para os menus.
        /*
        SE o jogo estiver ativo
		{
			sistemaJogo.Atualizar();
		}
        SE o jogo estiver pausado
        {
            menuPausa.Atualizar();
        }
		SE o jogador perdeu
		{
			menuGameOver.Atualizar();
		}
        SE o jogador ganhou
        {
            menuVitoria.Atualizar();
        }
        */
    }

    private void Desenhar()
    {
        // Preparar novo frame
        LimparTela();

        // Começar desenho, todos os batch.Draw() e font.Draw() acontecem depois do .begin() e antes do .end();
        batch.begin();
        sistemaJogo.Desenhar();
        batch.end();

        // Atualizar as bordas rolantes
        AtualizarBordas();

        // Pseudocódigo para possíveis menus, não obrigatório.
        // Uma imagem de fundo e um botão que mande para a TelaMenu seria o suficiente para os menus.
        /*
        SE o jogo estiver ativo
		{
			sistemaJogo.Desenhar();
		}
        SE o jogo estiver pausado
        {
            menuPuasa.Desenhar();
        }
		SE o jogador perdeu
		{
			menuGameOver.Desenhar();
		}
        SE o jogador ganhou
        {
            menuVitoria.Desenhar();
        }
        */
    }

    private void LimparTela()
    {
        batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
        camera.update();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
    }

    // Nem olha pra isso, eu copiei colei o código e não faço ideia de como funciona :)
    private void AtualizarBordas()
    {
        if (viewport instanceof ScalingViewport)
        {
            scroll = scroll + 1 % fundo.getHeight();

    	    ScalingViewport scalingViewport = (ScalingViewport)viewport;
	    	int screenWidth = Gdx.graphics.getWidth();
	    	int screenHeight = Gdx.graphics.getHeight();
	    	HdpiUtils.glViewport(0, 0, screenWidth, screenHeight);
	    	batch.getProjectionMatrix().idt().setToOrtho2D(0, 0, screenWidth, screenHeight);
	    	batch.getTransformMatrix().idt();
	    	batch.begin();
	    	float leftGutterWidth = scalingViewport.getLeftGutterWidth();
	    	if (leftGutterWidth > 0) {
	    		batch.draw(fundo, 0, 0, (int) -leftGutterWidth - scroll, -scroll, (int) leftGutterWidth, screenHeight);
	    		batch.draw(fundo, scalingViewport.getRightGutterX(), 0, -scroll, -scroll, scalingViewport.getRightGutterWidth(), screenHeight);
	    	}
	    	float bottomGutterHeight = scalingViewport.getBottomGutterHeight();
	    	if (bottomGutterHeight > 0) {
	    		batch.draw(fundo, 0, 0, -scroll, -scroll, screenWidth, (int) bottomGutterHeight);
	    		batch.draw(fundo, 0, scalingViewport.getTopGutterY(), -scroll, (int) -bottomGutterHeight - scroll, screenWidth, scalingViewport.getTopGutterHeight());
	    	}
	    	batch.end();
	    	viewport.update(screenWidth, screenHeight, true); // Restore viewport.
        }
    }

    // Método chamado quando a janela é redimensionada.
    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }

    // Variáveis a serem jogadas fora ao trocar de Tela.
    @Override
    public void dispose()
    {
        batch.dispose();
        font.dispose();
    }

    // Método obrigatório da interface Screen, pode ignorar.
    @Override
    public void show()
    {

    }

    // Método obrigatório da interface Screen, pode ignorar.
    @Override
    public void hide()
    {

    }

    // Método obrigatório da interface Screen, pode ignorar.
    @Override
    public void pause()
    {

    }

    // Método obrigatório da interface Screen, pode ignorar.
    @Override
    public void resume()
    {

    }
}
