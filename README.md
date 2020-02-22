# A Simple CLI Example

This project represents two basic components of developing
a standalone java application with a CLI Interface.

1. It demonstrates the basic design using a singleton to
   contain and execute a command line argument processor
   as embodied in `edu.isu.cs.grifisaa.production.CLI.java`
   
2. It sets up the ability to construct a shadow jar (a 
   jar file with all the dependencies included).
   Additionally, as this is a gradle project using the
   `application` plugin it provides for the construction
   of scripts for execution.
   
The following subsections will describe the general operation
of gradle for this project.

### Using Apache Commons CLI

The Apache
Commons CLI dependency is added in the `dependencies` block of
the `build.gradle` file using the following:

`implementation 'commons-cli:commons-cli:1.4'`

### Updating the License Header in Source Files

As typical with Open Source projects it is wise to add a license
header to the top of each source file in the project. Towards this
end we have used the "Hierynomus" gradle license plugin as part of
this project. This adds two additional capabilities

1. To check if any source files are missing their license header,
   execute the following command:
   
   `$ gradle licenseCheck`

2. To add/update the license header of each source file (based on
   LICENSE file at the project root) execute the following command:
   
   `$ gradle licenseFormat`
   
### Executing the Main Class

This project uses the gradle "application" plugin which provides
several benefits, including the ability to generate runnable jar
files. Towards this end, we need to add the following lines of code
to the `build.gradle` file:

1. In the `plugins` block we add/verify the following line exists:

   `id 'application'`
   
2. Additionally we add the following block:
   
   ```groovy
   application {
        // Define the main class for the application.
        mainClassName = 'edu.isu.cs.grifisaa.production.App'
   }
   ```
   
   Where in the case of this project the main class is `App` in
   the package `edu.isu.cs.grifisaa.production`
   
### Generating the Installable Product

As most java projects require a significant number of dependencies
on other projects, it becomes difficult to ensure that users will
have these dependencies installed on their machine. In order to
address this issue we have the capability to include dependencies
into our packaged JAR files.

To accomplish this there are several approaches. I suggest using
the Gradle Shadow plugin [(see their user guide)](https://imperceptiblethoughts.com/shadow/introduction/)
which can be included in the `plugins` section of a gradle build
file as follows:

`id 'com.github.johnrengelman.shadow' version '5.2.0'`

This adds several tasks to the process. It should be noted that
when you execute the standard build using the following command:

`$ gradle build`

This will execute the following additional tasks

* shadowJar - constructs and packages the classes and dependencies
  in a JAR file in the folder `build/libs`, with "-all" appended
  before the ".jar" extension.
* shadowDistZip - constructs a zip distribution of the project in
  `build/distributions`
* shadowDistTar - constructs a tar distribution of the project in
  `build/distributions`
  
It will also create a folder `build/scriptsShadow` which contains
the linux and windows scripts for running project from the shadowed
JAR.

### Creating an Executable Java Launcher

* For linux visit: [java-launcher](https://github.com/hausen/java-launcher)
* For Windows
  - Use either [JSmooth](http://jsmooth.sourceforge.net/)
  - Or, [Launch4J](http://launch4j.sourceforge.net/index.html)

### Producing an Installer

* Windows: Use [IZPack](http://izpack.org/)
* Linux: Use the Maven/Gradle Installer Generator
