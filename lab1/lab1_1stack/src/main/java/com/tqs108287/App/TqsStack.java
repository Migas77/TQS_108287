package com.tqs108287.App;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
  private LinkedList<T> collection;

  public TqsStack(){
    collection = new LinkedList<T>();
  }

  public T pop(){
    // remove last already throws NoSuchElementException if Empty
    return collection.removeLast();
  }

  public int size(){
    return collection.size();
  }

  public T peek() throws NoSuchElementException{
    if (collection.isEmpty())
      throw new NoSuchElementException();
    return collection.peek();
  }

  public void push(T item){
    collection.addFirst(item);
  }

  public boolean isEmpty(){
    return collection.isEmpty();
  }
  
}
