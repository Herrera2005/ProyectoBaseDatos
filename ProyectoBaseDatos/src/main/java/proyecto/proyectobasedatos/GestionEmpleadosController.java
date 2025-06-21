/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package proyecto.proyectobasedatos;

import clases.Empleado;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author usuario
 */
public class GestionEmpleadosController implements Initializable {
    @FXML
    private TreeTableView<Empleado> treeTable;
    
    @FXML
    private TreeTableColumn<Empleado, String> empleadoColumn;
    
    @FXML
    private TreeTableColumn<Empleado, Void> eliminarColumn;
    
    @FXML
    private TreeTableColumn<Empleado, Void> editarColumn;

    private ObservableList<Empleado> empleados = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        empleadoColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));
        
        eliminarColumn.setCellFactory(new Callback<TreeTableColumn<Empleado, Void>, TreeTableCell<Empleado, Void>>() {
            @Override
            public TreeTableCell<Empleado, Void> call(TreeTableColumn<Empleado, Void> param) {
                return new TreeTableCell<Empleado, Void>() {
                    private final Button button = new Button("Eliminar");

                    {
                        button.setOnAction(event -> {
                            Empleado empleado = getTreeTableRow().getItem();
                            System.out.println("Acción 1 para: " + empleado.getNombre());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        });
        editarColumn.setCellFactory(new Callback<TreeTableColumn<Empleado, Void>, TreeTableCell<Empleado, Void>>() {
            @Override
            public TreeTableCell<Empleado, Void> call(TreeTableColumn<Empleado, Void> param) {
                return new TreeTableCell<Empleado, Void>() {
                    private final Button button = new Button("Editar");

                    {
                        button.setOnAction(event -> {
                            Empleado empleado = getTreeTableRow().getItem();
                            System.out.println("Acción 2 para: " + empleado.getNombre());
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
            }
        });
    }    
    
}
