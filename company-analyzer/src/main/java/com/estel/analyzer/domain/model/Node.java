package com.estel.analyzer.domain.model;

import java.util.List;

public class Node<T> {
  public T data;
  public Node<T> parent;
  public List<Node<T>> children;
  public int distanceFromRoot;
}
