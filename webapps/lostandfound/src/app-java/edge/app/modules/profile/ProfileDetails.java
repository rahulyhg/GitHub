package edge.app.modules.profile;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edge.core.modules.auth.SignUpEntity;
import edge.core.modules.common.EdgeEntity;

@Entity
@Table(name = "PROFILE_DETAILS")
public class ProfileDetails extends EdgeEntity{

	@Transient
	private SignUpEntity signUpEntity;
	
	@Id
	
	@Column(nullable = false, length = 50)
	private String profileId;
	
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
	
	@Column(nullable = false, length = 50)
	private String degreeType;
	
	@Column(nullable = false, length = 50)
	private String degreeDetails;
	
	@Column(nullable = false, length = 50)
	private String company;
	
	@Column(nullable = false, length = 50)
	private String designation;

	@Column(nullable = false)
	private Integer earning;
	
	@Column(nullable = false, length = 50)
	private String residingAtCity;
	
	@Column(nullable = false, length = 50)
	private String residingAtState;
	
	@Column(nullable = false, length = 50)
	private String residingAtCountry;
	
	@Column(nullable = false, length = 20)
	private String residingWith;
	
	@Column(nullable = false, length = 30)
	private String kundaliRas;
	
	@Column(nullable = false, length = 30)
	private String kundaliNakshatra;
	
	@Column(nullable = false, length = 10)
	private String kundaliNadi;
	
	@Column(nullable = false)
	private Integer kundaliCharan;
	
	@Column(nullable = false, length = 10)
	private String kundaliGan;
	
	@Column(nullable = false, length = 30)
	private String kundaliGotr;
	
	@Column(nullable = false, length = 30)
	private String kundaliDevak;
	
	@Column(nullable = false, length = 50)
	private String familyResidingAtCity;
	
	@Column(nullable = false, length = 50)
	private String familyResidingAtState;
	
	@Column(nullable = false, length = 50)
	private String familyResidingAtCountry;
	
	@Column(nullable = true, length = 50)
	private String nativeCity;
	
	@Column(nullable = true, length = 50)
	private String nativeState;
	
	@Column(nullable = true, length = 50)
	private String nativeCountry;

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

	@Transient
	private SecureProfileDetails secure;

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
		secure.setProfileId(profileId);
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

	public String getResidingAtCity() {
		return residingAtCity;
	}

	public void setResidingAtCity(String residingAtCity) {
		this.residingAtCity = residingAtCity;
	}

	public String getResidingAtState() {
		return residingAtState;
	}

	public void setResidingAtState(String residingAtState) {
		this.residingAtState = residingAtState;
	}

	public String getResidingAtCountry() {
		return residingAtCountry;
	}

	public void setResidingAtCountry(String residingAtCountry) {
		this.residingAtCountry = residingAtCountry;
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

	public Integer getKundaliCharan() {
		return kundaliCharan;
	}

	public void setKundaliCharan(Integer kundaliCharan) {
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

	public String getFamilyResidingAtCity() {
		return familyResidingAtCity;
	}

	public void setFamilyResidingAtCity(String familyResidingAtCity) {
		this.familyResidingAtCity = familyResidingAtCity;
	}

	public String getFamilyResidingAtState() {
		return familyResidingAtState;
	}

	public void setFamilyResidingAtState(String familyResidingAtState) {
		this.familyResidingAtState = familyResidingAtState;
	}

	public String getFamilyResidingAtCountry() {
		return familyResidingAtCountry;
	}

	public void setFamilyResidingAtCountry(String familyResidingAtCountry) {
		this.familyResidingAtCountry = familyResidingAtCountry;
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
	
}
