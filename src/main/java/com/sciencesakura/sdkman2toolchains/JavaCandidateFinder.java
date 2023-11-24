package com.sciencesakura.sdkman2toolchains;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A finder of Java candidates.
 */
final class JavaCandidateFinder implements CandidateFinder<JavaCandidate> {

  private static final Pattern identifierPattern = Pattern.compile("^([^-]+)-([^-]+)$");

  @Override
  public List<JavaCandidate> find() {
    var dir = SDKMAN_DIR.resolve("candidates/java");
    if (!Files.isDirectory(dir)) {
      return List.of();
    }
    try (var children = Files.list(dir)) {
      return children.filter(this::isCandidateDir)
          .map(this::recognize)
          .toList();
    } catch (IOException e) {
      e.printStackTrace(System.err);
      return List.of();
    }
  }

  private boolean isCandidateDir(Path candidatePath) {
    if (!Files.isDirectory(candidatePath)) {
      return false;
    }
    var name = candidatePath.getFileName().toString();
    return identifierPattern.matcher(name).matches();
  }

  private JavaCandidate recognize(Path candidatePath) {
    var name = candidatePath.getFileName().toString();
    var matcher = identifierPattern.matcher(name);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Invalid candidate directory: " + candidatePath);
    }
    var version = matcher.group(1);
    var distribution = matcher.group(2);
    return new JavaCandidate(name, distribution, version, candidatePath);
  }
}
