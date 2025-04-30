package io.github.projeto_aps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TelaMenu implements Screen{
    
    // TelaMenu é usada para segurar o menu principal do jogo.
    
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

    public TelaMenu(final Main jogo)
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
        // Se o jogador apertar enter, começar o jogo.
        if (Gdx.input.isKeyJustPressed(Keys.ENTER))
        {
            jogo.setScreen(new TelaJogo(jogo));
        }
    }

    private void Desenhar()
    {
        // Preparar novo frame
        LimparTela();

        // Começar desenho, todos os batch.Draw() e font.Draw() acontecem depois do .begin() e antes do .end();
        batch.begin();
        font.draw(batch, "Aperte Enter para começar o jogo.", 200, 200);
        batch.end();

        // Atualizar as bordas rolantes
        AtualizarBordas();
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


    @Override
    public void show()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        fundo.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }
}
