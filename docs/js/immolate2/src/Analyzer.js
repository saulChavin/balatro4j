
//jsdoc for this class
/** 		
 * @class Analyzer
 * @description This class contain all logic to get the content of seeds and analyze with the (serach parameters given //TODO).
 * @property {number} ante - The ante value used for the analysis.
 * @property {Array} cardsPerAnte - An array of cards per ante to be analyzed.
 * @static
 * @staticOptions {Array} OPTIONS - A static array of options that can be used for analysis.
 * @example
 * const analyzer = new Analyzer(100, ['Card1', 'Card2', 'Card3']);
 * analyzer.performAnalysis();
 * @throws {Error} If the ante is greater than the length of cardsPerAnte array.
 * @throws {Error} If the cardsPerAnte array does not have enough elements for the given ante.
 * @returns {void}
 * @memberof Analyzer
 * @since 1.0.0
 * @version 1.0.0
 */
export class Analyzer {
	static OPTIONS = Object.freeze([
		// Tags
		"Negative Tag",
		"Foil Tag",
		"Holographic Tag",
		"Polychrome Tag",
		"Rare Tag",

		// Special Cards
		"Golden Ticket",

		// Characters
		"Mr. Bones",
		"Acrobat",
		"Sock and Buskin",
		"Swashbuckler",
		"Troubadour",

		// Items & Certificates
		"Certificate",
		"Smeared Joker",
		"Throwback",
		"Hanging Chad",

		// Gems & Materials
		"Rough Gem",
		"Bloodstone",
		"Arrowhead",
		"Onyx Agate",
		"Glass Joker",

		// Performance & Entertainment
		"Showman",
		"Flower Pot",
		"Blueprint",
		"Wee Joker",
		"Merry Andy",

		// Special Effects
		"Oops! All 6s",
		"The Idol",
		"Seeing Double",
		"Matador",
		"Hit the Road",

		// Card Sets
		"The Duo",
		"The Trio",
		"The Family",
		"The Order",
		"The Tribe",

		// Special Characters
		"Stuntman",
		"Invisible Joker",
		"Brainstorm",
		"Satellite",
		"Shoot the Moon",

		// Licenses & Professions
		"Driver's License",
		"Cartomancer",
		"Astronomer",
		"Burnt Joker",
		"Bootstraps",

		// Shop & Economy
		"Overstock Plus",
		"Liquidation",
		"Glow Up",
		"Reroll Glut",
		"Omen Globe",

		// Tools & Equipment
		"Observatory",
		"Nacho Tong",
		"Recyclomancy",

		// Merchants
		"Tarot Tycoon",
		"Planet Tycoon",

		// Special Items
		"Money Tree",
		"Antimatter",
		"Illusion",
		"Petroglyph",
		"Retcon",
		"Palette"
	]);
	ante;
	cardsPerAnte;

	constructor(ante, cardsPerAnte) {
		this.ante = ante;
		this.cardsPerAnte = cardsPerAnte;
	}

	performAnalysis() {
		// Placeholder for analysis logic
		console.log(`Analyzing ante: ${this.ante} with ${this.cardsPerAnte} cards per ante.`);
		// Implement analysis logic here

		if (this.ante > this.cardsPerAnte.length) {
			throw new Error(`cardsPerAnte array does not have enough elements for ante ${ante}`);
		}
	}
}