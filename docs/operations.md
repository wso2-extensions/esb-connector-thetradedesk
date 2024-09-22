# Working with theTradeDesk Connector

## Overview

| Operation        | Description |
| ------------- |-------------|
| [createAdGroup](#create-ad-group)| This endpoint allows you to create a new Ad Group within a campaign. |
| [createAdvertiser](#create-advertiser)| Create a new advertiser entity with the necessary details for attribution and domain address. |
| [createBidList](#create-bid-list)| Create a bid list, returning the created bid list. |
| [createCampaign](#create-campaign)| Create a new campaign entity with the necessary details such as name, budget, and start date. |
| [createCreative](#create-creative)| Create a new creative under an advertiser. |
| [createFrequencyConfiguration](#create-frequency-configuration)| Creates a frequency configuration, associating entities to increment the counter and applying the frequency configuration to bid lists. |
| [createIpTargetingList](#create-ip-targeting-list)| Create a new IP Targeting List for an advertiser. Each Advertiser has a quota for the number of IP targeting ranges they may include across all of their IP Targeting Lists. |
| [generateForecast](#generate-forecast)| Generate a forecast based on the data available at the time of the request. |
| [getAdvertiserById](#get-advertiser-by-id)| Get details of an existing advertiser using the advertiser's platform ID. |
| [getAdvertiserOverview](#get-advertiser-overview)| Retrieve an overview of an advertiser, including descendant relationships for campaigns, ad groups, and creatives. |
| [getCampaignMetrics](#get-campaign-metrics)| Retrieve the performance metrics for a campaign, including spend, impressions, and other key metrics. |
| [getFirstPartyDataForAdvertisers](#get-first-party-data-for-advertisers)| Retrieve the First Party Data matching the provided query for an advertiser. |
| [getReportScheduleById](#get-report-schedule-by-id)| Retrieve details of a report schedule by its ID. |
| [getTrackingTagById](#get-tracking-tag-by-id)| Retrieve a tracking tag by its ID. |
| [querySellers](#query-sellers)| Retrieve a paged, filterable list of sellers, seller domains, and whether they can be targeted as a SellerDomain in a bid list. Only returns sellers that have spent through our platform. |

## Operation Details

This section provides details on each of the operations.

### Create Ad Group

This endpoint allows you to create a new Ad Group within a campaign.

**createAdGroup**

```xml
<theTradeDesk.createAdGroup>
    <adGroupName>{$ctx:adGroupName}</adGroupName>
    <campaignId>{$ctx:campaignId}</campaignId>
    <budgetSettings>{$ctx:budgetSettings}</budgetSettings>
    <pacingMode>{$ctx:pacingMode}</pacingMode>
    <isEnabled>{$ctx:isEnabled}</isEnabled>
    <creativeIds>{$ctx:creativeIds}</creativeIds>
</theTradeDesk.createAdGroup>
```

**Properties**

* adGroupName: The name of the Ad Group. Type: string
* campaignId: The ID of the Campaign that owns this Ad Group. Type: string
* budgetSettings: Settings for how much and how fast the Ad Group may spend. Type: object
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "required" : [ "BudgetInAdvertiserCurrency" ],
  "type" : "object",
  "properties" : {
    "BudgetInAdvertiserCurrency" : {
      "type" : "number",
      "description" : "The total amount the Ad Group may spend in the advertiser's currency.",
      "format" : "double"
    },
    "DailyTargetInAdvertiserCurrency" : {
      "type" : "number",
      "description" : "The daily spending limit for the Ad Group.",
      "format" : "double"
    }
  },
  "description" : "Settings for how much and how fast the Ad Group may spend."
}
```
* pacingMode: The pacing mode for the Ad Group. Type: string
* isEnabled: Whether the Ad Group is enabled to spend. Type: boolean
* creativeIds: A list of Creative IDs associated with this Ad Group. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "A list of Creative IDs associated with this Ad Group.",
  "items" : {
    "type" : "string"
  }
}
```
### Create Advertiser

Create a new advertiser entity with the necessary details for attribution and domain address.

**createAdvertiser**

```xml
<theTradeDesk.createAdvertiser>
    <advertiserName>{$ctx:advertiserName}</advertiserName>
    <attributionClickLookbackWindowInSeconds>{$ctx:attributionClickLookbackWindowInSeconds}</attributionClickLookbackWindowInSeconds>
    <attributionImpressionLookbackWindowInSeconds>{$ctx:attributionImpressionLookbackWindowInSeconds}</attributionImpressionLookbackWindowInSeconds>
    <clickDedupWindowInSeconds>{$ctx:clickDedupWindowInSeconds}</clickDedupWindowInSeconds>
    <conversionDedupWindowInSeconds>{$ctx:conversionDedupWindowInSeconds}</conversionDedupWindowInSeconds>
    <defaultRightMediaOfferTypeId>{$ctx:defaultRightMediaOfferTypeId}</defaultRightMediaOfferTypeId>
    <domainAddress>{$ctx:domainAddress}</domainAddress>
    <partnerId>{$ctx:partnerId}</partnerId>
</theTradeDesk.createAdvertiser>
```

**Properties**

* advertiserName: The name of the advertiser. Type: string
* attributionClickLookbackWindowInSeconds: Time window for click attribution in seconds. Type: integer
* attributionImpressionLookbackWindowInSeconds: Time window for impression attribution in seconds. Type: integer
* clickDedupWindowInSeconds: Window for de-duplicating similar clicks. Type: integer
* conversionDedupWindowInSeconds: Window for de-duplicating similar conversions. Type: integer
* defaultRightMediaOfferTypeId: Default Right Media Offer Type ID. Type: integer
* domainAddress: The domain address for the advertiser. Type: string
* partnerId: The ID of the partner who owns this advertiser. Type: string
### Create Bid List

Create a bid list, returning the created bid list.

**createBidList**

```xml
<theTradeDesk.createBidList>
    <bidLines>{$ctx:bidLines}</bidLines>
    <resolutionType>{$ctx:resolutionType}</resolutionType>
    <bidListAdjustmentType>{$ctx:bidListAdjustmentType}</bidListAdjustmentType>
    <bidListOwner>{$ctx:bidListOwner}</bidListOwner>
    <name>{$ctx:name}</name>
</theTradeDesk.createBidList>
```

**Properties**

* bidLines: The list of BidLines that will be used along with the ResolutionType to calculate the adjustment for this BidList. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "The list of BidLines that will be used along with the ResolutionType to calculate the adjustment for this BidList.",
  "items" : {
    "required" : [ "BidAdjustment", "DomainFragment" ],
    "type" : "object",
    "properties" : {
      "AdBugPageQualityCategoryId" : {
        "type" : "string",
        "description" : "The ID of the AdBug Page Quality category to target."
      },
      "AdFormatId" : {
        "type" : "string",
        "description" : "The Ad Format to be used for this bid line adjustment."
      },
      "BidAdjustment" : {
        "type" : "number",
        "description" : "The adjustment to be applied when everything in this BidLine is matched.",
        "format" : "double"
      },
      "DomainFragment" : {
        "type" : "string",
        "description" : "The domain or app ID to match for this adjustment."
      }
    }
  }
}
```
* resolutionType: The ResolutionType determines how BidAdjustments are handled when an impression matches multiple BidLines. Type: string
* bidListAdjustmentType: Defines how bid adjustments are applied (e.g., BlockList, TargetList, Optimized). Type: string
* bidListOwner: The type of owner that controls this bid list (e.g., AdGroup, Campaign, Advertiser). Type: string
* name: A name for the bid list. Type: string
### Create Campaign

Create a new campaign entity with the necessary details such as name, budget, and start date.

**createCampaign**

```xml
<theTradeDesk.createCampaign>
    <advertiserId>{$ctx:advertiserId}</advertiserId>
    <campaignName>{$ctx:campaignName}</campaignName>
    <budget>{$ctx:budget}</budget>
    <startDate>{$ctx:startDate}</startDate>
    <endDate>{$ctx:endDate}</endDate>
    <primaryGoal>{$ctx:primaryGoal}</primaryGoal>
    <trackingTags>{$ctx:trackingTags}</trackingTags>
</theTradeDesk.createCampaign>
```

**Properties**

* advertiserId: The platform ID of the advertiser that owns this campaign. Type: string
* campaignName: The name of the campaign. Type: string
* budget:  Type: object
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "object",
  "properties" : {
    "Amount" : {
      "type" : "number",
      "description" : "The maximum amount the campaign may spend.",
      "example" : 10000.0
    },
    "CurrencyCode" : {
      "type" : "string",
      "description" : "The currency of the money value specified in the Amount.",
      "example" : "USD"
    }
  }
}
```
* startDate: The start date and time in UTC when the campaign should begin spending. Type: string
* endDate: The end date and time in UTC when the campaign should stop spending. Type: string
* primaryGoal:  Type: object
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "object",
  "properties" : {
    "GoalType" : {
      "type" : "string",
      "description" : "The campaign's primary goal.",
      "example" : "Conversion",
      "enum" : [ "Awareness", "Consideration", "Conversion" ]
    },
    "Amount" : {
      "type" : "number",
      "description" : "The money amount associated with the primary goal.",
      "example" : 5.0
    },
    "CurrencyCode" : {
      "type" : "string",
      "description" : "The currency code for the primary goal amount.",
      "example" : "USD"
    }
  }
}
```
* trackingTags: List of tracking tags associated with the campaign. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "List of tracking tags associated with the campaign.",
  "items" : {
    "type" : "string",
    "description" : "Tracking tag ID"
  }
}
```
### Create Creative

Create a new creative under an advertiser.

**createCreative**

```xml
<theTradeDesk.createCreative>
    <advertiserId>{$ctx:advertiserId}</advertiserId>
    <creativeName>{$ctx:creativeName}</creativeName>
    <availability>{$ctx:availability}</availability>
    <description>{$ctx:description}</description>
    <clickthroughUrl>{$ctx:clickthroughUrl}</clickthroughUrl>
</theTradeDesk.createCreative>
```

**Properties**

* advertiserId: The platform ID of the advertiser that owns this creative. Type: string
* creativeName: The name of the creative. The value must not contain special characters like <>;^\r\n. Max length is 256. Type: string
* availability: Indicates if the Creative is 'Available' for use or 'Archived'. 'Archived' creatives are hidden and associations removed. Type: string
* description: An optional description of the creative. Max length is 512. Type: string
* clickthroughUrl: The URL to invoke when the user clicks the ad. This may include click tracking. Max length is 5000. Type: string
### Create Frequency Configuration

Creates a frequency configuration, associating entities to increment the counter and applying the frequency configuration to bid lists.

**createFrequencyConfiguration**

```xml
<theTradeDesk.createFrequencyConfiguration>
    <bidLists>{$ctx:bidLists}</bidLists>
    <counter>{$ctx:counter}</counter>
</theTradeDesk.createFrequencyConfiguration>
```

**Properties**

* bidLists: Bid lists apply frequency settings to ad groups, campaigns, and other entities. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "Bid lists apply frequency settings to ad groups, campaigns, and other entities.",
  "items" : {
    "type" : "object",
    "properties" : {
      "BidLines" : {
        "type" : "array",
        "description" : "A list of frequency bid lines that are used to calculate the adjustment.",
        "items" : {
          "required" : [ "BidAdjustment", "Range" ],
          "type" : "object",
          "properties" : {
            "Range" : {
              "type" : "object",
              "properties" : {
                "Min" : {
                  "type" : "integer",
                  "format" : "int32"
                },
                "Max" : {
                  "type" : "integer",
                  "format" : "int32"
                }
              },
              "description" : "The minimum and maximum number of times an ad can be served within the counter reset interval."
            },
            "BidAdjustment" : {
              "type" : "number",
              "format" : "double"
            }
          }
        }
      },
      "VolumeControlPriority" : {
        "type" : "string",
        "description" : "The volume control priority applied to the adjustment.",
        "enum" : [ "Neutral", "One", "Two", "FrequencyGoal", "Negative" ]
      },
      "AssociatedAdGroups" : {
        "type" : "array",
        "description" : "A list of IDs of ad groups where the bid list is associated and enabled.",
        "items" : {
          "type" : "string"
        }
      },
      "AssociatedAdvertisers" : {
        "type" : "array",
        "description" : "A list of IDs of advertisers where the bid list is associated and enabled.",
        "items" : {
          "type" : "string"
        }
      },
      "AssociatedCampaigns" : {
        "type" : "array",
        "description" : "A list of IDs of campaigns where the bid list is associated and enabled.",
        "items" : {
          "type" : "string"
        }
      }
    }
  }
}
```
* counter: Defines the frequency counter framework for tracking the number of times an ad is served. Type: object
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "required" : [ "CounterName", "ResetIntervalInMinutes" ],
  "type" : "object",
  "properties" : {
    "CounterName" : {
      "type" : "string",
      "description" : "The counter name."
    },
    "ResetIntervalInMinutes" : {
      "type" : "integer",
      "description" : "The time interval (in minutes) during which the counter tracks a user's frequency count.",
      "format" : "int32"
    },
    "IncrementByAllEntitiesAssociatedWithBidLists" : {
      "type" : "boolean",
      "description" : "If true, ad groups, campaigns, and advertisers will be used as increments for this configuration."
    }
  },
  "description" : "Defines the frequency counter framework for tracking the number of times an ad is served."
}
```
### Create Ip Targeting List

Create a new IP Targeting List for an advertiser. Each Advertiser has a quota for the number of IP targeting ranges they may include across all of their IP Targeting Lists.

**createIpTargetingList**

```xml
<theTradeDesk.createIpTargetingList>
    <advertiserId>{$ctx:advertiserId}</advertiserId>
    <iPTargetingDataName>{$ctx:iPTargetingDataName}</iPTargetingDataName>
    <iPTargetingRanges>{$ctx:iPTargetingRanges}</iPTargetingRanges>
</theTradeDesk.createIpTargetingList>
```

**Properties**

* advertiserId: The platform ID of the advertiser that owns this IP Targeting List. Type: string
* iPTargetingDataName: The name of the Data Element to create for users who are in the associated IP targeting ranges. Type: string
* iPTargetingRanges: All the IP targeting ranges in this list. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "All the IP targeting ranges in this list.",
  "items" : {
    "required" : [ "MaxIP", "MinIP" ],
    "type" : "object",
    "properties" : {
      "MinIP" : {
        "type" : "string",
        "description" : "The minimum IP address in the targeting range (inclusive).",
        "example" : "5.5.5.5"
      },
      "MaxIP" : {
        "type" : "string",
        "description" : "The maximum IP address in the targeting range (inclusive).",
        "example" : "5.6.0.0"
      }
    }
  }
}
```
### Generate Forecast

Generate a forecast based on the data available at the time of the request.

**generateForecast**

```xml
<theTradeDesk.generateForecast>
    <advertiserId>{$ctx:advertiserId}</advertiserId>
    <audienceId>{$ctx:audienceId}</audienceId>
    <averageBidCPMInAdvertiserCurrency>{$ctx:averageBidCPMInAdvertiserCurrency}</averageBidCPMInAdvertiserCurrency>
    <demographic>{$ctx:demographic}</demographic>
    <existingBidLists>{$ctx:existingBidLists}</existingBidLists>
    <forecastingAudiences>{$ctx:forecastingAudiences}</forecastingAudiences>
    <generateBudgetPoints>{$ctx:generateBudgetPoints}</generateBudgetPoints>
    <timeWindow>{$ctx:timeWindow}</timeWindow>
    <universalForecastingBidLists>{$ctx:universalForecastingBidLists}</universalForecastingBidLists>
</theTradeDesk.generateForecast>
```

**Properties**

* advertiserId: The platform ID of the advertiser requesting the forecast. Type: string
* audienceId: The ID for the audience to be used in the forecast. Type: string
* averageBidCPMInAdvertiserCurrency: The average bid in the advertiser's currency to be used for the forecast. Type: number
* demographic:  Type: object
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "object",
  "properties" : {
    "StartAge" : {
      "type" : "string",
      "description" : "The minimum age to be targeted.",
      "nullable" : true
    },
    "EndAge" : {
      "type" : "string",
      "description" : "The maximum age to be targeted.",
      "nullable" : true
    },
    "Gender" : {
      "type" : "string",
      "description" : "The gender to be targeted.",
      "nullable" : true
    }
  },
  "nullable" : true
}
```
* existingBidLists: Existing bid lists to be applied to the forecast. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "Existing bid lists to be applied to the forecast.",
  "nullable" : true,
  "items" : {
    "type" : "integer",
    "format" : "int64"
  }
}
```
* forecastingAudiences:  Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "items" : {
    "type" : "object",
    "properties" : {
      "AdvertiserId" : {
        "type" : "string",
        "description" : "The platform ID of the advertiser that owns this Audience."
      },
      "AudienceName" : {
        "maxLength" : 64,
        "type" : "string",
        "description" : "The name of the audience."
      },
      "IncludedDataGroups" : {
        "type" : "array",
        "items" : {
          "type" : "object",
          "properties" : {
            "AdvertiserId" : {
              "type" : "string"
            },
            "DataGroupName" : {
              "maxLength" : 64,
              "type" : "string"
            }
          }
        }
      }
    }
  }
}
```
* generateBudgetPoints: Set to true to generate budget points for impressions and reach. Type: boolean
* timeWindow: The number of days (1 to 180) for the forecast. Type: integer
* universalForecastingBidLists: New bid lists to be applied to the forecast. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "New bid lists to be applied to the forecast.",
  "nullable" : true,
  "items" : {
    "type" : "object",
    "properties" : {
      "BidLines" : {
        "type" : "array",
        "items" : {
          "type" : "object",
          "properties" : {
            "AdBugPageQualityCategoryId" : {
              "type" : "string"
            },
            "AdFormatId" : {
              "type" : "string"
            },
            "BidAdjustment" : {
              "type" : "number",
              "format" : "double"
            }
          }
        }
      }
    }
  }
}
```
### Get Advertiser By Id

Get details of an existing advertiser using the advertiser's platform ID.

**getAdvertiserById**

```xml
<theTradeDesk.getAdvertiserById>
    <advertiserId>{$ctx:advertiserId}</advertiserId>
</theTradeDesk.getAdvertiserById>
```

**Properties**

* advertiserId: The platform ID of the advertiser to retrieve.
### Get Advertiser Overview

Retrieve an overview of an advertiser, including descendant relationships for campaigns, ad groups, and creatives.

**getAdvertiserOverview**

```xml
<theTradeDesk.getAdvertiserOverview>
    <advertiserId>{$ctx:advertiserId}</advertiserId>
</theTradeDesk.getAdvertiserOverview>
```

**Properties**

* advertiserId: The platform ID of the advertiser.
### Get Campaign Metrics

Retrieve the performance metrics for a campaign, including spend, impressions, and other key metrics.

**getCampaignMetrics**

```xml
<theTradeDesk.getCampaignMetrics>
    <campaignId>{$ctx:campaignId}</campaignId>
</theTradeDesk.getCampaignMetrics>
```

**Properties**

* campaignId: The ID of the campaign to retrieve metrics for
### Get First Party Data For Advertisers

Retrieve the First Party Data matching the provided query for an advertiser.

**getFirstPartyDataForAdvertisers**

```xml
<theTradeDesk.getFirstPartyDataForAdvertisers>
    <advertiserId>{$ctx:advertiserId}</advertiserId>
    <pageSize>{$ctx:pageSize}</pageSize>
    <pageStartIndex>{$ctx:pageStartIndex}</pageStartIndex>
    <dataTypes>{$ctx:dataTypes}</dataTypes>
    <lookAlikeModelBuildStatuses>{$ctx:lookAlikeModelBuildStatuses}</lookAlikeModelBuildStatuses>
    <lookAlikeModelEligibilities>{$ctx:lookAlikeModelEligibilities}</lookAlikeModelEligibilities>
    <lookAlikeModelResultStatuses>{$ctx:lookAlikeModelResultStatuses}</lookAlikeModelResultStatuses>
    <searchTerms>{$ctx:searchTerms}</searchTerms>
    <sortFields>{$ctx:sortFields}</sortFields>
    <uniqueCountMaximum>{$ctx:uniqueCountMaximum}</uniqueCountMaximum>
    <uniqueCountMinimum>{$ctx:uniqueCountMinimum}</uniqueCountMinimum>
</theTradeDesk.getFirstPartyDataForAdvertisers>
```

**Properties**

* advertiserId: The platform ID of the advertiser making the query. Type: string
* pageSize: The number of results to return per page. Type: integer
* pageStartIndex: The index at which to start the page of results. Type: integer
* dataTypes: The types of First Party Data to return. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "The types of First Party Data to return.",
  "items" : {
    "type" : "string",
    "enum" : [ "Keyword", "TrackingTag", "IPAddressRange", "ImportedAdvertiserData", "ImportedAdvertiserDataWithBaseBid", "HouseholdExtension", "ClickRetargeting", "DirectIPTargeting", "PlayerEventStart", "PlayerEventMidpoint", "PlayerEventComplete", "ThirdPartyImpression", "FixedPriceUser", "CrmData" ]
  }
}
```
* lookAlikeModelBuildStatuses:  Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "nullable" : true,
  "items" : {
    "type" : "string",
    "enum" : [ "NotRequested", "Queued", "Built" ]
  }
}
```
* lookAlikeModelEligibilities:  Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "nullable" : true,
  "items" : {
    "type" : "string",
    "enum" : [ "Eligible", "NotEligible" ]
  }
}
```
* lookAlikeModelResultStatuses:  Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "nullable" : true,
  "items" : {
    "type" : "string",
    "enum" : [ "NoResults", "Ready" ]
  }
}
```
* searchTerms:  Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "nullable" : true,
  "items" : {
    "type" : "string"
  }
}
```
* sortFields:  Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "nullable" : true,
  "items" : {
    "type" : "object",
    "properties" : {
      "FieldId" : {
        "type" : "string",
        "enum" : [ "Name", "DataType", "Uniques" ]
      },
      "Ascending" : {
        "type" : "boolean",
        "default" : true
      }
    }
  }
}
```
* uniqueCountMaximum:  Type: integer
* uniqueCountMinimum:  Type: integer
### Get Report Schedule By Id

Retrieve details of a report schedule by its ID.

**getReportScheduleById**

```xml
<theTradeDesk.getReportScheduleById>
    <scheduleId>{$ctx:scheduleId}</scheduleId>
</theTradeDesk.getReportScheduleById>
```

**Properties**

* scheduleId: The ID of the report schedule to retrieve.
### Get Tracking Tag By Id

Retrieve a tracking tag by its ID.

**getTrackingTagById**

```xml
<theTradeDesk.getTrackingTagById>
    <trackingTagId>{$ctx:trackingTagId}</trackingTagId>
</theTradeDesk.getTrackingTagById>
```

**Properties**

* trackingTagId: The ID of the tracking tag to retrieve.
### Query Sellers

Retrieve a paged, filterable list of sellers, seller domains, and whether they can be targeted as a SellerDomain in a bid list. Only returns sellers that have spent through our platform.

**querySellers**

```xml
<theTradeDesk.querySellers>
    <pageSize>{$ctx:pageSize}</pageSize>
    <pageStartIndex>{$ctx:pageStartIndex}</pageStartIndex>
    <includedSellerDomains>{$ctx:includedSellerDomains}</includedSellerDomains>
    <sellerNameFilters>{$ctx:sellerNameFilters}</sellerNameFilters>
    <sellerStatusFilter>{$ctx:sellerStatusFilter}</sellerStatusFilter>
    <sortFields>{$ctx:sortFields}</sortFields>
</theTradeDesk.querySellers>
```

**Properties**

* pageSize: The size of the page requested. If there are fewer items on the current page than the PageSize, only those items will be returned. The minimum value is 25 and the maximum is 100. Type: integer
* pageStartIndex: The zero-based index at which to start the page of results. Type: integer
* includedSellerDomains: Filter to a seller whose SellerDomain matches the value passed for this property. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "Filter to a seller whose SellerDomain matches the value passed for this property.",
  "nullable" : true,
  "items" : {
    "type" : "string"
  }
}
```
* sellerNameFilters: Filter to a seller whose SellerName contains the value passed for this property. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "Filter to a seller whose SellerName contains the value passed for this property.",
  "nullable" : true,
  "items" : {
    "type" : "string"
  }
}
```
* sellerStatusFilter: Filter to sellers whose SellerStatus matches the value specified. Defaults to Allowed. Type: string
* sortFields: Optionally, specify fields to sort the results by. Type: array
This is parameter is of a complex type. Use the below schema to build the parameter and set it as a property to this
parameter.
```json
{
  "type" : "array",
  "description" : "Optionally, specify fields to sort the results by.",
  "nullable" : true,
  "items" : {
    "type" : "object",
    "properties" : {
      "FieldId" : {
        "type" : "string",
        "description" : "The field ID by which to sort.",
        "enum" : [ "SellerName", "SellerDomain" ]
      },
      "Ascending" : {
        "type" : "boolean",
        "description" : "Whether to sort this field in ascending order (true) or descending order (false). Defaults to true."
      }
    }
  }
}
```
