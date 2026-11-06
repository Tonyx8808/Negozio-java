package database;

public class DatabaseQuery {

    public static final String CERCA_ORDINE_ID = "SELECT * FROM ordini WHERE id = ?";
        public static final String MOSTRA_ORDINI = "SELECT * FROM ordini";
    public static final String INSERISCI_ORDINE = "INSERT INTO ordini (id_cliente, id_prodotto, quantita, data_ordine) VALUES (?, ?, ?, ?)";
    // Mostra tutti i clienti
    public static String MOSTRA_CLIENTI =
            "SELECT * FROM clienti";

    // Ricerca per nome e cognome
    public static String CERCA_CLIENTE_NOME_COGNOME =
            "SELECT * FROM clienti WHERE nome = ? AND cognome = ?";

    // Ricerca per ID
    public static String CERCA_CLIENTE_ID =
            "SELECT * FROM clienti WHERE id = ?";


    //Inserimento nuovo cliente
    public static String INSERISCI_CLIENTE =
            "INSERT INTO clienti (nome, cognome, email, citta, saldo) VALUES (?, ?, ?, ?, ?)";

    //Mostra tutti i prodotti
    public static String MOSTRA_PRODOTTI =
            "SELECT * FROM prodotti";

    //Ricerca prodotto per ID
    public static String CERCA_PRODOTTO_ID =
            "SELECT * FROM prodotti WHERE id = ?";

    //Inserimento nuovo prodotto
    public static String INSERISCI_PRODOTTO =
            "INSERT INTO prodotti (nome, categoria, prezzo) VALUES (?, ?, ?)";

//Aggiorna saldo cliente
    public static final String AGGIORNA_SALDO_CLIENTE =
        "UPDATE clienti SET saldo = ? WHERE id = ?";

    //Aggiorna quantità prodotto
    public static final String AGGIORNA_QUANTITA_PRODOTTO =
            "UPDATE prodotti SET quantita = ? WHERE id = ?";

    //Aggiorna prezzo prodotto
    public static final String AGGIORNA_PREZZO_PRODOTTO =
            "UPDATE prodotti SET prezzo = ? WHERE id = ?";
}

