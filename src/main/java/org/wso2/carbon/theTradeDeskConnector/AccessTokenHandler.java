package org.wso2.carbon.theTradeDeskConnector;

/*
 *  Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
 *
 *  WSO2 LLC. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;
import org.wso2.carbon.connector.core.util.ConnectorUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccessTokenHandler extends AbstractConnector {

    private static final Log log = LogFactory.getLog(AccessTokenHandler.class);
    private static final JsonParser parser = new JsonParser();
    private static final String ERROR_MESSAGE = Constants.GENERAL_ERROR_MSG + "\"username\", \"password\", parameters are mandatory.";
    private static final String tokenEndpoint = "http://localhost:9090/v3/authentication";

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        String connectionName = (String) getParameter(messageContext, Constants.CONNECTION_NAME);
        System.out.println("Connection name: " + connectionName);

        String base = (String) getParameter(messageContext, Constants.BASE);
        if (StringUtils.endsWith(base, "/")) {
            base = StringUtils.removeEnd(base, "/");
        }
        messageContext.setProperty(Constants.PROPERTY_BASE, base);

        System.out.println("BASE: " + base);
        String username = (String) getParameter(messageContext, Constants.USERNAME);
        String password = (String) getParameter(messageContext, Constants.PASSWORD);
        int tokenExpiry = 1440;

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            Utils.setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.INVALID_CONFIG, ERROR_MESSAGE);
            handleException(ERROR_MESSAGE, messageContext);
        }
        Map<String, String> payloadParametersMap = new HashMap<>();
        payloadParametersMap.put(Constants.Auth.USERNAME, username);
        payloadParametersMap.put(Constants.Auth.PASSWORD, password);
        payloadParametersMap.put(Constants.Auth.TOKEN_EXPIRY, String.valueOf(tokenExpiry));

        Token token = TokenManager.getToken(getTokenKey(connectionName, tokenEndpoint, payloadParametersMap));
        if (token == null || !token.isActive()) {
            if (token != null && !token.isActive()) {
                TokenManager.removeToken(getTokenKey(connectionName, tokenEndpoint, payloadParametersMap));
            }
            if (log.isDebugEnabled()) {
                if (token == null) {
                    log.debug("Token does not exists in token store.");
                } else {
                    log.debug("Access token is inactive.");
                }
            }
            token = getAndAddNewToken(messageContext, connectionName, payloadParametersMap);
        }
        String accessToken = token.getAccessToken();
        messageContext.setProperty(Constants.PROPERTY_TTD_AUTH, accessToken);
    }

    /**
     * Function to get new token.
     */
    protected synchronized Token getAndAddNewToken(MessageContext messageContext, String connectionName,
                                                   Map<String, String> payloadParametersMap) {

        Token token = TokenManager.getToken(getTokenKey(connectionName, tokenEndpoint, payloadParametersMap));
        if (token == null || !token.isActive()) {
            token = getAccessToken(messageContext, payloadParametersMap);
            TokenManager.addToken(getTokenKey(connectionName, tokenEndpoint, payloadParametersMap), token);
        }
        return token;
    }

    /**
     * Function to retrieve new client credential access token from the token endpoint.
     */
    protected Token getAccessToken(MessageContext messageContext, Map<String, String> payloadParametersMap) {

        if (log.isDebugEnabled()) {
            log.debug("Retrieving new access token from token endpoint.");
        }

        long curTimeInMillis = System.currentTimeMillis();
        HttpPost postRequest = new HttpPost(tokenEndpoint);

        ArrayList<NameValuePair> parameters = new ArrayList<>();

        for (Map.Entry<String, String> entry : payloadParametersMap.entrySet()) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        try {
            postRequest.setEntity(new UrlEncodedFormEntity(parameters));
        } catch (UnsupportedEncodingException e) {
            String errorMessage = Constants.GENERAL_ERROR_MSG + "Error occurred while preparing access token request payload.";
            Utils.setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.TOKEN_ERROR, errorMessage);
            handleException(errorMessage, messageContext);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(postRequest)) {
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity == null) {
                String errorMessage = Constants.GENERAL_ERROR_MSG + "Failed to retrieve access token : No entity received.";
                Utils.setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.TOKEN_ERROR, errorMessage);
                handleException(errorMessage, messageContext);
            }

            int responseStatus = response.getStatusLine().getStatusCode();
            String respMessage = EntityUtils.toString(responseEntity);
            if (responseStatus == HttpURLConnection.HTTP_OK) {
                JsonElement jsonElement = parser.parse(respMessage);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                String accessToken = jsonObject.get(Constants.Auth.TOKEN).getAsString();
                long expireIn = Long.parseLong(payloadParametersMap.get(Constants.Auth.TOKEN_EXPIRY));
                return new Token(accessToken, curTimeInMillis, expireIn * 1000);
            } else {
                String errorMessage = Constants.GENERAL_ERROR_MSG + "Error occurred while retrieving access token. Response: "
                        + "[Status : " + responseStatus + " " + "Message: " + respMessage + "]";
                Utils.setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.TOKEN_ERROR, errorMessage);
                handleException(errorMessage, messageContext);
            }
        } catch (IOException e) {
            String errorMessage = Constants.GENERAL_ERROR_MSG + "Error occurred while retrieving access token.";
            Utils.setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.TOKEN_ERROR, errorMessage);
            handleException(errorMessage, messageContext);
        }
        return null;
    }

    private String getTokenKey(String connection, String tokenEp, Map<String, String> params) {

        return connection + "_" + Objects.hash(tokenEp, params);
    }
}

