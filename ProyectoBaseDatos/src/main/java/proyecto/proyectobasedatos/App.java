package proyecto.proyectobasedatos;

import clases.Cliente;
import clases.Empleado;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {
    public static Connection conn;
    private static Stage stage;
    public static Cliente cliente;
    public static Empleado empleado;
    
    public static Connection getConnection() {
        
        String url = "jdbc:postgresql://aws-0-us-west-1.pooler.supabase.com:6543/postgres?user=postgres.itliefcisdanaikgbsww&";
        String user = "ProyectoBaseDatos";
        String password = "VtRRE8qBRPwQ@$v";
        /*
        String url = "jdbc:postgresql://localhost:5432/DataBaseProyecto";
        String user = "postgres";
        String password = "criaherr";
        */
        Connection connection = null;
        
        try{
            connection =DriverManager.getConnection(url,user,password);
            System.out.println("Conexion a la database establecido");
        }catch ( SQLException e){
            System.out.println("Conexion a la database fallo");
            e.printStackTrace();
        }
        
        return connection;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        setRoot("inicioSecion", "Pagina Principal - Inicio de secion");
    }
  
    public static void setRoot(String fxml,String titulo) throws IOException {
        FXMLLoader fxmlLoader =new FXMLLoader(App.class.getResource(fxml+".fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.setTitle(titulo);
        stage.show();
    }


    public static void main(String[] args) {
        conn= getConnection();
        while (conn ==null){
            conn= getConnection();
        }
        launch();
    }

}