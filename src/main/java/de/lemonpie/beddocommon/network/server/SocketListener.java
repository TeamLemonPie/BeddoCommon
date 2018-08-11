package de.lemonpie.beddocommon.network.server;

import java.net.Socket;

public interface SocketListener
{

	void connectionEstablished(Socket socket);

	void connectionClosed(Socket socket);
}
