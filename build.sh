#!/bin/bash

HOME=/home/mayola/.minecraft/mods
ARC=star_wars_mod-1.0.0.jar
rm $HOME/$ARC
./gradlew build

cp ./build/libs/$ARC $HOME
