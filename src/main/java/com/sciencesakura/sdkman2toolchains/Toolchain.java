package com.sciencesakura.sdkman2toolchains;

import java.util.Map;

/**
 * A toolchain model.
 */
public interface Toolchain {

  String type();

  Map<String, String> provides();

  Map<String, String>  configuration();
}
