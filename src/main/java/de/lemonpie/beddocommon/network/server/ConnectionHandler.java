package de.lemonpie.beddocommon.network.server;

import de.tobias.logger.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHandler implements Runnable
{

	private Thread thread;
	private ControlServerSocket serverSocket;

	private List<ClientHandler> clientHandlers = new ArrayList<>();

	public ConnectionHandler(ControlServerSocket serverSocket)
	{
		this.serverSocket = serverSocket;
		this.thread = new Thread(this);
	}

	public void start()
	{
		thread.start();
	}

	public void interrupt()
	{
		thread.interrupt();
	}

	public void close() throws IOException
	{
		for(ClientHandler clientHandler : clientHandlers)
		{
			clientHandler.interrupt();
			clientHandler.close();
		}
	}

	@Override
	public void run()
	{
		try
		{
			Socket socket;
			while((socket = serverSocket.getServerSocket().accept()) != null)
			{
				Logger.info("[" + socket.getRemoteSocketAddress() + "]: Connection established");

				// Handle Client
				ClientHandler clientHandler = new ClientHandler(socket, serverSocket);
				clientHandler.start();
				clientHandlers.add(clientHandler);

				if(serverSocket.getSocketListener() != null)
				{
					serverSocket.getSocketListener().connectionEstablished(socket);
				}

				// Stop Thread
				if(thread.isInterrupted())
				{
					break;
				}
			}
		}
		catch(IOException e)
		{
			Logger.error(e);
		}
	}

	public List<ClientHandler> getClientHandlers()
	{
		return clientHandlers;
	}
}
