package de.lemonpie.beddocommon.network.server;

import de.lemonpie.beddocommon.network.Command;
import de.lemonpie.beddocommon.network.CommandData;
import de.lemonpie.beddocommon.network.CommandName;
import de.lemonpie.beddocommon.network.Scope;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor
{

	private static CommandExecutor instance;

	public static CommandExecutor getInstance()
	{
		if(instance == null)
		{
			instance = new CommandExecutor();
		}
		return instance;
	}

	private Map<Scope, Map<CommandName, Command>> commands;

	private CommandExecutor()
	{
		commands = new HashMap<>();
	}

	public void addCommand(Command command, Scope scope)
	{
		if(!commands.containsKey(scope))
		{
			commands.put(scope, new HashMap<>());
		}
		this.commands.get(scope).put(command.name(), command);
	}

	public void execute(CommandData commandData)
	{
		commands.get(commandData.getScope()).forEach((name, command) -> {
			if(name.getName().equals(commandData.getCommand()))
			{
				command.execute(commandData);
			}
		});
	}
}
