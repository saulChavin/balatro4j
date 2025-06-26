import DeckType from '../enums/DeckType.js';
import StakeType  from '../enums/Stake.js';
import Version  from '../enums/Version.js';

export class InstanceParams {
	constructor(d, s, showman, v) {
		this.deck = d || DeckType.RED_DECK;
		this.stake = s || StakeType.WHITE_STAKE
		this.showman = showman || false;
		this.sixesFactor = 1;
		this.version = v || Version.v_101c.getVersion();
		this.vouchers = new Set();
	}

	getDeck() {
		return this.deck;
	}

	setDeck(deck) {
		this.deck = deck;
	}

	getStake() {
		return this.stake;
	}

	setStake(stake) {
		this.stake = stake;
	}

	isShowman() {
		return this.showman;
	}

	setShowman(showman) {
		this.showman = showman;
	}

	getSixesFactor() {
		return this.sixesFactor;
	}

	setSixesFactor(sixesFactor) {
		this.sixesFactor = sixesFactor;
	}

	getVersion() {
		return this.version;
	}

	setVersion(version) {
		this.version = version;
	}

	getVouchers() {
		return this.vouchers;
	}

	setVouchers(vouchers) {
		this.vouchers = vouchers;
	}
}