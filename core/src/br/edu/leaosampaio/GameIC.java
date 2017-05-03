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
    private int distanciaMosquitos,alturaMosquitos,alturaMosquito2;





    //Atributos de configuração
    private float numeroVidas,pulo;
    private int estadoJogo;
    private float alturaDispositivo;
    private float larguraDispositivo;
    private float deltaTime;
    private float posicaoMovimentoHorizontal;
    private float posicaoMovimentoHorizontal2; //pelo amor de Deus, procure outros nomes, nada de 1,2,3,4,5... kkkk
    private float posicaoMovimentoMosquito,posicaoMovimentoMosquito2;
    private float variacao =0;
    private float posicaoHorizontalPneu;
    private float velocidadeQueda =0;
    private float posicaoInicialVertical;
    private boolean pulou = false;
    private Circle circuloMosquito2;
    private Circle circuloPneu;
    private Circle circuloPernilongo;
    private Circle circuloSegundoPernilongo;
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
    public boolean houveColisaoSegundoMosquito = false;
    public boolean houveColisaoPneu = false;




	@Override
	public void create () {



        batch = new SpriteBatch();

        alturaRandomica = new Random();
        distanciaRandomica = new Random();
        numeroRandomico = new Random();


        circuloSegundoPernilongo = new Circle();
        circuloPneu = new Circle();
        retanguloPersonagem = new Rectangle();


        shape = new ShapeRenderer();

        fundo = new Texture("city_night.jpg");
        fundo2 = new Texture("city_night.jpg");
        personagem = new Texture[3];
        personagem[0] = new Texture("man_stand.png.png");
        personagem[1]= new Texture("man_walk1.png.png");
        personagem[2] = new Texture("man_walk2.png.png");

        pernilongo = new Texture[2];
        pernilongo[0] = new Texture("mosquito1.png");
        pernilongo[1] = new Texture("mosquito2.png");

        pneu = new Texture("pneu2.png");

        pontos = new BitmapFont();
        pontos.setColor(Color.WHITE);
        pontos.getData().setScale(3);




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

        posicaoInicialVertical = 45;
        posicaoMovimentoMosquito = -141.67f;
        posicaoMovimentoMosquito2 = -141.67f;

        numeroVidas = 3;
        estadoJogo =0;
        pontuacao =0;
        pontuacaoPosicao = 0;
        pulo = 0;
        posicaoHorizontalPneu = larguraDispositivo + distanciaRandomica.nextInt(1000);


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
            pontuacaoPosicao = larguraDispositivo -45;
        }else if(pontuacao >= 10 && pontuacao < 100 ){
            pontuacaoPosicao = larguraDispositivo -65;
        }else if(pontuacao >= 100 && pontuacao < 999){
            pontuacaoPosicao = larguraDispositivo -85;
        }else{
            pontuacaoPosicao = larguraDispositivo - 105;
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


            if(posicaoMovimentoMosquito <- larguraDispositivo){
                distanciaMosquitos = distanciaRandomica.nextInt(1000) + 100;
                alturaMosquitos = alturaRandomica.nextInt(200) + 67;
                posicaoMovimentoMosquito = larguraDispositivo + distanciaMosquitos;

            }


            if (posicaoInicialVertical <= 45) {
                pulou = false;
            }

            if(posicaoMovimentoMosquito2 <- larguraDispositivo){
                posicaoMovimentoMosquito2 = larguraDispositivo + distanciaRandomica.nextInt(700) + 100;
                alturaMosquito2 = alturaRandomica.nextInt(300) + 40;

            }
            deltaTime = Gdx.graphics.getDeltaTime();


            if(posicaoHorizontalPneu <- larguraDispositivo){
                int distanciaPneuRandomica;
                distanciaPneuRandomica = distanciaRandomica.nextInt(3000) - distanciaRandomica.nextInt(1000);
                posicaoHorizontalPneu = larguraDispositivo + distanciaPneuRandomica;
            }


            //muda velocidade do pernilongo de acordo com a pontuação
            if(pontuacao<=100) {
                posicaoMovimentoHorizontal -= deltaTime * 174.99f;
                posicaoMovimentoHorizontal2 -= deltaTime * 174.99f;
                posicaoHorizontalPneu -= deltaTime * 174.99f;
                posicaoMovimentoMosquito -= deltaTime * 190.99f;
                posicaoMovimentoMosquito2 -= deltaTime * 190.99f;


                //Animação do personagem
                variacao += deltaTime * 5;
                if (variacao > 2) variacao = 0;

            } else if(pontuacao>=100 && pontuacao<300){
                posicaoMovimentoHorizontal -= deltaTime * 250;
                posicaoMovimentoHorizontal2 -= deltaTime * 250;
                posicaoHorizontalPneu -= deltaTime * 250;
                posicaoMovimentoMosquito -= deltaTime * 270;
                posicaoMovimentoMosquito2 -= deltaTime * 270;


                //Animação do personagem
                variacao += deltaTime * 10;
                if (variacao > 2) variacao = 0;

            } else if(pontuacao>=300 && pontuacao<800){
                posicaoMovimentoHorizontal -= deltaTime * 400;
                posicaoMovimentoHorizontal2 -= deltaTime * 400;
                posicaoHorizontalPneu -= deltaTime * 400;
                posicaoMovimentoMosquito -= deltaTime * 430;
                posicaoMovimentoMosquito2 -= deltaTime * 430;


                //Animação do personagem
                variacao += deltaTime * 18;
                if (variacao > 2) variacao = 0;

            } else {
                posicaoMovimentoHorizontal -= deltaTime * 800;
                posicaoMovimentoHorizontal2 -= deltaTime * 800;
                posicaoHorizontalPneu -= deltaTime * 800;
                posicaoMovimentoMosquito -= deltaTime * 830;
                posicaoMovimentoMosquito2 -= deltaTime * 830;


                //Animação do personagem
                variacao += deltaTime * 36;
                if (variacao > 2) variacao = 0;

            }

            if(houveColisao == true){
                if(posicaoMovimentoMosquito < 15 - personagem[0].getWidth()){
                    houveColisao = false;
                }
            }
            if(houveColisaoSegundoMosquito == true){
                if(posicaoMovimentoMosquito2 < 15 - personagem[0].getWidth()){
                    houveColisaoSegundoMosquito = false;

                }
            }
            if(houveColisaoPneu == true ){

                   int distanciaPneuRandomica;
                   distanciaPneuRandomica = distanciaRandomica.nextInt(3000) - distanciaRandomica.nextInt(100);
                   posicaoHorizontalPneu += distanciaPneuRandomica + larguraDispositivo;

                   if (posicaoHorizontalPneu > larguraDispositivo) {
                       houveColisaoPneu = false;
                   }

            }


            /*if (Gdx.input.justTouched()) {
                if (pulou != true) {
                    if (posicaoInicialVertical < 250) {
                        velocidadeQueda = -13.75f;
                        if (posicaoInicialVertical >= 174.99f) {
                            posicaoInicialVertical = 174.99f;
                            velocidadeQueda = 0;
                        }

                    } else {
                        pulou = true;
                    }
                }

            }*/



            if(Gdx.input.justTouched()) {
                if (pulou != true) {
                    velocidadeQueda = -15;
                    pulou = true;

                }
            }


            else {
                velocidadeQueda += 0.7;
                if (posicaoInicialVertical > 45 || velocidadeQueda < 0 && posicaoInicialVertical < 300)  {
                    posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;

                }

            }



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
                    posicaoMovimentoMosquito2 = 400;
                    posicaoInicialVertical = 45;
                    posicaoHorizontalPneu += distanciaRandomica.nextInt(3000) + larguraDispositivo;
                    music.play();
                    pontuacao =0;
                    houveColisao = false;
                    houveColisaoSegundoMosquito = false;
                    houveColisaoPneu = false;

                      gameOverSound.stop();
            }

        }
    batch.setProjectionMatrix(camera.combined);
        batch.begin();


        batch.draw(fundo,posicaoMovimentoHorizontal ,0 , larguraDispositivo,alturaDispositivo);
        batch.draw(fundo2,posicaoMovimentoHorizontal2 + larguraDispositivo ,0,larguraDispositivo,alturaDispositivo);

        if(houveColisaoPneu == false) {
                batch.draw(pneu, posicaoHorizontalPneu, 30);

        }
        batch.draw(personagem[(int) variacao],50,posicaoInicialVertical);
        batch.draw(pernilongo[(int) variacao], posicaoMovimentoMosquito , alturaMosquitos);
        batch.draw(pernilongo[(int) variacao], posicaoMovimentoMosquito2,  alturaMosquito2);

        pontos.draw(batch,String.valueOf(pontuacao),pontuacaoPosicao,alturaDispositivo -20.625f);







        if(estadoJogo ==2){
            batch.draw(gameOver,larguraDispositivo /2 - gameOver.getWidth() /2,alturaDispositivo /2 - gameOver.getHeight() /2);
        }

        //Vidas
        if(numeroVidas == 3){
            batch.draw(vida1,5,alturaDispositivo -65) ;
            batch.draw(vida2,5 + vida1.getWidth(),alturaDispositivo -65);
            batch.draw(vida3,5 + vida1.getWidth() + vida1.getWidth(),alturaDispositivo -65);
        } else if(numeroVidas ==2){
            batch.draw(vida1,5,alturaDispositivo -65) ;
            batch.draw(vida2,5 + vida1.getWidth(),alturaDispositivo -65);
        } else if(numeroVidas ==1){
            batch.draw(vida1,5,alturaDispositivo -65) ;
        } else{
            estadoJogo = 2;
        }


        batch.end();

        shape.setProjectionMatrix(batch.getProjectionMatrix());

        circuloPernilongo = new Circle(
                posicaoMovimentoMosquito + pernilongo[0].getWidth() /2 ,
                alturaMosquitos + pernilongo[0].getHeight() /2,
                pernilongo[0].getWidth() /3 + 5
        );
        retanguloPersonagem = new Rectangle(
                50 ,
                posicaoInicialVertical,
                personagem[1].getWidth(),
                personagem[1].getHeight()
        );
        circuloPneu = new Circle(
                posicaoHorizontalPneu + pneu.getWidth() /2,
                40 + pneu.getHeight() /2,
                pneu.getWidth()/2

                );
        circuloSegundoPernilongo = new Circle(
                posicaoMovimentoMosquito2 + pernilongo[0].getWidth() /2,
                alturaMosquito2 + pernilongo[0].getHeight() /2,
                pernilongo[0].getWidth() /3 + 5
        );





/*
       shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.circle(circuloSegundoPernilongo.x,circuloSegundoPernilongo.y,circuloSegundoPernilongo.radius);
        shape.circle(circuloPernilongo.x,circuloPernilongo.y,circuloPernilongo.radius);
        shape.rect(retanguloPersonagem.x,retanguloPersonagem.y,retanguloPersonagem.width,retanguloPersonagem.height);
        shape.setColor(Color.RED);
        shape.circle(circuloPneu.x,circuloPneu.y,circuloPneu.radius);
        shape.end();
*/
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
        if(Intersector.overlaps(circuloPneu,retanguloPersonagem)){

            if(houveColisaoPneu == false){

                pontuacao += 20;
                houveColisaoPneu = true;
            }
        }
        if(Intersector.overlaps(circuloSegundoPernilongo,retanguloPersonagem)){
            if(houveColisaoSegundoMosquito == false){
                numeroVidas --;
                houveColisaoSegundoMosquito = true;
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
