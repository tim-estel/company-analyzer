package com.estel.analyzer.configuration;

public record Configuration(
    double MIN_MANAGER_TO_MEAN_REPORT_PAY_RATIO,
    double MAX_MANAGER_TO_MEAN_REPORT_PAY_RATIO,
    double MAX_REPORTING_LINE_LENGTH,
    String DEFAULT_FILE_PATH
) {

  public static class Builder {

    private double minManagerToMeanReportPayRatio = 1.2;
    private double maxManagerToMeanReportPayRatio = 1.5;
    /**
     * Reporting line length is the number of vertical relationships between a given employee and the CEO
     **/
    private double maxReportingLineLength = 5;
    private String defaultFilePath = "company.csv";

    public static Builder newInstance() {
      return new Builder();
    }

    public Builder minManagerToMeanReportPayRatio(double value) {
      this.minManagerToMeanReportPayRatio = value;
      return this;
    }

    public Builder maxManagerToMeanReportPayRatio(double value) {
      this.maxManagerToMeanReportPayRatio = value;
      return this;
    }

    public Builder maxReportingLineLength(double value) {
      this.maxReportingLineLength = value;
      return this;
    }

    public Builder defaultFilePath(String value) {
      this.defaultFilePath = value;
      return this;
    }

    // it's a pity java doesn't have named or default arguments like kotlin
    public Configuration build() {
      return new Configuration(
          minManagerToMeanReportPayRatio,
          maxManagerToMeanReportPayRatio,
          maxReportingLineLength,
          defaultFilePath
      );
    }
  }
}
