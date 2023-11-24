package com.sciencesakura.sdkman2toolchains;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * A main class.
 */
public class App {

  private static final Options options = new Options();

  static {
    options.addOption(Option.builder("h")
        .longOpt("help")
        .desc("Prints this help message.")
        .build());
    options.addOption(Option.builder("O")
        .hasArg()
        .argName("FILE")
        .desc("Specifies the output file. STDOUT is used if not specified.")
        .build());
  }

  /**
   * The entry point of sdkman2toolchains.
   *
   * @param args the command line arguments
   * @throws Exception if an error occurs
   */
  public static void main(String[] args) throws Exception {
    var cmd = parseArgs(args);

    if (cmd.hasOption("h")) {
      printUsage(System.out);
      System.exit(0);
    }

    OutputStream output;
    if (cmd.hasOption("O")) {
      output = new FileOutputStream(cmd.getOptionValue("O"));
    } else {
      output = System.out;
    }
    try (var tw = new ToolchainWriter(output)) {
      var finder = CandidateFinder.newInstance("java");
      finder.find().stream()
          .map(new ToolchainMapper())
          .forEach(tw::write);
    }
  }

  private static CommandLine parseArgs(String[] args) {
    var cmdParser = new DefaultParser();
    try {
      return cmdParser.parse(options, args);
    } catch (ParseException e) {
      e.printStackTrace(System.err);
      printUsage(System.err);
      System.exit(1);
      return null; // unreachable
    }
  }

  private static void printUsage(PrintStream s) {
    var fmt = new HelpFormatter();
    var ps = new PrintWriter(s, false, StandardCharsets.UTF_8);
    fmt.printHelp(ps, fmt.getWidth(), "sdkman2toolchains", null, options, 1, 1, null, true);
    ps.flush();
  }
}
