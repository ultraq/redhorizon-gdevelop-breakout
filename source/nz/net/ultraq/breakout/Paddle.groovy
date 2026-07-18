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
import nz.net.ultraq.redhorizon.engine.physics.CollisionStartEvent
import nz.net.ultraq.redhorizon.engine.physics.MovementNode
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
	static final float speed = 300f

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
		addChild(new MovementNode(speed))
		addChild(new BoxCollider(width, height))
		addChild(new ScriptNode(PaddleScript))
	}

	/**
	 * Paddle movement and behaviour.
	 */
	static class PaddleScript extends Script<Paddle> {

		private float leftBounds
		private float rightBounds
		private MovementNode movement

		@Override
		void init() {

			leftBounds = -((Breakout.WIDTH / 2f) - (node.width / 2f)) as float
			rightBounds = -leftBounds
			movement = node.find(MovementNode)

			node.find(BoxCollider).on(CollisionStartEvent) { event ->
				var otherCollider = event.otherCollider()
				var otherObject = otherCollider.parent
				logger.debug('Collision between paddle and {}', otherObject.name)

				// Prevent the paddle from moving outside of the screen
				if (otherObject instanceof ScreenEdges) {
					movement.vector.x = 0
				}
			}
		}

		@Override
		void update(float delta) {

			if (input.keyPressed(GLFW_KEY_LEFT) || input.keyPressed(GLFW_KEY_A)) {
				movement.vector.x = -1f
			}
			else if (input.keyPressed(GLFW_KEY_RIGHT) || input.keyPressed(GLFW_KEY_D)) {
				movement.vector.x = 1f
			}
			else {
				movement.vector.x = 0f
			}
		}
	}
}
