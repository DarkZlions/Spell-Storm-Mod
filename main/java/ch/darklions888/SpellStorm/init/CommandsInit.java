package ch.darklions888.SpellStorm.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;

import ch.darklions888.SpellStorm.SpellStormMain;
import ch.darklions888.SpellStorm.commands.CommandTest;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class CommandsInit {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		//LiteralCommandNode<CommandSource> cmd = dispatcher.register(Commands.literal(SpellStormMain.MODID).then(CommandTest.register(dispatcher)));

	}
}
