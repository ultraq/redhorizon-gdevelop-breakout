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

import nz.net.ultraq.redhorizon.graphics.Sprite
import nz.net.ultraq.redhorizon.scenegraph.Node
import static nz.net.ultraq.breakout.ScopedValues.RESOURCE_MANAGER

/**
 * The player/paddle character.
 *
 * @author Emanuel Rabina
 */
class Paddle extends Node<Paddle> {

	/**
	 * Constructor, set up the paddle entity.
	 */
	Paddle() {

		var resourceManager = RESOURCE_MANAGER.get()
		var paddleImage = resourceManager.loadImage('paddle.png')
		addChild(new Sprite(paddleImage))
			.translate(0f, -330f)
	}
}
