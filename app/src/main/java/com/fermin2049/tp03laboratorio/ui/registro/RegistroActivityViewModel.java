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

    public void cargarDatosUsuario() {
        Usuario usuario = ApiClient.leer(context);
        if (usuario != null) {
            usuarioMutableLiveData.setValue(usuario);
            uriMutableLiveData.setValue(usuario.getImagen());  // Emitimos el Uri si existe
        } else {
            // Creamos un usuario vacío para evitar null
            usuarioMutableLiveData.setValue(new Usuario("", "", 0, "", "", null));
        }
    }

    public void guardarUsuario(String apellido, String nombre, String dni, String email, String password) {
        try {
            long dnis = Long.parseLong(dni);
            Usuario usuarioNuevo = new Usuario(nombre, apellido, dnis, email, password, uri);
            boolean guardado = ApiClient.guardar(context, usuarioNuevo);

            String mensaje = guardado ? "Usuario guardado correctamente" : "Error al guardar los datos";
            mensajeLiveData.setValue(mensaje);
        } catch (NumberFormatException e) {
            mensajeLiveData.setValue("DNI inválido");
        }
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                uri = data.getData();
                uriMutableLiveData.setValue(uri);
            }
        }
    }
}