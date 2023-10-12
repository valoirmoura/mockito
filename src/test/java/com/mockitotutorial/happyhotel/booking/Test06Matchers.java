package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test06Matchers {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;


    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

        System.out.println("List returned " + roomServiceMock.getAvailableRooms());
        System.out.println("Object returned " + roomServiceMock.findAvailableRoomId(null));
        System.out.println("Primitive returned " + roomServiceMock.getRoomCount());
    }

    @Test
    void should_NotCompleteBooking_When_PriceTooHigh() {
        //given
        BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, true);
        when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);

        //when

        //then
        Assertions.assertThrows(BusinessException.class, () -> bookingService.makeBooking(bookingRequest));

    }
}
