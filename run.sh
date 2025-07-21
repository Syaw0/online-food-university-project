#!/bin/bash

PATH_TO_FX=/home/siavash/javafx-sdk-24.0.2/lib

java --version


mkdir -p out

cp -r src/main/resources/assets out/

# Compile
javac --module-path $PATH_TO_FX --add-modules javafx.controls -d out $(find src/main/java -name "*.java")


# Run
java --module-path $PATH_TO_FX --add-modules javafx.controls -cp out app.Main
