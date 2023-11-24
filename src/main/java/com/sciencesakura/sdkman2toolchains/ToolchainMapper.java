package com.sciencesakura.sdkman2toolchains;

import java.util.function.Function;

/**
 * A mapper from {@link Candidate} to {@link Toolchain}.
 */
public final class ToolchainMapper implements Function<Candidate, Toolchain> {

  @Override
  public Toolchain apply(Candidate candidate) {
    return switch (candidate) {
      case JavaCandidate c -> JdkToolchain.of(c.identifier(), jdkVendor(c.distribution()), c.version(), c.home());
    };
  }

  private String jdkVendor(String distribution) {
    return switch (distribution) {
      case "amzn" -> "corretto";
      case "albba" -> "dragonwell";
      case "gln" -> "gluon";
      case "graalce", "graal" -> "graalvm";
      case "open" -> "java.net";
      case "jbr" -> "jetbrains";
      case "librca", "nik" -> "liberica";
      case "mandrel" -> "mandrel";
      case "ms" -> "microsoft";
      case "sapmchn" -> "sapmachine";
      case "sem" -> "semeru";
      case "tem" -> "temurin";
      case "kona" -> "tencent";
      default -> distribution;
    };
  }
}
