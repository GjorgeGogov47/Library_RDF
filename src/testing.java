import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.FileManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.InputStream;
import java.text.Collator;
import java.util.*;


public class testing {
    public static void main(String[] args){

        Logger.getRootLogger().setLevel(Level.OFF);
        Model model = ModelFactory.createDefaultModel();
        InputStream vlez = FileManager.get().open("classicalmusicnav.rdf");
        model.read(vlez,"");
        ResIterator iter = model.listResourcesWithProperty(FOAF.name);
        LinkedList<Resource> pero = new LinkedList<>();
        LinkedList<String> pero1 = new LinkedList<>();

        while(iter.hasNext()){
            Resource kompozitor = iter.nextResource();
            pero.add(kompozitor);
            pero1.add(kompozitor.getRequiredProperty(FOAF.name).getObject().toString());
        }
        Collections.sort(pero1, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Collator.getInstance().compare(o1, o2);
            }
        });
        DefaultListModel<String> modelot = new DefaultListModel<>();
        ListIterator<String> dvizi1 = pero1.listIterator(0);
        while(dvizi1.hasNext()){
            modelot.addElement(dvizi1.next());
        }

        new MainFrame(modelot, pero);

    }
}