/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author usuario
 */
public class RolEmpleado {
    private String rol;
    private String descripcion;

    public RolEmpleado(String rol, String descripcion) {
        this.rol = rol;
        this.descripcion = descripcion;
    }

    public String getRol() {
        return rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return rol ;
    }
    
    
}
