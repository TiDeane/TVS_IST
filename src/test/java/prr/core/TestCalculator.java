package prr.core;
 
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

// NOTE: I keep this file to use as a reference when developing tests

@Test 
public class TestCalculator {
  private Calculator calc;

  @DataProvider
  private Object[][] computeDataForValidCtor() {
    return new Object[][] {
      {"12"},
      {"123"},
      {"12345"}};
  }

  @Test(dataProvider = "computeDataForValidCtor")
  public void testCtorWithNameValidSize2(String name) {
    // Arrange
    Calculator calc;

    // Act
    calc = new Calculator(name);

    // Assert
    assertEquals(calc.getName(), name);
    assertEquals(calc.getNumberOfOperations(), 0);
  }

  @DataProvider
  private Object[][] computeInvalideDataForCtor() {
    return new Object[][] {
      {"1"},
      {""},
      {null},
      {"123456"}};
  }

  @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "computeInvalideDataForCtor")
  public void testCtorWithNameInvalidSize(String name) {
    // Act
    new Calculator(name);
  }

  @BeforeMethod private void setup() {
    calc = new Calculator("test");
  }

  @DataProvider
  private Object[][] computeDataForSum() {
    return new Object[][] {
      {3, 4, 7},
      {-3 , -44, -47},
      {null, 4, 4},
      {-3, null, -3},
      {null, null, 0},
      {0, 0, 0}};
  }
  
  @Test(dataProvider = "computeDataForSum")
  public void testSum(Integer param1, Integer param2, Integer expectedResult) {
    // Act
    Integer result = calc.sum(param1, param2);

    // Assert
    assertEquals(result, expectedResult);
    assertEquals(calc.getNumberOfOperations(), 1);
  }

  @DataProvider
  private Object[][] computeInvalidDataForDivide() {
    return new Object[][] {
      {1, null},
      {-2, 0},
      {null, null},
      {0, 0},
      {0, null}};
  }

  @Test(dataProvider = "computeInvalidDataForDivide")
  public void testDivideWithInvalidData(Integer dividendo, Integer divider) {
    //Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {calc.divide(dividendo, divider);});
    assertEquals(calc.getNumberOfOperations(), 0);
  }

  /**
   * Another way of handling expected exceptions. This one iis the old solution, it still is valid
   * but you have to write more code (compared with the version using assertThrows)
   **/
  @Test(dataProvider = "computeInvalidDataForDivide")
  public void testDivideWithInvalidDataV2(Integer dividendo, Integer divider) {
    //Act & Assert
    try {
      calc.divide(dividendo, divider);
      fail("Should have thrown IllegalAgumentExcetion in divide");
    } catch(IllegalArgumentException iae) {
      assertEquals(calc.getNumberOfOperations(), 0);
    }
  }

  @DataProvider
  private Object[][] computeValidDataForDivide() {
    return new Object[][] {
      {0, 4, 0},
      {null, 50, 0},
      {10, 5, 2},
      {-100, -5, 20},
      {-7, 4, -1}};
  }

  @Test(dataProvider = "computeValidDataForDivide")
  public void testDivideWithValidData(Integer dividendo, Integer divider, Integer expectedResult) {
    //Act
    Integer result = calc.divide(dividendo, divider);

    //Assert
    assertEquals(result, expectedResult);
    assertEquals(calc.getNumberOfOperations(), 1);
  }
}
