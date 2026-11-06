package model;

import java.util.ArrayList;

public class Studente {

    private ArrayList<Integer> voti;

    public Studente() {
        voti = new ArrayList<>();

        voti.add(4);
        voti.add(6);
        voti.add(7);
        voti.add(4);
        voti.add(6);
    }

    public void aggiungiVoto(int voto) {
        if (voto < 0 || voto > 10) {
            throw new IllegalArgumentException("Voto non valido");
        }

        voti.add(voto);
    }

    public double calcolaMedia() {
        if (voti.isEmpty()) {
            return 0;
        }

        int somma = 0;

        for (Integer voto : voti) {
            somma += voto;
        }

        return (double) somma / voti.size();
    }

    public boolean isPromosso() {
        return calcolaMedia() >= 6;
    }

    public ArrayList<Integer> getVoti() {
        return voti;
    }
}