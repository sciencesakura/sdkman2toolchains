package com.sciencesakura.sdkman2toolchains;

import java.nio.file.Path;

/**
 * A Java candidate model.
 *
 * @param identifier   the identifier string
 * @param distribution the distribution identifier
 * @param version      the version string
 * @param home         the home directory path
 */
public record JavaCandidate(String identifier, String distribution, String version, Path home) implements Candidate {

}
