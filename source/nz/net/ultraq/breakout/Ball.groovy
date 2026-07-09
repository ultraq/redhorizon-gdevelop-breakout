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
import nz.net.ultraq.redhorizon.graphics.Sprite
import nz.net.ultraq.redhorizon.scenegraph.Node
import static nz.net.ultraq.redhorizon.runtime.ScopedValues.RESOURCE_MANAGER

import org.joml.Vector2f

/**
 * The ball to bat around.
 *
 * @author Emanuel Rabina
 */
class Ball extends Node<Ball> {

	static final float SPEED = 300f

	final Vector2f vector = new Vector2f()

	Ball() {

		var resourceManager = RESOURCE_MANAGER.get()

		var ballImage = resourceManager.loadImage('Ball.png')
		addChild(new Sprite(ballImage))
		addChild(new ScriptNode(BallScript))
	}

	/**
	 * Ball behaviour script.
	 */
	static class BallScript extends Script<Ball> {

		@Override
		void update(float delta) {

			if (node.vector) {
				node.translate(node.vector.x() * SPEED * delta as float, node.vector.y() * SPEED * delta as float)
			}
		}
	}
}
