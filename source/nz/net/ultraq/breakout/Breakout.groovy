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

import nz.net.ultraq.redhorizon.runtime.Application
import nz.net.ultraq.redhorizon.runtime.Runtime
import nz.net.ultraq.redhorizon.scenegraph.Scene

/**
 * Entry point to the Breakout game.
 *
 * @author Emanuel Rabina
 */
class Breakout extends Application {

	public static final int WIDTH = 960
	public static final int HEIGHT = 540

	static void main(String[] args) {

		System.exit(new Runtime(new Breakout()).execute(
			'--window-background-colour=#344055',
			"--window-width=${WIDTH}",
			"--window-height=${HEIGHT}",
			"--framebuffer-width=${WIDTH * 2}",
			"--framebuffer-height=${HEIGHT * 2}",
			'--simulation-update-frequency=120',
			'--resource-manager-path-prefix=nz/net/ultraq/breakout/assets',
			*args
		))
	}

	/**
	 * Constructor, create a new Breakout game.
	 */
	Breakout() {

		super('Breakout', '0.1.0-dev')
	}

	@Override
	protected Scene configureScene(Scene scene) {

		return scene
			.addChild(new ScreenEdges())
			.addChild(new PaddleLine().translate(0f, -HEIGHT / 2f + 20f as float).scale(0.5f))
			.addChild(new Bricks())
			.addChild(new Ball())
			.addChild(new Paddle().translate(0f, -HEIGHT / 2f + 20f as float))
			.addChild(new GameFlowController())
	}
}
