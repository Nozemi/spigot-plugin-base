package io.nozemi.minecraft.commands

import dev.alangomes.springspigot.context.Context
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import picocli.CommandLine

@Component
@CommandLine.Command(name = "heal")
class TestCommand @Autowired constructor(private var context: Context) : Runnable {

    override fun run() {
        val player = context.player
        player.health = 20.0
        player.sendMessage("You have been healed!");
    }

}