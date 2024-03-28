package com.estel.analyzer.infra.io;

import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {

  public String SEPARATOR = ",";

  public List<String[]> readCsv(String fileName) {
    List<String> lines = readLines(fileName);
    return lines.stream()
        .skip(1)
        .map(this::lineToCsvCells)
        .collect(Collectors.toList());
  }

  private List<String> readLines(String fileName) {
    return List.of();
  }

  private String[] lineToCsvCells(String line) {
    return line.split(SEPARATOR);
  }
}
