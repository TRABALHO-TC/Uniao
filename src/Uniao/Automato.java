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
                      eElement.getElementsByTagName("initial").item(0) != null,
                      eElement.getElementsByTagName("final").item(0) != null
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
                      Integer.valueOf(eElement.getElementsByTagName("from").item(0).getTextContent()),
                      Integer.valueOf(eElement.getElementsByTagName("to").item(0).getTextContent()),
                      eElement.getElementsByTagName("read").item(0).getTextContent()
                    );

                    this.transicoes.add(transicao);

                }
            }
        }catch(IOException error) {
            System.out.println(error);
        }
    }
    
    @Override
    public String toString() {
        String message;
        message = "Estados:";
        for(int i=0;i<this.estados.size();i++){ 
            message = message + "\n" + estados.get(i).getNome();
        }
        
        message = message + "\n\nTransições:";
        for(int i=0;i<this.transicoes.size();i++){ 
            message = message 
                    + "\n" 
                    + transicoes.get(i).getFrom() 
                    + " -> " 
                    + transicoes.get(i).getTo() 
                    + " com " 
                    + transicoes.get(i).getInput();
        }
        return message;
    }
    
}
