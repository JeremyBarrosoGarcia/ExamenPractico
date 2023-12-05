package examenpractica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraEcuacionCuadratica extends JFrame {
    private JTextField txtA, txtB, txtC;
    private JLabel lblResultado;

    public CalculadoraEcuacionCuadratica() {
 
        setTitle("Calculadora Ecuación Cuadrática");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JLabel lblA = new JLabel("A:");
        JLabel lblB = new JLabel("B:");
        JLabel lblC = new JLabel("C:");

        txtA = new JTextField(10);
        txtB = new JTextField(10);
        txtC = new JTextField(10);

        lblResultado = new JLabel("Resultado:");

        JButton btnCalcular = new JButton("Calcular");
        JButton btnLimpiar = new JButton("Limpiar");
        JButton btnBorrar = new JButton("Borrar");

        
        setLayout(new SpringLayout());

        add(lblA);
        add(txtA);
        add(lblB);
        add(txtB);
        add(lblC);
        add(txtC);

        add(lblResultado);
        add(new JLabel()); 

        add(btnCalcular);
        add(btnLimpiar);
        add(btnBorrar);

        // Configurar eventos
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularEcuacionCuadratica();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarCampos();
            }
        });

        // Alinear y mostrar la ventana
        SpringUtilities.makeCompactGrid(this.getContentPane(), 5, 2, 10, 10, 10, 10);
        setVisible(true);
    }

    private void calcularEcuacionCuadratica() {
        try {
            double a = Double.parseDouble(txtA.getText());
            double b = Double.parseDouble(txtB.getText());
            double c = Double.parseDouble(txtC.getText());

            double discriminante = b * b - 4 * a * c;

            if (discriminante < 0) {
                lblResultado.setText("No hay soluciones reales");
            } else if (discriminante == 0) {
                double solucion = -b / (2 * a);
                lblResultado.setText("La solución es: " + solucion);
            } else {
                double raiz1 = (-b + Math.sqrt(discriminante)) / (2 * a);
                double raiz2 = (-b - Math.sqrt(discriminante)) / (2 * a);
                lblResultado.setText("Las soluciones son: " + raiz1 + " y " + raiz2);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos en A, B y C", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtA.setText("");
        txtB.setText("");
        txtC.setText("");
        lblResultado.setText("Resultado:");
    }

    private void borrarCampos() {
        txtA.setText("");
        txtB.setText("");
        txtC.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculadoraEcuacionCuadratica();
            }
        });
    }
}

class SpringUtilities {
    public static void makeCompactGrid(Container parent,
                                       int rows, int cols,
                                       int initialX, int initialY,
                                       int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            throw new RuntimeException("The first argument to makeCompactGrid must use SpringLayout.");
        }

        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        
        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).getHeight();
        for (int i = 1; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));

            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }

        
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));

            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }

      
        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
            if (i % cols == 0) { 
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            } else { 
                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST), xPadSpring));
            }

            if (i / cols == 0) { 
                cons.setY(initialYSpring);
            } else { 
                cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH), yPadSpring));
            }
            lastCons = cons;
        }

    
        layout.getConstraints(parent).setConstraint(SpringLayout.SOUTH, Spring.sum(
                lastCons.getConstraint(SpringLayout.SOUTH), yPadSpring));
        layout.getConstraints(parent).setConstraint(SpringLayout.EAST, Spring.sum(
                lastCons.getConstraint(SpringLayout.EAST), xPadSpring));
    }
}
