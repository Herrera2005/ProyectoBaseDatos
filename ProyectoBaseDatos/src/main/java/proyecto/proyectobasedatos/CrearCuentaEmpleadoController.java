/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package proyecto.proyectobasedatos;

import clases.Empleado;
import clases.RolEmpleado;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import static proyecto.proyectobasedatos.App.conn;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class CrearCuentaEmpleadoController implements Initializable {
    @FXML
    private TextField nombresText;
    
    @FXML
    private TextField apellidosText;
    
    @FXML
    private TextField correoText;
    
    @FXML
    private TextField cedulaText;
    
    @FXML
    private TextField contraseniaText;
    
    @FXML
    private ComboBox<RolEmpleado> rolComboBox;
    
    Connection conexion=App.conn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RolEmpleado rolEmpleado= new RolEmpleado("Empleado","EMP");
        RolEmpleado rolAdministrador=new RolEmpleado("Administrador","ADM");
        
        rolComboBox.getItems().addAll(rolEmpleado,rolAdministrador);
        // TODO
    }    
    
    @FXML
    private void crearCuenta(ActionEvent e){
        String nombres = nombresText.getText();
        String apellidos = apellidosText.getText();
        String correo = correoText.getText();
        String cedula = cedulaText.getText();
        String contrasenia = contraseniaText.getText();
        RolEmpleado rolEmpleado = rolComboBox.getValue();
        if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || cedula.isEmpty() || contrasenia.isEmpty() || rolEmpleado == null) {
            mostrarAlerta("Campos Incompletos", "Faltan campos por llenar");
            return;
        }
        
        LocalDate localDate = LocalDate.now();
        Date sqlDate = Date.valueOf(localDate);
        String rol = rolEmpleado.getDescripcion();
        
        try {
            if (existeCedula(cedula)) {
                mostrarAlerta("Cédula ya ingresada", "La cédula ingresada ya tiene una cuenta");
            } else if (existeCorreo(correo)) {
                mostrarAlerta("Correo ya Ingresado", "Ya tiene una cuenta el correo que ingresó");
            } else {
                crearCuentaUsuario(correo, contrasenia);
                crearCuentaEmpleado(cedula, rol, nombres, apellidos, sqlDate, correo);
                mostrarAlerta("Éxito", "Cuenta creada correctamente");
                nombresText.clear();
                apellidosText.clear();
                correoText.clear();
                cedulaText.clear();
                contraseniaText.clear();
                rolComboBox.getSelectionModel().clearSelection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean existeCedula(String cedula) throws SQLException {
        String query = "SELECT COUNT(*) FROM empleado WHERE cedula = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    private boolean existeCorreo(String correo) throws SQLException {
        String query = "SELECT COUNT(*) FROM empleado WHERE correo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    private void crearCuentaUsuario(String u, String c) {
        try {
            String query = "INSERT INTO usuarios (usuario, contrasenia, categoria) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, u);
                ps.setString(2, c);
                ps.setString(3, "EMP");
                
                int rowsAffected = ps.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Usuario creado correctamente.");
                } else {
                    System.out.println("No se pudo crear el usuario.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void crearCuentaEmpleado(String c, String r, String n, String a, Date d, String co) {
        try {
            String query = "INSERT INTO empleado (cedula, rol, nombre, apellido, fechacontratacion, correo) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, c);
                ps.setString(2, r);
                ps.setString(3, n);
                ps.setString(4, a);
                ps.setDate(5, d);
                ps.setString(6, co);
                
                int rowsAffected = ps.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Empleado creado correctamente.");
                } else {
                    System.out.println("No se pudo crear el empleado.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}

