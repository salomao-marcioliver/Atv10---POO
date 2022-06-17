package strategy;

public class SequentialEvenNumberStrategy implements NumberGeneratorStrategy {

  private int nextNumber;

  public SequentialEvenNumberStrategy() {
    this.nextNumber = 0;
  }
  
  public String nextNumber() {
    nextNumber += 2;
    return String.valueOf(nextNumber);
  }
  
}
