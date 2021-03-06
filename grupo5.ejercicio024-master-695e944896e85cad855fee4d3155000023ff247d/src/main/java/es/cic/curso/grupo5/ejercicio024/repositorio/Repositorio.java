package es.cic.curso.grupo5.ejercicio024.repositorio;

import java.util.List;

import es.cic.curso.grupo5.ejercicio024.modelo.Identificable;

public interface Repositorio<K extends Number, T extends Identificable<K>> {
	
	T create(T element);
	T read(K id);
	T update(T element);
	T delete(K id);
	void delete(T element);
	List<T> list();

}
