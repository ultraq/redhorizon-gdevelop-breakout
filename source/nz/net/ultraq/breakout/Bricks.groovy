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

import nz.net.ultraq.redhorizon.scenegraph.Node

/**
 * A holder and generator of the bricks to hit.
 *
 * @author Emanuel Rabina
 */
class Bricks extends Node<Bricks> {

	private static final float gap = 10f

	Bricks() {

		createBricks()
	}

	/**
	 * Create the initial brick layout.
	 */
	private void createBricks() {

		// TODO: Some kind of brick layout system?
		var bricksX = 10
		var bricksY = 4
		var brick = new Brick()
		var totalWidth = (brick.width * bricksX) + (gap * (bricksX - 1))

		bricksY.times { y ->
			var row = new Node()
			bricksX.times { x ->
				row.addChild(new Brick()
					.translate(x * (brick.width + (x ? gap : 0)) as float, 0f)
				)
			}
			addChild(row.translate(
				(brick.width / 2) - (totalWidth / 2) as float,
				(Breakout.HEIGHT / 2f) - (y * (brick.height + gap)) - brick.height as float
			))
		}
	}

	/**
	 * Reset to the initial brick layout.
	 */
	void reset() {

		scene.queueUpdate { ->
			clear()
			createBricks()
		}
	}
}
