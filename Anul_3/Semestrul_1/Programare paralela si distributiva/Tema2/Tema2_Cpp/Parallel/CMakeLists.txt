cmake_minimum_required(VERSION 3.29)
project(Parallel)

set(CMAKE_CXX_STANDARD 20)

add_executable(Parallel main.cpp
        my_barrier.cpp)
