cmake_minimum_required(VERSION 3.19)
project(external_checkpoint)

set(CMAKE_CXX_STANDARD 14)

include_directories(include)


find_library(hiredis
        hiredis.1.0.0
        PATHS
        include/lib)

find_library(libevent
        event_core-2.1.7
        PATHS
        include/lib)

add_executable(external_checkpoint
        main.cpp
        include/hiredis.h
        include/event2/event-config.h)

target_link_libraries(
        external_checkpoint
        ${hiredis} ${libevent})
