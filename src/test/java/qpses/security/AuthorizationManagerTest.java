/**
 * 
 */
package qpses.security;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthorizationManagerTest {

	private AuthorizationManager manager;

	@Test(expected=java.lang.RuntimeException.class)
	public void testInit() {
		manager = new AuthorizationManagerDefaultImpl();
	}
	
	@Test(expected=java.lang.NullPointerException.class)
	public void testAuthorized() {
		manager.isUserAuthorized(new ArrayList(), "/");
	}

}
