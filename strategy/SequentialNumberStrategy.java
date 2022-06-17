package strategy;

import java.io.Serializable;

public class SequentialNumberStrategy implements NumberGeneratorStrategy, Serializable {

  private int nextNumber;

  public SequentialNumberStrategy() {
    this.nextNumber = 1;
  }
  
  public String nextNumber() {
    return String.valueOf(nextNumber++);
  }
  
}
