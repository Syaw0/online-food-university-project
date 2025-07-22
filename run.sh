#!/bin/bash

PATH_TO_FX=/home/siavash/javafx-sdk-24.0.2/lib




rm -rf ./out


mkdir -p out

cp -r src/main/resources/assets out/

npx postcss src/main/resources/assets/style.css -o out/assets/style.css



# Compile
javac --module-path $PATH_TO_FX --add-modules javafx.controls -d out $(find src/main/java -name "*.java")


# Run
java --module-path $PATH_TO_FX --add-modules javafx.controls -cp out app.Main
