package ocsf.server;

import java.io.IOException;

public interface iConnector {

	/**
	   * Sends an object to the client.
	   *
	   * @param msg the message to be sent.
	   * @exception IOException if an I/O error occur when sending the
	   *    message.
	   */
	void sendToClient(Object msg) throws IOException;

}