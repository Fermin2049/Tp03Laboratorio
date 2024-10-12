package com.fermin2049.tp03laboratorio.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.fermin2049.tp03laboratorio.databinding.ActivityMainBinding;
import com.fermin2049.tp03laboratorio.ui.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Observa los eventos de navegación
        mainActivityViewModel.getNavegarARegistro().observe(this, unused -> navegarARegistro());

        // Observa los mensajes de error
        mainActivityViewModel.getMensaje().observe(this, this::mostrarMensajeError);

        // Acción del botón Ingresar
        binding.btnIngresar.setOnClickListener(v -> {
            String correo = binding.etMail.getText().toString();
            String password = binding.etPassword.getText().toString();
            mainActivityViewModel.ingresar(correo, password);
        });

        // Acción del botón Registrar
        binding.btnRegistrar.setOnClickListener(v -> mainActivityViewModel.registrar());
    }

    private void navegarARegistro() {
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }

    private void mostrarMensajeError(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}