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

import nz.net.ultraq.redhorizon.audio.Sound
import nz.net.ultraq.redhorizon.engine.physics.BoxCollider
import nz.net.ultraq.redhorizon.engine.physics.CollisionStartEvent
import nz.net.ultraq.redhorizon.engine.physics.MovementNode
import nz.net.ultraq.redhorizon.engine.scripts.Script
import nz.net.ultraq.redhorizon.engine.scripts.ScriptNode
import nz.net.ultraq.redhorizon.graphics.Sprite
import nz.net.ultraq.redhorizon.scenegraph.Node
import nz.net.ultraq.redhorizon.scenegraph.Scene
import static nz.net.ultraq.redhorizon.runtime.ScopedValues.RESOURCE_MANAGER

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * The ball to bat around.
 *
 * @author Emanuel Rabina
 */
class Ball extends Node<Ball> {

	private static final Logger logger = LoggerFactory.getLogger(Ball)
	private static final String softImpactSoundName = 'Soft impact sound'
	private static final String brickImpactSoundName = 'Brick impact sound'
	static final float speed = 300f

	final float width
	final float height

	Ball() {

		var resourceManager = RESOURCE_MANAGER.get()

		var ballImage = resourceManager.loadImage('Ball.png')
		width = ballImage.width
		height = ballImage.height
		addChild(new Sprite(ballImage))
		addChild(new MovementNode(speed))
		addChild(new BoxCollider(width, height))
		addChild(resourceManager.loadSound('impactSoft_medium_004.ogg')
			.withName(softImpactSoundName))
		addChild(resourceManager.loadSound('impactMining_000.ogg')
			.withName(brickImpactSoundName))
		addChild(new ScriptNode(BallScript))
	}

	/**
	 * Ball behaviour script.
	 */
	static class BallScript extends Script<Ball> {

		private Scene scene

		@Override
		void init() {

			scene = node.scene

			var movement = node.find(MovementNode)
			var softImpactSound = node.<Sound> find(softImpactSoundName)
			var brickImpactSound = node.<Sound> find(brickImpactSoundName)

			node.find(BoxCollider).on(CollisionStartEvent) { event ->
				var otherCollider = event.otherCollider()
				var otherObject = otherCollider.parent
				logger.debug('Collision between ball and {}', otherObject.name)

				// Send the ball back into the middle when hitting an edge
				if (otherObject instanceof ScreenEdges) {
					// TODO: Lose a life when colliding with the bottom edge
					if (otherCollider.name == 'Top edge' || otherCollider.name == 'Bottom edge') {
						movement.vector.y *= -1f
					}
					else if (otherCollider.name == 'Left edge' || otherCollider.name == 'Right edge') {
						movement.vector.x *= -1f
					}
					softImpactSound.play()
				}

				else if (otherObject instanceof Paddle || otherObject instanceof Brick) {
					// Figure out where the objects are relative to each other to know which way to bounce
					// TODO: Allow changing the ball's trajectory based on where it hits the paddle
					var ballPosition = node.position
					var otherBounds = ((BoxCollider)otherCollider).bounds

					if ((ballPosition.x() < otherBounds.minX && movement.vector.x > 0) ||
						(ballPosition.x() > otherBounds.maxX && movement.vector.x < 0)) {
						movement.vector.x *= -1f
						logger.debug("Bounce horizontal")
					}
					if ((ballPosition.y() < otherBounds.minY && movement.vector.y > 0) ||
						(ballPosition.y() > otherBounds.maxY && movement.vector.y < 0)) {
						movement.vector.y *= -1f
						logger.debug("Bounce vertical")
					}

					if (otherObject instanceof Paddle) {
						softImpactSound.play()
					}
					else if (otherObject instanceof Brick) {
						brickImpactSound.play()
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
	}
}
