import { Lock } from './utils/Lock.js';
import { Cache } from './utils/Cache.js';
import { pseudohash, LuaRandom, round13 } from './LuaRandom.js'
export class Game extends Lock {
	constructor(seed, instanceParams) {
		super();
		this.seed = seed;
		this.params = instanceParams;
		this.cache = new Cache();
		this.hashedSeed = pseudohash(seed);
	}

	getNode(id) {
		var c = this.cache.getNode(id);

		if (c == null) {
			c = pseudohash(id + this.seed);
			this.cache.setNode(id, c);
		}

		var value = round13((c * 1.72431234 + 2.134453429141) % 1);

		this.cache.setNode(id, value);

		return (value + this.hashedSeed) / 2;
	}

	random(id) {
		const rng = new LuaRandom(this.getNode(id));
		return rng.random();
	}

	randint(id, min, max) {
		const rng = new LuaRandom(this.getNode(id));
		return rng.randint(min, max);
	}

	randchoice(id, items) {
		if (!items || items.length === 0) {
			throw new Error('Items array cannot be empty');
		}

		let item = items[this.randint(id, 0, items.length - 1)];

		if (!this.params.isShowman() && (this.isLocked(item) || (typeof item === "string" ? item : item.getName()) === "RETRY")) {
			let resample = 2;
			while (true) {
				item = items[this.randint(`${id}_resample${resample}`, 0, items.length - 1)];
				resample++;
				if ((!this.isLocked(item) && (typeof item === "string" ? item : item.getName()) !== "RETRY") || resample > 1000) {
					return item;
				}
			}
		}
		return item;
	}
}