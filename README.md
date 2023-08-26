<div align="center">
<img src="https://cdn.discordapp.com/attachments/1143388669240164383/1144835422762049537/emeraldwater1024.png" width="150" height="150">
<h1>EmeraldWater</h1>
</div>

## What is this?
EmeraldWater is an open-source take on [DiamondFire](https://mcdiamondfire.com) written using 
[Minestom](https://minestom.net/) and [Kotlin](https://kotlinlang.org/).

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