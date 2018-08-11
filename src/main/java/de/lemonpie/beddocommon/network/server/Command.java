package de.lemonpie.beddocommon.network.server;

import de.lemonpie.beddocommon.network.CommandName;

public interface Command
{
	CommandName name();

	void execute(CommandData command);
}
