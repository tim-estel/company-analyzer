package com.estel.analyzer.domain.model;

import java.util.List;

public class Node<T> {
  private T data;
  private Node<T> parent;
  private List<Node<T>> children;
  private int distanceFromRoot;
}
