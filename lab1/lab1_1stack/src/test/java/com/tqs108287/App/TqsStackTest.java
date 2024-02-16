package com.tqs108287.App;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TqsStackTest {
  TqsStack<Integer> stack;

  @BeforeEach
  void setup(){
    stack = new TqsStack<Integer>();
  }

  @DisplayName("A stack is empty on construction")
  @Test
  void NewStackIsEmptyTest(){
    // arrange (required objects, initial state)
      // BeforeEach already creates an empty stack; noting to do here
    // act
    // assess
    Assertions.assertTrue(stack.isEmpty());
    Assertions.assertEquals(stack.size(), 0);
  }

  @DisplayName("A stack has size 0 on construction.")
  @Test
  void nPushToEmptyStackTest(){
    // arrange
    int n = 10;
    // act
    for (int i=1; i <= n; i++){
      stack.push(0);
    }
    // assess
    Assertions.assertFalse(stack.isEmpty());
    Assertions.assertEquals(stack.size(),n);
  }

  @DisplayName("")
  @Test
  void popTest(){
    // arrange
    int x = 1;
    // act
    stack.push(x);
    // assess
    Assertions.assertEquals(stack.pop(), x);
  }

  @DisplayName("")
  @Test 
  void peekTest(){
    // arrange
    int x = 1;
    // act
    stack.push(x);
    int size = stack.size();
    // assess
    Assertions.assertEquals(stack.peek(), x);
    Assertions.assertEquals(size, stack.size());
  }

  @DisplayName("")
  @Test
  void sizeAfterNPopsTest(){
    // arrange
    int n = 10;
    for (int i=1; i <= n; i++){
      stack.push(0);
    }
    // act
    for (int i=1; i <= n; i++){
      stack.pop();
    }
    // assess
    Assertions.assertTrue(stack.isEmpty());
  }

  @DisplayName("")
  @Test
  void popFromAnEmptyStack(){
    // arrange (required objects, initial state)
    // act
    // assert
    Assertions.assertThrows(NoSuchElementException.class, () -> stack.pop());
  }

  @DisplayName("")
  @Test
  void peekAnEmptyStack(){
    // arrange (required objects, initial state)
    // act
    // assert
    Assertions.assertThrows(NoSuchElementException.class, () -> stack.peek());
  }


  
}

// @Disabled
// @DisplayName


// size()
// pushNValues_thenSizeIsN