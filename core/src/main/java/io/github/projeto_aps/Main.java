package io.github.projeto_aps;

// Classes do libGDX
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game 
{
    // usado para desenhar sprites. batch.draw()
    private SpriteBatch batch;
    // usado para desenhar texto. font.draw()
    private BitmapFont font;

    // usado para manter a resolução do jogo
    private Viewport viewport;
    private Camera camera;

    @Override
    public void create() 
    {
        batch = new SpriteBatch();
        font  = new BitmapFont(Gdx.files.internal("font.fnt"));
        camera = new OrthographicCamera();
        viewport = new FitViewport(900, 600, camera);

        // setScreen troca qual tela deve ser mostrada. Nesse caso a classe TelaJogo.
        this.setScreen(new TelaMenu(this));
    }

    @Override
    public void render() 
    {
        super.render();
    }

    @Override
    public void dispose() 
    {
        batch.dispose();
        font.dispose();
    }
    
    // Usar para acessar o SpriteBatch principal.
    public SpriteBatch getSpriteBatch()
    {
        return batch;
    }

    // Usar para acessar o BitmapFont principal.
    public BitmapFont getFont()
    {
        return font;
    }
    
    // Usar para acessar o Viewport principal.
    public Viewport getViewport()
    {
        return viewport;
    }
 
    // Usar para acessar a Camera principal.
    public Camera getCamera()
    {
        return camera;
    }
    
}
