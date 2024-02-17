package com.tqs108287.App;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// NÃO PODE SER ASSUMIDA A ORDEM DE EXECUÇÃO DOS TESTES
// Use @Disabled to disable a test.

public class TqsStackTest {
  TqsStack<Integer> stack;

  @BeforeEach
  void setup(){
    stack = new TqsStack<>();
  }

  @DisplayName("Stack is empty on construction.")
  @Test
  void stackEmptyOnConstruction(){
    // arrange (required objects, initial state)
      // BeforeEach already creates an empty stack; noting to do here
    // act
    // assess
      // use Assertions.assertAll to check every assertion even if one of them fails
      // without this only the first assertion would fail
    Assertions.assertTrue(stack.isEmpty());
  }

  @Test
  @DisplayName("Stack has size 0 on construction")
  void stackSizeOnConstruction() {
    Assertions.assertEquals(stack.size(), 0);
  }

  @DisplayName("After n pushes to an empty stack, n > 0, the stack is not empty and its size is n.")
  @Test
  void pushNValues_thenSizeIsN(){
    int n = 10;
    for (int i=1; i <= n; i++){
      stack.push(0);
    }
    Assertions.assertAll(
            () -> Assertions.assertFalse(stack.isEmpty()),
            () -> Assertions.assertEquals(stack.size(), n)
    );
  }

  @DisplayName("If one pushes x then pops, the value popped is x.")
  @Test
  void pushX_thenPopX(){
    int x = 1;
    stack.push(x);
    Assertions.assertEquals(stack.pop(), x);
  }

  @DisplayName("If one pushes x then peeks, the value returned is x, but the size stays the same.")
  @Test 
  void pushX_thenPeekX(){
    int x = 1;
    stack.push(x);
    int size = stack.size();
    Assertions.assertAll(
            () -> Assertions.assertEquals(stack.peek(), x),
            () -> Assertions.assertEquals(size, stack.size())
    );
  }

  @DisplayName("If the size is n, then after n pops, the stack is empty and has a size 0.")
  @Test
  void popNValues_thenSizeIs0(){
    int n = 10;
    for (int i=1; i <= n; i++){
      stack.push(0);
    }
    for (int i=1; i <= n; i++){
      stack.pop();
    }
    Assertions.assertTrue(stack.isEmpty());
  }

  @DisplayName("Popping from an empty stack does throw a NoSuchElementException")
  @Test
  void popFromAnEmptyStack(){
    Assertions.assertThrows(NoSuchElementException.class, () -> stack.pop());
  }

  @DisplayName("Peeking into an empty stack does throw a NoSuchElementException.")
  @Test
  void peekAnEmptyStack(){
    Assertions.assertThrows(NoSuchElementException.class, () -> stack.peek());
  }

}

