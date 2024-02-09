/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ht3oscarescriba;

/**
 *
 * @author Oscar Escriba
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.StopWatch;
public class OrdenarNumeros { 
    
     public static void main(String[] args) {
        int[] tamaños = {100, 500, 1000, 1500, 2000, 2500, 3000};

        for (int tamaño : tamaños) {
            int[] numeros = leerNumerosDesdeArchivo("numeros_generados.txt", tamaño);

            System.out.println("\nOrdenando " + tamaño + " números:");

            // Gnome Sort
            ejecutarYMedirTiempo(() -> gnomeSort(numeros.clone()), "Gnome Sort");

            // Merge Sort
            ejecutarYMedirTiempo(() -> mergeSort(numeros.clone()), "Merge Sort");

            // Quick Sort
            ejecutarYMedirTiempo(() -> quickSort(numeros.clone()), "Quick Sort");

            // Radix Sort
            ejecutarYMedirTiempo(() -> radixSort(numeros.clone()), "Radix Sort");
        }
    }

    static int[] leerNumerosDesdeArchivo(String rutaArchivo, int tamaño) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            String[] numerosString = contenido.split("\\s+");
            int[] numeros = Arrays.stream(numerosString)
                    .limit(tamaño)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            return numeros;
        } catch (IOException e) {
            e.printStackTrace();
            return new int[0];
        }
    }

    static void ejecutarYMedirTiempo(Runnable runnable, String nombreAlgoritmo) {
        StopWatch stopWatch = StopWatch.createStarted();
        runnable.run();
        stopWatch.stop();
        System.out.println(nombreAlgoritmo + ": " + stopWatch.getTime() + " ms");
    }

    static void gnomeSort(int[] arr) {
        int index = 0;
        while (index < arr.length) {
            if (index == 0 || arr[index] >= arr[index - 1]) {
                index++;
            } else {
                ArrayUtils.swap(arr, index, index - 1);
                index--;
            }
        }
    }

    static void mergeSort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);

            merge(arr, left, middle, right);
        }
    }

    static void merge(int[] arr, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; ++i)
            leftArray[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            rightArray[j] = arr[middle + 1 + j];

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(arr, low, high);

            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                ArrayUtils.swap(arr, i, j);
            }
        }

        ArrayUtils.swap(arr, i + 1, high);
        return i + 1;
    }

    static void radixSort(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }

    static void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        Arrays.fill(count, 0);

        for (int i = 0; i < n; i++) {
            count[(arr[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }
}
