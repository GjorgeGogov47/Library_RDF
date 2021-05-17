import org.apache.jena.rdf.model.Resource;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

public class MainFrame extends JFrame {
    private JList<String> lista;
    private Details desno;

    public MainFrame(DefaultListModel<String> modelot, LinkedList<Resource> rdfto){
        super("Web Based Systems - Classical Music Composers");
        setLayout(new BorderLayout());
        lista = new JList<>();
        lista.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        lista.setModel(modelot);

        lista.addListSelectionListener(new ListSelectionListener() {
            boolean flag = true;
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(flag) {
                    flag = false;
                    try {
                        desno = new Details(lista.getSelectedValue(), rdfto);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    add(desno, BorderLayout.CENTER);
                    desno.revalidate();
                    desno.repaint();
                }
                else{
                    remove(desno);
                    try {
                        desno = new Details(lista.getSelectedValue(), rdfto);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    add(desno, BorderLayout.CENTER);
                    desno.revalidate();
                    desno.repaint();
                    revalidate();
                    repaint();
                }
            }
        });
        JScrollPane scrollpane = new JScrollPane(lista);
        add(scrollpane,BorderLayout.WEST);

        Object[] defo = modelot.toArray();
        String[] defaultValues = new String[defo.length];
        for(int i=0;i<defaultValues.length;i++){
            defaultValues[i] = defo[i].toString();
        }
        add(new Gore(modelot, defaultValues), BorderLayout.NORTH);


        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}