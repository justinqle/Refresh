package com.justinqle.refresh.models.listing;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Subreddit {

    @SerializedName("user_flair_position")
    private String userFlairPosition;

    @SerializedName("public_description")
    private String publicDescription;

    @SerializedName("key_color")
    private String keyColor;

    @SerializedName("active_user_count")
    private Object activeUserCount;

    @SerializedName("accounts_active")
    private Object accountsActive;

    @SerializedName("user_is_banned")
    private boolean userIsBanned;

    @SerializedName("submit_text_label")
    private Object submitTextLabel;

    @SerializedName("user_flair_text_color")
    private Object userFlairTextColor;

    @SerializedName("emojis_enabled")
    private boolean emojisEnabled;

    @SerializedName("user_is_muted")
    private boolean userIsMuted;

    @SerializedName("public_description_html")
    private String publicDescriptionHtml;

    @SerializedName("whitelist_status")
    private String whitelistStatus;

    @SerializedName("user_is_subscriber")
    private boolean userIsSubscriber;

    @SerializedName("icon_size")
    private List<Integer> iconSize;

    @SerializedName("user_flair_enabled_in_sr")
    private boolean userFlairEnabledInSr;

    @SerializedName("id")
    private String id;

    @SerializedName("show_media")
    private boolean showMedia;

    @SerializedName("created_utc")
    private int createdUtc;

    @SerializedName("comment_score_hide_mins")
    private int commentScoreHideMins;

    @SerializedName("is_enrolled_in_new_modmail")
    private Object isEnrolledInNewModmail;

    @SerializedName("header_title")
    private Object headerTitle;

    @SerializedName("user_flair_richtext")
    private List<Object> userFlairRichtext;

    @SerializedName("created")
    private int created;

    @SerializedName("subscribers")
    private int subscribers;

    @SerializedName("community_icon")
    private String communityIcon;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("primary_color")
    private String primaryColor;

    @SerializedName("link_flair_position")
    private String linkFlairPosition;

    @SerializedName("user_is_contributor")
    private boolean userIsContributor;

    @SerializedName("link_flair_enabled")
    private boolean linkFlairEnabled;

    @SerializedName("can_assign_user_flair")
    private boolean canAssignUserFlair;

    @SerializedName("submit_link_label")
    private Object submitLinkLabel;

    @SerializedName("banner_img")
    private String bannerImg;

    @SerializedName("user_flair_css_class")
    private Object userFlairCssClass;

    @SerializedName("name")
    private String name;

    @SerializedName("user_flair_type")
    private String userFlairType;

    @SerializedName("allow_videogifs")
    private boolean allowVideogifs;

    @SerializedName("notification_level")
    private String notificationLevel;

    @SerializedName("description_html")
    private String descriptionHtml;

    @SerializedName("audience_target")
    private String audienceTarget;

    @SerializedName("user_sr_flair_enabled")
    private Object userSrFlairEnabled;

    @SerializedName("wls")
    private int wls;

    @SerializedName("suggested_comment_sort")
    private Object suggestedCommentSort;

    @SerializedName("submit_text")
    private String submitText;

    @SerializedName("user_has_favorited")
    private boolean userHasFavorited;

    @SerializedName("accounts_active_is_fuzzed")
    private boolean accountsActiveIsFuzzed;

    @SerializedName("allow_images")
    private boolean allowImages;

    @SerializedName("public_traffic")
    private boolean publicTraffic;

    @SerializedName("description")
    private String description;

    @SerializedName("user_flair_text")
    private Object userFlairText;

    @SerializedName("title")
    private String title;

    @SerializedName("banner_background_color")
    private String bannerBackgroundColor;

    @SerializedName("user_flair_template_id")
    private Object userFlairTemplateId;

    @SerializedName("display_name_prefixed")
    private String displayNamePrefixed;

    @SerializedName("submission_type")
    private String submissionType;

    @SerializedName("spoilers_enabled")
    private boolean spoilersEnabled;

    @SerializedName("user_sr_theme_enabled")
    private boolean userSrThemeEnabled;

    @SerializedName("show_media_preview")
    private boolean showMediaPreview;

    @SerializedName("lang")
    private String lang;

    @SerializedName("user_is_moderator")
    private boolean userIsModerator;

    @SerializedName("user_flair_background_color")
    private Object userFlairBackgroundColor;

    @SerializedName("allow_discovery")
    private boolean allowDiscovery;

    @SerializedName("banner_background_image")
    private String bannerBackgroundImage;

    @SerializedName("banner_size")
    private Object bannerSize;

    @SerializedName("over18")
    private boolean over18;

    @SerializedName("subreddit_type")
    private String subredditType;

    @SerializedName("collapse_deleted_comments")
    private boolean collapseDeletedComments;

    @SerializedName("header_size")
    private List<Integer> headerSize;

    @SerializedName("has_menu_widget")
    private boolean hasMenuWidget;

    @SerializedName("advertiser_category")
    private String advertiserCategory;

    @SerializedName("original_content_tag_enabled")
    private boolean originalContentTagEnabled;

    @SerializedName("all_original_content")
    private boolean allOriginalContent;

    @SerializedName("url")
    private String url;

    @SerializedName("user_can_flair_in_sr")
    private Object userCanFlairInSr;

    @SerializedName("allow_videos")
    private boolean allowVideos;

    @SerializedName("header_img")
    private String headerImg;

    @SerializedName("icon_img")
    private String iconImg;

    @SerializedName("submit_text_html")
    private String submitTextHtml;

    @SerializedName("wiki_enabled")
    private boolean wikiEnabled;

    @SerializedName("hide_ads")
    private boolean hideAds;

    @SerializedName("quarantine")
    private boolean quarantine;

    @SerializedName("can_assign_link_flair")
    private boolean canAssignLinkFlair;

    public void setUserFlairPosition(String userFlairPosition) {
        this.userFlairPosition = userFlairPosition;
    }

    public String getUserFlairPosition() {
        return userFlairPosition;
    }

    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }

    public String getPublicDescription() {
        return publicDescription;
    }

    public void setKeyColor(String keyColor) {
        this.keyColor = keyColor;
    }

    public String getKeyColor() {
        return keyColor;
    }

    public void setActiveUserCount(Object activeUserCount) {
        this.activeUserCount = activeUserCount;
    }

    public Object getActiveUserCount() {
        return activeUserCount;
    }

    public void setAccountsActive(Object accountsActive) {
        this.accountsActive = accountsActive;
    }

    public Object getAccountsActive() {
        return accountsActive;
    }

    public void setUserIsBanned(boolean userIsBanned) {
        this.userIsBanned = userIsBanned;
    }

    public boolean isUserIsBanned() {
        return userIsBanned;
    }

    public void setSubmitTextLabel(Object submitTextLabel) {
        this.submitTextLabel = submitTextLabel;
    }

    public Object getSubmitTextLabel() {
        return submitTextLabel;
    }

    public void setUserFlairTextColor(Object userFlairTextColor) {
        this.userFlairTextColor = userFlairTextColor;
    }

    public Object getUserFlairTextColor() {
        return userFlairTextColor;
    }

    public void setEmojisEnabled(boolean emojisEnabled) {
        this.emojisEnabled = emojisEnabled;
    }

    public boolean isEmojisEnabled() {
        return emojisEnabled;
    }

    public void setUserIsMuted(boolean userIsMuted) {
        this.userIsMuted = userIsMuted;
    }

    public boolean isUserIsMuted() {
        return userIsMuted;
    }

    public void setPublicDescriptionHtml(String publicDescriptionHtml) {
        this.publicDescriptionHtml = publicDescriptionHtml;
    }

    public String getPublicDescriptionHtml() {
        return publicDescriptionHtml;
    }

    public void setWhitelistStatus(String whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    public String getWhitelistStatus() {
        return whitelistStatus;
    }

    public void setUserIsSubscriber(boolean userIsSubscriber) {
        this.userIsSubscriber = userIsSubscriber;
    }

    public boolean isUserIsSubscriber() {
        return userIsSubscriber;
    }

    public void setIconSize(List<Integer> iconSize) {
        this.iconSize = iconSize;
    }

    public List<Integer> getIconSize() {
        return iconSize;
    }

    public void setUserFlairEnabledInSr(boolean userFlairEnabledInSr) {
        this.userFlairEnabledInSr = userFlairEnabledInSr;
    }

    public boolean isUserFlairEnabledInSr() {
        return userFlairEnabledInSr;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setShowMedia(boolean showMedia) {
        this.showMedia = showMedia;
    }

    public boolean isShowMedia() {
        return showMedia;
    }

    public void setCreatedUtc(int createdUtc) {
        this.createdUtc = createdUtc;
    }

    public int getCreatedUtc() {
        return createdUtc;
    }

    public void setCommentScoreHideMins(int commentScoreHideMins) {
        this.commentScoreHideMins = commentScoreHideMins;
    }

    public int getCommentScoreHideMins() {
        return commentScoreHideMins;
    }

    public void setIsEnrolledInNewModmail(Object isEnrolledInNewModmail) {
        this.isEnrolledInNewModmail = isEnrolledInNewModmail;
    }

    public Object getIsEnrolledInNewModmail() {
        return isEnrolledInNewModmail;
    }

    public void setHeaderTitle(Object headerTitle) {
        this.headerTitle = headerTitle;
    }

    public Object getHeaderTitle() {
        return headerTitle;
    }

    public void setUserFlairRichtext(List<Object> userFlairRichtext) {
        this.userFlairRichtext = userFlairRichtext;
    }

    public List<Object> getUserFlairRichtext() {
        return userFlairRichtext;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getCreated() {
        return created;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setCommunityIcon(String communityIcon) {
        this.communityIcon = communityIcon;
    }

    public String getCommunityIcon() {
        return communityIcon;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setLinkFlairPosition(String linkFlairPosition) {
        this.linkFlairPosition = linkFlairPosition;
    }

    public String getLinkFlairPosition() {
        return linkFlairPosition;
    }

    public void setUserIsContributor(boolean userIsContributor) {
        this.userIsContributor = userIsContributor;
    }

    public boolean isUserIsContributor() {
        return userIsContributor;
    }

    public void setLinkFlairEnabled(boolean linkFlairEnabled) {
        this.linkFlairEnabled = linkFlairEnabled;
    }

    public boolean isLinkFlairEnabled() {
        return linkFlairEnabled;
    }

    public void setCanAssignUserFlair(boolean canAssignUserFlair) {
        this.canAssignUserFlair = canAssignUserFlair;
    }

    public boolean isCanAssignUserFlair() {
        return canAssignUserFlair;
    }

    public void setSubmitLinkLabel(Object submitLinkLabel) {
        this.submitLinkLabel = submitLinkLabel;
    }

    public Object getSubmitLinkLabel() {
        return submitLinkLabel;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setUserFlairCssClass(Object userFlairCssClass) {
        this.userFlairCssClass = userFlairCssClass;
    }

    public Object getUserFlairCssClass() {
        return userFlairCssClass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUserFlairType(String userFlairType) {
        this.userFlairType = userFlairType;
    }

    public String getUserFlairType() {
        return userFlairType;
    }

    public void setAllowVideogifs(boolean allowVideogifs) {
        this.allowVideogifs = allowVideogifs;
    }

    public boolean isAllowVideogifs() {
        return allowVideogifs;
    }

    public void setNotificationLevel(String notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public String getNotificationLevel() {
        return notificationLevel;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setAudienceTarget(String audienceTarget) {
        this.audienceTarget = audienceTarget;
    }

    public String getAudienceTarget() {
        return audienceTarget;
    }

    public void setUserSrFlairEnabled(Object userSrFlairEnabled) {
        this.userSrFlairEnabled = userSrFlairEnabled;
    }

    public Object getUserSrFlairEnabled() {
        return userSrFlairEnabled;
    }

    public void setWls(int wls) {
        this.wls = wls;
    }

    public int getWls() {
        return wls;
    }

    public void setSuggestedCommentSort(Object suggestedCommentSort) {
        this.suggestedCommentSort = suggestedCommentSort;
    }

    public Object getSuggestedCommentSort() {
        return suggestedCommentSort;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    public String getSubmitText() {
        return submitText;
    }

    public void setUserHasFavorited(boolean userHasFavorited) {
        this.userHasFavorited = userHasFavorited;
    }

    public boolean isUserHasFavorited() {
        return userHasFavorited;
    }

    public void setAccountsActiveIsFuzzed(boolean accountsActiveIsFuzzed) {
        this.accountsActiveIsFuzzed = accountsActiveIsFuzzed;
    }

    public boolean isAccountsActiveIsFuzzed() {
        return accountsActiveIsFuzzed;
    }

    public void setAllowImages(boolean allowImages) {
        this.allowImages = allowImages;
    }

    public boolean isAllowImages() {
        return allowImages;
    }

    public void setPublicTraffic(boolean publicTraffic) {
        this.publicTraffic = publicTraffic;
    }

    public boolean isPublicTraffic() {
        return publicTraffic;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUserFlairText(Object userFlairText) {
        this.userFlairText = userFlairText;
    }

    public Object getUserFlairText() {
        return userFlairText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setBannerBackgroundColor(String bannerBackgroundColor) {
        this.bannerBackgroundColor = bannerBackgroundColor;
    }

    public String getBannerBackgroundColor() {
        return bannerBackgroundColor;
    }

    public void setUserFlairTemplateId(Object userFlairTemplateId) {
        this.userFlairTemplateId = userFlairTemplateId;
    }

    public Object getUserFlairTemplateId() {
        return userFlairTemplateId;
    }

    public void setDisplayNamePrefixed(String displayNamePrefixed) {
        this.displayNamePrefixed = displayNamePrefixed;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    public String getSubmissionType() {
        return submissionType;
    }

    public void setSpoilersEnabled(boolean spoilersEnabled) {
        this.spoilersEnabled = spoilersEnabled;
    }

    public boolean isSpoilersEnabled() {
        return spoilersEnabled;
    }

    public void setUserSrThemeEnabled(boolean userSrThemeEnabled) {
        this.userSrThemeEnabled = userSrThemeEnabled;
    }

    public boolean isUserSrThemeEnabled() {
        return userSrThemeEnabled;
    }

    public void setShowMediaPreview(boolean showMediaPreview) {
        this.showMediaPreview = showMediaPreview;
    }

    public boolean isShowMediaPreview() {
        return showMediaPreview;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setUserIsModerator(boolean userIsModerator) {
        this.userIsModerator = userIsModerator;
    }

    public boolean isUserIsModerator() {
        return userIsModerator;
    }

    public void setUserFlairBackgroundColor(Object userFlairBackgroundColor) {
        this.userFlairBackgroundColor = userFlairBackgroundColor;
    }

    public Object getUserFlairBackgroundColor() {
        return userFlairBackgroundColor;
    }

    public void setAllowDiscovery(boolean allowDiscovery) {
        this.allowDiscovery = allowDiscovery;
    }

    public boolean isAllowDiscovery() {
        return allowDiscovery;
    }

    public void setBannerBackgroundImage(String bannerBackgroundImage) {
        this.bannerBackgroundImage = bannerBackgroundImage;
    }

    public String getBannerBackgroundImage() {
        return bannerBackgroundImage;
    }

    public void setBannerSize(Object bannerSize) {
        this.bannerSize = bannerSize;
    }

    public Object getBannerSize() {
        return bannerSize;
    }

    public void setOver18(boolean over18) {
        this.over18 = over18;
    }

    public boolean isOver18() {
        return over18;
    }

    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public void setCollapseDeletedComments(boolean collapseDeletedComments) {
        this.collapseDeletedComments = collapseDeletedComments;
    }

    public boolean isCollapseDeletedComments() {
        return collapseDeletedComments;
    }

    public void setHeaderSize(List<Integer> headerSize) {
        this.headerSize = headerSize;
    }

    public List<Integer> getHeaderSize() {
        return headerSize;
    }

    public void setHasMenuWidget(boolean hasMenuWidget) {
        this.hasMenuWidget = hasMenuWidget;
    }

    public boolean isHasMenuWidget() {
        return hasMenuWidget;
    }

    public void setAdvertiserCategory(String advertiserCategory) {
        this.advertiserCategory = advertiserCategory;
    }

    public String getAdvertiserCategory() {
        return advertiserCategory;
    }

    public void setOriginalContentTagEnabled(boolean originalContentTagEnabled) {
        this.originalContentTagEnabled = originalContentTagEnabled;
    }

    public boolean isOriginalContentTagEnabled() {
        return originalContentTagEnabled;
    }

    public void setAllOriginalContent(boolean allOriginalContent) {
        this.allOriginalContent = allOriginalContent;
    }

    public boolean isAllOriginalContent() {
        return allOriginalContent;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUserCanFlairInSr(Object userCanFlairInSr) {
        this.userCanFlairInSr = userCanFlairInSr;
    }

    public Object getUserCanFlairInSr() {
        return userCanFlairInSr;
    }

    public void setAllowVideos(boolean allowVideos) {
        this.allowVideos = allowVideos;
    }

    public boolean isAllowVideos() {
        return allowVideos;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setSubmitTextHtml(String submitTextHtml) {
        this.submitTextHtml = submitTextHtml;
    }

    public String getSubmitTextHtml() {
        return submitTextHtml;
    }

    public void setWikiEnabled(boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public boolean isWikiEnabled() {
        return wikiEnabled;
    }

    public void setHideAds(boolean hideAds) {
        this.hideAds = hideAds;
    }

    public boolean isHideAds() {
        return hideAds;
    }

    public void setQuarantine(boolean quarantine) {
        this.quarantine = quarantine;
    }

    public boolean isQuarantine() {
        return quarantine;
    }

    public void setCanAssignLinkFlair(boolean canAssignLinkFlair) {
        this.canAssignLinkFlair = canAssignLinkFlair;
    }

    public boolean isCanAssignLinkFlair() {
        return canAssignLinkFlair;
    }

    @Override
    public String toString() {
        return
                "Subreddit{" +
                        "user_flair_position = '" + userFlairPosition + '\'' +
                        ",public_description = '" + publicDescription + '\'' +
                        ",key_color = '" + keyColor + '\'' +
                        ",active_user_count = '" + activeUserCount + '\'' +
                        ",accounts_active = '" + accountsActive + '\'' +
                        ",user_is_banned = '" + userIsBanned + '\'' +
                        ",submit_text_label = '" + submitTextLabel + '\'' +
                        ",user_flair_text_color = '" + userFlairTextColor + '\'' +
                        ",emojis_enabled = '" + emojisEnabled + '\'' +
                        ",user_is_muted = '" + userIsMuted + '\'' +
                        ",public_description_html = '" + publicDescriptionHtml + '\'' +
                        ",whitelist_status = '" + whitelistStatus + '\'' +
                        ",user_is_subscriber = '" + userIsSubscriber + '\'' +
                        ",icon_size = '" + iconSize + '\'' +
                        ",user_flair_enabled_in_sr = '" + userFlairEnabledInSr + '\'' +
                        ",id = '" + id + '\'' +
                        ",show_media = '" + showMedia + '\'' +
                        ",created_utc = '" + createdUtc + '\'' +
                        ",comment_score_hide_mins = '" + commentScoreHideMins + '\'' +
                        ",is_enrolled_in_new_modmail = '" + isEnrolledInNewModmail + '\'' +
                        ",header_title = '" + headerTitle + '\'' +
                        ",user_flair_richtext = '" + userFlairRichtext + '\'' +
                        ",created = '" + created + '\'' +
                        ",subscribers = '" + subscribers + '\'' +
                        ",community_icon = '" + communityIcon + '\'' +
                        ",display_name = '" + displayName + '\'' +
                        ",primary_color = '" + primaryColor + '\'' +
                        ",link_flair_position = '" + linkFlairPosition + '\'' +
                        ",user_is_contributor = '" + userIsContributor + '\'' +
                        ",link_flair_enabled = '" + linkFlairEnabled + '\'' +
                        ",can_assign_user_flair = '" + canAssignUserFlair + '\'' +
                        ",submit_link_label = '" + submitLinkLabel + '\'' +
                        ",banner_img = '" + bannerImg + '\'' +
                        ",user_flair_css_class = '" + userFlairCssClass + '\'' +
                        ",name = '" + name + '\'' +
                        ",user_flair_type = '" + userFlairType + '\'' +
                        ",allow_videogifs = '" + allowVideogifs + '\'' +
                        ",notification_level = '" + notificationLevel + '\'' +
                        ",description_html = '" + descriptionHtml + '\'' +
                        ",audience_target = '" + audienceTarget + '\'' +
                        ",user_sr_flair_enabled = '" + userSrFlairEnabled + '\'' +
                        ",wls = '" + wls + '\'' +
                        ",suggested_comment_sort = '" + suggestedCommentSort + '\'' +
                        ",submit_text = '" + submitText + '\'' +
                        ",user_has_favorited = '" + userHasFavorited + '\'' +
                        ",accounts_active_is_fuzzed = '" + accountsActiveIsFuzzed + '\'' +
                        ",allow_images = '" + allowImages + '\'' +
                        ",public_traffic = '" + publicTraffic + '\'' +
                        ",description = '" + description + '\'' +
                        ",user_flair_text = '" + userFlairText + '\'' +
                        ",title = '" + title + '\'' +
                        ",banner_background_color = '" + bannerBackgroundColor + '\'' +
                        ",user_flair_template_id = '" + userFlairTemplateId + '\'' +
                        ",display_name_prefixed = '" + displayNamePrefixed + '\'' +
                        ",submission_type = '" + submissionType + '\'' +
                        ",spoilers_enabled = '" + spoilersEnabled + '\'' +
                        ",user_sr_theme_enabled = '" + userSrThemeEnabled + '\'' +
                        ",show_media_preview = '" + showMediaPreview + '\'' +
                        ",lang = '" + lang + '\'' +
                        ",user_is_moderator = '" + userIsModerator + '\'' +
                        ",user_flair_background_color = '" + userFlairBackgroundColor + '\'' +
                        ",allow_discovery = '" + allowDiscovery + '\'' +
                        ",banner_background_image = '" + bannerBackgroundImage + '\'' +
                        ",banner_size = '" + bannerSize + '\'' +
                        ",over18 = '" + over18 + '\'' +
                        ",subreddit_type = '" + subredditType + '\'' +
                        ",collapse_deleted_comments = '" + collapseDeletedComments + '\'' +
                        ",header_size = '" + headerSize + '\'' +
                        ",has_menu_widget = '" + hasMenuWidget + '\'' +
                        ",advertiser_category = '" + advertiserCategory + '\'' +
                        ",original_content_tag_enabled = '" + originalContentTagEnabled + '\'' +
                        ",all_original_content = '" + allOriginalContent + '\'' +
                        ",url = '" + url + '\'' +
                        ",user_can_flair_in_sr = '" + userCanFlairInSr + '\'' +
                        ",allow_videos = '" + allowVideos + '\'' +
                        ",header_img = '" + headerImg + '\'' +
                        ",icon_img = '" + iconImg + '\'' +
                        ",submit_text_html = '" + submitTextHtml + '\'' +
                        ",wiki_enabled = '" + wikiEnabled + '\'' +
                        ",hide_ads = '" + hideAds + '\'' +
                        ",quarantine = '" + quarantine + '\'' +
                        ",can_assign_link_flair = '" + canAssignLinkFlair + '\'' +
                        "}";
    }
}