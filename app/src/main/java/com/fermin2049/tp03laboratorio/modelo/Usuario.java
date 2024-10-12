package com.fermin2049.tp03laboratorio.modelo;

import android.net.Uri;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;
    private String apellido;
    private long dni;
    private String mail;
    private String password;
    private transient Uri imagen; // `transient` para evitar la serialización directa
    private String imagenUri; // Para guardar el `Uri` como String


    public Usuario(String nombre, String apellido, long dni, String mail, String password, Uri imagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.mail = mail;
        this.password = password;
        this.imagen = imagen;
        this.imagenUri = imagen != null ? imagen.toString() : null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    public String getImagenUri() {
        return imagenUri;
    }

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", imagen=" + imagen +
                ", imagenUri='" + imagenUri + '\'' +
                '}';
    }
}
