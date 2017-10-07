package edge.app.modules.profile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edge.app.modules.profileConnection.ProfileConnection;
import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.EdgeEntity;

@Entity
@Table(name = "PROFILE_DETAILS")
public class ProfileDetails extends EdgeEntity{

	@Transient
	private SignUpEntity signUpEntity;
	
	@Id
	@Column(nullable = false, updatable=false, length = 50)
	private String profileId;
	
	@Column(nullable = false, length = 10)
	private String gender;
	
	@Column(nullable = false)
	private Integer heightFt;

	@Column(nullable = false)
	private Integer heightInch;

	@Column(nullable = false)
	private Integer weight;
	
	@Column(nullable = false, length = 20)
	private String bodyType;
	
	@Column(nullable = false, length = 20)
	private String skinColor;
	
	@Column(nullable = false, length = 20)
	private String religion;
	
	@Column(nullable = false, length = 20)
	private String cast;
	
	@Column(nullable = false, length = 20)
	private String motherTongue;
	
	@Column(nullable = false, length = 5)
	private String bloodGroup;

	@Column(nullable = false)
	private Date birthDate;

	@Column(nullable = false)
	private Integer birthHr;

	@Column(nullable = false)
	private Integer birthMin;
	
	@Column(nullable = false, length = 3)
	private String birthAP;
	
	@Column(nullable = false, length = 50)
	private String birthCity;
	
	@Column(nullable = false, length = 50)
	private String birthState;
	
	@Column(nullable = false, length = 50)
	private String birthCountry;
	
	@Column(nullable = false, length = 20)
	private String maritalStatus;
	
	@Column(nullable = false, length = 20)
	private String manglikStatus;

	@Column(nullable = false, length = 20)
	private String physicalStatus;
	
	@Column(nullable = false, length = 50)
	private String degreeType;
	
	@Column(nullable = false, length = 50)
	private String degreeDetails;

	@Column(nullable = false, length = 50)
	private String professionalType;
	
	@Column(nullable = false, length = 50)
	private String company;
	
	@Column(nullable = false, length = 50)
	private String designation;

	@Column(nullable = false)
	private Integer earning;
	
	@Column(nullable = false, length = 50)
	private String workCity;
	
	@Column(nullable = false, length = 50)
	private String workState;
	
	@Column(nullable = false, length = 50)
	private String workCountry;

	@Column(nullable = false, length = 50)
	private String familyCity;
	
	@Column(nullable = false, length = 50)
	private String familyState;
	
	@Column(nullable = false, length = 50)
	private String familyCountry;

	@Column(nullable = true, length = 50)
	private String nativeCity;
	
	@Column(nullable = true, length = 50)
	private String nativeState;
	
	@Column(nullable = true, length = 50)
	private String nativeCountry;
	
	@Column(nullable = false, length = 20)
	private String residingWith;
	
	@Column(nullable = false, length = 30)
	private String kundaliRas="";
	
	@Column(nullable = false, length = 30)
	private String kundaliNakshatra="";
	
	@Column(nullable = false, length = 10)
	private String kundaliNadi="";
	
	@Column(nullable = false, length = 5)
	private String kundaliCharan="";
	
	@Column(nullable = false, length = 10)
	private String kundaliGan="";
	
	@Column(nullable = false, length = 30)
	private String kundaliGotr="";
	
	@Column(nullable = false, length = 30)
	private String kundaliDevak="";

	@Column(nullable = true, length = 100)
	private String fatherOccupation;

	@Column(nullable = true, length = 100)
	private String motherOccupation;
	
	@Column(nullable = false)
	private Integer brothers;

	@Column(nullable = false)
	private Integer marriedBrothers;

	@Column(nullable = false)
	private Integer sisters;

	@Column(nullable = false)
	private Integer marriedSisters;
	
	@Column(nullable = true, length = 100)
	private String wealth;
	
	@Column(nullable = false, length = 30)
	private String profilePic="NA";
	
	@Column(nullable = false, length = 30)
	private String albumImg1="";

