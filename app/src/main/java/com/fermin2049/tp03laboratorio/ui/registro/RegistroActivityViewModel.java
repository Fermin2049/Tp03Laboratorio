package com.fermin2049.tp03laboratorio.ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.activity.result.ActivityResult;

import com.fermin2049.tp03laboratorio.modelo.Usuario;
import com.fermin2049.tp03laboratorio.request.ApiClient;

public class RegistroActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<Usuario> usuarioMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mensajeLiveData = new MutableLiveData<>();

    private final Context context;
    private Uri uri;

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getUsuario() {
        return usuarioMutableLiveData;
    }

    public LiveData<Uri> getUriMutable() {
        return uriMutableLiveData;
    }

    public LiveData<String> getMensaje() {
        return mensajeLiveData;
    }

    public void cargarDatosUsuario(boolean isEditing) {
        // Lógica para cargar datos según el estado de edición
        if (isEditing) {
            Usuario usuario = ApiClient.leer(context);
            if (usuario != null) {
                usuarioMutableLiveData.setValue(usuario);
                uriMutableLiveData.setValue(usuario.getImagen());
            }
        } else {
            // Crea un usuario vacío para inicializar campos en blanco
            usuarioMutableLiveData.setValue(new Usuario("", "", 0, "", "", null));
        }
    }


    public void guardarUsuario(String apellido, String nombre, String dni, String email, String password) {
        try {
            long dnis = Long.parseLong(dni);
            Usuario usuarioNuevo = new Usuario(nombre, apellido, dnis, email, password, uri);
            boolean guardado = ApiClient.guardar(context, usuarioNuevo);

            mensajeLiveData.setValue(guardado ? "Usuario guardado correctamente" : "Error al guardar los datos");
        } catch (NumberFormatException e) {
            mensajeLiveData.setValue("DNI inválido");
        }
    }


    public void recibirFoto(Uri uri) {
        this.uri = uri;
        uriMutableLiveData.setValue(uri);  // Notificar a la vista del nuevo URI
    }

    public void manejarResultadoActividad(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                // Tomar permiso persistente para el URI seleccionado
                getApplication().getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                recibirFoto(uri);
            }
        }
    }

}