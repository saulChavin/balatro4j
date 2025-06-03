import CardRenderer from './CardRenderer.js';
import tarotsAndPlanetsData from '../data/tarotsAndPlanetsData.js';
import enhancersData from '../data/enhancersData.js';

const LEGENDARIES_NAMES = [
	"Perkeo",
	"Triboulet",
	"Chicot",
	"Canio",
	"Yorick",
	"The Soul",
]

export class TarotAndPlanetRenderer extends CardRenderer {
	constructor() {
		super("public/Tarots_Sprite.png");
		this.imageHeight = 570;
		this.gridWidth = 10;
		this.gridHeight = 6;
		this.itemWidth = this.imageWidth / this.gridWidth;
		this.itemHeight = this.imageHeight / this.gridHeight;
	}

	overlayTheSoulStone(ctx, canvas) {
		const soulStone = enhancersData.get("Soul");
		const enhancersImg = new Image();
		enhancersImg.src = "public/Enhancers.png";

		enhancersImg.onload = () => {
			ctx.drawImage(
				enhancersImg,
				soulStone.x * this.itemWidth,
				soulStone.y * this.itemHeight,
				this.itemWidth,
				this.itemHeight,
				0,
				0,
				this.itemWidth,
				this.itemHeight
			);

			return canvas;
		}

	}

	createCard(name) {
		const tarotOrPlanet = tarotsAndPlanetsData.get(name);
		if (!tarotOrPlanet) {
			throw new Error(`Tarot or Planet with name ${name} not found`);
		}

		if (LEGENDARIES_NAMES.includes(name)) {
			const card = this.createCardWithCoordenates({ x: tarotOrPlanet.x, y: tarotOrPlanet.y });
			card.classList.add('legendary')
			const canvas = document.createElement('canvas');
			canvas.id = 'soul-stone-overlay';
			const ctx = canvas.getContext('2d');

			this.overlayTheSoulStone(ctx, canvas);

			card.appendChild(canvas);
			return card;
		}

		return this.createCardWithCoordenates({ x: tarotOrPlanet.x, y: tarotOrPlanet.y });
	}
}
