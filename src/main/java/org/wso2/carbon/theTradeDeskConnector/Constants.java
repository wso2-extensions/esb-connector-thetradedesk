package org.wso2.carbon.theTradeDeskConnector;

public class Constants {

    public static final String CONNECTION_NAME = "connectionName";
    public static final String BASE = "base";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TOKEN_EXPIRY = "tokenExpirationInMinutes";
    public static final String PROPERTY_BASE = "uri.var.base";
    public static final String PROPERTY_ERROR_CODE = "ERROR_CODE";
    public static final String PROPERTY_ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String GENERAL_ERROR_MSG = "The Trade Desk connector encountered an error: ";

    static class ErrorCodes {
        public static final String INVALID_CONFIG = "701002";
        public static final String TOKEN_ERROR = "701001";
        public static final String GENERAL_ERROR = "701003";
    }

    static class Auth {

        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String TOKEN_EXPIRY = "tokenExpirationInMinutes";
        public static final String TOKEN = "token";
    }

    public static final String PROPERTY_TTD_AUTH = "_TTD_AUTH_HEADER_";
    public static final String TTD_AUTH_HEADER = "TTD-auth";
    public static final String EQUAL_SIGN = "=";

}