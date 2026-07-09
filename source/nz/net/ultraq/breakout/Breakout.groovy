/*
 * Copyright 2026, Emanuel Rabina (http://www.ultraq.net.nz/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nz.net.ultraq.breakout

import nz.net.ultraq.redhorizon.engine.scripts.Script
import nz.net.ultraq.redhorizon.engine.scripts.ScriptNode
import nz.net.ultraq.redhorizon.runtime.Application
import nz.net.ultraq.redhorizon.runtime.Runtime
import nz.net.ultraq.redhorizon.scenegraph.Scene

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE

/**
 * Entry point to the Breakout game.
 *
 * @author Emanuel Rabina
 */
class Breakout extends Application {

	static final int WIDTH = 800
	static final int HEIGHT = 500

	static void main(String[] args) {

		System.exit(new Runtime(new Breakout()).execute(
			"--window-width=${WIDTH}",
			"--window-height=${HEIGHT}",
			'--resource-manager-path-prefix=nz/net/ultraq/breakout/assets',
			*args
		))
	}

	/**
	 * Constructor, create a new Breakout game.
	 */
	Breakout() {

		super('Breakout', '0.1.0')
	}

	@Override
	protected Scene configureScene(Scene scene) {

		return scene
			.addChild(new Paddle().translate(0f, -HEIGHT / 2f + 10f as float))
			.addChild(new Ball())
			.addChild(new ScriptNode(StartGameScript))
	}

	/**
	 * Script for beginning the game.
	 */
	static class StartGameScript extends Script {

		private boolean gameStarted
		private Ball ball

		@Override
		void init() {

			ball = node.scene.findByType(Ball)
		}

		@Override
		void update(float delta) {

			// Start the game by sending the ball in a random direction within 45
			// degress of straight down
			if (!gameStarted && input.keyPressed(GLFW_KEY_SPACE, true)) {
				ball.setPosition(0f, 0f)

				var randomDirectionRadians = Math.toRadians((Math.random() * 90) + 225) as float
				ball.vector.set(Math.cos(randomDirectionRadians), Math.sin(randomDirectionRadians))
				gameStarted = true
			}
		}
	}
}
