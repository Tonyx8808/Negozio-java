
package dao;

import database.DatabaseManager;
import database.DatabaseQuery;
import model.Clienti;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClientiDAO implements BaseDAO<Clienti, Integer> {

    private final Connection conn;

    public ClientiDAO() {
        conn = DatabaseManager.getConnection("negozio");
    }

    @Override
    public Clienti findById(Integer id) {

        String query = DatabaseQuery.CERCA_CLIENTE_ID;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Clienti cliente = new Clienti();

                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCognome(rs.getString("cognome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCitta(rs.getString("citta"));
                    cliente.setSaldo(rs.getDouble("saldo"));

                    return cliente;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Clienti findById(Integer id, Connection conn) {

        String query = DatabaseQuery.CERCA_CLIENTE_ID;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Clienti cliente = new Clienti();

                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCognome(rs.getString("cognome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCitta(rs.getString("citta"));
                    cliente.setSaldo(rs.getDouble("saldo"));

                    return cliente;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Clienti> findAll() {

        ArrayList<Clienti> clienti = new ArrayList<>();

        String query = DatabaseQuery.MOSTRA_CLIENTI;

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Clienti cliente = new Clienti();

                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCognome(rs.getString("cognome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCitta(rs.getString("citta"));
                cliente.setSaldo(rs.getDouble("saldo"));

                clienti.add(cliente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clienti;
    }

    @Override
    public void insert(Clienti cliente) {

        String query = DatabaseQuery.INSERISCI_CLIENTE;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCognome());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getCitta());
            stmt.setDouble(5, cliente.getSaldo());

            int righe = stmt.executeUpdate();

            if (righe > 0) {
                System.out.println("Cliente inserito con successo!");
            } else {
                System.out.println("Cliente non inserito.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public void aggiornaSaldoCliente(int idCliente, double nuovoSaldo, Connection conn)
    throws SQLException{
        String query = DatabaseQuery.AGGIORNA_SALDO_CLIENTE;
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setDouble(1, nuovoSaldo);
        stmt.setInt(2, idCliente);
        stmt.executeUpdate();
        stmt.close();
    }

    public void chiudiConnessione() {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connessione clienti chiusa!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

