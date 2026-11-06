import model.Studente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StudenteTest {

    Studente studente;

    // Viene eseguito prima di ogni test
    @BeforeEach
    public void inizializzaStudente() {
        studente = new Studente();
    }

    // Test per verificare che la lista dei voti sia corretta
    @Test
    public void testGetVoti() {

        ArrayList<Integer> votiAttesi = new ArrayList<>();

        votiAttesi.add(4);
        votiAttesi.add(6);
        votiAttesi.add(7);
        votiAttesi.add(4);
        votiAttesi.add(6);

        assertEquals(votiAttesi, studente.getVoti());
    }

    // Test per verificare il voto negativo
    @Test
    public void testAggiungiVotoNegativo() {

        assertThrows(IllegalArgumentException.class, () -> {
            studente.aggiungiVoto(-1);
        });
    }

    // Test per verificare il voto superiore a 10
    @Test
    public void testAggiungiVotoMaggioreDieci() {

        assertThrows(IllegalArgumentException.class, () -> {
            studente.aggiungiVoto(11);
        });
    }

    // Test per verificare l'aggiunta di un voto
    @Test
    public void testAggiungiVoto() {

        studente.aggiungiVoto(8);

        assertEquals(6, studente.getVoti().size());
    }
}