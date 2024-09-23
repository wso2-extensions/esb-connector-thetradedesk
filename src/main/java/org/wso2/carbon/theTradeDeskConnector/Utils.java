package org.wso2.carbon.theTradeDeskConnector;

import org.apache.synapse.MessageContext;

public class Utils {

    /**
     * Sets the error code and error message in message context.
     *
     * @param messageContext Message Context
     * @param errorCode      Error Code
     * @param errorMessage   Error Message
     */
    public static void setErrorPropertiesToMessage(MessageContext messageContext, String errorCode, String errorMessage) {

        messageContext.setProperty(Constants.PROPERTY_ERROR_CODE, errorCode);
        messageContext.setProperty(Constants.PROPERTY_ERROR_MESSAGE, errorMessage);
    }
}