package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test12BDD {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private PaymentService paymentServiceMock;

    @Mock
    private RoomService roomServiceMock;

    @Spy
    private BookingDAO bookingDAOMock;

    @Mock
    private MailSender mailSenderMock;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;


    @Test
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        //given
        given(this.roomServiceMock.getAvailableRooms()).willReturn(Collections.singletonList(new Room("Romm 1", 2)));
        int expected = 2;

        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void should_NotInvokePayment_When_NotPrepaid() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);


        //when
        bookingService.makeBooking(bookingRequest);

        //then
        then(paymentServiceMock).should(times((1))).pay(bookingRequest, 400.0);

    }

}
