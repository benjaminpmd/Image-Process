# Projet S3

## Introduction

This application is a multi purpose command line or graphical interface focused on images treatement.
This application features the following options:
* Directory scan for png, jpg and jpeg images
* EXIF data display of an image
* Steganography system
  * Write a message in an image. If you want to hide a message in a jpg image, the application will simply create a png copy of it to avoid compression.
  * Retrieve a message hidden in a png image.

## Cli version
the cli version must be used in a command terminal, you must navigate to the directory where the jar file is located. From here, you can call it by entering: `java -jar cli.jar`

Different options are available:
* `-h` or `--help` will print the help of the cli in the terminal, you can find here 
