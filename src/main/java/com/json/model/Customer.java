package com.json.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.json.model.base.AbstractModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer extends AbstractModel {

    private String id;

    private String name;

    @JsonProperty("phone")
    private List<Phone> phonesList;

    @JsonProperty("address")
    private List<Address> addressList;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Phone> getPhonesList() {
        return phonesList;
    }

    public void setPhonesList(final List<Phone> phonesList) {
        this.phonesList = phonesList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
