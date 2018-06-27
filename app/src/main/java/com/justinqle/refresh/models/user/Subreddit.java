package com.justinqle.refresh.models.user;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Subreddit{

	@SerializedName("public_description")
	private String publicDescription;

	@SerializedName("key_color")
	private String keyColor;

	@SerializedName("over_18")
	private boolean over18;

	@SerializedName("description")
	private String description;

	@SerializedName("user_is_banned")
	private boolean userIsBanned;

	@SerializedName("title")
	private String title;

	@SerializedName("is_default_banner")
	private boolean isDefaultBanner;

	@SerializedName("user_is_muted")
	private boolean userIsMuted;

	@SerializedName("is_default_icon")
	private boolean isDefaultIcon;

	@SerializedName("display_name_prefixed")
	private String displayNamePrefixed;

	@SerializedName("user_is_subscriber")
	private boolean userIsSubscriber;

	@SerializedName("icon_size")
	private List<Integer> iconSize;

	@SerializedName("show_media")
	private boolean showMedia;

	@SerializedName("default_set")
	private boolean defaultSet;

	@SerializedName("user_is_moderator")
	private boolean userIsModerator;

	@SerializedName("subreddit_type")
	private String subredditType;

	@SerializedName("banner_size")
	private Object bannerSize;

	@SerializedName("subscribers")
	private int subscribers;

	@SerializedName("header_size")
	private Object headerSize;

	@SerializedName("community_icon")
	private String communityIcon;

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("primary_color")
	private String primaryColor;

	@SerializedName("url")
	private String url;

	@SerializedName("link_flair_position")
	private String linkFlairPosition;

	@SerializedName("user_is_contributor")
	private boolean userIsContributor;

	@SerializedName("link_flair_enabled")
	private boolean linkFlairEnabled;

	@SerializedName("header_img")
	private Object headerImg;

	@SerializedName("icon_img")
	private String iconImg;

	@SerializedName("icon_color")
	private String iconColor;

	@SerializedName("banner_img")
	private String bannerImg;

	@SerializedName("name")
	private String name;

	@SerializedName("audience_target")
	private String audienceTarget;

	public void setPublicDescription(String publicDescription){
		this.publicDescription = publicDescription;
	}

	public String getPublicDescription(){
		return publicDescription;
	}

	public void setKeyColor(String keyColor){
		this.keyColor = keyColor;
	}

	public String getKeyColor(){
		return keyColor;
	}

	public void setOver18(boolean over18){
		this.over18 = over18;
	}

	public boolean isOver18(){
		return over18;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setUserIsBanned(boolean userIsBanned){
		this.userIsBanned = userIsBanned;
	}

	public boolean isUserIsBanned(){
		return userIsBanned;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setIsDefaultBanner(boolean isDefaultBanner){
		this.isDefaultBanner = isDefaultBanner;
	}

	public boolean isIsDefaultBanner(){
		return isDefaultBanner;
	}

	public void setUserIsMuted(boolean userIsMuted){
		this.userIsMuted = userIsMuted;
	}

	public boolean isUserIsMuted(){
		return userIsMuted;
	}

	public void setIsDefaultIcon(boolean isDefaultIcon){
		this.isDefaultIcon = isDefaultIcon;
	}

	public boolean isIsDefaultIcon(){
		return isDefaultIcon;
	}

	public void setDisplayNamePrefixed(String displayNamePrefixed){
		this.displayNamePrefixed = displayNamePrefixed;
	}

	public String getDisplayNamePrefixed(){
		return displayNamePrefixed;
	}

	public void setUserIsSubscriber(boolean userIsSubscriber){
		this.userIsSubscriber = userIsSubscriber;
	}

	public boolean isUserIsSubscriber(){
		return userIsSubscriber;
	}

	public void setIconSize(List<Integer> iconSize){
		this.iconSize = iconSize;
	}

	public List<Integer> getIconSize(){
		return iconSize;
	}

	public void setShowMedia(boolean showMedia){
		this.showMedia = showMedia;
	}

	public boolean isShowMedia(){
		return showMedia;
	}

	public void setDefaultSet(boolean defaultSet){
		this.defaultSet = defaultSet;
	}

	public boolean isDefaultSet(){
		return defaultSet;
	}

	public void setUserIsModerator(boolean userIsModerator){
		this.userIsModerator = userIsModerator;
	}

	public boolean isUserIsModerator(){
		return userIsModerator;
	}

	public void setSubredditType(String subredditType){
		this.subredditType = subredditType;
	}

	public String getSubredditType(){
		return subredditType;
	}

	public void setBannerSize(Object bannerSize){
		this.bannerSize = bannerSize;
	}

	public Object getBannerSize(){
		return bannerSize;
	}

	public void setSubscribers(int subscribers){
		this.subscribers = subscribers;
	}

	public int getSubscribers(){
		return subscribers;
	}

	public void setHeaderSize(Object headerSize){
		this.headerSize = headerSize;
	}

	public Object getHeaderSize(){
		return headerSize;
	}

	public void setCommunityIcon(String communityIcon){
		this.communityIcon = communityIcon;
	}

	public String getCommunityIcon(){
		return communityIcon;
	}

	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}

	public void setPrimaryColor(String primaryColor){
		this.primaryColor = primaryColor;
	}

	public String getPrimaryColor(){
		return primaryColor;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setLinkFlairPosition(String linkFlairPosition){
		this.linkFlairPosition = linkFlairPosition;
	}

	public String getLinkFlairPosition(){
		return linkFlairPosition;
	}

	public void setUserIsContributor(boolean userIsContributor){
		this.userIsContributor = userIsContributor;
	}

	public boolean isUserIsContributor(){
		return userIsContributor;
	}

	public void setLinkFlairEnabled(boolean linkFlairEnabled){
		this.linkFlairEnabled = linkFlairEnabled;
	}

	public boolean isLinkFlairEnabled(){
		return linkFlairEnabled;
	}

	public void setHeaderImg(Object headerImg){
		this.headerImg = headerImg;
	}

	public Object getHeaderImg(){
		return headerImg;
	}

	public void setIconImg(String iconImg){
		this.iconImg = iconImg;
	}

	public String getIconImg(){
		return iconImg;
	}

	public void setIconColor(String iconColor){
		this.iconColor = iconColor;
	}

	public String getIconColor(){
		return iconColor;
	}

	public void setBannerImg(String bannerImg){
		this.bannerImg = bannerImg;
	}

	public String getBannerImg(){
		return bannerImg;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAudienceTarget(String audienceTarget){
		this.audienceTarget = audienceTarget;
	}

	public String getAudienceTarget(){
		return audienceTarget;
	}

	@Override
 	public String toString(){
		return 
			"Subreddit{" + 
			"public_description = '" + publicDescription + '\'' + 
			",key_color = '" + keyColor + '\'' + 
			",over_18 = '" + over18 + '\'' + 
			",description = '" + description + '\'' + 
			",user_is_banned = '" + userIsBanned + '\'' + 
			",title = '" + title + '\'' + 
			",is_default_banner = '" + isDefaultBanner + '\'' + 
			",user_is_muted = '" + userIsMuted + '\'' + 
			",is_default_icon = '" + isDefaultIcon + '\'' + 
			",display_name_prefixed = '" + displayNamePrefixed + '\'' + 
			",user_is_subscriber = '" + userIsSubscriber + '\'' + 
			",icon_size = '" + iconSize + '\'' + 
			",show_media = '" + showMedia + '\'' + 
			",default_set = '" + defaultSet + '\'' + 
			",user_is_moderator = '" + userIsModerator + '\'' + 
			",subreddit_type = '" + subredditType + '\'' + 
			",banner_size = '" + bannerSize + '\'' + 
			",subscribers = '" + subscribers + '\'' + 
			",header_size = '" + headerSize + '\'' + 
			",community_icon = '" + communityIcon + '\'' + 
			",display_name = '" + displayName + '\'' + 
			",primary_color = '" + primaryColor + '\'' + 
			",url = '" + url + '\'' + 
			",link_flair_position = '" + linkFlairPosition + '\'' + 
			",user_is_contributor = '" + userIsContributor + '\'' + 
			",link_flair_enabled = '" + linkFlairEnabled + '\'' + 
			",header_img = '" + headerImg + '\'' + 
			",icon_img = '" + iconImg + '\'' + 
			",icon_color = '" + iconColor + '\'' + 
			",banner_img = '" + bannerImg + '\'' + 
			",name = '" + name + '\'' + 
			",audience_target = '" + audienceTarget + '\'' + 
			"}";
		}
}