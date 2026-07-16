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
import nz.net.ultraq.redhorizon.scenegraph.Node

/**
 * Colliders created at the edges of the screen to keep the ball in play.
 *
 * @author Emanuel Rabina
 */
class ScreenEdges extends Node<ScreenEdges> {

	ScreenEdges() {

		addChild(new BoxCollider(Breakout.WIDTH, 1)
			.translate(0f, Breakout.HEIGHT / 2f as float)
			.withName('Top edge'))
		addChild(new BoxCollider(Breakout.WIDTH, 1)
			.translate(0f, -Breakout.HEIGHT / 2f as float)
			.withName('Bottom edge'))
		addChild(new BoxCollider(1, Breakout.HEIGHT)
			.translate(-Breakout.WIDTH / 2f as float, 0f)
			.withName('Left edge'))
		addChild(new BoxCollider(1, Breakout.HEIGHT)
			.translate(Breakout.WIDTH / 2f as float, 0f)
			.withName('Right edge'))
	}
}
