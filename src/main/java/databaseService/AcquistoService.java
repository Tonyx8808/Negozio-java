package databaseService;

import database.DatabaseManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcquistoService {

    public void acquistaProdotti(int idCliente, int idProdotto, int quantita)
            throws SQLException {

        // Controllo quantità
        if (quantita <= 0) {
            throw new SQLException("Quantità non valida");
        }

        // Apertura connessione
        try (Connection conn = DatabaseManager.getConnection("negozio")) {

            conn.setAutoCommit(false);

            try {

                // 1. Ricerca cliente
                String queryCliente =
                        "SELECT saldo FROM clienti WHERE id = ? FOR UPDATE";

                BigDecimal saldo;

                try (PreparedStatement stmtCliente =
                             conn.prepareStatement(queryCliente)) {

                    stmtCliente.setInt(1, idCliente);

                    try (ResultSet rsCliente = stmtCliente.executeQuery()) {

                        if (!rsCliente.next()) {
                            throw new SQLException("Cliente non trovato");
                        }

                        saldo = rsCliente.getBigDecimal("saldo");
                    }
                }

                // Controllo saldo nullo
                if (saldo == null) {
                    throw new SQLException("Saldo cliente non disponibile");
                }

                // 2. Ricerca prodotto
                String queryProdotto =
                        "SELECT quantita, prezzo FROM prodotti WHERE id = ? FOR UPDATE";

                int quantitaDisponibile;
                BigDecimal prezzo;

                try (PreparedStatement stmtProdotto =
                             conn.prepareStatement(queryProdotto)) {

                    stmtProdotto.setInt(1, idProdotto);

                    try (ResultSet rsProdotto = stmtProdotto.executeQuery()) {

                        if (!rsProdotto.next()) {
                            throw new SQLException("Prodotto non trovato");
                        }

                        quantitaDisponibile =
                                rsProdotto.getInt("quantita");

                        prezzo = rsProdotto.getBigDecimal("prezzo");
                    }
                }

                // Controllo quantità disponibile
                if (quantitaDisponibile < quantita) {
                    throw new SQLException("Quantità non disponibile");
                }

                // Calcolo costo totale
                BigDecimal costoTotale =
                        prezzo.multiply(BigDecimal.valueOf(quantita));

                // Controllo saldo cliente
                if (saldo.compareTo(costoTotale) < 0) {
                    throw new SQLException("Saldo insufficiente");
                }

                // 3. Inserimento ordine
                String queryOrdine =
                        "INSERT INTO ordini " +
                                "(id_cliente, id_prodotto, quantita, data_ordine) " +
                                "VALUES (?, ?, ?, CURRENT_DATE)";

                try (PreparedStatement stmtOrdine =
                             conn.prepareStatement(queryOrdine)) {

                    stmtOrdine.setInt(1, idCliente);
                    stmtOrdine.setInt(2, idProdotto);
                    stmtOrdine.setInt(3, quantita);

                    stmtOrdine.executeUpdate();
                }

                // 4. Aggiornamento quantità prodotto
                String queryAggiornaQuantita =
                        "UPDATE prodotti SET quantita = quantita - ? " +
                                "WHERE id = ? AND quantita >= ?";

                try (PreparedStatement stmtAggiornaQuantita =
                             conn.prepareStatement(queryAggiornaQuantita)) {

                    stmtAggiornaQuantita.setInt(1, quantita);
                    stmtAggiornaQuantita.setInt(2, idProdotto);
                    stmtAggiornaQuantita.setInt(3, quantita);

                    int righeAggiornate =
                            stmtAggiornaQuantita.executeUpdate();

                    if (righeAggiornate == 0) {
                        throw new SQLException(
                                "Aggiornamento quantità non riuscito");
                    }
                }

                // 5. Aggiornamento saldo cliente
                String queryAggiornaSaldo =
                        "UPDATE clienti SET saldo = saldo - ? " +
                                "WHERE id = ? AND saldo >= ?";

                try (PreparedStatement stmtAggiornaSaldo =
                             conn.prepareStatement(queryAggiornaSaldo)) {

                    stmtAggiornaSaldo.setBigDecimal(1, costoTotale);
                    stmtAggiornaSaldo.setInt(2, idCliente);
                    stmtAggiornaSaldo.setBigDecimal(3, costoTotale);

                    int saldoAggiornato =
                            stmtAggiornaSaldo.executeUpdate();

                    if (saldoAggiornato == 0) {
                        throw new SQLException(
                                "Aggiornamento saldo non riuscito");
                    }
                }

                // Conferma transazione
                conn.commit();

                IO.println("Acquisto completato con successo!");
                IO.println("Costo totale: " + costoTotale + " euro");

            } catch (SQLException e) {

                // Annullamento di tutte le operazioni
                try {
                    conn.rollback();
                    IO.println("Rollback eseguito!");
                } catch (SQLException erroreRollback) {
                    e.addSuppressed(erroreRollback);
                }

                throw e;
            }
        }
    }
}
