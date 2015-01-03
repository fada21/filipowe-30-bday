package com.fada21.android.filipkowe30bday.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class EventShowDittyOnToggled {
    @Getter final boolean dittyToBeShown;
}
