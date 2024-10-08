### Configuration for Log4j

# Set this variable to the name of the Main class (there should be no Java package use at this time, we have other plans for using Java packages)
# Default: Main
MAIN_CLASS_NAME := Main 

# JUnit test class names including package (if declared)
JUNIT4_TEST_CLASS_NAME := TestSetJunit4
JUNIT5_TEST_CLASS_NAME := TestSetJunit5

# Where to look for the .jar files 
# Default: .. (parent directory)
COMMON_JAR_DIR := ..

# Set variables for the .jar file paths
JUNIT_JAR_PATH := $(COMMON_JAR_DIR)/junit-platform-console-standalone-1.9.3.jar
LOG4j_CORE_JAR_PATH := $(COMMON_JAR_DIR)/log4j-core-2.12.4.jar
LOG4J_API_JAR_PATH := $(COMMON_JAR_DIR)/log4j-api-2.12.4.jar

# All .java files that aren't JUnit tests
PROGRAM_FILES := $(shell find ./ ! -name "TestSet*.java" ! -name "*Test.java" -name "*.java")
# instead of the find command, the variable can also be a constant
# PROGRAM_FILES := Main.java Item.java Store.java Player.java Escrow.java

### Make targets
# These can be executed individually by running `make TARGET_NAME`

# Default if no target is specified: clean, run junit4 tests, run junit5 tests, and if they pass launch the program
all: clean compile-junit4 run-junit4 compile-junit5 run-junit5 compile run

# Compile the program with log4j
compile:
	javac -d . --class-path $(LOG4j_CORE_JAR_PATH):$(LOG4J_API_JAR_PATH) $(PROGRAM_FILES)

# Run main program
run:
	java --class-path $(LOG4j_CORE_JAR_PATH):$(LOG4J_API_JAR_PATH):. $(MAIN_CLASS_NAME)

# Compile only the JUnit4 test set with log4j and JUnit
compile-junit4:
	javac -d . --class-path $(LOG4j_CORE_JAR_PATH):$(LOG4J_API_JAR_PATH):$(JUNIT_JAR_PATH) $(PROGRAM_FILES) TestSetJ?nit4.java

# Compile only the JUnit5 test set with log4j and JUnit
compile-junit5:
	javac -d . --class-path $(LOG4j_CORE_JAR_PATH):$(LOG4J_API_JAR_PATH):$(JUNIT_JAR_PATH) $(PROGRAM_FILES) TestSetJ?nit5.java

# Run junit4 tests
run-junit4: 
	java -jar $(JUNIT_JAR_PATH) --class-path $(LOG4j_CORE_JAR_PATH):$(LOG4J_API_JAR_PATH):. --select-class $(JUNIT4_TEST_CLASS_NAME)

# Run junit5 tests
run-junit5: 
	java -jar $(JUNIT_JAR_PATH) --class-path $(LOG4j_CORE_JAR_PATH):$(LOG4J_API_JAR_PATH):. --select-class $(JUNIT5_TEST_CLASS_NAME)

# Remove all .class files in the current folder and all its subdirectories
clean:
	find . -name "*.class" -type f -delete