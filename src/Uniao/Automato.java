/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uniao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Janaina
 */
public final class Automato {

    private List<Estado> estados = new ArrayList();
    private List<Transicao> transicoes = new ArrayList();

    public Automato(String filePath) throws ParserConfigurationException, SAXException {
        this.getFromFile(filePath);
    }

    public Automato(List<Estado> estados, List<Transicao> transicao) {
        this.setEstados(estados);
        this.setTransicoes(transicao);

    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Transicao> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(List<Transicao> transicao) {
        this.transicoes = transicao;
    }

    public Automato unitWith(Automato automato) {

        Estado estadoInicial = new Estado(
                "uniao0",
                "iniciaUniao",
                0,
                0,
                false,
                true
        );

        Estado estadoFinal = new Estado(
                "uniao1",
                "finalizaUniao",
                0,
                0,
                true,
                false
        );

        List<Estado> estadosResultantes = new ArrayList();

        List<Transicao> transicoesResultantes = new ArrayList();

        estadosResultantes.add(estadoInicial);
        estadosResultantes.add(estadoFinal);

        for (int i = 0; i < this.estados.size(); i++) {

            if (this.estados.get(i).isIsInicial()) {

                Estado novoEstado = new Estado(
                        "a" + this.estados.get(i).getId(),
                        "a" + this.estados.get(i).getNome(),
                        this.estados.get(i).getX(),
                        this.estados.get(i).getY(),
                        false,
                        false
                );

                estadosResultantes.add(novoEstado);

                transicoesResultantes.add(
                        new Transicao(
                                estadoInicial.getId(),
                                novoEstado.getId(),
                                ""
                        )
                );
            } else if (this.estados.get(i).isIsFinal()) {

                Estado novoEstado = new Estado(
                        "a" + this.estados.get(i).getId(),
                        "a" + this.estados.get(i).getNome(),
                        this.estados.get(i).getX(),
                        this.estados.get(i).getY(),
                        false,
                        false
                );

                estadosResultantes.add(novoEstado);

                transicoesResultantes.add(
                        new Transicao(
                                novoEstado.getId(),
                                estadoFinal.getId(),
                                ""
                        )
                );

            } else {

                Estado novoEstado = new Estado(
                        "a" + this.estados.get(i).getId(),
                        "a" + this.estados.get(i).getNome(),
                        this.estados.get(i).getX(),
                        this.estados.get(i).getY(),
                        false,
                        false
                );

                estadosResultantes.add(novoEstado);

            }
        }

        for (int i = 0; i < this.transicoes.size(); i++) { 
            Transicao transicao = new Transicao(
                    "a" + this.transicoes.get(i).getFrom(),
                    "a" + this.transicoes.get(i).getTo(),
                    this.transicoes.get(i).getInput()
            );

            transicoesResultantes.add(transicao);

        }

        for (int i = 0; i < automato.getEstados().size(); i++) {

            if (automato.getEstados().get(i).isIsInicial()) {

                Estado novoEstado = new Estado(
                        "b" + automato.getEstados().get(i).getId(),
                        "b" + automato.getEstados().get(i).getNome(),
                        automato.getEstados().get(i).getX(),
                        automato.getEstados().get(i).getY(),
                        false,
                        false
                );

                estadosResultantes.add(novoEstado);

                transicoesResultantes.add(
                        new Transicao(
                                estadoInicial.getId(),
                                novoEstado.getId(),
                                ""
                        )
                );
            } else if (automato.getEstados().get(i).isIsFinal()) {

                Estado novoEstado = new Estado(
                        "b" + automato.getEstados().get(i).getId(),
                        "b" + automato.getEstados().get(i).getNome(),
                        automato.getEstados().get(i).getX(),
                        automato.getEstados().get(i).getY(),
                        false,
                        false
                );

                estadosResultantes.add(novoEstado);

                transicoesResultantes.add(
                        new Transicao(
                                novoEstado.getId(),
                                estadoFinal.getId(),
                                ""
                        )
                );

            } else {

                Estado novoEstado = new Estado(
                        "b" + automato.getEstados().get(i).getId(),
                        "b" + automato.getEstados().get(i).getNome(),
                        automato.getEstados().get(i).getX(),
                        automato.getEstados().get(i).getY(),
                        false,
                        false
                );

                estadosResultantes.add(novoEstado);

            }
        }

        for (int i = 0; i < automato.getTransicoes().size(); i++) {

            Transicao transicao = new Transicao(
                    "b" + automato.getTransicoes().get(i).getFrom(),
                    "b" + automato.getTransicoes().get(i).getTo(),
                    automato.getTransicoes().get(i).getInput()
            );

            transicoesResultantes.add(transicao);

        }

        return new Automato(estadosResultantes, transicoesResultantes);

    }

    public void getFromFile(String filePath) throws ParserConfigurationException, SAXException {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            // Obter estados --
            NodeList estados = document.getElementsByTagName("state");
            // Percorrer todos os estados e gravar no objeto
            for (int temp = 0; temp < estados.getLength(); temp++) {
                Node nNode = estados.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    Estado estado = new Estado(
                            eElement.getAttribute("id"),
                            eElement.getAttribute("name"),
                            Float.parseFloat(eElement.getElementsByTagName("x").item(0).getTextContent()),
                            Float.parseFloat(eElement.getElementsByTagName("y").item(0).getTextContent()),
                            eElement.getElementsByTagName("final").item(0) != null,
                            eElement.getElementsByTagName("initial").item(0) != null
                    );

                    this.estados.add(estado);

                }
            }

            // Obter Transições --
            NodeList transicoes = document.getElementsByTagName("transition");
            // Percorrer todas as transicoes e gravar no objeto
            for (int temp = 0; temp < transicoes.getLength(); temp++) {
                Node nNode = transicoes.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    Transicao transicao = new Transicao(
                            eElement.getElementsByTagName("from").item(0).getTextContent(),
                            eElement.getElementsByTagName("to").item(0).getTextContent(),
                            eElement.getElementsByTagName("read").item(0).getTextContent()
                    );

                    this.transicoes.add(transicao);

                }
            }
        } catch (IOException error) {
            System.out.println(error);
        }
    }

    @Override
    public String toString() {
        String message;
        message = "Estados:";
        for (int i = 0; i < this.estados.size(); i++) {
            message = message + "\n" + estados.get(i).getNome();
            if (estados.get(i).isIsFinal()) {
                message = message + " - Final\n";
            }
            if (estados.get(i).isIsInicial()) {
                message = message + " - Inicial";
            }
        }

        message = message + "\n\nTransições:";
        for (int i = 0; i < this.transicoes.size(); i++) {
            message = message
                    + "\n"
                    + transicoes.get(i).getFrom()
                    + " -> "
                    + transicoes.get(i).getTo()
                    + " com "
                    + transicoes.get(i).getInput();
        }

        message = message + "\n----------------------";
        return message;
    }

}
