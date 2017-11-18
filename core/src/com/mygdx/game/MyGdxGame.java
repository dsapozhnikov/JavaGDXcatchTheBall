package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


import javax.swing.*;


/*
Создаем простую игру, которая сводится к тому чтобы не потерять мяч, отбивая его битой
 */

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;


	static class Bita {
		private Vector2 position;
		private float speed;
		private float width;
		private float height;
		Texture texture;
		float angle;

		public Bita() {                                         // создаем Биту для отбивания мяча

			this.texture = new Texture("bita.png");    // назначаем параметры биты
			this.position = new Vector2(640, -70);
			this.speed = 500f;
			this.width = 340;
			this.height = 100;
			this.angle = 135;
		}

		public void render(SpriteBatch batch) {                // отрисовываем биту

			batch.draw(texture, position.x, position.y, 160, 160, 500, 500, 1, 1, angle, 0, 0, 640, 640, true, false
			);
		}

		public void update(float dt) {

			if (Gdx.input.isKeyPressed(Input.Keys.A)) {       // управление битой , при нажатии "А" бита движется влево
				position.x -= speed * dt;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {        // управление битой , при нажатии "D" бита движется вправо
				position.x += speed * dt;
			}

		}
	}
                                                            // создаем  класс мяча
	static class Obj {
		private float radius;
		private Vector2 position;
		private Vector2 velocity;
		Texture texture;
		float angle;

		public Obj() {
			this.position = new Vector2(640, 640);            //  задаем параметры мяча
			this.velocity = new Vector2(400f, 2000f);        // скорости перемещения мяча по x и y
			this.radius = 160;                                   // зааем радиус для отладки отскока от стены
			this.texture = new Texture("miyach.png");

			this.angle = 0;

		}

		public void render(SpriteBatch batch) {        // отрисовываем мяч

			batch.draw(texture, position.x-radius, position.y-radius, radius, radius, radius * 2, radius * 2, 1, 1, angle, 0, 0, 320, 320, false, false);

		}

		public void update(float dt) {

			position.mulAdd(velocity, dt);           // задаем условия отбивания мяча от стенок верхней планкиб меняем вектор скорости на противоположный
			angle *= dt;
			if (position.x + radius >= 1280) {
				velocity.x *= -1;
				position.x = 1280 - radius;

			}
			if (position.x - radius <= 0) {
				velocity.x *= -1;
				position.x = radius;

			}
			if (position.y + radius >= 1280) {
				velocity.y *= -1;
				position.y = 1280 - radius;

			}
		}
	}

	Bita bita;
	Obj obj;
	private int threshold = -175;

	@Override
	public void create() {
		batch = new SpriteBatch();

		bita = new Bita();
		obj = new Obj();

	}
	@Override
	public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		obj.render(batch);
		bita.render(batch);

		batch.end();
	}

	public void update(float dt) {

		obj.update(dt);
		bita.update(dt);

		 // создаем условия для отбивания мяча от биты с имзменением вектора скорости на противоположную

		if (obj.position.y - obj.radius <= bita.position.y+100
				&& obj.position.x >= bita.position.x - bita.width
				&& obj.position.x <= bita.position.x + bita.width) {
			if (obj.velocity.y < 0) {
				obj.velocity.y *= -1;
			}
		}
		if (obj.position.y < threshold) {

			// если мяч пролетает мимо биты , выводим сообщение и закрываем приложение

			JOptionPane.showMessageDialog(null, "ball is lost!", "You loose!", JOptionPane.INFORMATION_MESSAGE);
			Gdx.app.exit();
		}

	}

	@Override
	public void dispose () {
		batch.dispose();
		obj.texture.dispose();
		bita.texture.dispose();

	}
}



