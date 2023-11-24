package com.sciencesakura.sdkman2toolchains;

import java.nio.file.Path;
import java.util.Map;

/**
 * A JDK toolchain model.
 */
public record JdkToolchain(Map<String, String> provides, Map<String, String> configuration) implements Toolchain {

  static JdkToolchain of(String id, String vendor, String version, Path jdkHome) {
    return new JdkToolchain(
        Map.of("id", id, "vendor", vendor, "version", version),
        Map.of("jdkHome", jdkHome.toString()));
  }

  @Override
  public String type() {
    return "jdk";
  }
}
