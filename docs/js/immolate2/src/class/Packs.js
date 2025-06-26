import { PackType } from '../enums/packs/PackType.js';
import { PackKind } from '../enums/packs/PackKind.js';
export class Pack {
	static VALUES = {
		[PackType.RETRY]: 22.42,
		[PackType.ARCANA_PACK]: 4,
		[PackType.JUMBO_ARCANA_PACK]: 2,
		[PackType.MEGA_ARCANA_PACK]: 0.5,
		[PackType.CELESTIAL_PACK]: 4,
		[PackType.JUMBO_CELESTIAL_PACK]: 2,
		[PackType.MEGA_CELESTIAL_PACK]: 0.5,
		[PackType.STANDARD_PACK]: 4,
		[PackType.JUMBO_STANDARD_PACK]: 2,
		[PackType.MEGA_STANDARD_PACK]: 0.5,
		[PackType.BUFFOON_PACK]: 1.2,
		[PackType.JUMBO_BUFFOON_PACK]: 0.6,
		[PackType.MEGA_BUFFOON_PACK]: 0.15,
		[PackType.SPECTRAL_PACK]: 0.6,
		[PackType.JUMBO_SPECTRAL_PACK]: 0.3,
		[PackType.MEGA_SPECTRAL_PACK]: 0.07
	};

	constructor(name, value) {
		this.name = name;
		this.value = value;
	}

	getName() {
		return this.name;
	}

	getValue() {
		return this.value;
	}

	getKind() {
		if (this.isArcana()) return PackKind.ARCANA;
		if (this.isCelestial()) return PackKind.CELESTIAL;
		if (this.isStandard()) return PackKind.STANDARD;
		if (this.isBuffoon()) return PackKind.BUFFOON;
		if (this.isSpectral()) return PackKind.SPECTRAL;
		throw new Error(`Invalid pack type: ${this.name}`);
	}

	isMega() {
		return this.name.includes("Mega");
	}

	isJumbo() {
		return this.name.includes("Jumbo");
	}

	isStandard() {
		return this.name.includes("Standard");
	}

	isCelestial() {
		return this.name.includes("Celestial");
	}

	isArcana() {
		return this.name.includes("Arcana");
	}

	isBuffoon() {
		return this.name.includes("Buffoon");
	}

	isSpectral() {
		return this.name.includes("Spectral");
	}
	getPacks() {
		return Pack.VALUES;
	}
}
