# SpigotPluginBase

Spigot plugin base using [Spring for dependency injection](https://github.com/Alan-Gomes/mcspring-boot). YIKES!

Although Spring might not be ideal to use in a plugin, the Gradle setup used in this project might prove useful to you.
I made a few gradle tasks that will make life of plugin developers easier (I think).

##### How to use?
Just clone, fork or download the source code, make sure to change the [gradle.properties](/gradle.properties) file appropriately for your plugin. I personally used 1.14.4, so you should change that if you use something else.

##### Gradle Tasks
- cleanProject - This task cleans the project (also runs the default :clean task).
- copyPluginToTestServer - This task depends on build, and copies the built plugin JAR to the test server.
- downloadSpigotServer - This task will download Spigot server JAR from [Get Bukkit](https://getbukkit.org/), and put it in the testserver directory.
- fatJar - Creates a JAR file for the plugin with all the necessary dependencies.
- setupTestServer - Copies the files from config/devserver into testserver directory. (Example eula.txt which is required to run the server)
- runTestServer - Runs the test server with the plugin, also takes care of all the other steps listed above.
