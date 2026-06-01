import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MiniCraft extends ApplicationAdapter {
    SpriteBatch batch;
    Texture block;

    @Override
    public void create () {
        batch = new SpriteBatch();
        block = new Texture("grass_block.png"); // block texture
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for(int x=0; x<10; x++){
            for(int y=0; y<5; y++){
                batch.draw(block, x*64, y*64); // draw blocks
            }
        }
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        block.dispose();
    }
}
