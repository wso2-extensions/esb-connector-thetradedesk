package org.wso2.carbon.theTradeDeskConnector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TokenManager {

    private static final Log log = LogFactory.getLog(TokenManager.class);
    private static final TokenStore TOKEN_STORE = new InMemoryTokenStore();

    private TokenManager() {

    }

    /**
     * Function to add access token for given client ID and token endpoint.
     */
    public static void addToken(String resourceKey, Token token) {

        TOKEN_STORE.add(resourceKey, token);
    }

    /**
     * Function to get access token for given client ID and token endpoint.
     */
    public static Token getToken(String resourceKey) {

        return TOKEN_STORE.get(resourceKey);
    }

    /**
     * Function to remove token from the token cache.
     */
    public static void removeToken(String resourceKey) {

        TOKEN_STORE.remove(resourceKey);
    }

    /**
     * Clean all access tokens from the token cache.
     */
    public static void clean() {

        TOKEN_STORE.clean();
        if (log.isDebugEnabled()) {
            log.debug("Token map cleaned.");
        }
    }
}

