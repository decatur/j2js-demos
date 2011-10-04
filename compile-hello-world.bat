set COMPILER_DIR=../j2js-compiler

REM Usage: java com.j2js.J2JSCompiler <basedir> <classpathElements> <entryPointClassName> <targetLocation>

set RUNTIME_CLASSPATH=^
%COMPILER_DIR%/libs/1.4/commons-io-1.4.jar;^
%COMPILER_DIR%/libs/bcel-5.1.jar;^
%COMPILER_DIR%/target/classes

set CROSS_COMPILE_CLASSPATH=^
target/classes;../j2js-jre/target/classes;../j2js-agent/target/classes;

java -cp %RUNTIME_CLASSPATH% com.j2js.J2JSCompiler . ^
%CROSS_COMPILE_CLASSPATH% j2js.demo.HelloWorld1 ./target/assemblies/HelloWorld1