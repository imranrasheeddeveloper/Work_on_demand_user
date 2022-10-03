package com.rizorsiumani.workondemanduser.data.businessModels;

public class RecommendedServicesModel {

    private final String serviceProviderName;
    private final double servicePrice;
    private final int imgSource;

    public RecommendedServicesModel(String serviceName, double servicePrice, int imgSource) {
        this.serviceProviderName = serviceName;
        this.servicePrice = servicePrice;
        this.imgSource = imgSource;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public int getImgSource() {
        return imgSource;
    }
}
