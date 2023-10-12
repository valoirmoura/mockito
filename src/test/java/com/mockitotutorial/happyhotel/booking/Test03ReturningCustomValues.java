package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test03ReturningCustomValues {

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
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        //given
        when(this.roomServiceMock.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Romm 1", 2)));
        int expected = 2;

        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void should_CountAvailablePlaces_When_MultipleRoomsAvailable() {
        //given
        List<Room> rooms = List.of(new Room("Rom 1", 2), new Room("Rom 2", 5));
        when(this.roomServiceMock.getAvailableRooms()).thenReturn(rooms);
        int expected = 7;

        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        Assertions.assertEquals(expected, actual);

    }
}
