/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2004, 2005, 2006, 2007, 2008, 2009, 2010 Zimbra, Inc.
 *
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.lmtpserver;

import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.tcpserver.ProtocolHandler;
import com.zimbra.cs.tcpserver.TcpServer;

public class TcpLmtpServer extends TcpServer implements LmtpServer {
    public TcpLmtpServer(LmtpConfig config) throws ServiceException {
        super("LmtpServer", config);
    }

    @Override
    protected ProtocolHandler newProtocolHandler() {
        return new TcpLmtpHandler(this);
    }

    @Override
    public LmtpConfig getConfig() {
        return (LmtpConfig) super.getConfig();
    }
}
