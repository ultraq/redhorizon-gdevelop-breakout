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

import nz.net.ultraq.redhorizon.graphics.Camera
import nz.net.ultraq.redhorizon.scenegraph.Scene
import static nz.net.ultraq.breakout.ScopedValues.WINDOW

/**
 * Scene setup for the Breakout game.
 *
 * @author Emanuel Rabina
 */
class BreakoutScene extends Scene {

	static final int WIDTH = 800
	static final int HEIGHT = 500

	final Camera camera
	final Paddle paddle

	/**
	 * Constructor, create a new scene.
	 */
	BreakoutScene() {

		var window = WINDOW.get()

		camera = addAndReturnChild(new Camera(WIDTH, HEIGHT, window))
		paddle = addAndReturnChild(new Paddle())
	}
}
