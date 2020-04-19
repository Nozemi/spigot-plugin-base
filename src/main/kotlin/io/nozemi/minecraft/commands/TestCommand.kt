package io.nozemi.minecraft.commands;

import dev.alangomes.springspigot.context.Context;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@CommandLine.Command(name = "heal")
public class TestCommand implements Runnable {

    private final Context context;

    @Autowired
    public TestCommand(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        Player player = context.getPlayer();
        player.setHealth(20);
    }
}
