import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.shared.PropertyNotFoundException;
import org.apache.jena.sparql.vocabulary.FOAF;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Details extends JPanel{
    class DisabledItemSelectionModel extends DefaultListSelectionModel {
        @Override
        public void setSelectionInterval(int index0, int index1) {
            super.setSelectionInterval(-1, -1);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.LIGHT_GRAY);
    }

    public Details(String ime, LinkedList<Resource> rdfto) throws IOException {
        setLayout(new BorderLayout());

        ListIterator<Resource> dvizi = rdfto.listIterator(0);

        while(dvizi.hasNext()){
            Resource composer = dvizi.next();
            if(composer.getRequiredProperty(FOAF.name).getObject().toString().equals(ime)) {
                StmtIterator itero = composer.listProperties();
                String path = "";
                try {
                    path = composer.getRequiredProperty(FOAF.img).getObject().toString();
                }
                catch (Exception e){System.out.print(e);}

                BufferedImage myPicture = null;
                try {
                    myPicture = ImageIO.read(new URL(path));
                }
                catch (IOException e) {
                    myPicture = ImageIO.read(new URL("https://www.warnersstellian.com/Content/images/product_image_not_available.png"));
                }
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                picLabel.setToolTipText(ime);
                add(picLabel, BorderLayout.CENTER);

                JLabel labela = new JLabel(composer.getRequiredProperty(FOAF.birthday).getObject().toString());
                labela.setFont(new Font("Serif", Font.BOLD, 40));
                labela.setForeground(Color.black);
                labela.setHorizontalAlignment(JLabel.CENTER);
                add(labela,BorderLayout.SOUTH);

                LinkedList<String> hasInfluenced = new LinkedList<>();
                LinkedList<String> influencedBy = new LinkedList<>();
                int n1 = 0;
                int n2 = 0;

                while (itero.hasNext()) {
                    Statement svojstvo = itero.nextStatement();
                    if(svojstvo.getPredicate().toString().equals("http://purl.org/ontology/classicalmusicnav#influencedBy")){
                        try {
                            influencedBy.add(svojstvo.getObject().asResource().getRequiredProperty(FOAF.name).getObject().toString());
                        }
                        catch (PropertyNotFoundException e){continue;}
                        n2++;
                    }
                    if(svojstvo.getPredicate().toString().equals("http://purl.org/ontology/classicalmusicnav#hasInfluenced")){
                        try {
                            hasInfluenced.add(svojstvo.getObject().asResource().getRequiredProperty(FOAF.name).getObject().toString());
                        }
                        catch (PropertyNotFoundException e){continue;}
                        n1++;
                    }
                }

                DefaultListModel<String> listoHas = new DefaultListModel<>();
                DefaultListModel<String> listoBy = new DefaultListModel<>();
                Collections.sort(hasInfluenced, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return Collator.getInstance().compare(o1, o2);
                    }
                });
                Collections.sort(influencedBy, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return Collator.getInstance().compare(o1, o2);
                    }
                });
                ListIterator<String> iterHas = hasInfluenced.listIterator(0);
                ListIterator<String> iterBy = influencedBy.listIterator(0);
                listoHas.addElement("Инспирирал/а:");
                listoBy.addElement("Инспириран/а од:");
                listoBy.addElement(" ");
                listoHas.addElement(" ");
                while(iterHas.hasNext()){
                    listoHas.addElement(iterHas.next());
                }
                while(iterBy.hasNext()){
                    listoBy.addElement(iterBy.next());
                }

                JList<String> izlezHas = new JList<>();
                JList<String> izlezBy = new JList<>();
                izlezBy.setSelectionModel(new DisabledItemSelectionModel());
                izlezHas.setSelectionModel(new DisabledItemSelectionModel());
                izlezBy.setModel(listoBy);
                izlezHas.setModel(listoHas);
                izlezBy.setFont(new Font("Verdana", Font.BOLD,20));
                izlezHas.setFont(new Font("Verdana", Font.BOLD,20));
                izlezBy.setBackground(Color.LIGHT_GRAY);
                izlezHas.setBackground(Color.LIGHT_GRAY);
                JScrollPane i1 = new JScrollPane(izlezBy);
                JScrollPane i2 = new JScrollPane(izlezHas);

                add(i1,BorderLayout.WEST);
                add(i2,BorderLayout.EAST);

                break;
            }
        }

    }
}
