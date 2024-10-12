package com.fermin2049.tp03laboratorio.ui.registro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
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
    private ActivityResultLauncher<Intent> arl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registroActivityViewModel = new ViewModelProvider(this).get(RegistroActivityViewModel.class);

        // Observa los datos del usuario
        registroActivityViewModel.getUsuario().observe(this, this::actualizarVistaConUsuario);

        // Observa los cambios en el Uri y actualiza la imagen
        registroActivityViewModel.getUriMutable().observe(this, binding.ivFoto::setImageURI);

        // Observa los mensajes del ViewModel para mostrar Toasts
        registroActivityViewModel.getMensaje().observe(this, mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        );

        // Acción del botón "Guardar"
        binding.btnGuardar.setOnClickListener(v -> {
            String dni = binding.etDni.getText().toString();
            String nombre = binding.etNombre.getText().toString();
            String apellido = binding.etApellido.getText().toString();
            String correo = binding.etMail.getText().toString();
            String password = binding.etPassword.getText().toString();

            registroActivityViewModel.guardarUsuario(apellido, nombre, dni, correo, password);
        });

        // Acción del botón "Cargar Foto"
        binding.btnCargarFoto.setOnClickListener(v -> arl.launch(intent));

        abrirGaleria();
        registroActivityViewModel.cargarDatosUsuario();
    }

    private void actualizarVistaConUsuario(Usuario usuario) {
        binding.etDni.setText(String.valueOf(usuario.getDni()));
        binding.etNombre.setText(usuario.getNombre());
        binding.etApellido.setText(usuario.getApellido());
        binding.etMail.setText(usuario.getMail());
        binding.etPassword.setText(usuario.getPassword());
        binding.ivFoto.setImageURI(usuario.getImagen());
    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                registroActivityViewModel::recibirFoto);
    }
}