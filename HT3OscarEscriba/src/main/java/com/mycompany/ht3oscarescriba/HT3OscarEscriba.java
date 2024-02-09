/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.ht3oscarescriba;

/**
 *
 * @author Oscar Escriba
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
public class HT3OscarEscriba {

    public static void main(String[] args) {
        // Ruta del archivo donde se guardarán los números
        String rutaArchivo = "numeros_generados.txt";

        // Número máximo de elementos a generar
        int maxElementos = 3000;

        generarYGuardarNumeros(rutaArchivo, maxElementos);

        System.out.println("Números generados y guardados en el archivo: " + rutaArchivo);
    }

    static void generarYGuardarNumeros(String rutaArchivo, int maxElementos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (int i = 0; i < maxElementos; i++) {
                String numero = RandomStringUtils.randomNumeric(5); // Genera un número entero aleatorio de 5 dígitos
                writer.write(numero);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
