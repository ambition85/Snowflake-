package com.spiderpig86.snowflake.configuration;

import com.google.common.base.Preconditions;
import com.spiderpig86.snowflake.lib.OverflowStrategy;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GeneratorConfiguration {

  private final long dataCenter;
  private final long worker;
  private final OverflowStrategy overflowStrategy;

  private GeneratorConfiguration(
      final long dataCenter, final long worker, final OverflowStrategy overflowStrategy) {
    this.dataCenter = dataCenter;
    this.worker = worker;
    this.overflowStrategy = overflowStrategy;
  }

  public static class Builder {
    Long dataCenter;
    Long worker;
    OverflowStrategy overflowStrategy;

    public Builder withDataCenter(@Nonnull final Long dataCenter) {
      this.dataCenter = dataCenter;
      return this;
    }

    public Builder withWorker(@Nonnull final Long worker) {
      this.worker = worker;
      return this;
    }

    public Builder withOverflowStrategy(@Nonnull final OverflowStrategy overflowStrategy) {
      this.overflowStrategy = overflowStrategy;
      return this;
    }

    public GeneratorConfiguration build() {
      validate();
      return new GeneratorConfiguration(this.dataCenter, this.worker, this.overflowStrategy);
    }

    private void validate() {
      Preconditions.checkNotNull(
          dataCenter, "Data center cannot be null for GeneratorConfiguration");
      Preconditions.checkNotNull(worker, "Worker cannot be null for GeneratorConfiguration");

      Preconditions.checkArgument(dataCenter >= 0, "Data center must be non-negative");
      Preconditions.checkArgument(worker >= 0, "Worker must be non-negative.");
      Preconditions.checkNotNull(overflowStrategy, "Overflow strategy must be provided.");
    }
  }

  public static GeneratorConfiguration.Builder builder() {
    return new Builder();
  }

  public static GeneratorConfiguration getDefault() {
    return new GeneratorConfiguration.Builder()
        .withDataCenter(0L)
        .withWorker(0L)
        .withOverflowStrategy(OverflowStrategy.SLEEP)
        .build();
  }
}
