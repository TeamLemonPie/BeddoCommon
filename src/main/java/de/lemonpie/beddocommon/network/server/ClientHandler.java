package de.lemonpie.beddocommon.network.server;

import com.google.gson.Gson;
import de.lemonpie.beddocommon.network.CommandData;
import de.tobias.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler implements Runnable
{

	private static Gson gson;

	static
	{
		gson = new Gson();
	}

	private Thread thread;
	private ControlServerSocket controlServerSocket;

	private Socket socket;
	private BufferedReader inputStream;
	private PrintStream outputStream;

	public ClientHandler(Socket socket, ControlServerSocket controlServerSocket) throws IOException
	{
		this.controlServerSocket = controlServerSocket;

		this.socket = socket;
		this.inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.outputStream = new PrintStream(socket.getOutputStream(), true);

		this.thread = new Thread(this);
	}

	public void start()
	{
		this.thread.start();
	}

	public void interrupt()
	{
		this.thread.interrupt();
	}

	public void write(String data)
	{
		outputStream.println(data); // AutoFlush is enable
	}

	@Override
	public void run()
	{
		try
		{
			String line;
			while((line = inputStream.readLine()) != null)
			{
				// Handle line
				Logger.debug("Read from [" + socket.getRemoteSocketAddress() + "]: " + line);

				CommandData commandData = gson.fromJson(line, CommandData.class);
				CommandExecutor.getInstance().execute(commandData);

				if(thread.isInterrupted())
				{
					break;
				}
			}
		}
		catch(IOException e)
		{
			Logger.info("Connection closed: " + e.getMessage());
			if(controlServerSocket.getSocketListener() != null)
			{
				controlServerSocket.getSocketListener().connectionClosed(socket);
			}
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

	public void close() throws IOException
	{
		if(controlServerSocket.getSocketListener() != null)
		{
			controlServerSocket.getSocketListener().connectionClosed(socket);
		}

		outputStream.close();
		inputStream.close();
		socket.close();
		Logger.info("[" + socket.getRemoteSocketAddress() + "]: Connection closed");
	}
}
