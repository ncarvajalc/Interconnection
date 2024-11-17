package model.logic.utils;

import java.util.Comparator;

import model.data_structures.ArregloDinamico;
import model.data_structures.ILista;
import model.data_structures.NullException;
import model.data_structures.PosException;
import model.data_structures.VacioException;
import utils.Ordenamiento;

public class Merger {
    public <T extends Comparable<T>> ILista<T> unificarListas(ILista<T> lista, Comparator<T> comparador) {
        ILista<T> lista2 = new ArregloDinamico<>(1);
        try {
            if (lista != null && lista.size() > 0) {
                Ordenamiento<T> ordenamiento = new Ordenamiento<>();
                ordenamiento.ordenarMergeSort(lista, comparador, false);
                T previous = null;
                for (int i = 1; i <= lista.size(); i++) {
                    T current = lista.getElement(i);
                    if (previous == null || comparador.compare(previous, current) != 0) {
                        lista2.insertElement(current, lista2.size() + 1);
                    }
                    previous = current;
                }
            }
        } catch (PosException | VacioException | NullException e) {
            e.printStackTrace();
        }
        return lista2;
    }
}
