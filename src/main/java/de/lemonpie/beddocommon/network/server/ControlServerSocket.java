package de.lemonpie.beddocommon.network.server;

import com.google.gson.Gson;
import de.tobias.logger.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public abstract class ControlServerSocket implements Closeable
{

	private ServerSocket serverSocket;
	private ConnectionHandler connectionHandler;

	private SocketListener socketListener;

	public ControlServerSocket(String host, int port)
	{
		this(new InetSocketAddress(host, port));
	}

	public ControlServerSocket(InetAddress host, int port)
	{
		this(new InetSocketAddress(host, port));
	}

	public ControlServerSocket(InetSocketAddress socketAddress)
	{
		try
		{
			serverSocket = new ServerSocket();
			serverSocket.bind(socketAddress);

			init();

			connectionHandler = new ConnectionHandler(this);
			connectionHandler.start();
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	protected abstract void init();

	public SocketListener getSocketListener()
	{
		return socketListener;
	}

	public void setSocketListener(SocketListener socketListener)
	{
		this.socketListener = socketListener;
	}

	public void writeAll(CommandData command)
	{
		Gson gson = new Gson();
		final String data = gson.toJson(command);
		Logger.debug("Write All: " + data);
		writeAll(data);
	}

	public void writeAll(String data)
	{
		for(ClientHandler client : connectionHandler.getClientHandlers())
		{
			client.write(data);
		}
	}

	@Override
	public void close() throws IOException
	{
		if(connectionHandler != null)
		{
			connectionHandler.interrupt();
			connectionHandler.close();
		}

		if(serverSocket != null)
		{
			serverSocket.close();
			serverSocket = null;
		}
	}

	ServerSocket getServerSocket()
	{
		return serverSocket;
	}

	ConnectionHandler getConnectionHandler()
	{
		return connectionHandler;
	}

	public int count()
	{
		return connectionHandler.getClientHandlers().size();
	}
}
