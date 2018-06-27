package com.justinqle.refresh.models.user;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class User{

	@SerializedName("is_gold")
	private boolean isGold;

	@SerializedName("is_mod")
	private boolean isMod;

	@SerializedName("over_18")
	private boolean over18;

	@SerializedName("pref_clickgadget")
	private int prefClickgadget;

	@SerializedName("in_beta")
	private boolean inBeta;

	@SerializedName("has_verified_email")
	private Object hasVerifiedEmail;

	@SerializedName("is_suspended")
	private boolean isSuspended;

	@SerializedName("pref_geopopular")
	private String prefGeopopular;

	@SerializedName("comment_karma")
	private int commentKarma;

	@SerializedName("is_sponsor")
	private boolean isSponsor;

	@SerializedName("subreddit")
	private Subreddit subreddit;

	@SerializedName("features")
	private Features features;

	@SerializedName("seen_redesign_modal")
	private boolean seenRedesignModal;

	@SerializedName("suspension_expiration_utc")
	private Object suspensionExpirationUtc;

	@SerializedName("seen_layout_switch")
	private boolean seenLayoutSwitch;

	@SerializedName("pref_nightmode")
	private boolean prefNightmode;

	@SerializedName("pref_show_trending")
	private boolean prefShowTrending;

	@SerializedName("id")
	private String id;

	@SerializedName("has_visited_new_profile")
	private boolean hasVisitedNewProfile;

	@SerializedName("link_karma")
	private int linkKarma;

	@SerializedName("created_utc")
	private double createdUtc;

	@SerializedName("num_friends")
	private int numFriends;

	@SerializedName("oauth_client_id")
	private String oauthClientId;

	@SerializedName("pref_autoplay")
	private boolean prefAutoplay;

	@SerializedName("gold_creddits")
	private int goldCreddits;

	@SerializedName("in_redesign_beta")
	private boolean inRedesignBeta;

	@SerializedName("hide_from_robots")
	private boolean hideFromRobots;

	@SerializedName("created")
	private double created;

	@SerializedName("verified")
	private boolean verified;

	@SerializedName("has_subscribed")
	private boolean hasSubscribed;

	@SerializedName("pref_top_karma_subreddits")
	private boolean prefTopKarmaSubreddits;

	@SerializedName("is_employee")
	private boolean isEmployee;

	@SerializedName("pref_no_profanity")
	private boolean prefNoProfanity;

	@SerializedName("icon_img")
	private String iconImg;

	@SerializedName("pref_show_snoovatar")
	private boolean prefShowSnoovatar;

	@SerializedName("inbox_count")
	private int inboxCount;

	@SerializedName("name")
	private String name;

	@SerializedName("gold_expiration")
	private Object goldExpiration;

	@SerializedName("seen_subreddit_chat_ftux")
	private boolean seenSubredditChatFtux;

	public void setIsGold(boolean isGold){
		this.isGold = isGold;
	}

	public boolean isIsGold(){
		return isGold;
	}

	public void setIsMod(boolean isMod){
		this.isMod = isMod;
	}

	public boolean isIsMod(){
		return isMod;
	}

	public void setOver18(boolean over18){
		this.over18 = over18;
	}

	public boolean isOver18(){
		return over18;
	}

	public void setPrefClickgadget(int prefClickgadget){
		this.prefClickgadget = prefClickgadget;
	}

	public int getPrefClickgadget(){
		return prefClickgadget;
	}

	public void setInBeta(boolean inBeta){
		this.inBeta = inBeta;
	}

	public boolean isInBeta(){
		return inBeta;
	}

	public void setHasVerifiedEmail(Object hasVerifiedEmail){
		this.hasVerifiedEmail = hasVerifiedEmail;
	}

	public Object getHasVerifiedEmail(){
		return hasVerifiedEmail;
	}

	public void setIsSuspended(boolean isSuspended){
		this.isSuspended = isSuspended;
	}

	public boolean isIsSuspended(){
		return isSuspended;
	}

	public void setPrefGeopopular(String prefGeopopular){
		this.prefGeopopular = prefGeopopular;
	}

	public String getPrefGeopopular(){
		return prefGeopopular;
	}

	public void setCommentKarma(int commentKarma){
		this.commentKarma = commentKarma;
	}

	public int getCommentKarma(){
		return commentKarma;
	}

	public void setIsSponsor(boolean isSponsor){
		this.isSponsor = isSponsor;
	}

	public boolean isIsSponsor(){
		return isSponsor;
	}

	public void setSubreddit(Subreddit subreddit){
		this.subreddit = subreddit;
	}

	public Subreddit getSubreddit(){
		return subreddit;
	}

	public void setFeatures(Features features){
		this.features = features;
	}

	public Features getFeatures(){
		return features;
	}

	public void setSeenRedesignModal(boolean seenRedesignModal){
		this.seenRedesignModal = seenRedesignModal;
	}

	public boolean isSeenRedesignModal(){
		return seenRedesignModal;
	}

	public void setSuspensionExpirationUtc(Object suspensionExpirationUtc){
		this.suspensionExpirationUtc = suspensionExpirationUtc;
	}

	public Object getSuspensionExpirationUtc(){
		return suspensionExpirationUtc;
	}

	public void setSeenLayoutSwitch(boolean seenLayoutSwitch){
		this.seenLayoutSwitch = seenLayoutSwitch;
	}

	public boolean isSeenLayoutSwitch(){
		return seenLayoutSwitch;
	}

	public void setPrefNightmode(boolean prefNightmode){
		this.prefNightmode = prefNightmode;
	}

	public boolean isPrefNightmode(){
		return prefNightmode;
	}

	public void setPrefShowTrending(boolean prefShowTrending){
		this.prefShowTrending = prefShowTrending;
	}

	public boolean isPrefShowTrending(){
		return prefShowTrending;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setHasVisitedNewProfile(boolean hasVisitedNewProfile){
		this.hasVisitedNewProfile = hasVisitedNewProfile;
	}

	public boolean isHasVisitedNewProfile(){
		return hasVisitedNewProfile;
	}

	public void setLinkKarma(int linkKarma){
		this.linkKarma = linkKarma;
	}

	public int getLinkKarma(){
		return linkKarma;
	}

	public void setCreatedUtc(double createdUtc){
		this.createdUtc = createdUtc;
	}

	public double getCreatedUtc(){
		return createdUtc;
	}

	public void setNumFriends(int numFriends){
		this.numFriends = numFriends;
	}

	public int getNumFriends(){
		return numFriends;
	}

	public void setOauthClientId(String oauthClientId){
		this.oauthClientId = oauthClientId;
	}

	public String getOauthClientId(){
		return oauthClientId;
	}

	public void setPrefAutoplay(boolean prefAutoplay){
		this.prefAutoplay = prefAutoplay;
	}

	public boolean isPrefAutoplay(){
		return prefAutoplay;
	}

	public void setGoldCreddits(int goldCreddits){
		this.goldCreddits = goldCreddits;
	}

	public int getGoldCreddits(){
		return goldCreddits;
	}

	public void setInRedesignBeta(boolean inRedesignBeta){
		this.inRedesignBeta = inRedesignBeta;
	}

	public boolean isInRedesignBeta(){
		return inRedesignBeta;
	}

	public void setHideFromRobots(boolean hideFromRobots){
		this.hideFromRobots = hideFromRobots;
	}

	public boolean isHideFromRobots(){
		return hideFromRobots;
	}

	public void setCreated(double created){
		this.created = created;
	}

	public double getCreated(){
		return created;
	}

	public void setVerified(boolean verified){
		this.verified = verified;
	}

	public boolean isVerified(){
		return verified;
	}

	public void setHasSubscribed(boolean hasSubscribed){
		this.hasSubscribed = hasSubscribed;
	}

	public boolean isHasSubscribed(){
		return hasSubscribed;
	}

	public void setPrefTopKarmaSubreddits(boolean prefTopKarmaSubreddits){
		this.prefTopKarmaSubreddits = prefTopKarmaSubreddits;
	}

	public boolean isPrefTopKarmaSubreddits(){
		return prefTopKarmaSubreddits;
	}

	public void setIsEmployee(boolean isEmployee){
		this.isEmployee = isEmployee;
	}

	public boolean isIsEmployee(){
		return isEmployee;
	}

	public void setPrefNoProfanity(boolean prefNoProfanity){
		this.prefNoProfanity = prefNoProfanity;
	}

	public boolean isPrefNoProfanity(){
		return prefNoProfanity;
	}

	public void setIconImg(String iconImg){
		this.iconImg = iconImg;
	}

	public String getIconImg(){
		return iconImg;
	}

	public void setPrefShowSnoovatar(boolean prefShowSnoovatar){
		this.prefShowSnoovatar = prefShowSnoovatar;
	}

	public boolean isPrefShowSnoovatar(){
		return prefShowSnoovatar;
	}

	public void setInboxCount(int inboxCount){
		this.inboxCount = inboxCount;
	}

	public int getInboxCount(){
		return inboxCount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setGoldExpiration(Object goldExpiration){
		this.goldExpiration = goldExpiration;
	}

	public Object getGoldExpiration(){
		return goldExpiration;
	}

	public void setSeenSubredditChatFtux(boolean seenSubredditChatFtux){
		this.seenSubredditChatFtux = seenSubredditChatFtux;
	}

	public boolean isSeenSubredditChatFtux(){
		return seenSubredditChatFtux;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"is_gold = '" + isGold + '\'' + 
			",is_mod = '" + isMod + '\'' + 
			",over_18 = '" + over18 + '\'' + 
			",pref_clickgadget = '" + prefClickgadget + '\'' + 
			",in_beta = '" + inBeta + '\'' + 
			",has_verified_email = '" + hasVerifiedEmail + '\'' + 
			",is_suspended = '" + isSuspended + '\'' + 
			",pref_geopopular = '" + prefGeopopular + '\'' + 
			",comment_karma = '" + commentKarma + '\'' + 
			",is_sponsor = '" + isSponsor + '\'' + 
			",subreddit = '" + subreddit + '\'' + 
			",features = '" + features + '\'' + 
			",seen_redesign_modal = '" + seenRedesignModal + '\'' + 
			",suspension_expiration_utc = '" + suspensionExpirationUtc + '\'' + 
			",seen_layout_switch = '" + seenLayoutSwitch + '\'' + 
			",pref_nightmode = '" + prefNightmode + '\'' + 
			",pref_show_trending = '" + prefShowTrending + '\'' + 
			",id = '" + id + '\'' + 
			",has_visited_new_profile = '" + hasVisitedNewProfile + '\'' + 
			",link_karma = '" + linkKarma + '\'' + 
			",created_utc = '" + createdUtc + '\'' + 
			",num_friends = '" + numFriends + '\'' + 
			",oauth_client_id = '" + oauthClientId + '\'' + 
			",pref_autoplay = '" + prefAutoplay + '\'' + 
			",gold_creddits = '" + goldCreddits + '\'' + 
			",in_redesign_beta = '" + inRedesignBeta + '\'' + 
			",hide_from_robots = '" + hideFromRobots + '\'' + 
			",created = '" + created + '\'' + 
			",verified = '" + verified + '\'' + 
			",has_subscribed = '" + hasSubscribed + '\'' + 
			",pref_top_karma_subreddits = '" + prefTopKarmaSubreddits + '\'' + 
			",is_employee = '" + isEmployee + '\'' + 
			",pref_no_profanity = '" + prefNoProfanity + '\'' + 
			",icon_img = '" + iconImg + '\'' + 
			",pref_show_snoovatar = '" + prefShowSnoovatar + '\'' + 
			",inbox_count = '" + inboxCount + '\'' + 
			",name = '" + name + '\'' + 
			",gold_expiration = '" + goldExpiration + '\'' + 
			",seen_subreddit_chat_ftux = '" + seenSubredditChatFtux + '\'' + 
			"}";
		}
}