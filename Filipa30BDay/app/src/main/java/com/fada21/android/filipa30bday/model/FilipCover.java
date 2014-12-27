package com.fada21.android.filipa30bday.model;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
