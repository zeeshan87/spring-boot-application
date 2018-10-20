package com.sbapp.messaging;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

/*
 * Class to serve as subscriber in order to demonstrate Pub/Sub pattern.
 * Note: Instead of implementing the solution completely 
 * we are using java provided support for Observer.
 */
@Component
public class EmployeeNotificationSubscriber implements Observer {
	private static final Log LOG = LogFactory.getLog(EmployeeNotificationSubscriber.class);
	@Override
	/**
	 * To update the subscribed object about the changes being done
	 * in the publisher in order to consume it.
	 * @param observable - the publisher object.
	 * @param notification - an argument passed to the notifyObserversmethod i.e. the notification for it.
	 */
	public void update(Observable observable, Object notification) {
		LOG.info("-------- Received Notification ---------");
		LOG.info(notification);
	}	

}
