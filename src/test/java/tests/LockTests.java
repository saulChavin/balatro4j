package tests;

import com.balatro.Functions;
import com.balatro.enums.Deck;
import com.balatro.enums.LegendaryJoker;
import com.balatro.enums.Stake;
import com.balatro.enums.Version;
import com.balatro.structs.InstanceParams;
import org.junit.jupiter.api.Test;

import static com.balatro.Functions.*;

public class LockTests {

    @Test
    void testLegendaryLock() {
        var functions = new Functions("IGSPUNF", new InstanceParams(Deck.PLASMA_DECK, Stake.White_Stake, false, Version.v_101f.getVersion()));
        functions.lock(LegendaryJoker.Triboulet);
        var data = functions.nextJoker("sou", joker1SouArr, joker2SouArr, joker3SouArr, joker4SouArr, raritySouArr, editionSouArr, 1, true);
        System.out.println(data.joker.getName());
    }
}
