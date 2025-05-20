package prr.core;

import prr.core.exceptions.InvalidInvocationException;

public class Terminal {

  public enum TerminalMode { OFF, NORMAL, SILENT, BUSY }

  private String id;
  private Client client;
  private TerminalMode mode;
  private int balance;
  private TerminalMode previousMode;
  private Communication currentCommunication;

    // creates a terminal with a given identifier and associated to the given client.
    Terminal(String id, Client client) {
      if (id == null) throw new IllegalArgumentException("Invalid id");
      this.id = id;
      this.client = client;
      this.mode = TerminalMode.OFF;
      this.balance = 0;
      this.previousMode = TerminalMode.OFF;
    }

    // Returns the mode of this terminal
    public final TerminalMode getMode() {
      return mode;
    }

    // Decreases the debt of this terminal by the given amount. The amount must be a number greater than 5 cents
    public void pay(int amount) {
      if (mode == TerminalMode.OFF && amount > 5) {
        balance -= amount;
      } else {
        throw new InvalidInvocationException("Terminal must be OFF and amount >= 5"); 
      }
    }

    // returns the balance of this terminal
    public int balance() {
      if (mode == TerminalMode.BUSY) {
        throw new InvalidInvocationException("Terminal is Busy"); 
      }
      return balance;
    }

    // send a SMS to terminal to with text msg. Returns if the SMS was successfully delivered.
    public boolean sendSMS(Terminal to, String msg) {
      if (mode == TerminalMode.BUSY || mode == TerminalMode.OFF) {
        throw new InvalidInvocationException("Cannot send SMS in current mode"); 
      }
      if (to.getMode() == TerminalMode.OFF)
        return false; // Q: should this cause exception?
      if (to.getMode() == TerminalMode.SILENT && !to.client.hasFriend(this.client)) {
        return false; // Q: should this cause exception?
      }

      currentCommunication = Communication.textCommunication(to, this, msg.length());

      // maybe this should be before the textCommunication is created?
      to.receiveSMS(this, msg); // TODO: ask professor about text communication logic
      balance += (int) currentCommunication.getCost();
      return true;
    }

    // receives a SMS from terminal from with text msg
    public void receiveSMS(Terminal from, String msg) {
      if (mode == TerminalMode.OFF) {
        throw new InvalidInvocationException("Cannot receive message in OFF mode"); 
      }
      if (mode == TerminalMode.SILENT && !from.getClient().hasFriend(this.client)) {
        throw new InvalidInvocationException("Cannot receive message from non-friend in SILENT mode"); 
      }
    }

    // start a voice call with terminal to
    public void makeVoiceCall(Terminal to) {
      if (mode == TerminalMode.OFF || mode == TerminalMode.BUSY) {
        throw new InvalidInvocationException("Cannot make voice call in current mode"); 
      }
      if (to.getMode() != TerminalMode.NORMAL) {
        throw new InvalidInvocationException("Cannot make voice call to receiver in its current mode"); 
      }

      // Tiago - the following two lines don't make total sense, but I'm not sure how else it's supposed to be
      to.acceptVoiceCall(null); // TODO: ask professor about voice communication logic
      currentCommunication = Communication.voiceCommunication(to, this);

      previousMode = mode;
      mode = TerminalMode.BUSY;
    }

    // to invoke over the receiving terminal of a voice call (represented by c). The voice
    // call is established if the terminal accepts the call, otherwise it throws an exception.
    void acceptVoiceCall(Communication c) {
      if (mode != TerminalMode.NORMAL) {
        throw new InvalidInvocationException("Cannot accept voice call in current mode"); 
      }
      previousMode = mode;
      mode = TerminalMode.BUSY;
    }

    // turns on this terminal
    public void turnOn() {
      if (mode != TerminalMode.OFF) {
        throw new InvalidInvocationException("Terminal is Already On"); 
      }
      mode = TerminalMode.NORMAL;
    }

    // turns off this terminal
    public void turnOff() {
      if (mode != TerminalMode.NORMAL && mode != TerminalMode.SILENT) {
        throw new InvalidInvocationException("Terminal is not powered-on and idle");
      }
      mode = TerminalMode.OFF;
    }

    // toggles the On mode: normal to silent or silent to normal
    public void toggleOnMode() {
      if (mode == TerminalMode.NORMAL) {
        mode = TerminalMode.SILENT;
      } else if (mode == TerminalMode.SILENT) {
        mode = TerminalMode.NORMAL;
      } else {
        throw new InvalidInvocationException("Cannot toggle from current mode");
      }
    }

    // Ends the ongoing communication.
    public void endOngoingCommunication() {
      if (mode == TerminalMode.BUSY) {
        currentCommunication.to().mode = currentCommunication.to().previousMode; 
        currentCommunication.from().mode = currentCommunication.from().previousMode; 
        // Tiago - I assumed that both Terminals involved paid for voice communication, but not sure
        int cost = (int) currentCommunication.getCost();
        currentCommunication.from().balance += cost;
        currentCommunication.to().balance += (int) cost;
      } else {
        throw new InvalidInvocationException("No ongoing communication");
      }
    }

    public Client getClient() {
      return this.client;
    } 
}
