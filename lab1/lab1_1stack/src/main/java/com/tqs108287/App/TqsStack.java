package com.tqs108287.App;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

  // despite final contents of collection can still be modified
  private final LinkedList<T> collection;

  public TqsStack(){
    collection = new LinkedList<>();
  }

  public void push(T x){
    collection.addFirst(x);
  }

  public T pop(){
    // remove last already throws NoSuchElementException if Empty
    return collection.removeLast();
  }

  public T peek(){
    if (collection.isEmpty())
      throw new NoSuchElementException();
    return collection.peek();
  }

  public int size(){
    return collection.size();
  }

  public boolean isEmpty(){
    return collection.isEmpty();
  }

}
