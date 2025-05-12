/**
 * This class represents a calculator that is able to sum and divide two Integer numbers.
 * Each calculator has a name and keeps the number of operations executed without errors.
 **/

package prr.core;

public class Calculator {
	
  private final String name;
  private int numberOfOperations;
  
  /**
   * Creates a calculator with a given name. The name must be a string with a length between 2 and 5.
   * 
   * @param name the name of the created calculator
   *
   * @throws IllegalArgumentException if the name is null, or its length is greater than 5
   *          or smaller than 2.
   **/
  public Calculator(String name) {
    if (name == null || name.length() < 2 || name.length() > 5) throw new IllegalArgumentException("no name");
    this.name = name;
  }
  
  /**
   * Sums two integers and returns the result of the sum. Null integers are considered
   * as 0.
   *
   * @param a the first integer to add
   * @param b the second integer to add
   *
   * @returns the result of this operation.
   **/
  public Integer sum(Integer a, Integer b) {
    ++numberOfOperations;
    
    if (a == null)
      a = 0;
    
    if (b == null)
      return a;
    
    return a + b;
  }
  
  /**
   * Divides two integers and returns the result of the division. Null integers are considered
   * as 0.
   *
   * @param a the first integer
   * @param b the second integer
   *
   * @returns the result of the division.
   * @throws IllegalArgumentException if b is 0.
   **/
  public Integer divide(Integer a, Integer b) {
    if (b == null || b == 0)
      throw new IllegalArgumentException();
    
    ++numberOfOperations;
    
    if (a == null)
      return 0;   
    
    return a / b;
  }
  
  /**
   * @returns the name of this calculator
   **/
  public String getName() {
    return name;
  }
  
  /**
   * @returns the number of operations performed on this claculator without error.
   **/
  public int getNumberOfOperations() {
    return numberOfOperations;
  }
}
