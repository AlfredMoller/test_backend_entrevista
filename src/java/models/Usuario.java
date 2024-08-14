/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author pc
 */
public class Usuario {
    private String nombre_usuario;
    private String apellido_usuario;
    private String email_usuario;
    private String telefono_usuario;
    private String clave_usuario;
    private String uuid_usuario;
    private int id_ciudad;
    private String cedula_usuario;
    
    public Usuario() {
    }

    public Usuario(String nombre_usuario, String apellido_usuario, String email_usuario, String telefono_usuario, String clave_usuario, String uuid_usuario, int id_ciudad, String cedula_usuario) {
        this.nombre_usuario = nombre_usuario;
        this.apellido_usuario = apellido_usuario;
        this.email_usuario = email_usuario;
        this.telefono_usuario = telefono_usuario;
        this.clave_usuario = clave_usuario;
        this.uuid_usuario = uuid_usuario;
        this.id_ciudad = id_ciudad;
        this.cedula_usuario = cedula_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getApellido_usuario() {
        return apellido_usuario;
    }

    public void setApellido_usuario(String apellido_usuario) {
        this.apellido_usuario = apellido_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.email_usuario = email_usuario;
    }

    public String getTelefono_usuario() {
        return telefono_usuario;
    }

    public void setTelefono_usuario(String telefono_usuario) {
        this.telefono_usuario = telefono_usuario;
    }

    public String getClave_usuario() {
        return clave_usuario;
    }

    public void setClave_usuario(String clave_usuario) {
        this.clave_usuario = clave_usuario;
    }

    public String getUuid_usuario() {
        return uuid_usuario;
    }

    public void setUuid_usuario(String uuid_usuario) {
        this.uuid_usuario = uuid_usuario;
    }

    public int getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(int id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public String getCedula_usuario() {
        return cedula_usuario;
    }

    public void setCedula_usuario(String cedula_usuario) {
        this.cedula_usuario = cedula_usuario;
    }
    
    
}
