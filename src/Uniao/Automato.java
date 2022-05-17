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
import static java.lang.Integer.parseInt;
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
    
    public Automato unir(Automato a1, Automato a2){
        List<Estado> estadosUniao = new ArrayList();
        List<Transicao> transicaoUniao = new ArrayList();
        
        int maiorID = 0;
        
        for(int i=0; i<a1.getEstados().size(); i++){
            //adiciona os estados de a1 a lista de estados do novo automato
            estadosUniao.add(a1.getEstados().get(i));
            //pega o maior id do automato 1 para os ids do automato 2 começar a partir dele
            if(a1.getEstados().get(i).getId() > maiorID)
                maiorID = a1.getEstados().get(i).getId();
        }
        System.out.println("Maior id:" + maiorID);
        //altera os ids do automato 2
        for(int i=0; i<a2.getEstados().size(); i++){
            a2.getEstados().get(i).setId(a2.getEstados().get(i).getId()+maiorID+1);  //Mais 1 pq o próximo começa com 0
            //adiciona os estados de a2 a lista de estados do novo automato
            estadosUniao.add(a2.getEstados().get(i));
        }
        //adiciona as transições de a1 a lista de transições do novo automato
        for(int j=0; j<a1.getTransicoes().size(); j++){
            transicaoUniao.add(a1.getTransicoes().get(j));
        }
        //altera o id das transições do automato 2
        for(int j=0; j<a2.getTransicoes().size(); j++){
            a2.getTransicoes().get(j).setFrom(a2.getTransicoes().get(j).getFrom()+maiorID+1);
            a2.getTransicoes().get(j).setTo(a2.getTransicoes().get(j).getTo()+maiorID+1);
            //adiciona as transições de a2 a lista de transições do novo automato
            transicaoUniao.add(a2.getTransicoes().get(j));
        }
        
        //Verifica qual maior id do novo automato
        maiorID = 0;
        for(int j=0; j<estadosUniao.size(); j++){
            if(estadosUniao.get(j).getId() > maiorID)
                maiorID = estadosUniao.get(j).getId();
        }
        
        //Cria o novo estado inicial
        Estado estadoInicial = new Estado(maiorID+1, "iniUniao", 0, 0, false, true);
        
        estadosUniao.add(estadoInicial);
        //Pegamos até o tamanho-1 para não incluir o estado inicial que acabamos de adicionar
        for(int j=0; j<estadosUniao.size()-1; j++){
            if(estadosUniao.get(j).isIsInicial()){
                estadosUniao.get(j).setIsInicial(false);
                Transicao t1 = new Transicao(maiorID+1, estadosUniao.get(j).getId(), "");
                transicaoUniao.add(t1);
            }
        }
        
        Automato autUniao = new Automato(estadosUniao, transicaoUniao);
        
        return autUniao;
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
                            parseInt(eElement.getAttribute("id")),
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
                            parseInt(eElement.getElementsByTagName("from").item(0).getTextContent()),
                            parseInt(eElement.getElementsByTagName("to").item(0).getTextContent()),
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
