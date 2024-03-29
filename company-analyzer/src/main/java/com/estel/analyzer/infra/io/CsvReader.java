package com.estel.analyzer.infra.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
    File file = getFile(fileName);
    List<String> lines = List.of();
    // TODO: consider if recovery is possible.
    try {
      lines = Files.readAllLines(file.toPath());
    } catch(IOException e) {
      System.err.println("Cannot read from file " + fileName + ": ");
      e.printStackTrace();
      System.exit(1);
    }
    return lines;
  }

  private File getFile(String fileName)
  {
    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);

    if (resource == null) {
      throw new IllegalArgumentException("file is not found!");
    } else {
      return new File(resource.getFile());
    }
  }

  private String[] lineToCsvCells(String line) {
    return line.split(SEPARATOR);
  }
}
