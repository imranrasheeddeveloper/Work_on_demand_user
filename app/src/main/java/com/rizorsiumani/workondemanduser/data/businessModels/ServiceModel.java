package com.rizorsiumani.workondemanduser.data.businessModels;

import java.util.List;

public class ServiceModel {

    String serviceName;
    List<RecommendedServicesModel> model;

    public ServiceModel(String serviceName, List<RecommendedServicesModel> model) {
        this.serviceName = serviceName;
        this.model = model;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<RecommendedServicesModel> getModel() {
        return model;
    }

    public void setModel(List<RecommendedServicesModel> model) {
        this.model = model;
    }
}
