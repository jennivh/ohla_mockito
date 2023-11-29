package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TilaustenKäsittelyTest {

@Mock
IHinnoittelija hinnoittelijaMock;

@BeforeEach
public void setup() {
    MockitoAnnotations.openMocks(this);
}
@Test
public void testaaUusiKäsittelijäWithMockitoHinnoittelijaYli100(){
    float alkuSaldo = 300.0f;
    float tuotteenHinta = 105.0f;
    float alennus = 15.0f;
    float loppuSaldo = alkuSaldo - (tuotteenHinta* (1-(alennus+5)/100));
    Asiakas asiakas = new Asiakas(alkuSaldo);
    Tuote tuote = new Tuote("Best book ever", tuotteenHinta);

    //Record
    when(hinnoittelijaMock.getAlennusProsentti(asiakas,tuote))
            .thenReturn(alennus+5);
    
    // Act 
    TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
    käsittelijä.setHinnoittelija((hinnoittelijaMock));
    käsittelijä.käsittele(new Tilaus(asiakas, tuote));
    
    assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
    verify(hinnoittelijaMock, times(2))
            .getAlennusProsentti(asiakas, tuote);

}
@Test
public void testaaUusiKäsittelijäWithMockitoHinnoittelijaAlle100(){
        float alkuSaldo = 300.0f;
        float tuotteenHinta = 99.0f;
        float alennus = 15.0f;
        float loppuSaldo = alkuSaldo - (tuotteenHinta* (1-(alennus)/100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("Best book ever", tuotteenHinta);

        //Record
        when(hinnoittelijaMock.getAlennusProsentti(asiakas,tuote))
                .thenReturn(alennus);

        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija((hinnoittelijaMock));
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));

        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        verify(hinnoittelijaMock, times(2))
                .getAlennusProsentti(asiakas, tuote);

    }
}