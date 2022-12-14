/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.hl7.tinkar.common.service.DataServiceController;
import org.hl7.tinkar.entity.EntityService;
import org.hl7.tinkar.provider.websocket.client.WebsocketServiceController;

@SuppressWarnings("module") // 7 in HL7 is not a version reference
module org.hl7.tinkar.provider.websocket.client {
    requires io.activej.eventloop;
    requires io.activej.http;
    requires io.activej.inject;
    requires io.activej.launcher;
    requires io.activej.promise;
    requires io.activej.service;
    requires java.base;
    requires org.hl7.tinkar.common;
    requires org.hl7.tinkar.component;
    requires java.net.http;
    requires org.hl7.tinkar.entity;

    provides DataServiceController
            with WebsocketServiceController;

    uses EntityService;
    opens org.hl7.tinkar.provider.websocket.client
            to io.activej.inject;
}
