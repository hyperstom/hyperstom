<div align="center">
<img src="https://cdn.discordapp.com/attachments/1143388669240164383/1144835422762049537/emeraldwater1024.png" width="150" height="150">
<h1>EmeraldWater</h1>
</div>

## What is this?
EmeraldWater is an open-source take on [DiamondFire](https://mcdiamondfire.com) written using 
[Minestom](https://minestom.net/) and [Kotlin](https://kotlinlang.org/). A public instance is hosted at `emeraldwater.infernity.dev`

## Notable Features
> **Warning**
> Most of these features are not implemented yet, as EmeraldWater is under development.
### World Plots
Each plot is hosted using 2 Minestom Instances - one for the coding area, and one for the building area.
The building area sizes will include 128x128, 256x256, and 512x512. The coding area sizes will remain 20x128,
but will have plenty of vertical area.
### Functional Paradigm

EmeraldWater focuses in on the functional paradigm - this includes extra special features such as:
- **Immutable Variables** are variables that can not be changed after they are declared
- **Higher-Order Functions** are functions as arguments to other functions or codeblocks
### Advanced Types
EmeraldWater introduces a few new types:
- **Expressions** allow you to easily get, interpolate, and change the types of values through your code.
- **Functions** are references to functions, described above.
- **Iterators** allow you to use the `Set Variable: Next Iteration` code action on them to get the next
item in the list of values they iterate over. Iterators replace **List**s.
- **Exceptions** are errors thrown during the runtime of your code. See below for more information.

### Runtime Errors
EmeraldWater also throws errors during runtime if you do invalid actions such as:
- A codeblock with an invalid action
- Invalid arguments passed to a function or codeblock
- Illegal accesses to lists or dictionaries
- And more!
These exceptions can be caught using the **Try** and **Catch** codeblocks.

## Hosting a Local Instance
You can host a local instance of **EmeraldWater** by doing the following:

### Building from Source
1. Clone the repository locally
2. Make sure you have the needed dependencies installed (Kotlin & Gradle).
3. Create the `./worlds` directory. This is where plots will get stored.
4. Run `./gradlew run`
5. A local instance is now hosted at `127.0.0.1:25565`.


### Using a JAR Download
> **Warning**
> We do not currently have a GitHub workflow setup for this project yet. Feel free to contribute one or 
> wait for one to be added.

Alternatively, you can download a JAR from us.
1. Go to our GitHub releases page and install the latest version.
2. Create the `./worlds` directory in the JAR's folder.
3. Run it with `java -jar emeraldwater.jar`
4. A local instance is now hosted at `127.0.0.1:25565`.