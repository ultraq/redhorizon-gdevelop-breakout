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

		// TODO: Some kind of brick layout system?
		var bricksAcross = 5
		var brick = new Brick()
		var totalWidth = (brick.width * bricksAcross) + (gap * (bricksAcross - 1))

		var row = new Node()
		bricksAcross.times { i ->
			row.addChild(new Brick()
				.translate(i * (brick.width + (i == 0 ? 0 : gap)) as float, Breakout.HEIGHT / 2f - 20f as float)
			)
		}
		addChild(row.translate((brick.width / 2) - (totalWidth / 2) as float, 0f))
	}
}
