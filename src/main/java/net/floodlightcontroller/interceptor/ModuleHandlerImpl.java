/*
 * Copyright (c) 2015 NetIDE Consortium and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package net.floodlightcontroller.interceptor;

import eu.netide.lib.netip.ModuleAnnouncementMessage;
import eu.netide.lib.netip.NetIDEProtocolVersion;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @author giuseppex.petralia@intel.com
 *
 */
public class ModuleHandlerImpl implements IModuleHandler {

	public HashMap<String, Integer> moduleRegistry;

	private ZeroMQBaseConnector coreConnector;
	
	public int whuwzp_module_flag = 0;
	private static final Logger LOG = LoggerFactory.getLogger(ModuleHandlerImpl.class);

	public ModuleHandlerImpl(ZeroMQBaseConnector connector) {
		moduleRegistry = new HashMap<>();
		coreConnector = connector;
	}

	@Override
	public int getModuleId(int xId, String moduleName) {
		if (moduleRegistry.containsKey(moduleName)) {
			return moduleRegistry.get(moduleName);
		}

		return -1;
	}

	@Override
	public String getModuleName(int moduleID) {
		for (String moduleName : moduleRegistry.keySet()) {
			if (moduleRegistry.get(moduleName) == moduleID)
				return moduleName;
		}
		return null;
	}

	@Override
	public void obtainModuleId(int xId, String moduleName) {
		LOG.debug("ObtainModuleID for " + moduleName);
		ModuleAnnouncementMessage msg = new ModuleAnnouncementMessage();
		msg.getHeader().setDatapathId(0);
		msg.getHeader().setModuleId(0);
		msg.getHeader().setNetIDEProtocolVersion(NetIDEProtocolVersion.VERSION_1_4);
		msg.getHeader().setTransactionId(xId);
		msg.setModuleName(moduleName);
//		msg.setModuleName("floodlight-backend");
		
		System.out.println("--------ANN message : " + msg);
		sendToCore(msg);
		boolean ack = false;

		while (!ack) {
			if (moduleRegistry.containsKey(moduleName)) {
				ack = true;
			}
		}

	}
	
	public void obtainModuleId_test(int xId, String moduleName, int module_id) {
		LOG.debug("ObtainModuleID for " + moduleName);
		ModuleAnnouncementMessage msg = new ModuleAnnouncementMessage();
		msg.getHeader().setDatapathId(0);
		msg.getHeader().setModuleId(module_id);
		msg.getHeader().setNetIDEProtocolVersion(NetIDEProtocolVersion.VERSION_1_4);
		msg.getHeader().setTransactionId(xId);
//		msg.setModuleName(moduleName);
		msg.setModuleName("learningswitch");
		
		System.out.println("--------ANN message : " + msg);
		sendToCore(msg);
		boolean ack = false;

		while (!ack) {
			if (moduleRegistry.containsKey(moduleName)) {
				ack = true;
			}
		}

	}

	@Override
	public void onModuleAckMessage(String moduleName, int moduleId) {
//		LOG.info("Module name : " + moduleName + " ModuleId: " + moduleId);
		moduleRegistry.put(moduleName, moduleId);

//		if (whuwzp_module_flag == 0) {
//			obtainModuleId_test(0, "learningswitch", moduleId);
//			whuwzp_module_flag += 1;
//		}
	}

	public void sendToCore(ModuleAnnouncementMessage msg) {
		coreConnector.SendData(msg.toByteRepresentation());
		System.out.println("--------ANN message toByte : " + msg.toByteRepresentation());
		
	}

}
