package com.fada21.android.filipkowe30bday.model;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@Parcel
public class FilipCover {
    int id;
    String title;
    String author;
    String ditty;
    String url;

    public FilipCover() {}
}
