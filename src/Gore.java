import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

class Gore extends JPanel {
    private String[] defaultValues;
    private DefaultListModel<String> model;

    private JTextField createTextField() {
        final JTextField field = new JTextField(15);
        field.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                String filter = field.getText();
                if(filter.equals("")){
                    model.removeAllElements();
                    for(String s : defaultValues){
                        model.addElement(s);
                    }
                }
                else {
                    filterModel(model, filter, defaultValues);
                }
            }
        });
        return field;
    }
    public void filterModel(DefaultListModel<String> model, String filter, String[] defaultValues) {
        for (String s : defaultValues) {
            if(Character.isLowerCase(filter.charAt(0))) {
                filter = filter.substring(0, 1).toUpperCase() + filter.substring(1);
            }
            String[] deli = s.split(" ");
            if(deli.length == 2) {
                if (!deli[0].startsWith(filter) && !deli[1].startsWith(filter)) {
                    if (model.contains(s)) {
                        model.removeElement(s);
                    }
                } else {
                    if (!model.contains(s)) {
                        model.addElement(s);
                    }
                }
            }
            if(deli.length == 3){
                if (!deli[0].startsWith(filter) && !deli[1].startsWith(filter) && !deli[2].startsWith(filter)) {
                    if (model.contains(s)) {
                        model.removeElement(s);
                    }
                } else {
                    if (!model.contains(s)) {
                        model.addElement(s);
                    }
                }
            }
            if(deli.length == 4){
                if (!deli[0].startsWith(filter) && !deli[1].startsWith(filter) && !deli[2].startsWith(filter) && !deli[3].startsWith(filter)) {
                    if (model.contains(s)) {
                        model.removeElement(s);
                    }
                } else {
                    if (!model.contains(s)) {
                        model.addElement(s);
                    }
                }
            }
        }
    }

    public Gore(DefaultListModel<String> model, String[] defaultValues){
        this.defaultValues = defaultValues;
        this.model = model;
        setLayout(new FlowLayout());
        add(new Label("Пребарај:"));

        JTextField pole = createTextField();
        add(pole);

    }
}
