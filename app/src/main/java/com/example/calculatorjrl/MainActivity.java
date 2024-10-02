package com.example.calculatorjrl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Declaramos variables globales que se van a usar en el código del programa
    private TextView textViewResult; // Campo de texto para mostrar resultados
    private double firstNumber = 0; // Almacena el primer número de la operación
    private double secondNumber = 0; // Almacena el segundo número de la operación
    private String operator = ""; // Almacena el operador seleccionado
    // Valor booleano para saber si es una nueva operación o no
    private boolean newOperation = true; // Inicialmente, es una nueva operación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtenemos el campo de texto de la aplicación y lo buscamos por id
        textViewResult = findViewById(R.id.textViewResult);

        // Llamamos a los dos métodos que realizan la funcionalidad de la aplicación
        // y las acciones de los botones
        setNumberButtonListeners(); // Configura los botones numéricos
        setOperatorButtonListeners(); // Configura los botones de operación
    }

    /**
     * Configura los listeners para los botones numéricos
     */
    private void setNumberButtonListeners() {
        // Metemos en un array de int los id de los botones numéricos
        int[] numberIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9
        };

        // Añadimos un listener a la vista
        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            // Método que se llama cuando se clickea un botón
            public void onClick(View v) {
                // Creamos un botón y lo instanciamos a la vista
                Button button = (Button) v;
                // Pasamos el texto del botón a String
                String buttonText = button.getText().toString();

                // En el caso de ser una nueva operación
                if (newOperation) {
                    // Se establece el texto del textView con el del botón presionado
                    textViewResult.setText(buttonText);
                    // Al introducir el primer número, ya no sería una nueva operación
                    newOperation = false; // Cambia a false
                } else {
                    // Concatenamos el resto de números al texto existente
                    textViewResult.append(buttonText);
                }
            }
        };

        // Recorremos con un for-each los ids de los botones numéricos
        for (int id : numberIds) {
            // Obtenemos los botones y les asignamos el listener para el clic
            findViewById(id).setOnClickListener(numberClickListener);
        }
    }

    /**
     * Configura los listeners para los botones de operadores y funciones
     */
    private void setOperatorButtonListeners() {
        // Listener común para los operadores
        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v; // Obtiene el botón que fue clickeado
                firstNumber = Double.parseDouble(textViewResult.getText().toString()); // Obtiene el primer número
                operator = button.getText().toString(); // El operador se toma directamente del texto del botón
                newOperation = true; // Se establece que es una nueva operación
            }
        };

        // Asignar listener a cada operador
        findViewById(R.id.buttonAdd).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonSub).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonMul).setOnClickListener(operatorClickListener);
        findViewById(R.id.buttonDiv).setOnClickListener(operatorClickListener);

        // Listener para el botón igual
        findViewById(R.id.buttonEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondNumber = Double.parseDouble(textViewResult.getText().toString()); // Obtiene el segundo número
                double result = 0; // Variable para almacenar el resultado
                switch (operator) { // Dependiendo del operador
                    case "+":
                        result = firstNumber + secondNumber; // Suma
                        break;
                    case "-":
                        result = firstNumber - secondNumber; // Resta
                        break;
                    case "*":
                        result = firstNumber * secondNumber; // Multiplicación
                        break;
                    case "/":
                        if (secondNumber != 0) { // Comprobación de división por cero
                            result = firstNumber / secondNumber; // División
                        } else {
                            textViewResult.setText("Error"); // Mensaje de error
                            newOperation = true; // Establecer como nueva operación
                            return; // Salir del método
                        }
                        break;
                }
                textViewResult.setText(String.valueOf(result)); // Mostrar el resultado
                newOperation = true; // Establecer como nueva operación
            }
        });

        // Listener para el botón de borrar
        findViewById(R.id.buttonC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reiniciar la calculadora
                textViewResult.setText("0"); // Reiniciar el display
                firstNumber = 0; // Reiniciar el primer número
                secondNumber = 0; // Reiniciar el segundo número
                operator = ""; // Reiniciar el operador
                newOperation = true; // Establecer como nueva operación
            }
        });
    }
}
