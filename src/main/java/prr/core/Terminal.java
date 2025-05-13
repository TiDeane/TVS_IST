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
  private int cost;

    // creates a terminal with a given identifier and associated to the given client.
    Terminal(String id, Client client) {
      if (id == null || client == null) throw new IllegalArgumentException("Invalid terminal or client");
      this.id = id;
      this.client = client;
      this.mode = TerminalMode.OFF;
      this.balance = 0;
      this.previousMode = TerminalMode.OFF;
      this.cost = 0;
    }

    // Returns the mode of this terminal
    public final TerminalMode getMode() {
      return mode;
    }

    // Decreases the debt of this terminal by the given amount. The amount must be a number greater than 5 cents
    public void pay(int amount) {
      if(mode == TerminalMode.OFF && amount > 5) {
        balance -= amount;
        cost = 0;
      } 
      else {
        throw new InvalidInvocationException("Terminal must be OFF and amount >= 5"); 
      }
    }

    // returns the balance of this terminal
    public int balance() {
      if(mode == TerminalMode.BUSY){
        throw new InvalidInvocationException("Terminal is Busy"); 
      }
      return balance;
    }

    // send a SMS to terminal to with text msg. Returns if the SMS was successfully delivered.
    public boolean sendSMS(Terminal to, String msg) {
      if(mode == TerminalMode.BUSY || mode == TerminalMode.OFF){
        throw new InvalidInvocationException("Cannot send SMS in current mode"); 
      }
      if (to.getMode() == TerminalMode.OFF)
        return false;
      if (to.getMode() == TerminalMode.SILENT) {
        if (!to.client.hasFriend(this.client)) return false;
      }
      to.receiveSMS(this,msg);
      currentCommunication = new Communication(CommunicationType.SMS, to, this);
      cost += (int) currentCommunication.getCost();
      return true;
    }

    // receives a SMS from terminal from with text msg
    public void receiveSMS(Terminal from, String msg) {
      if (mode == TerminalMode.OFF){
        throw new InvalidInvocationException("Cannot receive message in corrent mode"); 
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
      previousMode = mode;
      mode = TerminalMode.BUSY;
      currentCommunication = new Communication(CommunicationType.VOICE, to, this);
      to.acceptVoiceCall(currentCommunication);
    }

    // to invoke over the receiving terminal of a voice call (represented by c). The voice
    // call is established if the terminal accepts the call, otherwise it throws an exception.
    void acceptVoiceCall(Communication c) {
      if(mode != TerminalMode.NORMAL){
        throw new InvalidInvocationException("Cannot accepy voice call in current mode"); 
      }
      previousMode = mode;
      mode = TerminalMode.BUSY;
    }

    // turns on this terminal
    public void turnOn() {
      if (mode != TerminalMode.OFF){
        throw new InvalidInvocationException("Terminal is Already On"); 
      }
      mode = TerminalMode.NORMAL;
    }

    // turns off this terminal
    public void turnOff() {
      if(mode != TerminalMode.NORMAL || mode != TerminalMode.SILENT) {
        throw new InvalidInvocationException("Terminal is not powered-on and idle");
      }
      mode = TerminalMode.OFF;
      pay(cost);
    }

    // toggles the On mode: normal to silent or silent to normal
    public void toggleOnMode() {
      if (mode == TerminalMode.NORMAL){
        mode = TerminalMode.SILENT;
      }
      else if(mode == TerminalMode.SILENT){
        mode = TerminalMode.NORMAL;
      }
      else {
        throw new InvalidInvocationException("Cannot toggle from current mode");
      }
    }

    // Ends the ongoing communication.
    public void endOngoingCommunication() {
      if(mode == TerminalMode.BUSY){
        currentCommunication.to().mode = currentCommunication.to().previousMode; 
        currentCommunication.from().mode = currentCommunication.from().previousMode; 
        currentCommunication.to().cost += (int) currentCommunication.getCost();
      }
    }

    public Client getClient(){
      return this.client;
    } 
}
