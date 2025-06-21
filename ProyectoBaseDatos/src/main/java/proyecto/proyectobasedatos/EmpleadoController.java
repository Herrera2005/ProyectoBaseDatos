/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package proyecto.proyectobasedatos;

import clases.Empleado;
import clases.Orden;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author usuario
 */
public class EmpleadoController implements Initializable {
    Connection conexion=App.conn;
    private Empleado empleado;
    @FXML
    private ListView<Orden> listaOrdenes;
    @FXML
    private Label nombreText;
    
    @FXML
    private Stage detailStage;
    
    @FXML
    private HBox hBoxAdm;
    
    private int idOrden;
    private Label estadoLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        empleado=App.empleado;
        
        System.out.println(empleado.getRol());
        if(empleado.getRol().equals("ADM")){
            javafx.scene.control.Button crearEmpleado= new javafx.scene.control.Button ("Crear Cuenta Empleado");
            javafx.scene.control.Button admEmpleados= new javafx.scene.control.Button ("Gestion de empleados");
            crearEmpleado.setOnAction(event ->{
                CrearCuentaEmpleado();
            });
            hBoxAdm.getChildren().addAll(crearEmpleado, admEmpleados);

        }
        CargarOrdenes();
        // TODO
    }
    private void CrearCuentaEmpleado(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("crearCuentaEmpleado.fxml"));
            Parent root =fxmlLoader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Crear Cuenta para Empleados");
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    private void CargarOrdenes(){
        nombreText.setText(empleado.getNombre()+ " "+ empleado.getApellido());
        String query = "select * from orden ";
        try (PreparedStatement ps = conexion.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
            ObservableList<Orden> ordenItems= FXCollections.observableArrayList();
            while (rs.next()){
                String estado = rs.getString("estado");
                
                if (estado.equals("PENDIENTE")|| estado.equals("REALIZANDO")){
                    String idCliente = String.valueOf(rs.getInt("idcliente"));
                    String nombre =rs.getString("nombrecliente");
                    String apellido= rs.getString("apellidocliente");
                    String fecha = rs.getDate("fecha").toString();
                    String hora = rs.getTime("hora").toString();
                    String descripcion = rs.getString("descripcion");
                    double precioTotal = rs.getDouble("precioTotal");
                    idOrden=rs.getInt("id_orden");
                    Orden orden= new Orden(idCliente,nombre,apellido,fecha, hora, estado,descripcion, precioTotal);
                    ordenItems.add(orden);
                }
            }
            listaOrdenes.setItems(ordenItems);
            listaOrdenes.setCellFactory(param -> new ListCell<Orden>() {
            @Override
            protected void updateItem(Orden item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
            });
            
            listaOrdenes.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() == 2) {
                    Orden selectedOrder = listaOrdenes.getSelectionModel().getSelectedItem();
                    if (selectedOrder != null) {
                        mostrarDetallesOrden(selectedOrder);
                    }
                }
            });
        }catch (SQLException ex) {
        ex.printStackTrace();
        }
    }
    private void mostrarDetallesOrden(Orden orden) {
        detailStage = new Stage();
        estadoLabel=new Label();
        VBox detailBox = new VBox();
        HBox hBox = new HBox();
        String descripcion ="";
        if (orden.getDescripcion().contains(";")){
            String[] descripcionLista=orden.getDescripcion().split(";");
            for (String cadena :descripcionLista){
                descripcion = descripcion+ "\n"+cadena;
            }
        }else{
            descripcion=orden.getDescripcion();
        }
         
        hBox.getChildren().addAll(
                new javafx.scene.control.Label("ID Cliente: " + orden.getIdCliente()),
                new javafx.scene.control.Label("       "+"Fecha: " + orden.getFecha()+ " "+orden.getHora())
        );
        
        estadoLabel.setText("Estado: " + orden.getEstado());
        
        detailBox.getChildren().addAll(
                hBox,
                new javafx.scene.control.Label("Nombre: " + orden.getNombre()+" "+ orden.getApellido()),
                estadoLabel,
                new javafx.scene.control.Label("Descripción: " +"\n"+ descripcion),
                new javafx.scene.control.Label("Precio Total: $" + orden.getPrecioTotal())
                
        );
        javafx.scene.control.Button cambiarEstado= new javafx.scene.control.Button ("Cambiar estado");
        cambiarEstado.setOnAction(event->{
            cambiarEstadoOrden(orden);
        });
        detailBox.getChildren().add(cambiarEstado);
        Scene detailScene = new Scene(detailBox, 300, 200);
        detailStage.setScene(detailScene);
        detailStage.show();
    }
    
    private void cambiarEstadoOrden(Orden orden){
        String nuevoEstado;
        switch (orden.getEstado()) {
            case "PENDIENTE":
                nuevoEstado = "REALIZANDO";
                break;
            case "REALIZANDO":
                nuevoEstado = "REALIZADO";
                break;
            default:
                nuevoEstado = orden.getEstado();
                break;
        }

        String query = "UPDATE orden SET estado = ? WHERE id_orden = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, orden.getEstado());
            ps.setInt(2, idOrden);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                orden.setEstado(nuevoEstado);
                System.out.println("Estado de la orden actualizado correctamente."+orden.getEstado());
                estadoLabel .setText("Descripción: "+ nuevoEstado);
                if (orden.getEstado().equals("REALIZADO")){
                    detailStage.close();
                }
            } else {
                System.out.println("No se encontró la orden o no se pudo actualizar.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        CargarOrdenes();
    }

    
    
    @FXML
    private void PaginaOrden(ActionEvent e){
        try {
            App.setRoot("ordenar","Ordenar el pedido");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
