package prr.core;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.*;
import prr.core.exceptions.InvalidOperationException;

@Test
public class TestClient {
    private Terminal baseTerminal;

    @BeforeMethod private void setup() {
        baseTerminal = new Terminal("1234");
    }

    /**
     * Chosen SUCCESS test cases: 1, 5, 7 and 11
     **/

    @DataProvider
    public Object[][] computeDataForValidClient() {
        return new Object[][] {
            // name, numTerminals, points, numFriends
            { "A".repeat(40), 2, 1, 2 },  // ON: name length = 40
            { "12", 9, 5, 4 },            // ON: numTerminals = 9
            { "1234", 4, 0, 10 },         // ON: points = 0
            { "12345678", 5, 7, 22 }      // ON: numFriends = 5 * numTerminals - 3
        };
    }

    @Test(dataProvider = "computeDataForValidClient")
    public void testValidClient(String name, int numTerminals, int points, int numFriends) {
        // Arrange
        Client client;
        
        // Act
        client = new Client(name, 12345, baseTerminal);

        client.updateName(name);
        client.updatePoints(points);
        Terminal terminal;
        for (int i = 1; i < numTerminals; i++) {
            terminal = new Terminal("T" + i);
            client.addTerminal(terminal);
            terminal.setClient(client);
        }

        for (int i = 0; i < numFriends; i++) {
            client.addFriend(new Client("F" + i, 1, new Terminal("TF" + i)));
        }
        
        // Assert
        assertEquals(client.getName(), name);
        assertEquals(client.getPoints(), points);
        assertEquals(client.numberOfTerminals(), numTerminals);
        assertEquals(client.numberOfFriends(), numFriends);
    }


    /**
     * Chosen FAILURE test cases: 2, 4, 10 and 12
     **/

    @Test // Test case 2
    public void testClientWithNameTooLong() {
        // Arrange
        String name = "A".repeat(41); // Off point

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Client(name, 12345, baseTerminal);
        });
        assertThrows(InvalidOperationException.class, () -> {
            (new Client("A", 12345, baseTerminal)).updateName(name);
        });
    }

    @Test // Test case 4
    public void testClientWithNoTerminals() {
        // Arrange
        String name = "A";
        Client client = new Client(name, 12345, baseTerminal);

        // Act + Assert
        assertThrows(InvalidOperationException.class, () -> {
            client.removeTerminal(baseTerminal); // numTerminals = 0 is Off point
        });
    }

    @Test // Test case 10
    public void testClientWithTooManyPoints() {
        // Arrange
        Client client = new Client("1234567", 12345, baseTerminal);

        // Act + Assert
        assertThrows(InvalidOperationException.class, () -> {
            client.updatePoints(201); // Off point
        });
    }

    @Test // Test case 12
    public void testClientWithTooManyFriends() {
        // Arrange
        Client client = new Client("123456789", 12345, baseTerminal);
        int numTerminals = 5;
        int numFriends = 23; // Off point with numTerminals = 5
        Terminal  terminal ;
        for (int i = 1; i < numTerminals; i++) {
            terminal = new Terminal("TF" + i);
            client.addTerminal(terminal);

        }

        // Act + Assert
        assertThrows(InvalidOperationException.class, () -> {
            for (int i = 0; i < numFriends; i++) { // Off point: 23 > 5*5 - 3 = 22
                client.addFriend(new Client("F" + i, 1, new Terminal("TF" + i)));
            }
        });
    }
}