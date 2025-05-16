package prr.core;

import prr.core.exceptions.InvalidOperationException;
import java.time.Instant;
import java.time.Duration;
/**
* This class represents a communication (text or voice) made between
* two terminals.
**/
public class Communication {

  int size = 0;
  double cost = 0;
  Terminal receiver;
  Terminal sender;
  CommunicationType type;
  Instant timestampBegin;
  Instant timestampEnd;


  private Communication(CommunicationType type, Terminal to, Terminal from) {
    this.type = type;
    this.receiver = to;
    this.sender = from;
    this.timestampBegin = Instant.now();
  }

  public static Communication textCommunication(Terminal to, Terminal from, int length) {
    if (length < 0) {
      throw new InvalidOperationException("Length can't be negative");
    }
    Communication text = new Communication(CommunicationType.SMS, to, from);
    text.size = (int) Math.ceil(length / 100.0);
    //text.cost = text.computeCost();
    return text;
  }

  public static Communication voiceCommunication(Terminal to, Terminal from) {
    Communication voice = new Communication(CommunicationType.VOICE, to, from);
    return voice;
  }

  // sets duration for voice communication (currently unused... maybe Terminal should call it?)
  public void duration(int duration) {
    this.size = duration;
  }

  public Terminal to() {
    return sender;
  }

  public Terminal from() {
    return receiver;
  }

  double computeCost() {
    int points = sender.getClient().getPoints();
    int numFriends = sender.getClient().numberOfFriends();

    if (type == CommunicationType.VOICE) {
      timestampEnd = Instant.now();
      Duration duration_time = Duration.between(timestampBegin, timestampEnd);
      size = (int) duration_time.getSeconds(); 
    }

    if (size == 0) {
      return 0;
    }

    if (size < 10) {
      return points > 100 ? 1 : 2;
    }

    if (size < 120) {
      if (points < 75) {
        return type == CommunicationType.SMS ? 6 : 12;
      } else {
        if (type == CommunicationType.SMS) {
          return 4;
        } else {
          return numFriends < 4 ? 8 : 5 ;
        }
      }
    }

    return points < 150 ? 15 : 12;
  }

  public double getCost() {
    computeCost();
    return this.cost;
  }
}

