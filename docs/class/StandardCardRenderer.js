import CardRenderer from "./CardRenderer.js";
import enhancerData from "../data/enhancersData.js"

const editionMap = {
	"Foil": 1,
	"Holographic": 2,
	"Polychrome": 3
};

function overlayEdition(ctx, canvas, index) {
	const editionImg = new Image();
	editionImg.src = 'public/Editions.png';
	editionImg.onload = function () {
		const editionWidth = editionImg.width / 5;
		const editionHeight = editionImg.height;

		ctx.drawImage(
			editionImg,
			index * editionWidth,
			0,
			editionWidth,
			editionHeight,
			0,
			0,
			canvas.width,
			canvas.height
		);
	};
}

export class StandardCardRenderer extends CardRenderer {
	deckWidth;
	deckHeight;
	constructor() {
		super("public/Enhancers.png");
		this.imageWidth = 497;//enhancer image size
		this.imageHeight = 475;//enhancer image size
		this.deckWidth = 923;//deck image size
		this.deckHeight = 380;//deck image size
		this.gridWidth = 7;
		this.gridHeight = 5;
		this.cardWidth = 71;
		this.cardHeight = 95;
	}

	//structure of name example:  "name": "PurpleSeal 3 of Spades"
	createCard(name) {
		const { rank, suit, modifiers, seal } = this.parseStandardCardName(name);
		const modifier = modifiers.pop();
		const enhancerPos = enhancerData.get(modifier) || { x: 1, y: 0 }
		const card = this.createCardWithCoordenates({ x: enhancerPos.x, y: enhancerPos.y });
		card.classList.add('standard-card');

		const rankSuitPos = this.getStandardCardPosition(rank, suit);
		const canvas = document.createElement('canvas');

		const ctx = canvas.getContext('2d');

		const deckImg = new Image();
		deckImg.src = "public/8BitDeck.png";

		deckImg.onload = () => {
			if (modifier !== "Stone") {

				canvas.width = this.cardWidth;
				canvas.height = this.cardHeight;
				canvas.id = 'card-canvas';
				ctx.drawImage(
					deckImg,
					rankSuitPos.x * (this.deckWidth / 13),
					rankSuitPos.y * (this.deckHeight / 4),
					this.deckWidth / 13,
					this.deckHeight / 4,
					0,
					0,
					this.cardWidth,
					this.cardHeight
				);
			}

			const edition = modifiers.find(mod => ["Foil", "Holographic", "Polychrome"].includes(mod));
			if (edition) {
				overlayEdition(ctx, canvas, editionMap[edition]);
			}

			// Draw seal
			if (seal) {
				const sealImg = new Image();
				sealImg.src = "public/Enhancers.png";
				sealImg.onload = () => {
					// console.log('seal', seal);
					const sealPos = this.getSealPosition(seal);
					console.log('sealPos', sealPos)
					ctx.drawImage(
						sealImg,
						sealPos.x * (this.imageWidth / 7),
						sealPos.y * (this.imageHeight / 5),
						this.imageWidth / 7,
						this.imageHeight / 5,
						0,
						0,
						this.cardWidth,
						this.cardHeight
					);
				}
			}
		}

		card.appendChild(canvas);

		return card;
	}

	parseStandardCardName(cardName) {
		const sealRegex = /(Purple|Red|Blue|Gold)Seal/;
		const sealMatch = cardName.match(sealRegex);
		const seal = sealMatch ? sealMatch[0] : null;

		let cleanedCardName = seal ? cardName.replace(sealRegex, '').trim() : cardName;

		const modifierRegex = /(Foil|Holographic|Polychrome|Bonus|Mult|Wild|Glass|Steel|Stone|Gold|Lucky)/g;
		const modifiers = cleanedCardName.match(modifierRegex) || [];

		// Remove all modifiers from the cleaned card name
		cleanedCardName = cleanedCardName.replace(modifierRegex, '').trim();

		const parts = cleanedCardName.split(' of ');
		if (parts.length !== 2) {
			console.error('Invalid card name format:', cardName);
			return null;
		}

		const suit = parts[1].trim();
		const rankPart = parts[0].trim();
		const rank = rankPart.split(' ').pop(); // Get the last word as rank

		return { rank, suit, modifiers, seal };
	}

	getStandardCardPosition(rank, suit) {
		const rankMap = {
			'2': 0, '3': 1, '4': 2, '5': 3, '6': 4, '7': 5, '8': 6, '9': 7, '10': 8, 'Jack': 9, 'Queen': 10, 'King': 11, 'Ace': 12
		};
		const suitMap = {
			'Hearts': 0, 'Clubs': 1, 'Diamonds': 2, 'Spades': 3
		};

		const x = rankMap[rank];
		const y = suitMap[suit];

		return { x, y };
	}

	getSealPosition(seal) {
		const sealMap = {
			'GoldSeal': { x: 2, y: 0 },
			'PurpleSeal': { x: 4, y: 4 },
			'RedSeal': { x: 5, y: 4 },
			'BlueSeal': { x: 6, y: 4 }
		};

		return sealMap[seal];
	}


}