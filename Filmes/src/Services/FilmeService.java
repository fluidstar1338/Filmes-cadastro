package Services;

import dtos.FilmeDto;
import entities.Filme;
import entities.Ator;
import entities.Diretor;
import repositories.AtorRepository;
import repositories.FilmeRepository;
import repositories.DiretorRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class FilmeService {

    private FilmeRepository fr;
    private DiretorRepository dr;
    private AtorRepository ar;

    public FilmeService() {
        this.fr = new FilmeRepository();
        this.ar = new AtorRepository();
        this.dr = new DiretorRepository();
    }

    public boolean addFilme(String nome, String nomeDiretor,
                            String atores[], float nota) {

        Diretor diretor = new Diretor(nomeDiretor);
        this.dr.addDiretor(diretor);

        HashSet<Ator> elenco = new HashSet<Ator>();

        for(String nomeAtor : atores) {
            Ator ator = new Ator(nomeAtor);
            this.ar.addAtor(ator);
            elenco.add(ator);
        }

        Filme filme = new Filme(nome, diretor, elenco, nota);

        return this.fr.addFilme(filme);
    }

    public boolean addFilme(FilmeDto filmeDto) {
        return this.addFilme(filmeDto.getNome(), filmeDto.getDiretor(),
                filmeDto.getElenco(), filmeDto.getNota());

    }

    public List<FilmeDto> listaFilmesPorNome() {
        List<Filme> filmesFiltrados = new ArrayList<Filme>(
                        this.fr.getFilmes() );

        filmesFiltrados.sort((o1, o2) -> {
                        return o1.getNome().compareTo(o2.getNome());

        });

        return converteListaFilmeParaDto(filmesFiltrados);
    }

    public List<FilmeDto> listaFilmesPorDiretor(String diretor){

        ArrayList<Filme> filmesFiltrados = new ArrayList<Filme>();

        HashSet<Filme> filmes = this.fr.getFilmes();

        Iterator<Filme> i = filmes.iterator();

        while(i.hasNext()) {
            Filme f = i.next();

            if (f.getDiretor().getNome().equals(diretor)) {
                filmesFiltrados.add(new Filme(f));
            }
        }
        return converteListaFilmeParaDto(filmesFiltrados);

    }

    public List<FilmeDto> listaFilmesPorAtor (String ator) {

        ArrayList<Filme> filmesFiltrados = new ArrayList<Filme>();

        HashSet<Filme> filmes = this.fr.getFilmes();

        Iterator<Filme> i = filmes.iterator();

        while (i.hasNext()) {
            Filme f = i.next();

            Iterator<Ator> j = f.getElenco().iterator();

            while (j.hasNext()){
                    Ator a = j.next();

                    if(a.getNome().equals(ator)) {
                        filmesFiltrados.add(new Filme(f));
                        break;
                    }
            }
        }
        return converteListaFilmeParaDto(filmesFiltrados);
    }

    public List<FilmeDto> listaFilmesPorNota (float nota) {

        ArrayList<Filme> filmesFiltrados = new ArrayList<Filme>();

        HashSet<Filme> filmes = this.fr.getFilmes();

        for (Filme f : filmes) {
            if(f.getNota() >= nota) {
                filmesFiltrados.add(f);
            }
        }
        return converteListaFilmeParaDto(filmesFiltrados);
    }

    private List<FilmeDto> converteListaFilmeParaDto(List<Filme> filmes) {
        List<FilmeDto> filmeDto = new ArrayList<FilmeDto>();

        for (Filme f : filmes){
            filmeDto.add(new FilmeDto(f));
        }
        return filmeDto;
    }

    public boolean addAtor(String nome) {
        return this.ar.addAtor(new Ator(nome));
    }

    public boolean removeAtor(String nome) {
        return this.ar.removeAtor(new Ator(nome));
    }

    public Ator getAtor(String nome) {
        return ar.getAtor(nome);
    }

    public ArrayList<String> listaAtores(){
        HashSet<Ator> atoresArray = this.ar.getAtores();

        ArrayList<String> atores = new ArrayList<>();

        for(Ator a : atoresArray)
            atores.add(a.getNome());

        return atores;
    }

    public boolean addDiretor(String nome) {
        return this.dr.addDiretor(new Diretor(nome));
    }

    public boolean removeDiretor(String nome) {
        return this.dr.removeDiretor(new Diretor(nome));
    }

    public ArrayList<String> listaDiretores(){

        HashSet<Diretor> diretoresArray = this.dr.getDiretores();

        ArrayList<String> diretores = new ArrayList<String>();

        for(Diretor d : diretoresArray)
            diretores.add(d.getNome());

        return diretores;
    }
}
