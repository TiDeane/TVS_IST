package prr.core;

/**
* This class represents a communication (text or voice) made between
* two terminals.
**/
public class Communication {

  public enum CommunicationType { SMS, VOICE }

  private Communication(CommunicationType type, Terminal to, Terminal from) {
    // TODO: implement this
  }

  public static Communication textCommunication(Terminal to, Terminal from, int length) {
    // TODO: implement this
    return null;
  }

  public static Communication textCommunication(Terminal to, Terminal from) {
    // TODO: implement this
    return null;
  }

  public void duration(int duration) {
    // TODO: implement this
    return;
  }

  public Terminal to() {
    // TODO: implement this
    return null;
  }

  public Terminal from() {
    // TODO: implement this
    return null;
  }

  double computeCost() {
    // TODO: implement this
    return -1.0;
  }

  public double getCost() {
    // TODO: implement this
    return -1.0;
  }
}

