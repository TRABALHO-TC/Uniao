
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
        System.out.println(automato.toString());
    }
    
}
