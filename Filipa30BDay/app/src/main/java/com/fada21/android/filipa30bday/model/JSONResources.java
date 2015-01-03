package com.fada21.android.filipa30bday.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import lombok.Data;

@Data
public class JSONResources {
    @JsonProperty
    List<FilipCover> resources;
}