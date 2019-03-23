package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class KassapaateTest {
    Kassapaate kassapaate;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
    }

    @Test
    public void luodussaKassapaatteessäRahamaaraOikein() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void luodussaKassapaatteessaEdullisetLounaatOikein() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void luodussaKassapaatteessaMaukkaatLounaatOikein() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiToimiiKateisellaOikein() {
        assertEquals(60, kassapaate.syoEdullisesti(300));
        assertEquals(100240, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        
        assertEquals(200, kassapaate.syoEdullisesti(200));
        assertEquals(100240, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiToimiiKateisellaOikein() {
        assertEquals(100, kassapaate.syoMaukkaasti(500));
        assertEquals(100400, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        
        assertEquals(300, kassapaate.syoMaukkaasti(300));
        assertEquals(100400, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiVeloittaaSummanKortiltaJaPalauttaaTrue() {
        Maksukortti kortti = new Maksukortti(300);
        
        assertEquals(true, kassapaate.syoEdullisesti(kortti));
        assertEquals(60, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiVeloittaaSummanKortiltaJaPalauttaaTrue() {
        Maksukortti kortti = new Maksukortti(500);
        
        assertEquals(true, kassapaate.syoMaukkaasti(kortti));
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void syoEdullisestiKortillaKasvattaaMyytyjenMaaraa() {
        Maksukortti kortti = new Maksukortti(500);
        
        kassapaate.syoEdullisesti(kortti);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKortillaKasvattaaMyytyjenMaaraa() {
        Maksukortti kortti = new Maksukortti(500);
        
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiToimiiOikeinKunEiTarpeeksiRahaaKortilla() {
        Maksukortti kortti = new Maksukortti(200);
        
        assertEquals(false, kassapaate.syoEdullisesti(kortti));
        assertEquals(200, kortti.saldo());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiToimiiOikeinKunEiTarpeeksiRahaaKortilla() {
        Maksukortti kortti = new Maksukortti(200);
        
        assertEquals(false, kassapaate.syoMaukkaasti(kortti));
        assertEquals(200, kortti.saldo());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKortillaEiMuutaKassanRahamaaraa() {
        Maksukortti kortti = new Maksukortti(500);
        kassapaate.syoEdullisesti(kortti);
        
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiKortillaEiMuutaKassanRahamaaraa() {
        Maksukortti kortti = new Maksukortti(500);
        kassapaate.syoMaukkaasti(kortti);
        
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void lataaRahaaKortilleToimiiOikeinPostitiivisellaSummalla() {
        Maksukortti kortti = new Maksukortti(500);
        kassapaate.lataaRahaaKortille(kortti, 500);
        
        assertEquals(1000, kortti.saldo());
        assertEquals(100500, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void lataaRahaaKortilleEiTeeMitaanNegatiivisellaSummalla() {
        Maksukortti kortti = new Maksukortti(500);
        kassapaate.lataaRahaaKortille(kortti, -200);
        
        assertEquals(500, kortti.saldo());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
}
