# AutoBashAlias
command line app for Linux for quickly adding new bash aliases for the bash command line

**this is a Linux only app**

# Installation
## Build from sources
if you want to build from sources, clone or download this repository.  
  navigate to the folder you cloned it to and run:  
  ```Shell
    ./gradlew assemble
  ```  
  
this will build the app, generate the start scripts and create the distributable.  
  you can find the .zip and .tar.gz in the build/distributions folder (isn't gradle amazing).  
  extract these (if you don't know how google is your friend) and run install.sh
  
# Usage

the installation script automatically adds the start up script to the list of aliases,
  you can use   
  ```Shell
    addalias <name> <path> [--dry_run (-d)] [--help (-h)] [--target-file (-f) <file>] [--verbose (-v)]
  ```  
  to execute the app, use addalias --help to get the *full* documentation for the command usage 

    