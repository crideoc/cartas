package com.example.cartas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Arrays;

public class dinamico extends AppCompatActivity {

    TextView CartasTotales;
    Button reset;
    SeekBar Barra;
    LinearLayout layout;
    int tamano = 15, njugadores, nCartas;

    ImageButton[] BotonMas;
    ImageButton[] BotonMenos;
    TextView[] nombre;
    TextView[] ValorCartas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinamico);

        CartasTotales = findViewById(R.id.textViewCartasTotales);
        Barra = findViewById(R.id.Barra);
        reset = findViewById(R.id.buttonReset);
        layout = findViewById(R.id.gridLayoutTabla);

        Intent intent = getIntent();
        String cartas = getIntent().getStringExtra(MainActivity.NCARTAS);
        String jugadores = getIntent().getStringExtra(MainActivity.NJUGADORES);

        String cartasT = CartasTotales.getText().toString() + " " + cartas;

        CartasTotales.setText(cartasT);
        Log.i("informacion", "valor de jugadores " + jugadores + " valor de cartas " + cartas);

        int CartasTotalesAux=nCartas;
        nCartas = Integer.parseInt(cartas);
        njugadores = Integer.parseInt(jugadores);

        creacionDinamica(njugadores, layout);


        Barra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tamaño(ValorCartas,Barra,nombre);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<njugadores;i++){

                    ValorCartas[i].setText("0");
                }
                Log.i("informacion","Valor de nCartas es "+nCartas);
                CartasTotales.setText("Cartas Totales :"+nCartas);
            }
        });


    }


    public void creacionDinamica(int jugadores, LinearLayout layout) {
        BotonMas = new ImageButton[jugadores];
        BotonMenos = new ImageButton[jugadores];
        nombre = new TextView[jugadores];
        ValorCartas = new TextView[jugadores];

        for (int i = 0; i < jugadores; i++) {

            ViewGroup.LayoutParams params1 = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


            LinearLayout layout2 = new LinearLayout(dinamico.this);
            layout2.setLayoutParams(params1);
            layout2.setPadding(0, 0, 0, 20);
            layout2.setOrientation(layout.HORIZONTAL);

            //nombres del jugador
            TextView txtN = new TextView(this);
            txtN.setId(View.generateViewId());//generamos un id valido(Int)
            nombre[i] = txtN;
            String auxiliar = getResources().getString(R.string.jugadores) + "" + i;
            txtN.setText(auxiliar);
            txtN.setTextSize(20f);
            txtN.setPadding(0, 0, 60, 0);
            txtN.setLayoutParams(params1);
            layout2.addView(txtN);
            Log.i("informacion", "valor de textN " + txtN);


            //Botones mas cartas

            ImageButton btnMas = new ImageButton(dinamico.this);
            BotonMas[i] = btnMas;
            btnMas.setId(View.generateViewId());
            btnMas.setImageResource(R.drawable.flechamas);
            btnMas.setLayoutParams(params1);
            layout2.addView(btnMas);

            btnMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (comprobar(nCartas, ValorCartas)) {
                        int aux = Arrays.asList(BotonMas).indexOf(btnMas);
                        int aux2 = Integer.parseInt(ValorCartas[aux].getText().toString());
                        aux2 = aux2 + 1;

                        ValorCartas[aux].setText("" + aux2);
                        nCartas--;

                        CartasTotales.setText("Cartas totales " + nCartas);
                    }
                }
            });


            //TextView cartas personales

            TextView t = new TextView(dinamico.this);
            ValorCartas[i] = t;
            ValorCartas[i].setText("0");
            t.setTextSize(20f);
            t.setLayoutParams(params1);
            t.setPadding(20, 0, 20, 0);
            layout2.addView(t);

            //Boton menos cartas
            ImageButton btnMenos = new ImageButton(dinamico.this);
            BotonMenos[i] = btnMenos;
            btnMenos.setId(View.generateViewId());
            btnMenos.setImageResource(R.drawable.flechamenos);
            btnMenos.setLayoutParams(params1);
            layout2.addView(btnMenos);

            btnMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (comprobar(nCartas, ValorCartas)) {
                        int aux = Arrays.asList(BotonMenos).indexOf(btnMenos);
                        int aux2 = Integer.parseInt(ValorCartas[aux].getText().toString());

                        if (aux2!=0) {
                            aux2 = aux2 + 1;

                            ValorCartas[aux].setText("" + aux2);
                            nCartas++;

                            CartasTotales.setText(R.string.cartastotales + nCartas);
                        }
                    }
                }
            });
            layout.addView(layout2);

        }
    }

    public boolean comprobar(int cartas, TextView[] n) {


        int p = 0;
        int aux = 0;

        if (cartas > 0) {
            return true;
        } else {
            for (int i = 0; i < njugadores; i++) {
                if (Integer.parseInt(n[i].getText().toString()) > aux) {
                    aux = Integer.parseInt(n[i].getText().toString());
                    p = i + 1;
                }
            }

            String ganador = "El ganador es el jugador ";
            ganador = ganador + "" + p;

            //mostramos el mensaje de ganador
            AlertDialog.Builder builder = new AlertDialog.Builder(dinamico.this);
            builder.setTitle("Ganador");
            builder.setMessage(ganador);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dinamico.this.finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return false;

    }


    public void tamaño(TextView[] vCartas,SeekBar barra,TextView[] nombre){

        int valorBarra=barra.getProgress();

        for(int i=0;i<njugadores;i++){
            vCartas[i].setTextSize(valorBarra);
            nombre[i].setTextSize(valorBarra);
        }


    }

}