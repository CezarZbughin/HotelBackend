package com.cezar.HotelBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowNonPortable;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class CreateReservationRequest {
    private List<Long> roomIds;
    private LocalDate startDate;
    private LocalDate endDate;
}
