export class Cache {
    constructor() {
        this.nodes = new Map();
        this.generatedFirstPack = false;
    }

    isGeneratedFirstPack() {
        return this.generatedFirstPack;
    }

    setGeneratedFirstPack(generatedFirstPack) {
        this.generatedFirstPack = generatedFirstPack;
    }

    getNode(key) {
        return this.nodes.get(key);
    }

    setNode(key, value) {
        this.nodes.set(key, value);
    }
}