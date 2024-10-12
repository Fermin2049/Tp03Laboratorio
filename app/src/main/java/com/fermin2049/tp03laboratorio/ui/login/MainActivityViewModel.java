package com.fermin2049.tp03laboratorio.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fermin2049.tp03laboratorio.modelo.Usuario;
import com.fermin2049.tp03laboratorio.request.ApiClient;
import com.fermin2049.tp03laboratorio.ui.registro.RegistroActivity;


public class MainActivityViewModel extends AndroidViewModel {
    private final Context context;
    private final MutableLiveData<Void> navegarARegistro = new MutableLiveData<>();
    private final MutableLiveData<String> mensajeMutableLiveData = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();
    }

    public LiveData<Void> getNavegarARegistro() {
        return navegarARegistro;
    }

    public LiveData<String> getMensaje() {
        return mensajeMutableLiveData;
    }

    // Método para manejar el login
    public void ingresar(String correo, String password) {
        Usuario usuario = ApiClient.login(context, correo, password);
        if (usuario != null) {
            navegarARegistro.setValue(null); // Emitir evento de navegación
        } else {
            mensajeMutableLiveData.setValue("Correo o contraseña incorrectos");
        }
    }

    public void registrar() {
        Intent intent = new Intent(context, RegistroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
