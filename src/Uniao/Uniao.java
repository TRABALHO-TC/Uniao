package Uniao;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Janaina
 */
public class Uniao {

    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        Automato automato = new Automato("automato.jff");
        Automato automato2 = new Automato("automato.jff");
        System.out.println(automato.toString());
        System.out.println(automato2.toString());

        System.out.println("\n==================================================");

        System.out.println(automato.unitWith(automato2).toString()); // AQUI A EXPORTAÇÃO DO ARQUIVO

    }

}
