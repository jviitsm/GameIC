package br.edu.leaosampaio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameIC extends Game {

    private SpriteBatch batch;
    private Texture fundo,fundo2;
	private Texture[] personagem, pernilongo;
    private Texture vida1,vida2,vida3;
    private BitmapFont pontos;

    //private ShapeRenderer shape;


    //Atributos de configuração
    private float numeroVidas;
    private int estadoJogo;
    private float alturaDispositivo;
    private float larguraDispositivo;
    private float deltaTime;
    private float posicaoMovimentoHorizontal;
    private float posicaoMovimentoHorizontal2; //pelo amor de Deus, procure outros nomes, nada de 1,2,3,4,5... kkkk
    private float posicaoMovimentoMosquito;
    private float variacao =0;
    private float velocidadeQueda =0;
    private float posicaoInicialVertical;
    private boolean pulou = false;
    private Circle circuloPersonagem, circuloPernilongo;
    private Music music;
    private int pontuacao;
    private float pontuacaoPosicao;

    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRUAL_WIDTH =1366;
    private final float VIRUAL_HEIGHT =1024;
    private ShapeRenderer shape;

    public boolean houveColisao = false;




	@Override
	public void create () {
        batch = new SpriteBatch();

        circuloPersonagem = new Circle();

        shape = new ShapeRenderer();

        fundo = new Texture("city_morning.jpg");
        fundo2 = new Texture("city_morning.jpg");
        personagem = new Texture[3];
        personagem[0] = new Texture("man_stand.png");
        personagem[1]= new Texture("man_walk1.png");
        personagem[2] = new Texture("man_walk2.png");

        pernilongo = new Texture[2];
        pernilongo[0] = new Texture("mosquito1.png");
        pernilongo[1] = new Texture("mosquito2.png");

        pontos = new BitmapFont();
        pontos.setColor(Color.WHITE);
        pontos.getData().setScale(5);


        vida1 = new Texture("vida2.png");
        vida2 = new Texture("vida2.png");
        vida3 = new Texture("vida2.png");



        music = Gdx.audio.newMusic(Gdx.files.internal("musicafunda.mp3"));
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();

      //alturaDispositivo = Gdx.graphics.getHeight();
      //larguraDispositivo = Gdx.graphics.getWidth();
        posicaoInicialVertical = 80;
        posicaoMovimentoMosquito = -100;
        numeroVidas = 3;
        estadoJogo =0;
        pontuacao =0;
        pontuacaoPosicao = 0;



        larguraDispositivo = VIRUAL_WIDTH;
        alturaDispositivo = VIRUAL_HEIGHT;


        camera = new OrthographicCamera();
        camera.position.set(VIRUAL_WIDTH /2,VIRUAL_HEIGHT /2,0);
        viewport = new StretchViewport(VIRUAL_WIDTH,VIRUAL_HEIGHT,camera);


	}

	@Override
	public void render () {
        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);



        //Ajustar posição da pontuação na tela
        if(pontuacao >= 0 && pontuacao < 10){
            pontuacaoPosicao = larguraDispositivo -50;
        }else if(pontuacao >= 10 && pontuacao < 100 ){
            pontuacaoPosicao = larguraDispositivo - 80;
        }else if(pontuacao >= 100 && pontuacao < 999){
            pontuacaoPosicao = larguraDispositivo - 120;
        }else{
            pontuacaoPosicao = larguraDispositivo - 160;
        }
        //Somente para testes, remover
        if(pontuacao >9999) pontuacao =0;
        //Jogo pausado antes de clicar pela primeira vez
        if(estadoJogo ==0){
            if(Gdx.input.justTouched()){
                estadoJogo =1;
            }
         //Apois o clique o jogo começa
        }else {

            if(posicaoMovimentoMosquito<-5){
                posicaoMovimentoMosquito = larguraDispositivo + 10;
            }

            pontuacao ++;
            if (posicaoInicialVertical <= 90) {
                pulou = false;
            }
            deltaTime = Gdx.graphics.getDeltaTime();

            posicaoMovimentoHorizontal -= deltaTime * 300;
            posicaoMovimentoHorizontal2 -= deltaTime * 300;
            //muda velocidade do pernilongo de acordo com a pontuação
            if(pontuacao<=1000) {
                posicaoMovimentoMosquito -= deltaTime * 350;
            } else if(pontuacao>=1000 && pontuacao<3000){
                posicaoMovimentoMosquito -= deltaTime * 550;
            } else if(pontuacao>=3000 && pontuacao<5000){
                posicaoMovimentoMosquito -= deltaTime * 650;
            } else {
                posicaoMovimentoMosquito -= deltaTime * 800;
            }


            if (Gdx.input.justTouched()) {
                if (pulou != true) {
                    if (posicaoInicialVertical < 300) {
                        velocidadeQueda = -20;
                        if (posicaoInicialVertical >= 300) {
                            posicaoInicialVertical = 300;
                            velocidadeQueda = 0;
                        }

                    } else {
                        pulou = true;
                    }
                }
            } else {
                velocidadeQueda++;
                if (posicaoInicialVertical > 90 || velocidadeQueda < 0) {
                    posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;

                }

            }

            //Animação do personagem
            variacao += deltaTime * 5;
            if (variacao > 2) variacao = 0;

            //Fundo voltar para o final
            if (posicaoMovimentoHorizontal < -larguraDispositivo) {
                posicaoMovimentoHorizontal = larguraDispositivo;
            }

            if (posicaoMovimentoHorizontal2 < -larguraDispositivo - larguraDispositivo) {
                posicaoMovimentoHorizontal2 = posicaoMovimentoHorizontal;
            }

        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        batch.draw(fundo,posicaoMovimentoHorizontal ,0 , larguraDispositivo,alturaDispositivo);
        batch.draw(fundo2,posicaoMovimentoHorizontal2 + larguraDispositivo ,0,larguraDispositivo,alturaDispositivo);
        batch.draw(personagem[(int) variacao],50,posicaoInicialVertical);
        batch.draw(pernilongo[(int) variacao], posicaoMovimentoMosquito , 100 );
        pontos.draw(batch,String.valueOf(pontuacao),pontuacaoPosicao,alturaDispositivo -30);

        //Vidas
        if(numeroVidas == 3){
            batch.draw(vida1,20,alturaDispositivo - 100) ;
            batch.draw(vida2,20 + vida1.getWidth(),alturaDispositivo -100);
            batch.draw(vida3,20 + vida1.getWidth() + vida1.getWidth(),alturaDispositivo -100);
        } else if(numeroVidas ==2){
            batch.draw(vida1,20,alturaDispositivo - 100) ;
            batch.draw(vida2,20 + vida1.getWidth(),alturaDispositivo -100);
        } else if(numeroVidas ==1){
            batch.draw(vida1,20,alturaDispositivo - 100) ;
        } else{
            estadoJogo = 2;
        }



        batch.end();

        //Cria o circulo no personagem para detectar colisões
        circuloPersonagem.set(40 + personagem[0].getWidth() ,
                posicaoInicialVertical + personagem[0].getHeight() /2 - 10,
                personagem[0].getWidth() /2 + 7);

        circuloPernilongo = new Circle(
                posicaoMovimentoMosquito,
                100 + (pernilongo[0].getHeight() /2),
                pernilongo[0].getWidth() /2
        );



        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.circle(circuloPersonagem.x,circuloPersonagem.y,circuloPersonagem.radius);
        shape.circle(circuloPernilongo.x,circuloPernilongo.y,circuloPernilongo.radius);
        shape.setColor(Color.RED);
        shape.end();

        //Se houver colisão com o pernilongo retira 300 pontos do placar
        if( Intersector.overlaps( circuloPersonagem, circuloPernilongo )){
//            Gdx.app.log("Colisão", "Houve colisão");
            if(houveColisao==false) {
                pontuacao -= 300;
                houveColisao = true;
            }
        } else {
            houveColisao = false;
        }

	}


    @Override
	public void dispose () {
        super.dispose();
        music.dispose();

	}
    public void resize(int width, int height) {
        viewport.update(width,height);
    }
}
