import bossData from "../data/bossData.js";
import CardRenderer from "./CardRenderer.js";

export class BlindRenderer extends CardRenderer {
	// const tagWidth = 204 / 6;
	// const tagHeight = 170 / 5;
	constructor() {
		super("public/BlindChips.png");
		this.gridWidth = 21;
		this.gridHeight = 31;
		this.imageWidth = 714;
		this.imageHeight = 1054;
		this.itemWidth = this.imageWidth / this.gridWidth;
		this.itemHeight = this.imageHeight / this.gridHeight;
	}

	createCard(name) {
		const tag = bossData.get(name);
		return this.createCardWithCoordenates({
			x: tag.x,
			y: tag.y
		})
	}
}