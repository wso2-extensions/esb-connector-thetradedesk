package org.wso2.carbon.theTradeDeskConnector;

public interface TokenStore {

    /**
     * Function to get token.
     */
    Token get(String tokenKey);

    /**
     * Function to add token to the store.
     */
    void add(String tokenKey, Token token);

    /**
     * Function to remove token from the store.
     */
    Token remove(String tokenKey);

    /**
     * Function to clean token store.
     */
    void clean();
}
