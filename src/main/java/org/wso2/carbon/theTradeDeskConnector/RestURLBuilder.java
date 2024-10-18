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

package org.wso2.carbon.theTradeDeskConnector;

import org.apache.commons.lang3.StringUtils;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The URLBuilderUtil class contains all the utility methods related to URL building for the Trade Desk connector.
 */
public class RestURLBuilder extends AbstractConnector {
    private static final String ENCODING = "UTF-8";
    private static final String URL_PATH = "uri.var.urlPath";

    private String operationPath = "";
    private String pathParameters = "";

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            String ttdAuthHeader = (String) messageContext.getProperty(Constants.PROPERTY_TTD_AUTH);
            if (StringUtils.isBlank(ttdAuthHeader)) {
                String errorMessage = Constants.GENERAL_ERROR_MSG + "TTD-auth header is not set.";
                setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.INVALID_CONFIG, errorMessage);
                handleException(errorMessage, messageContext);
            }

            String urlPath = getOperationPath();
            if (StringUtils.isNotEmpty(this.pathParameters)) {
                String[] pathParameterList = getPathParameters().split(",");
                for (String pathParameter : pathParameterList) {
                    String paramValue = (String) getParameter(messageContext, pathParameter);
                    if (StringUtils.isNotEmpty(paramValue)) {
                        String encodedParamValue = URLEncoder.encode(paramValue, ENCODING);
                        urlPath = urlPath.replace("{" + pathParameter + "}", encodedParamValue);
                    } else {
                        String errorMessage = Constants.GENERAL_ERROR_MSG + "Mandatory parameter '" + pathParameter + "' is not set.";
                        setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.INVALID_CONFIG, errorMessage);
                        handleException(errorMessage, messageContext);
                    }
                }
            }

            messageContext.setProperty(URL_PATH, urlPath);
        } catch (UnsupportedEncodingException e) {
            String errorMessage = Constants.GENERAL_ERROR_MSG + "Error occurred while constructing the URL query.";
            setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.GENERAL_ERROR, errorMessage);
            handleException(errorMessage, messageContext);
        }
    }

    public String getOperationPath() {
        return operationPath;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }

    public String getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(String pathParameters) {
        this.pathParameters = pathParameters;
    }

    /**
     * Sets the error code and error message in message context.
     *
     * @param messageContext Message Context
     * @param errorCode      Error Code
     * @param errorMessage   Error Message
     */
    private void setErrorPropertiesToMessage(MessageContext messageContext, String errorCode, String errorMessage) {
        messageContext.setProperty(Constants.PROPERTY_ERROR_CODE, errorCode);
        messageContext.setProperty(Constants.PROPERTY_ERROR_MESSAGE, errorMessage);
    }
}
