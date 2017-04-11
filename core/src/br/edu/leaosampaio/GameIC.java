package br.edu.leaosampaio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameIC extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture fundo;
	private Texture personagem;


    //Atributos de configuração
    private float alturaDispositivo;
    private float larguraDispositivo;
    private float posicaoInicialVertical;
    private float deltaTime;

    //Camera
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRUAL_WIDTH =768;
    private final float VIRUAL_HEIGHT =1366;

	@Override
	public void create () {
        batch = new SpriteBatch();
        fundo = new Texture("fundo1.png");

        personagem = new Texture("passaro1.png");

        alturaDispositivo = Gdx.graphics.getHeight();
        larguraDispositivo = Gdx.graphics.getWidth();

        posicaoInicialVertical = alturaDispositivo /2;

        //Cãmera
        camera = new OrthographicCamera();
        camera.position.set(VIRUAL_WIDTH /2,VIRUAL_HEIGHT /2,0);
        viewport = new StretchViewport(VIRUAL_WIDTH,VIRUAL_HEIGHT,camera);

	}

	@Override
	public void render () {
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        deltaTime = Gdx.graphics.getDeltaTime();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();


        batch.draw(fundo,0 ,0 , larguraDispositivo,alturaDispositivo);
        batch.draw(personagem,120,posicaoInicialVertical);


        batch.end();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
	public void dispose () {

	}
}
