package repositories;

import entities.Ator;

import java.util.HashSet;
import java.util.Iterator;

public class AtorRepository {

    private HashSet<Ator> atores;

    public AtorRepository(){
        this.atores = new HashSet<Ator>();
    }

    public HashSet<Ator> getAtores() {
        return new HashSet<Ator>(this.atores);
    }

    public boolean addAtor(Ator ator) {
        return this.atores.add(ator);
    }

    public boolean removeAtor(Ator ator) {
        return this.atores.remove(ator);
    }

    public Ator getAtor(String nome) {
        Ator a = null;

        Iterator<Ator> ator = atores.iterator();

        for(Ator i = null; ator.hasNext(); i = ator.next()) {
            if(i.getNome().equals(nome)) {
                a = new Ator(i);
                break;
            }
        }
        return a;
    }
}
