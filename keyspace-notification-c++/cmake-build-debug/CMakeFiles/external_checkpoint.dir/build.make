# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.19

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Disable VCS-based implicit rules.
% : %,v


# Disable VCS-based implicit rules.
% : RCS/%


# Disable VCS-based implicit rules.
% : RCS/%,v


# Disable VCS-based implicit rules.
% : SCCS/s.%


# Disable VCS-based implicit rules.
% : s.%


.SUFFIXES: .hpux_make_needs_suffix_list


# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/rahulb/Desktop/external-checkpoint

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/rahulb/Desktop/external-checkpoint/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/external_checkpoint.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/external_checkpoint.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/external_checkpoint.dir/flags.make

CMakeFiles/external_checkpoint.dir/main.cpp.o: CMakeFiles/external_checkpoint.dir/flags.make
CMakeFiles/external_checkpoint.dir/main.cpp.o: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/rahulb/Desktop/external-checkpoint/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/external_checkpoint.dir/main.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/external_checkpoint.dir/main.cpp.o -c /Users/rahulb/Desktop/external-checkpoint/main.cpp

CMakeFiles/external_checkpoint.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/external_checkpoint.dir/main.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/rahulb/Desktop/external-checkpoint/main.cpp > CMakeFiles/external_checkpoint.dir/main.cpp.i

CMakeFiles/external_checkpoint.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/external_checkpoint.dir/main.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/rahulb/Desktop/external-checkpoint/main.cpp -o CMakeFiles/external_checkpoint.dir/main.cpp.s

# Object files for target external_checkpoint
external_checkpoint_OBJECTS = \
"CMakeFiles/external_checkpoint.dir/main.cpp.o"

# External object files for target external_checkpoint
external_checkpoint_EXTERNAL_OBJECTS =

external_checkpoint: CMakeFiles/external_checkpoint.dir/main.cpp.o
external_checkpoint: CMakeFiles/external_checkpoint.dir/build.make
external_checkpoint: /usr/local/lib/libhiredis.1.0.0.dylib
external_checkpoint: /usr/local/lib/libevent_core-2.1.7.dylib
external_checkpoint: CMakeFiles/external_checkpoint.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/rahulb/Desktop/external-checkpoint/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable external_checkpoint"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/external_checkpoint.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/external_checkpoint.dir/build: external_checkpoint

.PHONY : CMakeFiles/external_checkpoint.dir/build

CMakeFiles/external_checkpoint.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/external_checkpoint.dir/cmake_clean.cmake
.PHONY : CMakeFiles/external_checkpoint.dir/clean

CMakeFiles/external_checkpoint.dir/depend:
	cd /Users/rahulb/Desktop/external-checkpoint/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/rahulb/Desktop/external-checkpoint /Users/rahulb/Desktop/external-checkpoint /Users/rahulb/Desktop/external-checkpoint/cmake-build-debug /Users/rahulb/Desktop/external-checkpoint/cmake-build-debug /Users/rahulb/Desktop/external-checkpoint/cmake-build-debug/CMakeFiles/external_checkpoint.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/external_checkpoint.dir/depend

