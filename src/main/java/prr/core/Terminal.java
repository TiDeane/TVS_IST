package prr.core;

public class Terminal {

  public enum TerminalMode { OFF, NORMAL, SILENT, BUS }

    // creates a terminal with a given identifier and associated to the given client.
    Terminal(String id, Client client) {
      // TODO: implement this
      return;
    }

    // Returns the mode of this terminal
    public final TerminalMode getMode() {
      // TODO: implement this
      return null;
    }

    // Decreases the debt of this terminal by the given amount. The amount must be a number greater than 5 cents
    public void pay(int amount) {
      // TODO: implement this
      return;
    }

    // returns the balance of this terminal
    public int balance() {
      // TODO: implement this
      return -1;
    }

    // send a SMS to terminal to with text msg. Returns if the SMS was successfully delivered.
    public boolean sendSMS(Terminal to, String msg) {
      // TODO: implement this
      return false;
    }

    // receives a SMS from terminal from with text msg
    public void receiveSMS(Terminal from, String msg) {
      // TODO: implement this
      return;
    }

    // start a voice call with terminal to
    public void makeVoiceCall(Terminal to) {
      // TODO: implement this
      return;
    }

    // to invoke over the receiving terminal of a voice call (represented by c). The voice
    // call is established if the terminal accepts the call, otherwise it throws an exception.
    void acceptVoiceCall(Communication c) {
      // TODO: implement this
      return;
    }

    // turns on this terminal
    public void turnOn() {
      // TODO: implement this
      return;
    }

    // turns off this terminal
    public void turnOff() {
      // TODO: implement this
      return;
    }

    // toggles the On mode: normal to silent or silent to normal
    public void toggleOnMode() {
      // TODO: implement this
      return;
    }

    // Ends the ongoing communication.
    public void endOngoingCommunication() {
      // TODO: implement this
      return;
    }
}
