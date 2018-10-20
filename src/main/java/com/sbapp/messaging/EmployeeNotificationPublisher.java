package com.sbapp.messaging;

import java.util.Observable;

import org.springframework.stereotype.Component;

/*
 * Class to serve as publisher in order to demonstrate Pub/Sub pattern.
 * Note: Instead of implementing the solution completely 
 * we are using java provided support for Subject in the form of Observable.
 */
@Component
public class EmployeeNotificationPublisher extends Observable {
	
	/**
     * Send notification to all the subscribers
     *
     * @param notification - notification to be published
     */
	public void sendNotification(String notification) {
		setChanged();
        notifyObservers("Notification : " + notification);
	}
}
