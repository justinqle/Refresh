package com.justinqle.refresh.models.user;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class EmailDigestHeaderPrefix{

	@SerializedName("owner")
	private String owner;

	@SerializedName("experiment_id")
	private int experimentId;

	@SerializedName("variant")
	private String variant;

	public void setOwner(String owner){
		this.owner = owner;
	}

	public String getOwner(){
		return owner;
	}

	public void setExperimentId(int experimentId){
		this.experimentId = experimentId;
	}

	public int getExperimentId(){
		return experimentId;
	}

	public void setVariant(String variant){
		this.variant = variant;
	}

	public String getVariant(){
		return variant;
	}

	@Override
 	public String toString(){
		return 
			"EmailDigestHeaderPrefix{" + 
			"owner = '" + owner + '\'' + 
			",experiment_id = '" + experimentId + '\'' + 
			",variant = '" + variant + '\'' + 
			"}";
		}
}