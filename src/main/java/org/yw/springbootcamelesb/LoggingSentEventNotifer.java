/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yw.springbootcamelesb;

import java.util.EventObject;
import org.apache.camel.management.event.ExchangeSentEvent;
import org.apache.camel.support.EventNotifierSupport;

/**
 *
 * @author yanwang
 */
public class LoggingSentEventNotifer extends EventNotifierSupport {

    @Override
    public void notify(EventObject event) throws Exception {
        if (event instanceof ExchangeSentEvent) {
            ExchangeSentEvent sentEvent = (ExchangeSentEvent) event;
            log.info("Took {} millis to send to: {}", sentEvent.getTimeTaken(), sentEvent.getEndpoint());
        }
    }

    @Override
    public boolean isEnabled(EventObject event) {
        return true;
    }
}
