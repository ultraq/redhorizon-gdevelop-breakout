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
import nz.net.ultraq.redhorizon.engine.scripts.Script
import nz.net.ultraq.redhorizon.engine.scripts.ScriptNode
import nz.net.ultraq.redhorizon.graphics.Sprite
import nz.net.ultraq.redhorizon.scenegraph.Node
import nz.net.ultraq.redhorizon.scenegraph.Scene
import static nz.net.ultraq.redhorizon.runtime.ScopedValues.RESOURCE_MANAGER

import org.joml.Vector2f
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * The ball to bat around.
 *
 * @author Emanuel Rabina
 */
class Ball extends Node<Ball> {

	private static final Logger logger = LoggerFactory.getLogger(Ball)

	static final float SPEED = 600f

	final float width
	final float height
	final Vector2f vector = new Vector2f()

	Ball() {

		var resourceManager = RESOURCE_MANAGER.get()

		var ballImage = resourceManager.loadImage('Ball.png')
		width = ballImage.width
		height = ballImage.height
		addChild(new Sprite(ballImage))
		addChild(new BoxCollider(width, height))
		addChild(new ScriptNode(BallScript))
	}

	/**
	 * Ball behaviour script.
	 */
	static class BallScript extends Script<Ball> {

		private Scene scene
		private float halfWidth
		private float halfHeight

		@Override
		void init() {

			scene = node.scene
			halfWidth = node.width / 2f as float
			halfHeight = node.height / 2f as float

			node.findByType(BoxCollider).on(CollisionStartEvent) { event ->
				var otherCollider = event.otherCollider()
				var otherObject = otherCollider.parent
				logger.debug('Collision between ball and {}', otherObject.name)

				// Send the ball back into the middle when hitting an edge
				if (otherObject instanceof ScreenEdges) {
					// TODO: Lose a life when colliding with the bottom edge
					if (otherCollider.name == 'Top edge' || otherCollider.name == 'Bottom edge') {
						node.vector.y *= -1f
					}
					else if (otherCollider.name == 'Left edge' || otherCollider.name == 'Right edge') {
						node.vector.x *= -1f
					}
				}

				else if (otherObject instanceof Paddle || otherObject instanceof Brick) {
					// Figure out where the objects are relative to each other to know which way to bounce
					// TODO: Allow changing the ball's trajectory based on where it hits the paddle
					var ballPosition = node.position
					var otherBounds = ((BoxCollider)otherCollider).bounds

					if ((ballPosition.x() < otherBounds.minX && node.vector.x > 0) ||
						(ballPosition.x() > otherBounds.maxX && node.vector.x < 0)) {
						node.vector.x *= -1f
						logger.debug("Bounce horizontal")
					}
					if ((ballPosition.y() < otherBounds.minY && node.vector.y > 0) ||
						(ballPosition.y() > otherBounds.maxY && node.vector.y < 0)) {
						node.vector.y *= -1f
						logger.debug("Bounce vertical")
					}

					if (otherObject instanceof Brick) {
						scene.queueUpdate { ->
							otherObject.remove()
						}
					}
				}

				// Log anything else for now
				else {
					logger.debug("Unhandled collision with {}", otherObject)
				}
			}
		}

		@Override
		void update(float delta) {

			// Move the ball along its current trajectory
			if (node.vector) {
				node.translate(node.vector.x() * SPEED * delta as float, node.vector.y() * SPEED * delta as float)
			}
		}
	}
}
