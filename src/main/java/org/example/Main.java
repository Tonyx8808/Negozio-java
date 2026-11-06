package org.example;

import dao.ClientiDAO;
import dao.OrdineDAO;
import dao.ProdottoDAO;

import model.Clienti;
import model.Ordine;
import model.Prodotti;

import databaseService.AcquistoService;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Creazione DAO
        ClientiDAO clientiDAO = new ClientiDAO();
        OrdineDAO ordineDAO = new OrdineDAO();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        try {

            // Mostra tutti i clienti
            IO.println("ELENCO CLIENTI");

            ArrayList<Clienti> clienti = clientiDAO.findAll();

            for (Clienti cliente : clienti) {
                IO.println("ID: " + cliente.getId()
                        + " Nome: " + cliente.getNome()
                        + " Cognome: " + cliente.getCognome());
            }

            // Ricerca cliente per ID
            IO.println("-------------------------------");
            IO.println("RICERCA CLIENTE PER ID");

            Clienti cliente = clientiDAO.findById(1);

            if (cliente != null) {
                IO.println("ID: " + cliente.getId()
                        + " Nome: " + cliente.getNome()
                        + " Cognome: " + cliente.getCognome());
            } else {
                IO.println("Cliente non trovato!");
            }

            // Mostra tutti gli ordini
            IO.println("-------------------------------");
            IO.println("ELENCO ORDINI");

            ArrayList<Ordine> ordini = ordineDAO.findAll();

            for (Ordine ordine : ordini) {
                IO.println("ID: " + ordine.getId()
                        + " Cliente: " + ordine.getIdCliente()
                        + " Prodotto: " + ordine.getIdProdotto()
                        + " Quantità: " + ordine.getQuantita()
                        + " Data: " + ordine.getDataOrdine());
            }

            // Ricerca ordine per ID
            IO.println("-------------------------------");
            IO.println("RICERCA ORDINE PER ID");

            Ordine ordine = ordineDAO.findById(1);

            if (ordine != null) {
                IO.println("ID: " + ordine.getId()
                        + " Cliente: " + ordine.getIdCliente()
                        + " Prodotto: " + ordine.getIdProdotto()
                        + " Quantità: " + ordine.getQuantita()
                        + " Data: " + ordine.getDataOrdine());
            } else {
                IO.println("Ordine non trovato!");
            }

            // Mostra tutti i prodotti
            IO.println("-------------------------------");
            IO.println("ELENCO PRODOTTI");

            ArrayList<Prodotti> prodotti = prodottoDAO.findAll();

            for (Prodotti prodotto : prodotti) {
                IO.println("ID: " + prodotto.getId()
                        + " Nome: " + prodotto.getNome()
                        + " Categoria: " + prodotto.getCategoria()
                        + " Prezzo: " + prodotto.getPrezzo());
            }

            // Ricerca prodotto per ID
            IO.println("-------------------------------");
            IO.println("RICERCA PRODOTTO PER ID");

            Prodotti prodotto = prodottoDAO.findById(1);

            if (prodotto != null) {
                IO.println("ID: " + prodotto.getId()
                        + " Nome: " + prodotto.getNome()
                        + " Categoria: " + prodotto.getCategoria()
                        + " Prezzo: " + prodotto.getPrezzo());
            } else {
                IO.println("Prodotto non trovato!");
            }

            // Acquisto prodotti
            IO.println("-------------------------------");
            IO.println("ACQUISTO PRODOTTI");

            AcquistoService acquistoService = new AcquistoService();

            try {
                acquistoService.acquistaProdotti(1, 1, 1);
            } catch (SQLException e) {
                IO.println("Acquisto non effettuato: " + e.getMessage());
            }

        } finally {

            // Chiusura connessioni DAO
            clientiDAO.chiudiConnessione();
            ordineDAO.chiudiConnessione();
            prodottoDAO.chiudiConnessione();
        }
    }
}
