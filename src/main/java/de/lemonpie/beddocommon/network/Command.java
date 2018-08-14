package de.lemonpie.beddocommon.network;

public interface Command
{
	CommandName name();

	void execute(CommandData command);
}
