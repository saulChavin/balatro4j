import CardRenderer from './CardRenderer.js';
import voucherData from '../data/voucherData.js';

export class VoucherRenderer extends CardRenderer {
	constructor() {
		super("public/Vouchers_Sprite.png");
		this.imageHeight = 380;
		this.imageWidth = 639;
		this.gridWidth = 9;
		this.gridHeight = 4;
		this.itemWidth = this.imageWidth / this.gridWidth;
		this.itemHeight = this.imageHeight / this.gridHeight;
	}

	createCard(name) {
		const voucher = voucherData.get(name);
		if (!voucher) {
			throw new Error(`Voucher with name ${name} not found`);
		}
		return this.createCardWithCoordenates({ x: voucher.x, y: voucher.y });
	}
}

