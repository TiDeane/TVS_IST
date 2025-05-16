package prr.core;

import prr.core.exceptions.InvalidOperationException;
import java.util.List;
import java.util.ArrayList;

/**
* This class represents a client.
**/

public class Client {
  // maximum of 40 characters
  private String name;
  private int taxNumber;
  // 0 to 200 points
  private int points;
   // 1 to 9 terminals -> should be 0 to 9 now, since Terminal can be null
  private List<Terminal> terminals;
  private List<Client> friends; // maximum of (5 * numberOfTerminals() - 3) friends


  // Creates a client with a single terminal. The provided terminal CAN be null?
  public Client(String name, int taxNumber, Terminal term) {
    if (name.length() > 40 || term == null) {
      throw new IllegalArgumentException();
    }

    this.name = name;
    this.taxNumber = taxNumber;
    terminals = new ArrayList<>();
    terminals.add(term);
    friends = new ArrayList<>();
    points = 20;
  }

  public void updateName(String name) {
    if (name.length() > 40) {
      throw new InvalidOperationException();
    }
    this.name = name;
  }

  public String getName() {
    return name;
  }

  // updates the number of points of the client. It can be a positive or negative number.
  public void updatePoints(int p) {
    if (p < 0  || p > 200) {
      throw new InvalidOperationException();
    }
    this.points = p;
  }

  public int getPoints() {
    return points;
  }

  public void addFriend(Client c) {
    if ((friends.size() >= (5 * numberOfTerminals() - 3))) {
      throw new InvalidOperationException();
    }

    if (c == this) {
      throw new InvalidOperationException();
    }
  
    friends.add(c);
  }

  public boolean removeFriend(Client c) {
    return friends.remove(c);
  }

  public boolean hasFriend(Client c) {
    return friends.contains(c);
  }

  public int numberOfFriends(){
    return friends.size();
  }

  public void addTerminal(Terminal terminal) {
    if (numberOfTerminals() >= 9) {
      throw new InvalidOperationException();
    }

    terminals.add(terminal);
  }

  public boolean removeTerminal(Terminal terminal) {
    // Q: should exception be thrown if terminal balance is negative?
    if (!terminals.contains(terminal) || terminal.balance() < 0) {
      return false;
    }

    if (numberOfTerminals() <= 1) {
      throw new InvalidOperationException();
    }

    // number of friends must remain valid after removing the terminal
    if (friends.size() > (5 * (numberOfTerminals() - 1) - 3)) {
      throw new InvalidOperationException();
    }

    return terminals.remove(terminal);
  }

  // returns the number of terminals of this client
  public int numberOfTerminals() {
    return terminals.size();
  }
}
