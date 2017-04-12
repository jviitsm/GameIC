package br.edu.leaosampaio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameIC extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture fundo,fundo2;
	private Texture[] personagem;
    //private ShapeRenderer shape;


    //Atributos de configuração
    private float alturaDispositivo;
    private float larguraDispositivo;
    private float deltaTime;
    private float posicaoMovimentoHorizontal;
    private float posicaoMovimentoHorizontal2;
    private float variacao =0;
    private float velocidadeQueda =0;
    private float posicaoInicialVertical;
    private boolean pulou = false;
    private Circle circuloPersonagem;




	@Override
	public void create () {
        batch = new SpriteBatch();

        circuloPersonagem = new Circle();
       // shape = new ShapeRenderer();

        fundo = new Texture("city_night.jpg");
        fundo2 = new Texture("city_night.jpg");
        personagem = new Texture[3];
        personagem[0] = new Texture("man_stand.png");
        personagem[1]= new Texture("man_walk1.png");
        personagem[2] = new Texture("man_walk2.png");

      alturaDispositivo = Gdx.graphics.getHeight();
      larguraDispositivo = Gdx.graphics.getWidth();
       posicaoInicialVertical = 120;




	}

	@Override
	public void render () {

        if(posicaoInicialVertical <= 120){
            pulou = false;
        }
        deltaTime = Gdx.graphics.getDeltaTime();

        posicaoMovimentoHorizontal -= deltaTime * 300;
        posicaoMovimentoHorizontal2 -= deltaTime * 300;


            if(Gdx.input.justTouched()){
                if(pulou != true){
                if(posicaoInicialVertical < 400){
                        velocidadeQueda = -20;

                    }else{
                    pulou = true;
                }
                }
            }


        else{
            velocidadeQueda ++;
            if(posicaoInicialVertical > 120 || velocidadeQueda <0){
            posicaoInicialVertical = posicaoInicialVertical -velocidadeQueda;

            }

        }



        //Animação do personagem
        variacao += deltaTime * 5;
        if(variacao >2) variacao =0;

        //Fundo voltar para o final
        if(posicaoMovimentoHorizontal < - larguraDispositivo){
            posicaoMovimentoHorizontal = larguraDispositivo;
        }

        if(posicaoMovimentoHorizontal2 < -larguraDispositivo - larguraDispositivo){
            posicaoMovimentoHorizontal2 = posicaoMovimentoHorizontal;
        }

        batch.begin();

        batch.draw(fundo,posicaoMovimentoHorizontal ,0 , larguraDispositivo,alturaDispositivo);
        batch.draw(fundo2,posicaoMovimentoHorizontal2 + larguraDispositivo ,0,larguraDispositivo,alturaDispositivo);
        batch.draw(personagem[(int) variacao],50,posicaoInicialVertical);



        batch.end();


        circuloPersonagem.set(50 + personagem[0].getWidth() /2,posicaoInicialVertical + personagem[0].getHeight() /2 - 10,
                personagem[0].getWidth() /2 + 7);



       /* shape.begin(ShapeRenderer.ShapeType.Filled);

            shape.circle(circuloPersonagem.x,circuloPersonagem.y,circuloPersonagem.radius);
            shape.setColor(Color.RED);
        shape.end();
*/




	}


    @Override
	public void dispose () {

	}
}
