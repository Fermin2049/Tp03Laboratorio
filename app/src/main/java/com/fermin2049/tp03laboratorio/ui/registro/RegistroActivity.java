package com.fermin2049.tp03laboratorio.ui.registro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.fermin2049.tp03laboratorio.databinding.ActivityRegistroBinding;
import com.fermin2049.tp03laboratorio.modelo.Usuario;


public class RegistroActivity extends AppCompatActivity {
    private RegistroActivityViewModel registroActivityViewModel;
    private ActivityRegistroBinding binding;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;  // Declarar el launcher aquí

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registroActivityViewModel = new ViewModelProvider(this).get(RegistroActivityViewModel.class);

        // Configurar el ActivityResultLauncher al inicio del onCreate()
        arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> registroActivityViewModel.manejarResultadoActividad(result.getResultCode(), result.getData())
        );

        // Configurar botones
        binding.btnCargarFoto.setOnClickListener(v -> abrirGaleria());
        binding.btnGuardar.setOnClickListener(v -> guardarUsuario());

        // Observar cambios en el usuario y el URI de la imagen
        registroActivityViewModel.getUsuario().observe(this, this::actualizarVistaConUsuario);
        registroActivityViewModel.getUriMutable().observe(this, binding.ivFoto::setImageURI);
        registroActivityViewModel.getMensaje().observe(this, mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show());

        // Obtener el estado de edición e inicializar los datos
        boolean isEditing = getIntent().getBooleanExtra("isEditing", false);
        registroActivityViewModel.cargarDatosUsuario(isEditing);
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        arl.launch(intent);  // Llamar al launcher aquí
    }

    private void guardarUsuario() {
        String dni = binding.etDni.getText().toString();
        String nombre = binding.etNombre.getText().toString();
        String apellido = binding.etApellido.getText().toString();
        String correo = binding.etMail.getText().toString();
        String password = binding.etPassword.getText().toString();
        registroActivityViewModel.guardarUsuario(apellido, nombre, dni, correo, password);
    }

    private void actualizarVistaConUsuario(Usuario usuario) {
        binding.etDni.setText(String.valueOf(usuario.getDni()));
        binding.etNombre.setText(usuario.getNombre());
        binding.etApellido.setText(usuario.getApellido());
        binding.etMail.setText(usuario.getMail());
        binding.etPassword.setText(usuario.getPassword());
        binding.ivFoto.setImageURI(usuario.getImagen());
    }
}