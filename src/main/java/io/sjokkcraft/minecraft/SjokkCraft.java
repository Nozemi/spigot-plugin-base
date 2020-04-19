package io.sjokkcraft.minecraft;

import dev.alangomes.springspigot.SpringSpigotInitializer;
import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class SjokkCraft extends JavaPlugin {

    private ConfigurableApplicationContext context;

    @Override
    public void onEnable() {
        ResourceLoader loader = new DefaultResourceLoader(getClassLoader());
        SpringApplication application = new SpringApplication(loader, Application.class);
        application.addInitializers(new SpringSpigotInitializer(this));
        context = application.run();
    }

    @Override
    public void onDisable() {
        context.close();
        context = null;
    }
}
