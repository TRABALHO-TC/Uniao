/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uniao;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janaina
 */
public class Automato {
    private List<Estado> estados = new ArrayList();
    private List<Transicao> transicao = new ArrayList();

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Transicao> getTransicao() {
        return transicao;
    }

    public void setTransicao(List<Transicao> transicao) {
        this.transicao = transicao;
    }
    
}
