package de.lemonpie.beddocommon;

import de.tobias.utils.settings.FilePath;
import de.tobias.utils.settings.Key;

@FilePath("network.yml")
public class ServerConnectionSettings
{
	@Key
	private String hostName;
	@Key
	private boolean discover = true;
	@Key
	private int port;

	public ServerConnectionSettings()
	{

	}

	public ServerConnectionSettings(String hostName, int port)
	{
		this.hostName = hostName;
		this.port = port;
	}

	public String getHostName()
	{
		return hostName;
	}

	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	public boolean isDiscover()
	{
		return discover;
	}

	public void setDiscover(boolean discover)
	{
		this.discover = discover;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	@Override
	public String toString()
	{
		return "Settings [hostName=" + hostName + ", port=" + port + "]";
	}
}