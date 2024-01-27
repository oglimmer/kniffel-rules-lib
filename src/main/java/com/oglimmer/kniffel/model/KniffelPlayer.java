package com.oglimmer.kniffel.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class KniffelPlayer {
    private final String name;
    private int score;
    /** each bookingType can only be used once per game */
    private List<BookingType> usedBookingTypes = new ArrayList<>();
}
