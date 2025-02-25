import CardRenderer from "./CardRenderer.js";
import jokerData from "../data/jokerData.js";

export class JokerRenderer extends CardRenderer {
	constructor() {
		super("../public/Jokers_Sprite.png");
	}

	//add behaviour of the createCard method to render jokers in the sprite sheet
	//but instead of getting the x and y coordinates of the sprite, it will get the name of the joker
	createCard(name) {
		const joker = jokerData.get(name);
		if (!joker) {
			throw new Error(`Joker with name ${name} not found`);
		}
		return this.createCardWithCoordenates({ x: joker.x, y: joker.y });
	}
}