package tests;

import com.balatro.BalatroImpl;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class CollisionTest {

    @Test
    void testCollisions() {
        Set<String> set = new HashSet<>();
        int collisions = 0;
        for (int i = 0; i < 6_000_000; i++) {
            var seed = BalatroImpl.generateRandomSeed();

            if (set.contains(seed)) {
                System.out.println("Collision found: " + seed + " at " + i);
                collisions++;
            } else {
                set.add(seed);
            }
        }

        System.out.println("Total collisions: " + collisions);
        System.out.println("Percentage of collisions: " + ((double) collisions / 100_000_000) * 100 + "%");
    }
}
