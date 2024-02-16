package com.tqs108287.App;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
  private LinkedList<T> linkedList;

  public TqsStack(){
    linkedList = new LinkedList<T>();
  }

  public T pop(){
    // remove last already throws NoSuchElementException if Empty
    return linkedList.removeLast();
  }

  public int size(){
    return linkedList.size();
  }

  public T peek() throws NoSuchElementException{
    if (linkedList.isEmpty())
      throw new NoSuchElementException();
    return linkedList.peek();
  }

  public void push(T item){
    linkedList.addFirst(item);
  }

  public boolean isEmpty(){
    return linkedList.isEmpty();
  }
  
}
