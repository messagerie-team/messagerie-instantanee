package clientServer;

public abstract class AbstractClientServer
{
	public abstract void treatIncomeTCP(Object object);
	public abstract void treatIncomeUDP(String message);
}
