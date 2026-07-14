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
import nz.net.ultraq.redhorizon.engine.scripts.Script
import nz.net.ultraq.redhorizon.engine.scripts.ScriptNode
import nz.net.ultraq.redhorizon.graphics.Sprite
import nz.net.ultraq.redhorizon.scenegraph.Node
import static nz.net.ultraq.redhorizon.runtime.ScopedValues.RESOURCE_MANAGER

/**
 * A single brick.
 *
 * @author Emanuel Rabina
 */
class Brick extends Node<Brick> {

	final int width
	final int height

	Brick() {

		var resourceManager = RESOURCE_MANAGER.get()
		var brickImage = resourceManager.loadImage('Block_White.png')
		width = brickImage.width
		height = brickImage.height
		addChild(new Sprite(brickImage))
		addChild(new BoxCollider(width, height))
		addChild(new ScriptNode(BrickScript))
	}

	static class BrickScript extends Script<Brick> {

		@Override
		void init() {
		}

		@Override
		void update(float delta) {
		}
	}
}
