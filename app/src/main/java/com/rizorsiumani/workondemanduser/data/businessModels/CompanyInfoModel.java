package com.rizorsiumani.workondemanduser.data.businessModels;

public class CompanyInfoModel {

    String building_name,owner_name,company_registration_number,company_name,vat,position_in_company,company_address;

    public CompanyInfoModel(String building_name, String owner_name, String company_registration_number, String company_name, String vat, String position_in_company, String company_address) {
        this.building_name = building_name;
        this.owner_name = owner_name;
        this.company_registration_number = company_registration_number;
        this.company_name = company_name;
        this.vat = vat;
        this.position_in_company = position_in_company;
        this.company_address = company_address;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getCompany_registration_number() {
        return company_registration_number;
    }

    public void setCompany_registration_number(String company_registration_number) {
        this.company_registration_number = company_registration_number;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getPosition_in_company() {
        return position_in_company;
    }

    public void setPosition_in_company(String position_in_company) {
        this.position_in_company = position_in_company;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }
}
