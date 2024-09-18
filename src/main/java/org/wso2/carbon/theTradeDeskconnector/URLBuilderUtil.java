/*

* Copyright (c) 2022, WSO2 LLC. (http://www.wso2.com). All Rights Reserved.
*
* This software is the property of WSO2 LLC. and its suppliers, if any.
* Dissemination of any information or reproduction of any material contained
* herein is strictly forbidden, unless permitted by WSO2 in accordance with
* the WSO2 Software License available at: https://wso2.com/licenses/eula/3.2
* For specific language governing the permissions and limitations under
* this license, please see the license as well as any agreement you’ve
* entered into with WSO2 governing the purchase of this software and any
* associated services.
*/
package org.wso2.carbon.theTradeDeskconnector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import java.util.HashMap;
import java.util.Map;

// Generated on 18-Wed, 09, 2024 21:27:07+0530

/**
 * The Utils class contains all the utils methods related to the connector.
 */
public class URLBuilderUtil extends AbstractConnector {

    private static final Log log = LogFactory.getLog(URLBuilderUtil.class);

    public static final Map<String, String> parameterNameMap = new HashMap<String, String>() {{

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
            messageContext.setProperty("_OH_INTERNAL_PARAM_NAME_MAP_", parameterNameMap);
        }
}
