package clientServer;

import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client extends AbstractClientServer
{
	private String id;
	private String name;
	private int listeningUDPPort;
	private HashMap<String, String> clientList;
	private ThreadComunicationClient threadComunicationClient;
	private ThreadListenerUDP threadListenerUDP;

	public Client(String name, int listeningUDPPort)
	{
		this.name = name;
		this.listeningUDPPort = listeningUDPPort;
		this.clientList = new HashMap<String, String>();
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

	}

	@Override
	public void treatIncomeUDP(String message)
	{
		System.out.println(message);
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		Client client = new Client("client1", 30001);
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
			sc.close();
		}
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
