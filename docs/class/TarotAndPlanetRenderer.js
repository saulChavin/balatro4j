import CardRenderer from './CardRenderer.js';
import tarotsAndPlanetsData from '../data/tarotsAndPlanetsData.js';

export class TarotAndPlanetRenderer extends CardRenderer {
	constructor() {
		super("../public/Tarots_Sprite.png");
		this.imageHeight = 570;
		this.gridWidth = 10;
		this.gridHeight = 6;
		this.itemWidth = this.imageWidth / this.gridWidth;
		this.itemHeight = this.imageHeight / this.gridHeight;
	}

	createCard(name) {
		const tarotOrPlanet = tarotsAndPlanetsData.get(name);
		if (!tarotOrPlanet) {
			throw new Error(`Tarot or Planet with name ${name} not found`);
		}
		
		return this.createCardWithCoordenates({ x: tarotOrPlanet.x, y: tarotOrPlanet.y });
	}
}
