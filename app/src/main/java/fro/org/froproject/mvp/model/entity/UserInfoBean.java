package fro.org.froproject.mvp.model.entity;

/**
 * Created by Lgm on 2017/6/1 0001.
 */

public class UserInfoBean {
    int id;
    long birthDay;//年龄
    String avatar;
    String email;
    String idNumber; // 证件号码
    CredentialsBean idTypeResponse;//证件类型
    String name;//姓名
    String nickName;//昵称
    String password;// 密码
    String phoneNumber;// 手机号
    String position;//职位
    String sex;// 性别
    String token;//token
    String workOrg;//工作单位
    OrgBean natureResponse;//机构性质
    OrgBean categoryResponse;//机构类别
    OrgBean organizationResponse;//具体机构
    WorkYear WorkYearResponse;//工作年限
    ProvinceData provinceResponse;//省
    CityData cityResponse;//市
    CountryData countyResponse;//县

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public CredentialsBean getIdTypeResponse() {
        return idTypeResponse;
    }

    public void setIdTypeResponse(CredentialsBean idTypeResponse) {
        this.idTypeResponse = idTypeResponse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWorkOrg() {
        return workOrg;
    }

    public void setWorkOrg(String workOrg) {
        this.workOrg = workOrg;
    }

    public OrgBean getNatureResponse() {
        return natureResponse;
    }

    public void setNatureResponse(OrgBean natureResponse) {
        this.natureResponse = natureResponse;
    }

    public OrgBean getCategoryResponse() {
        return categoryResponse;
    }

    public void setCategoryResponse(OrgBean categoryResponse) {
        this.categoryResponse = categoryResponse;
    }

    public WorkYear getWorkYearResponse() {
        return WorkYearResponse;
    }

    public void setWorkYearResponse(WorkYear workYearResponse) {
        WorkYearResponse = workYearResponse;
    }

    public ProvinceData getProvinceResponse() {
        return provinceResponse;
    }

    public void setProvinceResponse(ProvinceData provinceResponse) {
        this.provinceResponse = provinceResponse;
    }

    public CityData getCityResponse() {
        return cityResponse;
    }

    public void setCityResponse(CityData cityResponse) {
        this.cityResponse = cityResponse;
    }

    public CountryData getCountyResponse() {
        return countyResponse;
    }

    public void setCountyResponse(CountryData countyResponse) {
        this.countyResponse = countyResponse;
    }

    public OrgBean getOrganizationResponse() {
        return organizationResponse;
    }

    public void setOrganizationResponse(OrgBean organizationResponse) {
        this.organizationResponse = organizationResponse;
    }
}
