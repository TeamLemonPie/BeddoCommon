package de.lemonpie.beddocommon.network.client;

import de.lemonpie.beddocommon.network.CommandName;

public interface Command
{
	CommandName name();

	void execute(ControlCommandData data);
}
