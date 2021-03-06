package de.lemonpie.beddocommon.network.client;

import com.google.gson.Gson;
import de.lemonpie.beddocommon.ServerConnectionSettings;
import de.lemonpie.beddocommon.network.Command;
import de.lemonpie.beddocommon.network.CommandData;
import de.lemonpie.beddocommon.network.CommandName;
import de.tobias.logger.Logger;
import de.tobias.utils.net.DiscoveryClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class ControlSocket implements Runnable
{
	public static final int MAX = 10;
	public static final int SLEEP_TIME = 5000;

	private static Gson gson;

	static
	{
		gson = new Gson();
	}

	private Map<CommandName, Command> commands;

	private ServerConnectionSettings settings;
	private String host;
	private int port;

	private Socket socket;
	private BufferedReader inputStream;
	private PrintWriter outputStream;

	private ControlSocketDelegate delegate;

	private Thread readerThread;

	public ControlSocket(ServerConnectionSettings settings, ControlSocketDelegate delegate)
	{
		this.settings = settings;
		if(!settings.isDiscover())
		{
			this.host = settings.getHostName();
		}
		this.port = settings.getPort();
		this.delegate = delegate;

		commands = new HashMap<>();
		init();
	}

	private void init()
	{
		if(delegate != null)
		{
			delegate.init(this);
		}
	}

	public void addCommand(Command command)
	{
		commands.put(command.name(), command);
	}

	public String getHost()
	{
		return host;
	}

	public boolean connect()
	{
		if(host == null && settings.isDiscover())
		{
			InetAddress discover = null;

			while(discover == null)
			{
				DiscoveryClient discoveryClient = new DiscoveryClient();
				discoveryClient.setPort(9990);
				discoveryClient.setMessageKey("BEDDOMISCHER");
				discover = discoveryClient.discover();
				if(discover != null)
				{
					host = discover.getHostAddress();
				}
			}
		}

		delegate.startConnecting(host, port);

		int counter = 0;
		while(counter < MAX)
		{
			try
			{
				initConnection();

				if(delegate != null)
				{
					delegate.onConnectionEstablished();
				}
				break;
			}
			catch(IOException e)
			{
				delegate.onConnectionFailed(e, counter);

				try
				{
					Thread.sleep(SLEEP_TIME);
				}
				catch(InterruptedException e1)
				{
					Logger.error(e1);
					break;
				}
			}
			counter++;
		}
		return socket.isConnected();
	}

	private void initConnection() throws IOException
	{
		socket = new Socket();
		socket.connect(new InetSocketAddress(host, port));

		inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		outputStream = new PrintWriter(socket.getOutputStream(), true);

		readerThread = new Thread(this);
		readerThread.start();
	}

	public void interrupt()
	{
		readerThread.interrupt();
	}

	public void close() throws IOException
	{
		if(readerThread != null)
		{
			readerThread.interrupt();
		}

		inputStream.close();
		outputStream.close();
		socket.close();

		if(delegate != null)
		{
			delegate.onConnectionClosed();
		}
	}

	public void write(CommandData command) throws SocketException
	{
		if(isNewValueComingFromServer())
		{
			return;
		}

		Logger.debug("Write {0}", gson.toJson(command));
		write(gson.toJson(command));
	}

	public void write(String data) throws SocketException
	{
		if(isNewValueComingFromServer())
		{
			return;
		}

		if(!socket.isClosed())
		{
			outputStream.println(data); // AutoFlush is enable
		}
		else
		{
			if(connect())
			{
				write(data);
			}
			else
			{
				throw new SocketException("Socket closed");
			}
		}
	}

	public static boolean isNewValueComingFromServer()
	{
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for(StackTraceElement element : stackTrace)
		{
			try
			{
				if(element.getClassName().startsWith("de."))
				{
					Class<?> clazz = Class.forName(element.getClassName());
					if(Command.class.isAssignableFrom(clazz))
					{
						return true;
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void run()
	{
		try
		{
			String line;
			while((line = inputStream.readLine()) != null)
			{
				Logger.debug("Read {0}", line);
				CommandData commandData = gson.fromJson(line, CommandData.class);

				commands.forEach((name, command) -> {
					if(name.getName().equalsIgnoreCase(commandData.getCommand()))
					{
						command.execute(commandData);
					}
				});

				if(Thread.interrupted())
				{
					break;
				}
			}
		}
		catch(IOException e)
		{
			Logger.error(e);
		}
		finally
		{
			try
			{
				close();
			}
			catch(IOException e)
			{
				Logger.error(e);
			}
		}
	}
}
