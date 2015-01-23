package avrohugger
package tool

import org.apache.avro.tool.Tool
import org.apache.avro.generic.GenericData.StringType;
import org.apache.avro.Schema;
import org.apache.avro.compiler.specific.SpecificCompiler;
import org.apache.avro.Protocol;


import java.io.File
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.List;

import scala.collection.JavaConversions._
import scala.util.Try



class GeneratorTool extends Tool {
/**
 * A Tool for generatign Scala case classes from schemas 
 * Adapted from https://github.com/apache/avro/blob/branch-1.7/lang/java/tools/src/main/java/org/apache/avro/tool/SpecificCompilerTool.java
 */
  val generator = new Generator

  @Override
  def run(in: InputStream, out: PrintStream, err: PrintStream, args: List[String]): Int = {
    if (args.size() < 3) {
      System.err
          .println("Usage: [-string] (schema|protocol) input... outputdir");
      System.err
          .println(" input - input files or directories");
      System.err
          .println(" outputdir - directory to write generated scala");
      System.err.println(" -string - use java.lang.String instead of Utf8");
      1;
    }

    var stringType: StringType = StringType.CharSequence;

    var arg = 0;
    if ("-string".equals(args.get(arg))) {
      stringType = StringType.String;
      arg+=1;
    }
      
    val method: String = args.get(arg);
    var inputs: List[File] = new ArrayList[File]();

    for (i <- (arg + 1) until (args.size() - 1)) {
      Try {
         inputs.add(new File(args.get(i)));
      }
    }
    // TODO: add if-branch for .avro files, add DATAFILE_FILTER
    if ("schema".equals(method)) {
      for (src: File <- determineInputs(inputs, SCHEMA_FILTER)) {
        generator.fromFile(src, args.last)
      }
    } 
    else if ("protocol".equals(method)) {
      for (src: File <- determineInputs(inputs, PROTOCOL_FILTER)) {
        val protocol: Protocol = Protocol.parse(src);
        sys.error("sorry, protocol not yet supported")
        //TODO generator.fromProtocol(protocol, args(2))
      }
    } 
    else {
      System.err.println("Expected \"schema\" or \"protocol\".");
      1;
    }
    0;
  }

  @Override
  def getName: String = "generate";

  @Override
  def getShortDescription: String = "Generates Scala code for the given schema.";

  /**
   * For a List of files or directories, returns a File[] containing each file
   * passed as well as each file with a matching extension found in the directory.
   *
   * @param inputs List of File objects that are files or directories
   * @param filter File extension filter to match on when fetching files from a directory
   * @return Unique array of files
   */
  private def determineInputs(inputs: List[File], filter: FilenameFilter): Array[File] = {
    val fileSet: Set[File] = new LinkedHashSet[File](); // preserve order and uniqueness
    for (file: File <- inputs) {
      // if directory, look at contents to see what files match extension
      if (file.isDirectory()) {
        for (f: File <- file.listFiles(filter)) {
          fileSet.add(f);
        }
      }
      // otherwise, just add the file.
      else {
        fileSet.add(file);
      }
    }
    if (fileSet.size() > 0) {
      System.err.println("Input files to compile:");
      for (file: File <- fileSet) {
        System.err.println("  " + file);
      }
    }
    else {
      System.err.println("No input files found.");
    }

    Array[File](fileSet.toList:_*);
  }

  val SCHEMA_FILTER: FileExtensionFilter  =
    new FileExtensionFilter("avsc");
  val PROTOCOL_FILTER: FileExtensionFilter =
    new FileExtensionFilter("avpr");

  case class FileExtensionFilter(extension: String) extends FilenameFilter {
    @Override
    def accept(dir: File, name: String) = {
      name.endsWith(this.extension);
    }
  }
}