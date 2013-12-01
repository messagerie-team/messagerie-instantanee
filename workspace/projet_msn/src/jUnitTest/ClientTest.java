package jUnitTest;

import static org.junit.Assert.*;

import org.junit.Test;

import clientServer.Client;
/**
 * 
 * @author Dorian, Mickaël, Raphaël, Thibault
 * 
 */
public class ClientTest 
{

	@Test
	public void testTreatIncomeTCP() 
	{
		Client Ctest = new Client("TestUnitaire",3009,"localhost");
		Ctest.treatIncomeTCP("JUnit");
	}

	@Test
	public void testTreatIncomeUDP()
	{
		Client Ctest = new Client("TestUnitaire",3009,"localhost");
		//test avec un message bien formé
		Ctest.treatIncomeUDP("dialog:ALLLOOOO");
		//test avec message mal formé
		Ctest.treatIncomeUDP("toto");
	}

	@Test
	public void testClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegisterToServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnregisterToServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testAskListToServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testAskClientConnectionToServer() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartDialogToClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendMessageToClient() {
		fail("Not yet implemented");
	}

	@Test
	public void testLaunchThread() {
		fail("Not yet implemented");
	}

	@Test
	public void testTreatIncomeDialog() {
		fail("Not yet implemented");
	}
}
