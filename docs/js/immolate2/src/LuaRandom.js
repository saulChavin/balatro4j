export class LuaRandom {
    static MAX_UINT64 = 9223372036854775807n; // Long.MAX_VALUE

    static main() {
        console.log(LuaRandom.random(0.0));
    }

    static _randInt(seed) {
        let state;
        let randint = 0n;

        let r = 0x11090601;

        // State[0]
        let m = 1n << BigInt(r & 255);
        r = r >>> 8; // Use regular number for r, not BigInt
        seed = seed * 3.14159265358979323846;
        seed = seed + 2.7182818284590452354;

        let bits = this._doubleToLongBits(seed);

        if (bits < m) {
            bits += m;
        }

        state = bits;

        for (let i = 0; i < 5; i++) {
            state = this._unsignedRightShift(((state << 31n) ^ state), 45n) ^ ((state & (this.MAX_UINT64 << 1n)) << 18n);
            state = this._unsignedRightShift(((state << 31n) ^ state), 45n) ^ ((state & (this.MAX_UINT64 << 1n)) << 18n);
        }

        state = this._unsignedRightShift(((state << 31n) ^ state), 45n) ^ ((state & (this.MAX_UINT64 << 1n)) << 18n);

        randint ^= state;

        // State[1]
        m = 1n << BigInt(r & 255);
        r = r >>> 8;
        seed = seed * 3.14159265358979323846;
        seed = seed + 2.7182818284590452354;

        bits = this._doubleToLongBits(seed);

        if (bits < m) {
            bits += m;
        }

        state = bits;

        for (let i = 0; i < 5; i++) {
            state = this._unsignedRightShift(((state << 19n) ^ state), 30n) ^ ((state & (this.MAX_UINT64 << 6n)) << 28n);
            state = this._unsignedRightShift(((state << 19n) ^ state), 30n) ^ ((state & (this.MAX_UINT64 << 6n)) << 28n);
        }

        state = this._unsignedRightShift(((state << 19n) ^ state), 30n) ^ ((state & (this.MAX_UINT64 << 6n)) << 28n);

        randint ^= state;

        // State[2]
        m = 1n << BigInt(r & 255);
        r = r >>> 8;
        seed = seed * 3.14159265358979323846;
        seed = seed + 2.7182818284590452354;

        bits = this._doubleToLongBits(seed);

        if (bits < m) {
            bits += m;
        }

        state = bits;

        for (let i = 0; i < 5; i++) {
            state = this._unsignedRightShift(((state << 24n) ^ state), 48n) ^ ((state & (this.MAX_UINT64 << 9n)) << 7n);
            state = this._unsignedRightShift(((state << 24n) ^ state), 48n) ^ ((state & (this.MAX_UINT64 << 9n)) << 7n);
        }

        state = this._unsignedRightShift(((state << 24n) ^ state), 48n) ^ ((state & (this.MAX_UINT64 << 9n)) << 7n);

        randint ^= state;

        // State[3]
        m = 1n << BigInt(r & 255);
        seed = seed * 3.14159265358979323846;
        seed = seed + 2.7182818284590452354;

        bits = this._doubleToLongBits(seed);

        if (bits < m) {
            bits += m;
        }

        state = bits;

        for (let i = 0; i < 5; i++) {
            state = this._unsignedRightShift(((state << 21n) ^ state), 39n) ^ ((state & (this.MAX_UINT64 << 17n)) << 8n);
            state = this._unsignedRightShift(((state << 21n) ^ state), 39n) ^ ((state & (this.MAX_UINT64 << 17n)) << 8n);
        }

        state = this._unsignedRightShift(((state << 21n) ^ state), 39n) ^ ((state & (this.MAX_UINT64 << 17n)) << 8n);

        randint ^= state;

        return randint;
    }

    static randdblmem(seed) {
        return (this._randInt(seed) & 4503599627370495n) | 4607182418800017408n;
    }

    static random(seed) {
        return this._longBitsToDouble(this.randdblmem(seed)) - 1.0;
    }

    static randint(seed, max) {
        return Math.floor(this.random(seed) * (max + 1));
    }

    // Helper method for unsigned right shift with BigInt
    static _unsignedRightShift(value, shift) {
        // Ensure we're working with unsigned 64-bit values
        const mask = (1n << 64n) - 1n;
        return (value & mask) >> shift;
    }

    // Helper methods to convert between double and long bits
    static _doubleToLongBits(value) {
        const buffer = new ArrayBuffer(8);
        const floatView = new Float64Array(buffer);
        const intView = new BigInt64Array(buffer); // Use signed view to match Java

        floatView[0] = value;
        return BigInt.asUintN(64, intView[0]); // Convert to unsigned 64-bit
    }

    static _longBitsToDouble(bits) {
        const buffer = new ArrayBuffer(8);
        const intView = new BigInt64Array(buffer); // Use signed view
        const floatView = new Float64Array(buffer);

        intView[0] = BigInt.asIntN(64, bits); // Convert to signed 64-bit
        return floatView[0];
    }
}

function fract(n) {
    return n % 1;
}

export function pseudohash(seed) {
    let num = 1;
    for (let i = seed.length; i > 0; i--) {
        const charCode = seed.charCodeAt(i - 1);
        num = fract(1.1239285023 / num * charCode * Math.PI + Math.PI * i);
    }
    return isNaN(num) ? NaN : num;
}

const inv_prec = Math.pow(10, 13);
const two_inv_prec = Math.pow(2, 13);
const five_inv_prec = Math.pow(5, 13);

function nextAfter(x, direction) {
    const epsilon = Number.EPSILON;
    return direction > x ? x + epsilon : x - epsilon;
}

export function round13(x) {
    const tentative = Math.floor(x * inv_prec) / inv_prec;
    const truncated = (x * two_inv_prec % 1) * five_inv_prec;

    if (
        tentative !== x &&
        tentative !== nextAfter(x, 1) &&
        truncated % 1 >= 0.5
    ) {
        return (Math.floor(x * inv_prec) + 1) / inv_prec;
    }
    return tentative;
}
// Test
console.log(LuaRandom.random(0.0)); // Should output 0.794206292431241
