package com.sciencesakura.sdkman2toolchains;

import java.nio.file.Path;

/**
 * A candidate model.
 */
public sealed interface Candidate permits JavaCandidate {

  String identifier();

  Path home();
}
