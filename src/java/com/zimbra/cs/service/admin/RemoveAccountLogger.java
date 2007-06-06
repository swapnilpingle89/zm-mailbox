/*
 * ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 ("License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.zimbra.com/license
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is: Zimbra Collaboration Suite Server.
 * 
 * The Initial Developer of the Original Code is Zimbra, Inc.
 * Portions created by Zimbra are Copyright (C) 2006 Zimbra, Inc.
 * All Rights Reserved.
 * 
 * Contributor(s): 
 * 
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.cs.service.admin;

import java.util.Map;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.common.util.Log;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.Provisioning;
import com.zimbra.cs.account.Provisioning.AccountBy;
import com.zimbra.soap.ZimbraSoapContext;

/**
 * Removes a custom logger from the given account.
 * 
 * @author bburtin
 */
public class RemoveAccountLogger extends AdminDocumentHandler {

    // Support for proxying if the account isn't on this server 
    private static final String[] TARGET_ACCOUNT_PATH = new String[] { AdminConstants.E_ID };
    protected String[] getProxiedAccountPath()  { return TARGET_ACCOUNT_PATH; }

    @Override
    public Element handle(Element request, Map<String, Object> context)
    throws ServiceException {
        ZimbraSoapContext zsc = getZimbraSoapContext(context);
        Provisioning prov = Provisioning.getInstance();
        
        // Look up account
        String id = request.getElement(AdminConstants.E_ID).getText();
        Account account = prov.get(AccountBy.id, id);
        if (account == null) {
            throw AccountServiceException.NO_SUCH_ACCOUNT(id);
        }
        
        // Remove logger
        Element eLogger = request.getElement(AdminConstants.E_LOGGER);
        String category = eLogger.getAttribute(AdminConstants.A_CATEGORY);
        
        ZimbraLog.misc.info("Deleting custom logger: account=%s, category=%s",
            account.getName(), category);
        Log.deleteAccountLogger(category, account.getName());

        // Send response
        Element response = zsc.createElement(AdminConstants.REMOVE_ACCOUNT_LOGGER_RESPONSE);
        return response;
    }
}
