package handlers;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import elements.Net;
import elements.Wall;
import elements.cat.Cat;

public class Collisions implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		System.out.println(fixtureA.getUserData() + " " + fixtureB.getUserData());
		
	}
	

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
		// TODO Auto-generated method stub
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
		// TODO Auto-generated method stub
	}

}
