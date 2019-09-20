package com.optionringringtone.newringtonefree.object;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    @SerializedName("deafult_category")
    @Expose
    private Boolean deafultCategory;
    @SerializedName("category_name")
    @Expose
    private CategoryName categoryName;
    @SerializedName("number_of_ringtones")
    @Expose
    private Integer numberOfRingtones;
    @SerializedName("category_color")
    @Expose
    private String categoryColor;
    @SerializedName("package_link")
    @Expose
    private String packageLink;
    @SerializedName("category_icon")
    @Expose
    private String categoryIcon;
    @SerializedName("category_version")
    @Expose
    private Integer categoryVersion;
    @SerializedName("premium_reward_video")
    @Expose
    private Boolean premiumRewardVideo;
    @SerializedName("premium_order")
    @Expose
    private Boolean premiumOrder;
    @SerializedName("first_version_for_lock")
    @Expose
    private Integer firstVersionForLock;
    @SerializedName("promo_ringtones_of_day")
    @Expose
    private List<String> promoRingtonesOfDay = null;
    @SerializedName("category_promo_image")
    @Expose
    private String categoryPromoImage;
    @SerializedName("category_name_color")
    @Expose
    private String categoryNameColor;
    @SerializedName("category_call_to_action_color")
    @Expose
    private String categoryCallToActionColor;
    @SerializedName("category_call_to_action_txt_color")
    @Expose
    private String categoryCallToActionTxtColor;
    @SerializedName("country")
    @Expose
    private String country;


    public Boolean getDeafultCategory() {
        return deafultCategory;
    }

    public void setDeafultCategory(Boolean deafultCategory) {
        this.deafultCategory = deafultCategory;
    }

    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getNumberOfRingtones() {
        return numberOfRingtones;
    }

    public void setNumberOfRingtones(Integer numberOfRingtones) {
        this.numberOfRingtones = numberOfRingtones;
    }

    public String getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    public String getPackageLink() {
        return packageLink;
    }

    public void setPackageLink(String packageLink) {
        this.packageLink = packageLink;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public Integer getCategoryVersion() {
        return categoryVersion;
    }

    public void setCategoryVersion(Integer categoryVersion) {
        this.categoryVersion = categoryVersion;
    }

    public Boolean getPremiumRewardVideo() {
        return premiumRewardVideo;
    }

    public void setPremiumRewardVideo(Boolean premiumRewardVideo) {
        this.premiumRewardVideo = premiumRewardVideo;
    }

    public Boolean getPremiumOrder() {
        return premiumOrder;
    }

    public void setPremiumOrder(Boolean premiumOrder) {
        this.premiumOrder = premiumOrder;
    }

    public Integer getFirstVersionForLock() {
        return firstVersionForLock;
    }

    public void setFirstVersionForLock(Integer firstVersionForLock) {
        this.firstVersionForLock = firstVersionForLock;
    }

    public List<String> getPromoRingtonesOfDay() {
        return promoRingtonesOfDay;
    }

    public void setPromoRingtonesOfDay(List<String> promoRingtonesOfDay) {
        this.promoRingtonesOfDay = promoRingtonesOfDay;
    }

    public String getCategoryPromoImage() {
        return categoryPromoImage;
    }

    public void setCategoryPromoImage(String categoryPromoImage) {
        this.categoryPromoImage = categoryPromoImage;
    }

    public String getCategoryNameColor() {
        return categoryNameColor;
    }

    public void setCategoryNameColor(String categoryNameColor) {
        this.categoryNameColor = categoryNameColor;
    }

    public String getCategoryCallToActionColor() {
        return categoryCallToActionColor;
    }

    public void setCategoryCallToActionColor(String categoryCallToActionColor) {
        this.categoryCallToActionColor = categoryCallToActionColor;
    }

    public String getCategoryCallToActionTxtColor() {
        return categoryCallToActionTxtColor;
    }

    public void setCategoryCallToActionTxtColor(String categoryCallToActionTxtColor) {
        this.categoryCallToActionTxtColor = categoryCallToActionTxtColor;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNameByLanguage(String language) {
        switch (language) {
            case "en":
                return getCategoryName().getEn();
            case "ar":
                return getCategoryName().getAr();
            case "cs":
                return getCategoryName().getCs();
            case "da":
                return getCategoryName().getDa();
            case "de":
                return getCategoryName().getDe();
            case "es":
                return getCategoryName().getEs();
            case "fr":
                return getCategoryName().getFr();
            case "hr":
                return getCategoryName().getHr();
            case "hu":
                return getCategoryName().getHu();
            case "id":
                return getCategoryName().getId();
            case "it":
                return getCategoryName().getIt();
            case "ja":
                return getCategoryName().getJa();
            case "ko":
                return getCategoryName().getKo();
            case "ms":
                return getCategoryName().getMs();
            case "nb":
                return getCategoryName().getNb();
            case "nl":
                return getCategoryName().getNl();
            case "pl":
                return getCategoryName().getPl();
            case "pt":
                return getCategoryName().getPt();
            case "ro":
                return getCategoryName().getRo();
            case "ru":
                return getCategoryName().getRu();
            case "sr":
                return getCategoryName().getSr();
            case "sv":
                return getCategoryName().getSv();
            case "th":
                return getCategoryName().getTh();
            case "tr":
                return getCategoryName().getTr();
            case "vi":
                return getCategoryName().getVi();
            case "zh":
                return getCategoryName().getZh();
            default:
                return getCategoryName().getEn();
        }
    }

}

