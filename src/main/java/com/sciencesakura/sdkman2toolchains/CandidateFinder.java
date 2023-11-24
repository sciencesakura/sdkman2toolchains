package com.sciencesakura.sdkman2toolchains;

import java.nio.file.Path;
import java.util.List;

/**
 * A finder of candidates.
 *
 * @param <C> the type of candidates
 */
public interface CandidateFinder<C extends Candidate> {

  /**
   * The path to the SDKMAN! directory.
   *
   * <p>The path is determined by the following order:</p>
   * <ol>
   *   <li>the value of the {@code sdkman.dir} system property</li>
   *   <li>the value of the {@code SDKMAN_DIR} environment variable</li>
   *   <li>{@code $HOME/.sdkman}</li>
   * </ol>
   */
  Path SDKMAN_DIR = sdkmanDir();

  /**
   * Creates a new instance of {@link CandidateFinder}.
   *
   * @param type the type of candidates
   * @return the new instance
   */
  static CandidateFinder<? extends Candidate> newInstance(String type) {
    // Now, only Java is supported.
    return new JavaCandidateFinder();
  }

  /**
   * Finds candidates.
   *
   * @return the list of candidates
   */
  List<C> find();

  private static Path sdkmanDir() {
    var dir = System.getProperty("sdkman.dir");
    if (dir != null && !dir.isEmpty()) {
      return Path.of(dir);
    }
    dir = System.getenv("SDKMAN_DIR");
    if (dir != null && !dir.isEmpty()) {
      return Path.of(dir);
    }
    return Path.of(System.getProperty("user.home"), ".sdkman");
  }
}
