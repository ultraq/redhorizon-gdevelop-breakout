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

import nz.net.ultraq.redhorizon.engine.physics.BoxCollider
import nz.net.ultraq.redhorizon.engine.physics.CollisionEvent
import nz.net.ultraq.redhorizon.engine.scripts.Script
import nz.net.ultraq.redhorizon.engine.scripts.ScriptNode
import nz.net.ultraq.redhorizon.graphics.Sprite
import nz.net.ultraq.redhorizon.scenegraph.Node
import static nz.net.ultraq.redhorizon.runtime.ScopedValues.RESOURCE_MANAGER

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import static org.lwjgl.glfw.GLFW.*

/**
 * The player/paddle character.
 *
 * @author Emanuel Rabina
 */
class Paddle extends Node<Paddle> {

	private static final Logger logger = LoggerFactory.getLogger(Paddle)
	static final float SPEED = 300f

	private final float width
	private final float height

	/**
	 * Constructor, set up the paddle entity.
	 */
	Paddle() {

		var resourceManager = RESOURCE_MANAGER.get()

		var paddleImage = resourceManager.loadImage('paddle.png')
		width = paddleImage.width
		height = paddleImage.height
		addChild(new Sprite(paddleImage))
		addChild(new BoxCollider(width, height))
		addChild(new ScriptNode(PaddleScript))
	}

	/**
	 * Paddle movement and behaviour.
	 */
	static class PaddleScript extends Script<Paddle> {

		private float leftBounds
		private float rightBounds

		@Override
		void init() {

			leftBounds = -((Breakout.WIDTH / 2f) - (node.width / 2f)) as float
			rightBounds = -leftBounds

			node.findByType(BoxCollider).on(CollisionEvent) { event ->
				logger.debug("Collision with {}", event.otherCollider().parent)
			}
		}

		@Override
		void update(float delta) {

			var vector = 0f

			if (input.keyPressed(GLFW_KEY_LEFT) || input.keyPressed(GLFW_KEY_A)) {
				vector = -1f
			}
			else if (input.keyPressed(GLFW_KEY_RIGHT) || input.keyPressed(GLFW_KEY_D)) {
				vector = 1f
			}

			if (vector) {
				var position = node.position
				node.setPosition(
					Math.clamp(position.x() + vector * SPEED * delta as float, leftBounds, rightBounds),
					position.y())
			}
		}
	}
}
