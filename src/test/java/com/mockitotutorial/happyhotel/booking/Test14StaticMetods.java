package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;

@ExtendWith(MockitoExtension.class)
class Test14StaticMetods {

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
    void should_CaclculateCorrectPrice()  {

        try (MockedStatic<CurrencyConverter> mockedConverter = Mockito.mockStatic(CurrencyConverter.class)) {
            //given
            BookingRequest bookingRequest = new BookingRequest("1",
                    LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05), 2, false);
            double expected = 400.0;

            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);


            //when
            double actual = bookingService.calculatePrice(bookingRequest);

            //then
            assertEquals(expected, actual);

        }


    }

}
