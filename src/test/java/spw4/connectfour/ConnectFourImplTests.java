package spw4.connectfour;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConnectFourImplTests {

    ConnectFourImpl cf;

    @BeforeEach
    void setUp() {
        cf = new ConnectFourImpl(Player.red);
    }

    @Test
    void testGetPlayerOnTurn() {
        assertEquals(Player.red, cf.getPlayerOnTurn());
    }

    @Test
    void testGetPlayerAt() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                assertEquals(Player.none, cf.getPlayerAt(row, col));
            }
        }
    }

    @Test
    void testDrop() {
        cf.drop(0); // Red
        cf.drop(1); // Yellow
        assertEquals(Player.red, cf.getPlayerAt(5, 0));
        assertEquals(Player.yellow, cf.getPlayerAt(5, 1));
    }

    @Test
    void testToString() {
        cf.drop(4);
        String expectedBoard =
                "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  R  .  . |\n";
        assertEquals(expectedBoard, cf.toString());
    }

    @Test
    void testCheckWin() {
        cf.drop(4);
        cf.drop(2);
        cf.drop(5);
        cf.drop(4);
        cf.drop(6);
        cf.drop(6);
        cf.drop(3);
        System.out.println(cf.toString());
        assertTrue(cf.isGameOver());
    }

    @DisplayName("CheckWin where diagonal win works but reset is not implemented, game goes on")
    @Test
    void testCheckWinDiagonal() {
        cf.drop(4);
        cf.drop(2);
        cf.drop(5);
        cf.drop(4);
        cf.drop(6);
        cf.drop(0);
        cf.drop(5);
        cf.drop(3);
        cf.drop(4);
        cf.drop(3);
        cf.drop(3);
        cf.drop(1);
        cf.drop(3);
        System.out.println(cf.toString());
        assertTrue(cf.isGameOver());
    }

    @Test
    void testReset() {
        cf.drop(4);
        cf.drop(2);
        cf.drop(5);
        cf.drop(4);
        cf.drop(6);
        cf.drop(0);
        cf.drop(5);
        cf.drop(3);
        cf.drop(4);
        cf.drop(3);
        cf.drop(3);
        cf.drop(1);
        cf.drop(3);
        System.out.println(cf.toString());
        cf.reset(Player.yellow);
        System.out.println(cf.toString());
    }

    @Test
    void testGetWinner() {
        cf.drop(4);
        cf.drop(2);
        cf.drop(5);
        cf.drop(4);
        cf.drop(6);
        cf.drop(0);
        cf.drop(5);
        cf.drop(3);
        cf.drop(4);
        cf.drop(3);
        cf.drop(3);
        cf.drop(1);
        System.out.println(cf.toString());
        assertEquals(Player.yellow, cf.getWinner());
    }
}
