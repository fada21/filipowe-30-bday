package com.fada21.android.filipa30bday.events;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(suppressConstructorProperties = true)
public class EventShowDittyToggle {
    @Getter final boolean dittyToBeShown;
}
