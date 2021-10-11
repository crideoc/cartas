package com.example.cartas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String NCARTAS = "NCARTAS";
    public static final String NJUGADORES = "NJUGADORES";
    public static final int CODIGO = 1;

    Button btnEmpezar;
    EditText editTextNJugadores, editTextNCartas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEmpezar = findViewById(R.id.btnEmpezar);
        editTextNCartas = findViewById(R.id.editTextNCartas);
        editTextNJugadores = findViewById(R.id.editTextNJugadores);


        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean pasar = true;

                if (editTextNCartas.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.rellenaCartas), Toast.LENGTH_LONG).show();
                    Log.i("informacion", "valor de cartas ");
                    pasar = false;
                }

                if (editTextNJugadores.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, getResources().getText(R.string.rellenaJugadores), Toast.LENGTH_LONG).show();
                    Log.i("informacion", "valor de jugadores ");
                    pasar = false;
                }
                if (pasar) {
                    String cartas = editTextNCartas.getText().toString();
                    int ncartas = Integer.parseInt(cartas);

                    String jugadores = editTextNJugadores.getText().toString();
                    int njugadores = Integer.parseInt(jugadores);

                    if (ncartas < njugadores) {
                        Toast.makeText(MainActivity.this, getResources().getText(R.string.cartasMayor), Toast.LENGTH_LONG).show();
                        Log.i("informacion", "hay mas cartas que jugadores");
                        pasar = false;
                    } else {
                        Log.i("informacion", "valor de cartas numero " + ncartas);

                        Intent intent = new Intent(MainActivity.this, dinamico.class);
                        intent.putExtra(NJUGADORES, jugadores);
                        intent.putExtra(NCARTAS, cartas);
                        startActivity(intent);
                    }

                }


            }
        });
    }
}