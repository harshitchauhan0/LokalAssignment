package com.harshit.lokalassignment.data.models
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Jobs(
    @SerializedName("advertiser")
    val advertiser: Int? = null,

    @SerializedName("amount")
    val amount: String? = null,

    @SerializedName("button_text")
    val buttonText: String? = null,

    @SerializedName("city_location")
    val cityLocation: Int? = null,

    @SerializedName("company_name")
    val companyName: String? = null,

    @SerializedName("contact_preference")
    val contactPreference: ContactPreference? = null,

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("contentV3")
    val contentV3: ContentV3? = null,

    @SerializedName("created_on")
    val createdOn: String? = null,

    @SerializedName("creatives")
    val creatives: List<Creative>? = null,

    @SerializedName("custom_link")
    val customLink: String? = null,

    @SerializedName("enable_lead_collection")
    val enableLeadCollection: Boolean? = null,

    @SerializedName("experience")
    val experience: Int? = null,

    @SerializedName("expire_on")
    val expireOn: String? = null,

    @SerializedName("fb_shares")
    val fbShares: Int? = null,

    @SerializedName("fee_details")
    val feeDetails: FeeDetails? = null,

    @SerializedName("fees_charged")
    val feesCharged: Int? = null,

    @SerializedName("fees_text")
    val feesText: String? = null,

    @PrimaryKey
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("is_applied")
    val isApplied: Boolean? = null,

    @SerializedName("is_bookmarked")
    val isBookmarked: Boolean? = null,

    @SerializedName("is_job_seeker_profile_mandatory")
    val isJobSeekerProfileMandatory: Boolean? = null,

    @SerializedName("is_owner")
    val isOwner: Boolean? = null,

    @SerializedName("is_premium")
    val isPremium: Boolean? = null,

    @SerializedName("job_category")
    val jobCategory: String? = null,

    @SerializedName("job_category_id")
    val jobCategoryId: Int? = null,

    @SerializedName("job_hours")
    val jobHours: String? = null,

    @SerializedName("job_location_slug")
    val jobLocationSlug: String? = null,

    @SerializedName("job_role")
    val jobRole: String? = null,

    @SerializedName("job_role_id")
    val jobRoleId: Int? = null,

    @SerializedName("job_tags")
    val jobTags: List<JobTag>? = null,

    @SerializedName("job_type")
    val jobType: Int? = null,

    @SerializedName("locality")
    val locality: Int? = null,

    @SerializedName("locations")
    val locations: List<Location>? = null,

    @SerializedName("num_applications")
    val numApplications: Int? = null,

    @SerializedName("openings_count")
    val openingsCount: Int? = null,

    @SerializedName("other_details")
    val otherDetails: String? = null,

    @SerializedName("premium_till")
    val premiumTill: String? = null,

    @SerializedName("primary_details")
    val primaryDetails: PrimaryDetails? = null,

    @SerializedName("qualification")
    val qualification: Int? = null,

    @SerializedName("salary_max")
    val salaryMax: Int? = null,

    @SerializedName("salary_min")
    val salaryMin: Int? = null,

    @SerializedName("shares")
    val shares: Int? = null,

    @SerializedName("shift_timing")
    val shiftTiming: Int? = null,

    @SerializedName("should_show_last_contacted")
    val shouldShowLastContacted: Boolean? = null,

    @SerializedName("status")
    val status: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("type")
    val type: Int? = null,

    @SerializedName("updated_on")
    val updatedOn: String? = null,

    @SerializedName("views")
    val views: Int? = null,

    @SerializedName("whatsapp_no")
    val whatsappNo: String? = null
)
