package org.wso2.carbon.theTradeDeskConnector;

public class Constants {
    public static final String PROPERTY_ERROR_CODE = "ERROR_CODE";
    public static final String PROPERTY_ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String GENERAL_ERROR_MSG = "The Trade Desk connector encountered an error: ";

    static class ErrorCodes {
        public static final String INVALID_CONFIG = "701002";
        public static final String GENERAL_ERROR = "701003";
    }

    public static final String PROPERTY_TTD_AUTH = "_TTD_AUTH_HEADER_";
}
