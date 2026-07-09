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

	static final int WIDTH = 800
	static final int HEIGHT = 500

	static void main(String[] args) {

		System.exit(new Runtime(new Breakout()).execute(
			"--window-width=${WIDTH}",
			"--window-height=${HEIGHT}",
			'--resource-manager-path-prefix=nz/net/ultraq/breakout/assets',
			*args
		))
	}

	/**
	 * Constructor, create a new Breakout game.
	 */
	Breakout() {

		super('Breakout', '0.1.0')
	}

	@Override
	protected Scene configureScene(Scene scene) {

		return scene
			.addChild(new Paddle().translate(0f, -HEIGHT / 2f + 10f as float))
	}
}
