
import dao.ClientiDAO;
import model.Clienti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientiDaoTest {

    private ClientiDAO clientiDAO;
    private Connection conn;

    @BeforeEach
    public void inizializza() throws Exception {

        clientiDAO = new ClientiDAO();

        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/negozio",
                "root",
                "root"
        );
    }

    @AfterEach
    public void chiudiConnessione() throws Exception {

        if (conn != null && !conn.isClosed()) {
            conn.close();
        }

        if (clientiDAO != null) {
            clientiDAO.chiudiConnessione();
        }
    }

    // TEST 1: CERCARE UN CLIENTE TRAMITE ID
    @Test
    public void testFindById() {

        Clienti cliente = clientiDAO.findById(1);

        assertNotNull(cliente);
        assertEquals(1, cliente.getId());
        assertEquals("Mario", cliente.getNome());
        assertEquals("Rossi", cliente.getCognome());
    }


    //TEST 2: CERCARE UN CLIENTE ESISTENTE
    @Test
    public void testFindByIdEsistente() {
        Clienti cliente = clientiDAO.findById(1, conn);
        assertNotNull(cliente);
    }

    //TEST 3: CERCARE UN CLIENTE PASSANDO UNA CONNESSIONE
    @Test
    public void testFindByIdConConnessione() {
        Clienti cliente = clientiDAO.findById(1, conn);
        assertNotNull(cliente);
        assertEquals(1, cliente.getId());
        assertEquals("Mario", cliente.getNome());
        assertEquals("Rossi", cliente.getCognome());
    }

    // TEST 4: Cercare un cliente inesistente con connessione
    @Test
    public void testFindByIdConnessioneInesistente() {
        Clienti cliente = clientiDAO.findById(999, conn);
        assertNull(cliente);
    }

    //TEST 5:  RECUPERARE TUTTI I CLIENTI
    @Test
    public void testFindAll() {
        ArrayList<Clienti> clienti = clientiDAO.findAll();
        assertNotNull(clienti);
        assertFalse(clienti.isEmpty());
    }

    // TEST 6: INSERIRE UN NUOVO CLIENTE
    /*@Test
    public void testInsert() {

        Clienti cliente = new Clienti();

        cliente.setNome("Davy");
        cliente.setCognome("D. Xebec");
        cliente.setEmail("davy.d.xebec@example.com");
        cliente.setCitta("God Valley");
        cliente.setSaldo(10000.0);

        clientiDAO.insert(cliente);

        // Verifico che il cliente sia stato inserito
        Clienti risultato = null;

        ArrayList<Clienti> clienti = clientiDAO.findAll();

        for (Clienti c : clienti) {

            if (c.getEmail().equals("davy.d.xebec@example.com")) {
                risultato = c;
                break;
            }
        }

        assertNotNull(risultato);
        assertEquals("Davy", risultato.getNome());
        assertEquals("D. Xebec", risultato.getCognome());
        assertEquals("davy.d.xebec@example.com", risultato.getEmail());
        assertEquals("God Valley", risultato.getCitta());
        assertEquals(10000.0, risultato.getSaldo(), 0.01);
    }*/

    // TEST 7: AGGIORNARE IL SALDO DI UN CLIENTE
    @Test
    public void testAggiornaSaldoCliente() throws SQLException {

        Clienti cliente = clientiDAO.findById(1, conn);

        double saldoOriginale = cliente.getSaldo();
        double nuovoSaldo = 2000.0;

        clientiDAO.aggiornaSaldoCliente(1, nuovoSaldo, conn);

        Clienti risultato = clientiDAO.findById(1, conn);

        assertEquals(nuovoSaldo, risultato.getSaldo(), 0.01);

        clientiDAO.aggiornaSaldoCliente(1, saldoOriginale, conn);
    }

    // TEST 8: CHIUSURA CONNESSIONE
    @Test
    public void testChiudiConnessione() {

        assertDoesNotThrow(() -> clientiDAO.chiudiConnessione());
    }
}