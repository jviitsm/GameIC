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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class GameIC extends Game {

    private SpriteBatch batch;
    private Texture fundo,fundo2;
	private Texture[] personagem, pernilongo;
    private Texture vida1,vida2,vida3,gameOver,pneu;
    private BitmapFont pontos;
    private Random distanciaRandomica,alturaRandomica,numeroRandomico;
    private int distanciaMosquitos,alturaMosquitos;





    //Atributos de configuração
    private float numeroVidas,pulo;
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
    private Circle circuloPersonagem;
    private Circle circuloPernilongo;
    private Rectangle retanguloPersonagem;
    private Music music,gameOverSound;
    private int pontuacao;
    private float pontuacaoPosicao;

    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRUAL_WIDTH =800;
    private final float VIRUAL_HEIGHT =600;
    private ShapeRenderer shape;

    public boolean houveColisao = false;




	@Override
	public void create () {



        batch = new SpriteBatch();

        alturaRandomica = new Random();
        distanciaRandomica = new Random();
        numeroRandomico = new Random();

        circuloPersonagem = new Circle();
        retanguloPersonagem = new Rectangle();

        shape = new ShapeRenderer();

        fundo = new Texture("city_morning2.png");
        fundo2 = new Texture("city_morning2.png");
        personagem = new Texture[3];
        personagem[0] = new Texture("man_stand.png.png");
        personagem[1]= new Texture("man_walk1.png.png");
        personagem[2] = new Texture("man_walk2.png.png");

        pernilongo = new Texture[2];
        pernilongo[0] = new Texture("mosquito1.png.png");
        pernilongo[1] = new Texture("mosquito2.png.png");

        pneu = new Texture("pneu2.png");

        pontos = new BitmapFont();
        pontos.setColor(Color.WHITE);
        pontos.getData().setScale(5);



        vida1 = new Texture("vida2.png");
        vida2 = new Texture("vida2.png");
        vida3 = new Texture("vida2.png");

        gameOver = new Texture("gameover.png");
        gameOverSound = Gdx.audio.newMusic(Gdx.files.internal("gameover.mp3"));
        gameOverSound.setLooping(false);
        gameOverSound.setVolume(1);


        music = Gdx.audio.newMusic(Gdx.files.internal("musicafunda.mp3"));
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();

      alturaDispositivo = Gdx.graphics.getHeight();
      larguraDispositivo = Gdx.graphics.getWidth();

        posicaoInicialVertical = 61.875f;
        posicaoMovimentoMosquito = -141.67f;

        numeroVidas = 3;
        estadoJogo =0;
        pontuacao =0;
        pontuacaoPosicao = 0;
        pulo = 0;



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


        //Jogo pausado antes de clicar pela primeira vez
        if(estadoJogo ==0){
            if(Gdx.input.justTouched()){
                estadoJogo =1;
                if(!music.isPlaying()){
                    music.play();
                }
            }



         //Apos o clique o jogo começa
        }if(estadoJogo ==1) {
            if(!music.isPlaying()){
                music.play();
            }
    pontuacao++;
            if(posicaoMovimentoMosquito <- larguraDispositivo){
                posicaoMovimentoMosquito = larguraDispositivo + 5.833f;
                alturaMosquitos = alturaRandomica.nextInt(700) - 200;
                distanciaMosquitos = distanciaRandomica.nextInt(500) - 300;
            }


            if (posicaoInicialVertical <= 61.875f) {
                pulou = false;
            }

            deltaTime = Gdx.graphics.getDeltaTime();


            posicaoMovimentoHorizontal -= deltaTime * 174.99f;
            posicaoMovimentoHorizontal2 -= deltaTime * 174.99f;

            //muda velocidade do pernilongo de acordo com a pontuação
            if(pontuacao<=1000) {
                posicaoMovimentoMosquito -= deltaTime * 204.155f;
            } else if(pontuacao>=1000 && pontuacao<3000){
                posicaoMovimentoMosquito -= deltaTime * 320.815f;
            } else if(pontuacao>=3000 && pontuacao<5000){
                posicaoMovimentoMosquito -= deltaTime * 379.145f;
            } else {
                posicaoMovimentoMosquito -= deltaTime * 466.64f;
            }

            if(houveColisao == true){
                Gdx.app.log("Aqui", "1" +houveColisao );
                if(posicaoMovimentoMosquito < 29.165f - personagem[0].getWidth()){
                    houveColisao = false;
                    Gdx.app.log("Aqui2", "2 " + posicaoMovimentoMosquito);
                }
            }


            if (Gdx.input.justTouched()) {
                if (pulou != true) {
                    if (posicaoInicialVertical < 206.25f) {
                        velocidadeQueda = -13.75f;
                        if (posicaoInicialVertical >= 174.99f) {
                            posicaoInicialVertical = 174.99f;
                            velocidadeQueda = 0;
                        }

                    } else {
                        pulou = true;
                    }
                }
            } else {
                velocidadeQueda++;
                if (posicaoInicialVertical > 61.875f || velocidadeQueda < 0) {
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


        }if(estadoJogo ==2){
            music.stop();
            gameOverSound.play();
            if(Gdx.input.justTouched()){
                    estadoJogo = 0;
                    numeroVidas = 3;
                    posicaoMovimentoMosquito = -141.67f;
                    posicaoInicialVertical = 61.875f;
                    music.play();
                    pontuacao =0;
                    houveColisao = false;

                      gameOverSound.stop();
            }
        }
    batch.setProjectionMatrix(camera.combined);
        batch.begin();


        batch.draw(fundo,posicaoMovimentoHorizontal ,0 , larguraDispositivo,alturaDispositivo);
        batch.draw(fundo2,posicaoMovimentoHorizontal2 + larguraDispositivo ,0,larguraDispositivo,alturaDispositivo);
        //batch.draw(pneu,posicaoMovimentoMosquito,50);
        batch.draw(personagem[(int) variacao],50,posicaoInicialVertical);
        batch.draw(pernilongo[(int) variacao], posicaoMovimentoMosquito , 68.75f );

       //Mosquito 2
       //batch.draw(pernilongo [ (int) variacao], posicaoMovimentoMosquito + distanciaMosquitos, 100 + alturaMosquitos);
        pontos.draw(batch,String.valueOf(pontuacao),pontuacaoPosicao,alturaDispositivo -20.625f);

        if(estadoJogo ==2){
            batch.draw(gameOver,larguraDispositivo /2 - gameOver.getWidth() /2,alturaDispositivo /2 - gameOver.getHeight() /2);
        }

        //Vidas
        if(numeroVidas == 3){
            batch.draw(vida1,20,alturaDispositivo -68.75f) ;
            batch.draw(vida2,20 + vida1.getWidth(),alturaDispositivo -68.75f);
            batch.draw(vida3,20 + vida1.getWidth() + vida1.getWidth(),alturaDispositivo -68.75f);
        } else if(numeroVidas ==2){
            batch.draw(vida1,20,alturaDispositivo -68.75f) ;
            batch.draw(vida2,20 + vida1.getWidth(),alturaDispositivo -68.75f);
        } else if(numeroVidas ==1){
            batch.draw(vida1,20,alturaDispositivo -68.75f) ;
        } else{
            estadoJogo = 2;
        }


        batch.end();

        shape.setProjectionMatrix(batch.getProjectionMatrix());

        circuloPernilongo = new Circle(
                posicaoMovimentoMosquito ,
                68.75f + pernilongo[0].getHeight() /2,
                pernilongo[0].getWidth() /2
        );
        retanguloPersonagem = new Rectangle(
                50 ,
                posicaoInicialVertical,
                60 ,
                70
        );




        shape.begin(ShapeRenderer.ShapeType.Filled);


        shape.circle(circuloPernilongo.x,circuloPernilongo.y,circuloPernilongo.radius);
        shape.rect(retanguloPersonagem.x,retanguloPersonagem.y,retanguloPersonagem.width,retanguloPersonagem.height);
        shape.setColor(Color.RED);
        shape.end();

        //Se houver colisão com o pernilongo retira 300 pontos do placar
        /*if( Intersector.overlaps( circuloPernilongo, retPe )){
//            Gdx.app.log("Colisão", "Houve colisão");
            if(houveColisao==false) {
                pontuacao -= 300;
                houveColisao = true;
            }
        } else {
            houveColisao = false;
        }
    */
        if(Intersector.overlaps(circuloPernilongo,retanguloPersonagem)){
            if(houveColisao == false){
                numeroVidas --;
                houveColisao = true;

            }
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
