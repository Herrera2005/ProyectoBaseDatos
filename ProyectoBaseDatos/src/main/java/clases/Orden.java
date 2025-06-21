/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author usuario
 */
public class Orden {


    private String idCliente;
    private String nombre;
    private String apellido;
    private String fecha;
    private String hora;
    private String estado;
    private String descripcion;
    private double precioTotal;

    public Orden( String idCliente,String nombre, String apellido, String fecha, String hora, String estado, String descripcion, double precioTotal) {
        this.idCliente = idCliente;
        this.nombre=nombre;
        this.apellido=apellido;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.descripcion = descripcion;
        this.precioTotal = precioTotal;
    }


    public String getIdCliente() {
        return idCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }
    
    
    @Override
    public String toString() {
        return String.format("Cliente: %s %s\nTotal a pagar: %s\nFecha: %s\nEstado: %s", nombre,apellido, precioTotal, fecha, estado);
    }

}