	@Column(nullable = false, length = 30)
	private String albumImg2="";

	@Column(nullable = false, length = 30)
	private String albumImg3="";

	@Transient
	private SecureProfileDetails secure;
	
	@Transient
	private ProfileConnection profileConnection;

	public SignUpEntity getSignUpEntity() {
		return signUpEntity;
	}

	public void setSignUpEntity(SignUpEntity signUpEntity) {
		this.signUpEntity = signUpEntity;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public Integer getHeightFt() {
		return heightFt;
	}

	public void setHeightFt(Integer heightFt) {
		this.heightFt = heightFt;
	}

	public Integer getHeightInch() {
		return heightInch;
	}

	public void setHeightInch(Integer heightInch) {
		this.heightInch = heightInch;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getSkinColor() {
		return skinColor;
	}

	public void setSkinColor(String skinColor) {
		this.skinColor = skinColor;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getBirthHr() {
		return birthHr;
	}

	public void setBirthHr(Integer birthHr) {
		this.birthHr = birthHr;
	}

	public Integer getBirthMin() {
		return birthMin;
	}

	public void setBirthMin(Integer birthMin) {
		this.birthMin = birthMin;
	}

	public String getBirthAP() {
		return birthAP;
	}

	public void setBirthAP(String birthAP) {
		this.birthAP = birthAP;
	}

	public String getBirthCity() {
		return birthCity;
	}

	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}

	public String getBirthState() {
		return birthState;
	}

	public void setBirthState(String birthState) {
		this.birthState = birthState;
	}

	public String getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Integer getEarning() {
		return earning;
	}

	public void setEarning(Integer earning) {
		this.earning = earning;
	}

	public String getResidingWith() {
		return residingWith;
	}

	public void setResidingWith(String residingWith) {
		this.residingWith = residingWith;
	}

	public String getKundaliRas() {
		return kundaliRas;
	}

	public void setKundaliRas(String kundaliRas) {
		this.kundaliRas = kundaliRas;
	}

	public String getKundaliNakshatra() {
		return kundaliNakshatra;
	}

	public void setKundaliNakshatra(String kundaliNakshatra) {
		this.kundaliNakshatra = kundaliNakshatra;
	}

	public String getKundaliNadi() {
		return kundaliNadi;
	}

	public void setKundaliNadi(String kundaliNadi) {
		this.kundaliNadi = kundaliNadi;
	}

	public String getKundaliCharan() {
		return kundaliCharan;
	}

	public void setKundaliCharan(String kundaliCharan) {
		this.kundaliCharan = kundaliCharan;
	}

	public String getKundaliGan() {
		return kundaliGan;
	}

	public void setKundaliGan(String kundaliGan) {
		this.kundaliGan = kundaliGan;
	}

	public String getKundaliGotr() {
		return kundaliGotr;
	}

	public void setKundaliGotr(String kundaliGotr) {
		this.kundaliGotr = kundaliGotr;
	}

	public String getKundaliDevak() {
		return kundaliDevak;
	}

	public void setKundaliDevak(String kundaliDevak) {
		this.kundaliDevak = kundaliDevak;
	}

	public String getNativeCity() {
		return nativeCity;
	}

	public void setNativeCity(String nativeCity) {
		this.nativeCity = nativeCity;
	}

	public String getNativeState() {
		return nativeState;
	}

	public void setNativeState(String nativeState) {
		this.nativeState = nativeState;
	}

	public String getNativeCountry() {
		return nativeCountry;
	}

	public void setNativeCountry(String nativeCountry) {
		this.nativeCountry = nativeCountry;
	}

	public Integer getBrothers() {
		return brothers;
	}

	public void setBrothers(Integer brothers) {
		this.brothers = brothers;
	}

	public Integer getMarriedBrothers() {
		return marriedBrothers;
	}

	public void setMarriedBrothers(Integer marriedBrothers) {
		this.marriedBrothers = marriedBrothers;
	}

	public Integer getSisters() {
		return sisters;
	}

	public void setSisters(Integer sisters) {
		this.sisters = sisters;
	}

	public Integer getMarriedSisters() {
		return marriedSisters;
	}

	public void setMarriedSisters(Integer marriedSisters) {
		this.marriedSisters = marriedSisters;
	}

	public String getWealth() {
		return wealth;
	}

	public void setWealth(String wealth) {
		this.wealth = wealth;
	}

	public SecureProfileDetails getSecure() {
		return secure;
	}

	public void setSecure(SecureProfileDetails secure) {
		this.secure = secure;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		this.cast = cast;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}

	public String getDegreeDetails() {
		return degreeDetails;
	}

	public void setDegreeDetails(String degreeDetails) {
		this.degreeDetails = degreeDetails;
	}

	public ProfileConnection getProfileConnection() {
		return profileConnection;
	}

	public void setProfileConnection(ProfileConnection profileConnection) {
		this.profileConnection = profileConnection;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getAlbumImg1() {
		return albumImg1;
	}

	public void setAlbumImg1(String albumImg1) {
		this.albumImg1 = albumImg1;
	}

	public String getAlbumImg2() {
		return albumImg2;
	}

	public void setAlbumImg2(String albumImg2) {
		this.albumImg2 = albumImg2;
	}

	public String getAlbumImg3() {
		return albumImg3;
	}

	public void setAlbumImg3(String albumImg3) {
		this.albumImg3 = albumImg3;
	}
	
	public String getProfileThumbnail(){
		if(profilePic == null || profilePic.trim().length() == 0){
			profilePic = "NA";
		}
		return "server/secured/getImage/profilePic/" + profileId +"/" + profilePic;
	}
	
	private void setProfileThumbnail(String str) {
		// Nothing to do here -- Added for UI
	}
	
	public List<ImageDetails> getProfileImages (){
		List<ImageDetails> images = new ArrayList<ImageDetails>();
		addImagesPath(images,"Profile Pic","profilePic",profilePic);
		addImagesPath(images,"Image 1","albumImg1",albumImg1);
		addImagesPath(images,"Image 2","albumImg2",albumImg2);
		addImagesPath(images,"Image 3","albumImg3",albumImg3);
		return images;
	}

	private void addImagesPath(List<ImageDetails> images, String caption, String key, String value) {
		if(value != null && value.trim().length() != 0){
			images.add(new ImageDetails("server/secured/getImage/"+ key +"/" + profileId +"/" + value, caption));
		}
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getWorkCity() {
		return workCity;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	public String getWorkState() {
		return workState;
	}

	public void setWorkState(String workState) {
		this.workState = workState;
	}

	public String getWorkCountry() {
		return workCountry;
	}

	public void setWorkCountry(String workCountry) {
		this.workCountry = workCountry;
	}

	public String getFamilyCity() {
		return familyCity;
	}

	public void setFamilyCity(String familyCity) {
		this.familyCity = familyCity;
	}

	public String getFamilyState() {
		return familyState;
	}

	public void setFamilyState(String familyState) {
		this.familyState = familyState;
	}

	public String getFamilyCountry() {
		return familyCountry;
	}

	public void setFamilyCountry(String familyCountry) {
		this.familyCountry = familyCountry;
	}

	public String getFatherOccupation() {
		return fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	public String getMotherOccupation() {
		return motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	public String getPhysicalStatus() {
		return physicalStatus;
	}

	public void setPhysicalStatus(String physicalStatus) {
		this.physicalStatus = physicalStatus;
	}

	public String getManglikStatus() {
		return manglikStatus;
	}

	public void setManglikStatus(String manglikStatus) {
		this.manglikStatus = manglikStatus;
	}

	public String getProfessionalType() {
		return professionalType;
	}

	public void setProfessionalType(String professionalType) {
		this.professionalType = professionalType;
	}
}

class ImageDetails{
	private String image;
	private String caption;
	
	public ImageDetails() {
		// Nothing to do here -- Added for UI
	}
	
	public ImageDetails(String image, String caption) {
		this.image = image;
		this.caption = caption;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	
	
}

