import { JokerType } from "../enum/JokerType.js";

export class Joker {
	constructor(type, name, eternal = false, prehisable = false, rental = false) {
		this.type = type;
		this.name = name;
		this.eternal = eternal;
		this.prehisable = prehisable;
		this.rental = rental;
	}

	getType() {
		return this.type;
	}

	isRare() {
		return this.type === JokerType.RARE;
	}

	isCommon() {
		return this.type === JokerType.COMMON;
	}

	isUncommon() {
		return this.type === JokerType.UNCOMMON;
	}

	isLegendary() {
		return this.type === JokerType.LEGENDARY;
	}

	getName() {
		return this.name;
	}

	isEternal() {
		return this.eternal;
	}

	isPrehisable() {
		return this.prehisable;
	}

	isRental() {
		return this.rental;
	}

	eq(item) {
		return this.getName() === item.getName();
	}

	equals(value) {
		return this.getName() === value;
	}
}