package io.nozemi.minecraft

import dev.alangomes.springspigot.SpringSpigotInitializer
import org.bukkit.plugin.java.JavaPlugin
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.ResourceLoader

class PluginName : JavaPlugin() {

    private var context: ConfigurableApplicationContext? = null

    override fun onEnable() {
        val loader: ResourceLoader = DefaultResourceLoader(classLoader)
        val application = SpringApplication(loader, Application::class.java)
        application.addInitializers(SpringSpigotInitializer(this))
        context = application.run()
    }

    override fun onDisable() {
        context!!.close()
        context = null
    }
}