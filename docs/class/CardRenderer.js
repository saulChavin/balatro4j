/**
 * @typedef {Object} SpriteFrame
 * @property {number} x - X coordinate of the sprite
 * @property {number} y - Y coordinate of the sprite
 */

/**
 * Class that handles rendering of cards using sprite sheets
 */
export default class CardRenderer {
	/**
	 * @param {string} srcImage - Source path of the sprite sheet image
	 */
	constructor(srcImage) {
		this.imageWidth = 710;
		this.imageHeight = 1520;
		this.gridWidth = 10;
		this.gridHeight = 16;
		this.itemWidth = this.imageWidth / this.gridWidth;
		this.itemHeight = this.imageHeight / this.gridHeight;
		this.srcImage = srcImage;
		this.pos = {x: 0, y: 0};
	}

	/**
	 * Creates a card DOM element with the specified sprite frame
	 * @param {SpriteFrame} spriteFrame - Coordinates of the sprite in the sheet
	 * @returns {HTMLDivElement} - The created card element
	 */
	createCardWithCoordenates(spriteFrame) {
		const card = document.createElement('div');
		card.style.position = 'relative';
		card.style['min-width'] = `${this.itemWidth}px`;
		card.style.width = `${this.itemWidth}px`;
		card.style.height = `${this.itemHeight}px`;
		card.style.overflow = 'hidden';
		card.style.background = 'url(' + this.srcImage + ') no-repeat';
		card.style.backgroundPosition = `-${spriteFrame.x * this.itemWidth}px -${spriteFrame.y * this.itemHeight}px`;
		card.style.backgroundSize = `${this.imageWidth}px ${this.imageHeight}px`;
		return card;
	}
}