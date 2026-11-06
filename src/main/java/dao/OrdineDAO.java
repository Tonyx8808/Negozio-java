
package dao;

import database.DatabaseManager;
import database.DatabaseQuery;
import model.Ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OrdineDAO implements BaseDAO<Ordine, Integer> {

    private Connection conn;

    public OrdineDAO() {
        conn = DatabaseManager.getConnection("negozio");
    }

    @Override
    public Ordine findById(Integer id) {

        String query = DatabaseQuery.CERCA_ORDINE_ID;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Ordine ordine = new Ordine();

                    ordine.setId(rs.getInt("id"));
                    ordine.setIdCliente(rs.getInt("id_cliente"));
                    ordine.setIdProdotto(rs.getInt("id_prodotto"));
                    ordine.setQuantita(rs.getInt("quantita"));
                    ordine.setDataOrdine(rs.getDate("data_ordine"));

                    return ordine;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Ordine findById(Integer id, Connection conn) {

        String query = DatabaseQuery.CERCA_ORDINE_ID;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Ordine ordine = new Ordine();

                    ordine.setId(rs.getInt("id"));
                    ordine.setIdCliente(rs.getInt("id_cliente"));
                    ordine.setIdProdotto(rs.getInt("id_prodotto"));
                    ordine.setQuantita(rs.getInt("quantita"));
                    ordine.setDataOrdine(rs.getDate("data_ordine"));

                    return ordine;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Ordine> findAll() {

        ArrayList<Ordine> ordini = new ArrayList<>();

        String query = DatabaseQuery.MOSTRA_ORDINI;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Ordine ordine = new Ordine();

                ordine.setId(rs.getInt("id"));
                ordine.setIdCliente(rs.getInt("id_cliente"));
                ordine.setIdProdotto(rs.getInt("id_prodotto"));
                ordine.setQuantita(rs.getInt("quantita"));
                ordine.setDataOrdine(rs.getDate("data_ordine"));

                ordini.add(ordine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordini;
    }

    @Override
    public void insert(Ordine ordine) {

        String query = DatabaseQuery.INSERISCI_ORDINE;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, ordine.getIdCliente());
            stmt.setInt(2, ordine.getIdProdotto());
            stmt.setInt(3, ordine.getQuantita());
            stmt.setDate(4, ordine.getDataOrdine());

            int righe = stmt.executeUpdate();

            if (righe > 0) {
                System.out.println("Ordine inserito con successo!");
            } else {
                System.out.println("Ordine non inserito.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chiudiConnessione() {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connessione ordini chiusa!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
