package br.edu.leaosampaio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameIC extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture fundo,fundo2;
	private Texture[] personagem;



    //Atributos de configuração
    private float alturaDispositivo;
    private float larguraDispositivo;
    private float deltaTime;
    private float posicaoMovimentoHorizontal;
    private float getPosicaoMovimentoHorizontal2;
    private float variacao =0;




	@Override
	public void create () {
        batch = new SpriteBatch();

        fundo = new Texture("city_night.jpg");
        fundo2 = new Texture("city_night.jpg");
        personagem = new Texture[3];
        personagem[0] = new Texture("man_stand.png");
        personagem[1]= new Texture("man_walk1.png");
        personagem[2] = new Texture("man_walk2.png");

      alturaDispositivo = Gdx.graphics.getHeight();
      larguraDispositivo = Gdx.graphics.getWidth();




	}

	@Override
	public void render () {

        deltaTime = Gdx.graphics.getDeltaTime();

        posicaoMovimentoHorizontal -= deltaTime * 200;

        variacao += deltaTime * 5;
        if(variacao >2) variacao =0;

        if(posicaoMovimentoHorizontal < fundo.g)


        batch.begin();

        batch.draw(fundo,posicaoMovimentoHorizontal ,0 , larguraDispositivo,alturaDispositivo);
        batch.draw(fundo2,posicaoMovimentoHorizontal + larguraDispositivo ,0,larguraDispositivo,alturaDispositivo);
        batch.draw(personagem[(int) variacao],1,120);



        batch.end();








	}


    @Override
	public void dispose () {

	}
}
