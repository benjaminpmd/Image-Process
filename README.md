# Image Process

<!---
La ligne ci dessous affiche un badge indiquant si la compilation du projet Maven est réussie ou non, le test est réalisé en utilsant les actions github
-->
![workflow-test](https://github.com/benjaero/Image-Process/actions/workflows/maven-test.yml/badge.svg)

Ce projet est réalisé par Alice MABILLE et Benjamin PAUMARD, étudiants en L2-I dans le groupe TD-C à Cergy-Paris Université. Ce projet met en application les connaissances acquises dans les UE "Programmation orientée Objet" ainsi que "Boite à Outils du Programmeur".

## Introduction
Ce projet vise à la création d'une application bureautique orientée sur le traitement d'image. L'intégralité du code est en Java.
Cette application offre les options suivantes :
* Scan d'un répertoire et affichage des images PNG, JPG et JPEG présentes
* Affichage des données EXIF d'une image
* Système de stéganographie
  * Cache un message dans une image (Crée une nouvelle image au format PNG si l'image d'entrée est dans un autre format)
  * Retrouve un message caché dans une image par l'application

## Exigence
> Cette application est compilée pour fonctionner avec Java 11 ou supérieur. Pour installer Java, veuillez-vous rendre [ici](https://www.oracle.com/java/technologies/downloads/).

L'exécution du code source requiert également l'installation de la librairie [metadata-extractor](https://github.com/drewnoakes/metadata-extractor).


## Utilisation de la version en ligne de commande
La version en ligne de commande doit être utilisée dans le terminal. Pour l'appeler, il suffit de naviguer vers le répertoire où se situe le fichier `cli.jar`. Une fois dans le bon répertoire, l'appel se fait avec la commande suivante :  `java -jar cli.jar`

Vous pouvez ensuite placer les options suivantes :
* `-h` ou `--help` : affiche l'aide de l'application (version cli)
* `-d <chemin d'un répertoire>` : affiche toutes les images contenues dans un répertoire et dans les sous répertoires.
* `-f <chemin d'accès d'une image>` : affiche les métadonnées de l'image.
* `-s <message>` : cache un message dans une image, attention, cette option requiert l'option `-f`. 
* `-e` : affiche un message caché dans une image, attention, cette option requiert l'option `-f`.

## Utilisation de la version graphique

La version graphique de l'application offre les mêmes possibilités, à l'exception de l'exploration d'un répertoire qui se fait via un menu n'affichant que les images. Pour utiliser la version graphique, il est nécessaire de naviguer vers le répertoire ou est situé le fichier `gui.jar`. L'appel de ce dernier se fait par la commande : `java -jar gui.jar`
