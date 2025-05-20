package prr.core;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;
import prr.core.exceptions.InvalidOperationException;

@Test
public class TestClient {
    private Client client;
    private Terminal mockTerminal;

    @BeforeMethod
    private void setup() {
        Client mockOwner = new Client("Mock Owner", 99999, null);
        mockTerminal = new Terminal("123", mockOwner);
        client = new Client("Test Client", 12345, mockTerminal);
    }

    @DataProvider
    private Object[][] provideTestCases() {
        return new Object[][] {
                // Test Cases 1-12 with respective data
                // {namelength, numberOfTerminals , points, numberOfFriends, expected result}
                {40, 2, 1, 0, true},  // Case 1
                {41, 3, 2, 2, false}, // Case 2
                {0, 1, 3, 1, true},   // Case 3
                {1, 0, 4, 3, false},  // Case 4
                {2, 9, 5, 4, true},   // Case 5
                {3, 10, 6, 5, false}, // Case 6
                {4, 4, 0, 6, true},  // Case 7
                {5, 6, -1, 7, false},// Case 8
                {6, 7, 200, 8, true}, // Case 9
                {7, 8, 201, 9, false}, // Case 10
                {8, 5, 7, 22, true},   // Case 11
                {9, 5, 8, 23, false}  // Case 12
        };
    }

    @Test(dataProvider = "provideTestCases")
    public void testAllCases(int nameLength, int terminals, int points, int friends, boolean expectedResult) {
        boolean isValid = true;
        try{
            // Setup client with initial name
            String name = "a".repeat(nameLength);
            Client testClient = new Client(name, 12345, mockTerminal);

            // Adjust number of terminals
            for (int i = 2; i <= terminals; i++) {
                testClient.addTerminal(new Terminal(String.valueOf(i), testClient));
            }
            if (terminals == 0) {
                testClient.removeTerminal(mockTerminal);
            }

            // Set points
            try {
                testClient.updatePoints(points);
            } catch (Exception e) {
                throw new Exception("Unexpected exception for valid points");
            }

            // Add friends
            for (int i = 0; i < friends; i++) {
                Client friend = new Client("Friend" + i, 10000 + i, new Terminal("F" + i, testClient));
                try {
                    testClient.addFriend(friend);
                } catch (Exception e) {
                    throw new Exception("Unexpected exception for valid friends");
                }
            }

        } catch (Exception e){
            isValid = false;
        }

        assertEquals(isValid, expectedResult, "Test case failed for nameLength=" + nameLength + ", terminals=" + terminals + ", points=" + points + ", friends=" + friends);
    }
}