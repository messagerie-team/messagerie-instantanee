package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import network.Protocol;
import network.ProtocolTCP;

/**
 * 
 * Thread de comunication d'un client vers un serveur. Il permet de gérer les
 * demandes client. Connection, Deconnection, demande de lien etc...
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 */
public class ThreadComunicationClient extends Thread {
	/**
	 * Client a qui appartient le thread.
	 * 
	 * @see Client
	 */
	private Client client;
	/**
	 * Socket de communication.
	 * 
	 * @see Socket
	 */
	private Socket socket;
	/**
	 * Protocol permettant de communiquer.
	 * 
	 * @see Protocol
	 */
	private Protocol protocol;
	/**
	 * Parametre permettant d'arrêter le thread.
	 */
	private boolean running;
	/**
	 * Adresse ip du serveur.
	 * 
	 * @see Server
	 */
	private String ipServer;

	/**
	 * Constructeur par défaut de thread de communication.
	 * 
	 * @param client
	 * @param ipServer
	 */
	public ThreadComunicationClient(Client client, String ipServer) {
		this.client = client;
		this.ipServer = ipServer;
	}

	/**
	 * Constructeur de thread de communication. Pas encore utilisé.
	 * 
	 * @param client
	 * @param socket
	 * @param ipServer
	 */
	public ThreadComunicationClient(Client client, Socket socket,
			String ipServer) {
		this.client = client;
		this.socket = socket;
		this.ipServer = ipServer;
		this.protocol = new ProtocolTCP(socket);
	}

	@Override
	public void run() {
		try {
			System.out.println(this.ipServer);
			this.socket = new Socket(this.ipServer,
					this.client.getTcpServerPort());
			this.protocol = new ProtocolTCP(socket);
		} catch (Exception e) {
			System.err
					.println("Erreur du ThreadComunicationClient,Connexion, message: "
							+ e.getMessage());
		}
		try {
			System.out.println("Lancement du Thread de communication");
			this.running = true;
			while (running) {
				Thread.sleep(500);
				// On attent que le serveur nous envoie un message
				String message = protocol.readMessage();
				// On traite ensuite le message reçu.
				this.messageTraitement(message);
			}
			System.out.println("Arret du Thread de communication");
			this.socket.close();
			this.protocol.close();
		} catch (IOException | InterruptedException e) {
			System.err.println("Erreur du ThreadComunicationClient, message: "
					+ e.getMessage());
		}
	}

	/**
	 * Méthode permettant de traiter la réception d'un message.
	 * 
	 * @param message
	 *            message reçu à traité
	 */
	private void messageTraitement(String message) {
		System.out.println("Début du traitement du message : " + message);
		StringTokenizer token = new StringTokenizer(message, ":");
		String firstToken = token.nextToken();
		if (token.hasMoreTokens()) {
			String nextToken = token.nextToken();
			switch (firstToken) {
			case "request":
				this.messageTraitementRequest(nextToken, token);
				break;
			case "reply":
				this.messageTraitementReply(nextToken, token);
				break;
			case "end":
				this.stopThread();
				break;

			default:
				this.stopThread();
				break;
			}
		}
	}

	/**
	 * Méthode permettant de traiter les demandes d'un serveur.
	 * {@link #messageTraitement(String)}
	 * 
	 * @see Server
	 * @param message
	 * @param token
	 */
	private void messageTraitementRequest(String message, StringTokenizer token) {
		switch (message) {
		case "register":
			this.registerClient(token);
			break;
		case "unregister":
			this.unregisterClient(token);
			break;
		case "clientConnection":
			this.getClientConnection(token);
			break;

		default:
			break;
		}
	}

	/**
	 * Message permettant de traiter les réponses d'un serveur.
	 * {@link #messageTraitement(String)}
	 * 
	 * @see Server
	 * @param message
	 * @param token
	 */
	private void messageTraitementReply(String message, StringTokenizer token) {
		switch (message) {
		case "register":
			this.registerClient(token);
			break;
		case "unregister":
			this.unregisterClient(token);
			break;
		case "list":
			this.askListClient(token);
			break;
		case "clientConnection":
			this.getClientConnection(token);
			break;

		default:
			break;
		}
	}

	/**
	 * Méthode permettant traiter le processus de desenregistrement
	 * {@link #messageTraitementReply(String, StringTokenizer)}
	 * {@link #messageTraitementRequest(String, StringTokenizer)}
	 * 
	 * @param token
	 */
	public void unregisterClient(StringTokenizer token) {
		if (token.hasMoreTokens()) {
			String nextToken = token.nextToken();
			switch (nextToken) {
			case "DONE":
				this.client.setId("");
				this.stopThread();
				break;

			default:
				this.stopThread();
				break;
			}
		} else {
			this.protocol.sendMessage("request:unregister:"
					+ this.client.getId());
		}
	}

