
package dao;

import database.DatabaseManager;
import database.DatabaseQuery;
import model.Prodotti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdottoDAO implements BaseDAO<Prodotti, Integer> {

    private Connection conn;

    public ProdottoDAO() {
        conn = DatabaseManager.getConnection("negozio");
    }

    @Override
    public Prodotti findById(Integer id) {

        String query = DatabaseQuery.CERCA_PRODOTTO_ID;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Prodotti prodotto = new Prodotti();

                    prodotto.setId(rs.getInt("id"));
                    prodotto.setNome(rs.getString("nome"));
                    prodotto.setCategoria(rs.getString("categoria"));
                    prodotto.setPrezzo(rs.getDouble("prezzo"));

                    return prodotto;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Prodotti findById(Integer id, Connection conn) {

        String query = DatabaseQuery.CERCA_PRODOTTO_ID;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Prodotti prodotto = new Prodotti();

                    prodotto.setId(rs.getInt("id"));
                    prodotto.setNome(rs.getString("nome"));
                    prodotto.setCategoria(rs.getString("categoria"));
                    prodotto.setPrezzo(rs.getDouble("prezzo"));

                    return prodotto;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Prodotti> findAll() {

        ArrayList<Prodotti> prodotti = new ArrayList<>();

        String query = DatabaseQuery.MOSTRA_PRODOTTI;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Prodotti prodotto = new Prodotti();

                prodotto.setId(rs.getInt("id"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setCategoria(rs.getString("categoria"));
                prodotto.setPrezzo(rs.getDouble("prezzo"));

                prodotti.add(prodotto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return prodotti;
    }

    @Override
    public void insert(Prodotti prodotto) {

        String query = DatabaseQuery.INSERISCI_PRODOTTO;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, prodotto.getNome());
            stmt.setString(2, prodotto.getCategoria());
            stmt.setDouble(3, prodotto.getPrezzo());

            int righe = stmt.executeUpdate();

            if (righe > 0) {
                System.out.println("Prodotto inserito con successo!");
            } else {
                System.out.println("Prodotto non inserito.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aggiornaQuantitaProdotti(int idProdotto, int quantita, Connection conn)
            throws SQLException {

        String query = DatabaseQuery.AGGIORNA_QUANTITA_PRODOTTO;

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setInt(1, quantita);
        stmt.setInt(2, idProdotto);

        stmt.executeUpdate();

        stmt.close();
    }

    public void aggiornaPrezzoProdotto(int idProdotto, double prezzo, Connection conn)
        throws SQLException{
        String query = DatabaseQuery.AGGIORNA_PREZZO_PRODOTTO;
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDouble(1, prezzo);
        stmt.setInt(2, idProdotto);
        stmt.executeUpdate();
        stmt.close();
    }

    public void chiudiConnessione() {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
               IO.println("Connessione prodotti chiusa!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
