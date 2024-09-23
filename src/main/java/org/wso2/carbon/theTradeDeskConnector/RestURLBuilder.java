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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * The URLBuilderUtil class contains all the utility methods related to URL building for the Trade Desk connector.
 */
public class RestURLBuilder extends AbstractConnector {

        private static final Gson gson = new Gson();
        private static final String ENCODING = "UTF-8";
        private static final String URL_PATH = "uri.var.urlPath";
        private static final String URL_QUERY = "uri.var.urlQuery";
        private String operationPath = "";
        private String pathParameters = "";
        private String queryParameters = "";

        private static final Map<String, String> parameterNameMap = new HashMap<String, String>() {{
                put("budgetSettings", "BudgetSettings");
                put("endDate", "EndDate");
                put("searchTerms", "SearchTerms");
                put("universalForecastingBidLists", "UniversalForecastingBidLists");
                put("attributionImpressionLookbackWindowInSeconds", "AttributionImpressionLookbackWindowInSeconds");
                put("pageSize", "PageSize");
                put("bidListAdjustmentType", "BidListAdjustmentType");
                put("forecastingAudiences", "ForecastingAudiences");
                put("domainAddress", "DomainAddress");
                put("scheduleId", "scheduleId");
                put("clickDedupWindowInSeconds", "ClickDedupWindowInSeconds");
                put("budget", "Budget");
                put("lookAlikeModelResultStatuses", "LookAlikeModelResultStatuses");
                put("creativeName", "CreativeName");
                put("bidListOwner", "BidListOwner");
                put("trackingTagId", "trackingTagId");
                put("uniqueCountMaximum", "UniqueCountMaximum");
                put("bidLines", "BidLines");
                put("iPTargetingDataName", "IPTargetingDataName");
                put("adGroupName", "AdGroupName");
                put("campaignId", "campaignId");
                put("audienceId", "AudienceId");
                put("creativeIds", "CreativeIds");
                put("conversionDedupWindowInSeconds", "ConversionDedupWindowInSeconds");
                put("isEnabled", "IsEnabled");
                put("name", "Name");
                put("startDate", "StartDate");
                put("bidLists", "BidLists");
                put("includedSellerDomains", "IncludedSellerDomains");
                put("attributionClickLookbackWindowInSeconds", "AttributionClickLookbackWindowInSeconds");
                put("resolutionType", "ResolutionType");
                put("pacingMode", "PacingMode");
                put("trackingTags", "TrackingTags");
                put("generateBudgetPoints", "GenerateBudgetPoints");
                put("lookAlikeModelBuildStatuses", "LookAlikeModelBuildStatuses");
                put("description", "Description");
                put("pageStartIndex", "PageStartIndex");
                put("availability", "Availability");
                put("uniqueCountMinimum", "UniqueCountMinimum");
                put("advertiserName", "AdvertiserName");
                put("defaultRightMediaOfferTypeId", "DefaultRightMediaOfferTypeId");
                put("clickthroughUrl", "ClickthroughUrl");
                put("dataTypes", "DataTypes");
                put("averageBidCPMInAdvertiserCurrency", "AverageBidCPMInAdvertiserCurrency");
                put("sortFields", "SortFields");
                put("lookAlikeModelEligibilities", "LookAlikeModelEligibilities");
                put("existingBidLists", "ExistingBidLists");
                put("timeWindow", "TimeWindow");
                put("iPTargetingRanges", "IPTargetingRanges");
                put("counter", "Counter");
                put("advertiserId", "AdvertiserId");
                put("demographic", "Demographic");
                put("primaryGoal", "PrimaryGoal");
                put("partnerId", "PartnerId");
                put("campaignName", "CampaignName");
                put("sellerStatusFilter", "SellerStatusFilter");
                put("sellerNameFilters", "SellerNameFilters");
        }};

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
                                        String name = parameterNameMap.get(pathParameter);
                                        String paramValue = (String) getParameter(messageContext, pathParameter);
                                        if (StringUtils.isNotEmpty(paramValue)) {
                                                String encodedParamValue = URLEncoder.encode(paramValue, ENCODING);
                                                urlPath = urlPath.replace("{" + name + "}", encodedParamValue);
                                        } else {
                                                String errorMessage = Constants.GENERAL_ERROR_MSG + "Mandatory parameter '" + pathParameter + "' is not set.";
                                                setErrorPropertiesToMessage(messageContext, Constants.ErrorCodes.INVALID_CONFIG, errorMessage);
                                                handleException(errorMessage, messageContext);
                                        }
                                }
                        }

                        StringJoiner urlQuery = new StringJoiner("&");
                        if (StringUtils.isNotEmpty(this.queryParameters)) {
                                String[] queryParameterList = getQueryParameters().split(",");
                                for (String queryParameter : queryParameterList) {
                                        String paramValue = (String) getParameter(messageContext, queryParameter);
                                        if (StringUtils.isNotEmpty(paramValue)) {
                                                String name = parameterNameMap.get(queryParameter);
                                                if ("properties".equals(name)) {
                                                        processJSONProperties(urlQuery, parseJsonStringToMap(paramValue));
                                                        continue;
                                                }
                                                String encodedParamValue = URLEncoder.encode(paramValue, ENCODING);
                                                urlQuery.add(name + Constants.EQUAL_SIGN + encodedParamValue);
                                        }
                                }
                        }

                        messageContext.setProperty(URL_PATH, urlPath);
                        messageContext.setProperty(URL_QUERY, urlQuery.toString());
                        // Set the TTD-auth header for authentication
                        messageContext.setProperty(Constants.TTD_AUTH_HEADER, ttdAuthHeader);
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

        public String getQueryParameters() {
                return queryParameters;
        }

        public void setQueryParameters(String queryParameters) {
                this.queryParameters = queryParameters;
        }

        /**
         * Parse the JSON string to a Map.
         *
         * @param jsonString JSON string
         * @return Map<String, Object> Map of JSON properties
         */
        private static Map<String, Object> parseJsonStringToMap(String jsonString) {
                Type type = new TypeToken<Map<String, Object>>() {}.getType();
                return gson.fromJson(jsonString, type);
        }

        /**
         * Process JSON properties and add them to the URL query.
         *
         * @param urlQuery URL query
         * @param params   Map of JSON properties
         * @throws UnsupportedEncodingException If an error occurs while encoding the URL
         */
        private static void processJSONProperties(StringJoiner urlQuery, Map<String, Object> params) throws UnsupportedEncodingException {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                        String key = URLEncoder.encode(entry.getKey(), ENCODING);
                        Object value = entry.getValue();
                        if (value instanceof Map) {
                                String jsonStr = gson.toJson(value);
                                urlQuery.add(key + Constants.EQUAL_SIGN + jsonStr);
                        } else if (value instanceof List) {
                                List<?> list = (List<?>) value;
                                for (Object item : list) {
                                        String itemValue = URLEncoder.encode(item.toString(), ENCODING);
                                        urlQuery.add(key + Constants.EQUAL_SIGN + itemValue);
                                }
                        } else {
                                String encodedValue = URLEncoder.encode(value.toString(), ENCODING);
                                urlQuery.add(key + Constants.EQUAL_SIGN + encodedValue);
                        }
                }
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
