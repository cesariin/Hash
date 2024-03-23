import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;

public class RegistroVacunas {

    private static final String FILENAME = "registros.txt";
    private static HashMap<String, String> registros = new HashMap<>();

    public static void main(String[] args) {
        cargarRegistrosDesdeArchivo();

        JFrame frame = new JFrame("Registro de Vacunas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JTextField cuiField = new JTextField(10);
        JButton buscarButton = new JButton("Buscar");
        JTextArea resultadoArea = new JTextArea(5, 20);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cui = cuiField.getText();
                if (registros.containsKey(cui)) {
                    resultadoArea.setText(registros.get(cui));
                } else {
                    resultadoArea.setText("No existe la persona con CUI: " + cui);
                }
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(new JLabel("CUI:"));
        frame.add(cuiField);
        frame.add(buscarButton);
        frame.add(resultadoArea);

        frame.setVisible(true);
    }

    private static void cargarRegistrosDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    registros.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarRegistrosEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (String cui : registros.keySet()) {
                bw.write(cui + ":" + registros.get(cui));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
