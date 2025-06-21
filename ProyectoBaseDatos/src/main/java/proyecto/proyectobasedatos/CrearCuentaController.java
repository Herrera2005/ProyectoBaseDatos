/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package proyecto.proyectobasedatos;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class CrearCuentaController implements Initializable {
    @FXML
    private TextField nombresText;
            
    @FXML
    private TextField apellidosText;
            
    @FXML
    private TextField usuarioText;
            
    @FXML
    private TextField contraseniaText;
    
    Connection conexion=App.conn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void crearCuenta(ActionEvent e){
        String nombres = nombresText.getText();
        String apellidos = apellidosText.getText();
        String usuario = usuarioText.getText();
        String contrasenia = contraseniaText.getText();

        if (nombres.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || contrasenia.isEmpty()) {
            mostrarAlerta("Campos Incompletos", "Faltan campos por llenar");
            return;
        }
        
        try {
            if (existeUsuario(usuario)) {
                mostrarAlerta("Usuario ya ingresado", "El usuario ingresada ya tiene una cuenta");
            
            } else {
                crearCuentaUsuario(usuario, contrasenia);
                crearCuentaCliente(usuario, nombres, apellidos);
                
                mostrarAlerta("Éxito", "Cuenta creada correctamente");
                nombresText.clear();
                apellidosText.clear();
                usuarioText.clear();
                contraseniaText.clear();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private boolean existeUsuario(String usuario) throws SQLException {
        String query = "SELECT COUNT(*) FROM cliente WHERE usuario = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    
    private void crearCuentaUsuario(String u, String c) {
        try {
            String query = "INSERT INTO usuarios (usuario, contrasenia, categoria) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, u);
                ps.setString(2, c);
                ps.setString(3, "CLI");
                
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
    
    private void crearCuentaCliente(String u, String n, String a) {
        try {
            String query = "INSERT INTO cliente (usuario, nombre, apellido) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, u);
                ps.setString(2, n);
                ps.setString(3, a);
                
                int rowsAffected = ps.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Cliente creado correctamente.");
                } else {
                    System.out.println("No se pudo crear el cliente.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void PaginaInicioSecion(ActionEvent e){
        try{
            App.setRoot("inicioSecion", "Pagina Principal - Inicio de secion");
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    private static void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
