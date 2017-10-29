package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import javax.swing.*;
import java.util.Random;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img2;
    static class Bita {
		float x,y;
		float vx, vy;
		Texture texture;
		float angle;

		public Bita(Texture texture, float x, float y, float vx, float vy ) {
			this.texture = texture;
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.angle=135;
		}
		public void render(SpriteBatch batch) {

			batch.draw(texture,x,y,160,160,500,500,1,1,angle,0,0,640,640,true,false
			);
		}
		public void update(float dt) {

		}
	}

	static class Obj{
	    float x,y;
	    float vx, vy;
	    Texture texture;
        float angle;


        public Obj(Texture texture, float x, float y, float vx, float vy) {
            this.texture = texture;
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.angle = 0;

        }

        public void render(SpriteBatch batch) {

                batch.draw(texture, x-160, y-160,160,160,320,320,1,1,angle,0,0,320,320,false,false);

        }

        public void update(float dt) {

//            if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {   //move in x -dir, (decrease)
//                x -= vx * dt;
//            }
//            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {   //move in y-dir, with delta-time binding (increase)
//                y +=vy * dt;
//
//            }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {   // move in y direction with  dt -time delta binding (decrease)
//               y -= vy * dt;
//            }

        }

    }
    Bita bita;
	Obj obj;


	@Override
	public void create () {
		batch = new SpriteBatch();

		//img2=new Texture("bita.png");
		bita = new Bita(new Texture("bita.png"),640,-70,700,700);
		obj = new Obj(new Texture("miyach.png"), 640 ,640, 400, 1900);

	}

	@Override
	public void render () {
	    float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		obj.render(batch);
		bita.render(batch);

		batch.end();
	}

Random rand = new Random();
	public void update(float dt) {
      int i = rand.nextInt(2);
		float r =obj.vy*dt;
		float rx =obj.vx*dt;
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			bita.angle=125;}

			bita.x+=bita.vx*dt;

		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				bita.angle = 125;
			}
			bita.x-=bita.vx*dt;
		}

		obj.y +=(int) obj.vy * dt;          //  движение мяча по игрику
		obj.x +=(int) obj.vx * dt;          //  движение мяча по иксу
		obj.angle+=6;

		if ((int)obj.y+r>1280) {    //  верхняя граница
			obj.y=1280-r;
			obj.vy *= -1;
			obj.x+=2;


				}
		if ((int)obj.x+(int)rx>1280) {  // правая граница для мяча
			obj.x= (int) (1280-rx);
			obj.vx *= -1;
			obj.x-=2;


		}
		if ((int)obj.x-(int)rx<0) {     // левая граница
			obj.x=(int)rx;
			if (i==1) {
				obj.x+=25;
				obj.vx *= -1;
			}

			//obj.vx *= -1;

		}

		if ((int)obj.x<(int)bita.x+301&&(int)obj.x>(int)bita.x-301&&(int)obj.y<(int)bita.y+270) { // условие отбивания от биты( нижняя граница) cast to int  - для более точнного определения границ

			if (i==0){

				obj.vy *= -1;        // изменение направления после отскакивания от биты в случайную сторону посредством рандома
				obj.vx *= 1;

			}if (i==1) {

				obj.vy *= -1;         // изменение направления после отскакивания от биты в случайную сторону посредством рандома
				obj.vx *= -1;

			}

		}
		if (obj.y<-175) {
			JOptionPane.showMessageDialog(null,"ball is lost!","You loose!",JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
//
//	 obj.update(dt);
//	 bita.update(dt);

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		obj.texture.dispose();
		bita.texture.dispose();
		//img2.dispose();
	}
}
