package clientServer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import dataLink.Protocol;
import dataLink.ProtocolUDP;

public class Client extends AbstractClientServer
{
	private String id;
	private String name;
	private HashMap<String, String> clientList;
	private Vector<ClientDialog> dialogs;
	private ThreadComunicationClient threadComunicationClient;
	private int listeningUDPPort;
	private ThreadListenerUDP threadListenerUDP;

	public Client(String name, int listeningUDPPort)
	{
		this.name = name;
		this.listeningUDPPort = listeningUDPPort;
		this.clientList = new HashMap<String, String>();
		this.dialogs = new Vector<ClientDialog>();
		this.threadComunicationClient = new ThreadComunicationClient(this);
		this.threadListenerUDP = new ThreadListenerUDP(listeningUDPPort);
		this.threadListenerUDP.start();
	}

	public void registerToServer()
	{
		try
		{
			this.launchThread();
			Thread.sleep(500);
			threadComunicationClient.registerClient(new StringTokenizer(""));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void unregisterToServer()
	{
		try
		{
			this.launchThread();
			Thread.sleep(500);
			this.threadComunicationClient.unregisterClient(new StringTokenizer(""));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void askListToServer()
	{
		try
		{
			this.launchThread();
			Thread.sleep(500);
			this.threadComunicationClient.askListClient(new StringTokenizer(""));
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

	}

	public void askClientConnectionToServer(String clientId)
	{
		try
		{
			this.launchThread();
			Thread.sleep(500);
			this.threadComunicationClient.getClientConnection(clientId);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void starDialogToClient(String idClient, String ip, String port)
	{
		try
		{
			ClientDialog dialog = new ClientDialog(this.listeningUDPPort);
			dialog.addClient(new ClientServerData(this.clientList.get(idClient), InetAddress.getByName(ip), Integer.parseInt(port)));
			String idDialog = dialog.getIdDialog();
			Protocol protocol = new ProtocolUDP();
			protocol.sendMessage("dialog:newDialog:" + idDialog, InetAddress.getByName(ip), Integer.parseInt(port));
			protocol.sendMessage("dialog:newDialog:clients:" + idDialog + ":" + this.id, InetAddress.getByName(ip), Integer.parseInt(port));
		} catch (NumberFormatException | UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void launchThread()
	{
		if (this.threadComunicationClient.isInterrupted())
		{
			this.threadComunicationClient.start();
		} else
		{
			this.threadComunicationClient = new ThreadComunicationClient(this);
			this.threadComunicationClient.start();
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public HashMap<String, String> getClientList()
	{
		return clientList;
	}

	public void setClientList(HashMap<String, String> clientList)
	{
		this.clientList = clientList;
	}

	public void addClientList(String list)
	{
		StringTokenizer token = new StringTokenizer(list, "|");
		while (token.hasMoreTokens())
		{
			String element = token.nextToken();
			String elements[] = element.split("-");
			if (elements.length > 1 && !elements[0].equals(this.id))
			{
				this.clientList.put(elements[0], elements[1]);
			}
		}
		System.out.println(this.clientList);
	}

	public int getListeningUDPPort()
	{
		return listeningUDPPort;
	}

	public void setListeningUDPPort(int listeningUDPPort)
	{
		this.listeningUDPPort = listeningUDPPort;
	}

	@Override
	public void treatIncomeTCP(Object object)
	{
		// TODO Auto-generated method stub
		// Pour le moment pas d'income TCP a géré vue que aucune machine ne se
		// connecte a un client en TCP
	}

	@Override
	public void treatIncomeUDP(String message)
	{
		System.out.println(message);
		StringTokenizer token = new StringTokenizer(message, ":");
		String firstToken = token.nextToken();
		switch (firstToken)
		{
		case "dialog":
			this.treatIncomeDialog(token);
			break;

		default:
			break;
		}

	}

	public void treatIncomeDialog(StringTokenizer token)
	{
		if (token.hasMoreTokens())
		{
			String nextToken = token.nextToken();
			switch (nextToken)
			{
			case "newDialog":
				// Dans le cas d'une demande de dialogue d'un autre client
				if (token.hasMoreTokens())
				{
					// On récupère l'id de conversation
					String idDialog = token.nextToken();
					// SI c'est bien un id de conversation, alors on crée la
					// conversation
					if (idDialog.length() > 5)
					{
						// On crée le dialog
						this.dialogs.add(new ClientDialog(idDialog, this.listeningUDPPort));
					}

					else if (idDialog.equals("clients"))
					{
						if (token.hasMoreTokens())
						{
							String realIdDialog = token.nextToken();
							if (token.hasMoreTokens())
							{
								ClientDialog dialog = null;
								for (ClientDialog dialogL : this.dialogs)
								{
									if (dialog.getIdDialog().equals(realIdDialog))
									{
										dialog = dialogL;
									}
								}
								if (dialog != null)
								{
									String[] clients = token.nextToken().split("|");
									for (String client : clients)
									{
										if(this.clientList.containsKey(client))
										{
											//dialog.addClient(new ClientServerData(this.clientList.get(client), ip, port));
										}
									}
								}
							}
						}
					}
				}
				/*
				 * // Dans le cas d'une génération de dialog de notre part
				 * 
				 * else { ClientDialog dialog = new
				 * ClientDialog(this.listeningUDPPort);
				 * this.dialogs.add(dialog);
				 * dialog.sendMessage("dialog:newDialog:" +
				 * dialog.getIdDialog()); } break;
				 */

			default:
				if (token.hasMoreTokens())
				{
					String idDialog = token.nextToken();
					// SI c'est bien un id de conversation, alors on crée la
					// conversation
					if (idDialog.length() > 5 && token.hasMoreTokens())
					{
						for (ClientDialog dialog : this.dialogs)
						{
							if (dialog.getIdDialog().equals(idDialog))
							{
								// On récupère le message
								String message = token.nextToken();
								while (token.hasMoreTokens())
								{
									message += ":" + token.nextToken();
								}
								// On indique qu'on a reçu un message
								dialog.receiveMessage(message);
							}
						}
					}
				}
				break;
			}
		} else
		{

		}
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		Client client = new Client("client12", 30002);
		boolean running = true;
		while (running)
		{
			System.out.println("-register");
			System.out.println("-unregister");
			System.out.println("-list");
			System.out.println("-connectionClient");
			System.out.println("-refresh");
			System.out.println("-close");
			System.out.print("lecture=");
			switch (sc.nextLine())
			{
			case "register":
				client.registerToServer();
				break;
			case "unregister":
				client.unregisterToServer();
				break;
			case "list":
				client.askListToServer();
				break;
			case "connectionClient":
				Object[] list = client.clientList.keySet().toArray();
				System.out.println("Liste des clients:");
				for (int i = 0; i < list.length; i++)
				{
					System.out.println("-" + i + ":" + client.clientList.get(list[i]));
				}
				System.out.println("entré le numéro d'ordre dans la liste:");
				String num = sc.nextLine();
				client.askClientConnectionToServer((String) list[Integer.parseInt(num)]);
				break;
			case "refrech":
				break;
			case "close":
				running = false;
				break;

			default:
				break;
			}

		}
		sc.close();
		/*
		 * client.registerToServer(); while (client.id == null) { try {
		 * Thread.sleep(5000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } }
		 * System.out.println("Demande de liste"); client.askListToServer(); try
		 * { Thread.sleep(5000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * System.out.println("Demande de deco"); client.unregisterToServer();
		 */
	}
}