	/**
	 * Méthode permettant de gérer le processus d'enregistrement.
	 * {@link #messageTraitementReply(String, StringTokenizer)}
	 * {@link #messageTraitementRequest(String, StringTokenizer)}
	 * 
	 * @param token
	 */
	public void registerClient(StringTokenizer token) {
		if (token.hasMoreTokens()) {
			String nextToken = token.nextToken();
			switch (nextToken) {
			case "name":
				if (!token.hasMoreTokens()) {
					this.protocol.sendMessage("reply:register:name:"
							+ this.client.getName());
				}
				break;
			case "password":
				if (!token.hasMoreTokens()) {
					this.protocol.sendMessage("reply:register:password:"
							+ this.client.getPassword());
				}
				break;
			case "port":
				if (!token.hasMoreTokens()) {
					this.protocol.sendMessage("reply:register:port:"
							+ this.client.getListeningUDPPort());
				}
				break;
			case "id":
				if (token.hasMoreTokens()) {
					this.client.setId(token.nextToken());
					this.protocol.sendMessage("reply:register:id:OK");
				} else {
					this.protocol.sendMessage("reply:register:id:ERROR");
					this.stopThread();
				}
				break;
			case "DONE":
				this.stopThread();
				break;
			case "ERROR":
				this.stopThread();
				if (token.hasMoreTokens()) {
					String error = token.nextToken();
					// Faire un traitement en fonction du type d'erreur re�u
					if (("login_password").equals(error))
					{
						this.client.setErrorsMessages("Login/Password incorrect");
					} 
					else 
					{
						this.client.setErrorsMessages("Erreur de connexion");
					}
				} else {
					// Message d'erreur par defaut
					this.client.setErrorsMessages("Erreur de connexion");
				}
				break;
			default:
				this.stopThread();
				break;
			}
		} else {
			this.protocol.sendMessage("request:register");
		}
	}

	/**
	 * Méthode permettant de gerer le processus de demande de list Client au
	 * serveur. {@link #messageTraitementReply(String, StringTokenizer)}
	 * 
	 * @param token
	 */
	public void askListClient(StringTokenizer token) {
		if (token.hasMoreTokens()) {
			String list = token.nextToken();
			this.client.addClientList(list);
			this.protocol.sendMessage("reply:list:DONE");
			this.stopThread();
		} else {
			this.protocol.sendMessage("request:list");
		}
	}

	/**
	 * Méthode permettant de gérer le procedsus de demande d'information de
	 * connexion client.
	 * {@link #messageTraitementReply(String, StringTokenizer)}
	 * {@link #messageTraitementRequest(String, StringTokenizer)}
	 * 
	 * @param token
	 */
	public void getClientConnection(StringTokenizer token) {
		if (token.hasMoreTokens()) {
			// System.out.println(token.nextToken());
			String[] elements = token.nextToken().split(",");
			if (elements.length == 4) {
				ClientServerData client;
				try {
					client = new ClientServerData(elements[0], elements[1],
							InetAddress.getByName(elements[2]),
							Integer.parseInt(elements[3]));

					boolean add = this.client.getClients().add(client);
					System.out.println("Ajout du client recu");
					if (add) {
						protocol.sendMessage("reply:clientConnection:DONE");
						this.stopThread();
					} else {
						protocol.sendMessage("reply:clientConnection:ERROR");
						this.stopThread();
					}
				} catch (NumberFormatException | UnknownHostException e) {
					e.printStackTrace();
				}
			} else {
				if (elements[0].equals("ERROR")) {
					this.stopThread();
				}
			}
		}
	}

	/**
	 * Méthode permettant d'envoyer une demande d'information client au
	 * serveur.
	 * 
	 * @param clientId
	 */
	public void getClientConnection(String clientId) {
		this.protocol.sendMessage("request:clientConnection:" + clientId);
	}

	/**
	 * Méthode permettant d'arrêter le thread
	 */
	public void stopThread() {
		this.running = false;
	}

	/**
	 * Getter du socket du thread
	 * 
	 * @return le socket utilisé
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Setter qui fixe le socket du thread
	 * 
	 * @param socket
	 *            le socket que l'on souhaite utilisé pour le thread
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Getter du protocol du thread
	 * 
	 * @return le protocol utilisé
	 */
	public Protocol getProtocol() {
		return protocol;
	}

	/**
	 * Setter qui fixe le protocol du thread
	 * 
	 * @param protocol
	 *            protocol que l'on souhaite utiliser
	 */
	public void setProtocol(ProtocolTCP protocol) {
		this.protocol = protocol;
	}

	/**
	 * Getter pour savoir si le thread est en route ou non
	 * 
	 * @return l'état du thread, vrai pour en marche, faux sinon
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Setter qui fixe si le thread est en exécution ou non
	 * 
	 * @param running
	 *            vrai pour le mettre en route faux sinon
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
}
