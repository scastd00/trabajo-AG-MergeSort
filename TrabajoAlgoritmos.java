import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TrabajoAlgoritmos {
	private static final String codPostalEspania = "cp-espania.txt";
	private static final String codPostalUS = "uszips.txt";
	private static final String grande = "500000sales.txt";
	private static final String muyGrande = "millonDatos.txt";

	public static void main(String[] args) {
		int numEjecuciones = 1;
		long contadorMS = 0L;

		for (int i = 0; i < numEjecuciones; i++) {
			try {
				File archivo = new File(muyGrande);
				FileInputStream in = new FileInputStream(archivo.getCanonicalPath());
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String entrada = br.readLine();
				ArrayList<Integer> lista = new ArrayList<>();

				while (entrada != null) {
					lista.add(Integer.parseInt(entrada));
					entrada = br.readLine();
				}

				Integer[] arr = lista.toArray(new Integer[0]);

				long inicio = System.currentTimeMillis(); // Comienzo del tiempo
				sort(arr, 0, arr.length - 1);
				long fin = System.currentTimeMillis(); // Fin del tiempo

				long duration = (fin - inicio);
				contadorMS += duration;

				br.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Tiempo medio en " + numEjecuciones + " ejecuciones: " +
				(contadorMS / numEjecuciones) + "ms");
	}

	/*
	Procesador: Intel i7-9750H 2,6 GHz (4 GHz Max)

	Tiempos: 500 ejecuciones en Windows
		- PostalEspaña -> 2ms
		- Postal EE.UU -> 4ms
		- 500k -> 147ms
		- 1M -> 993ms

	Tiempos: 500 ejecuciones en Ubuntu
		- PostalEspaña -> 2ms
		- Postal EE.UU -> 4ms
		- 500k -> 92ms
		- 1M -> 645ms
	 */

	/*
	 Combina 2 vectores de arr
	 Primer vector es arr[left .. middle]
	 Segundo vector es arr[middle + 1 .. right]
	*/
	private static void merge(Integer[] arr, int left, int middle, int right) {
		// Cálculo de los tamaños de los vectores
		int n1 = middle - left + 1;
		int n2 = right - middle;

		/* Vectores auxiliares */
		int[] leftAux = new int[n1];
		int[] rightAux = new int[n2];

		/* Se copia la información en los vectores auxiliares, porque se va a actualizar el
		vector que se pasa como parámetro */
		for (int i = 0; i < n1; ++i) {
			leftAux[i] = arr[left + i];
		}

		for (int j = 0; j < n2; ++j) {
			rightAux[j] = arr[middle + 1 + j];
		}

		// Comienzo de la combinación de los vectores.
		int i = 0, j = 0, k = left;

		while (i < n1 && j < n2) {
			if (leftAux[i] <= rightAux[j]) {
				arr[k] = leftAux[i];
				i++;
			} else {
				arr[k] = rightAux[j];
				j++;
			}
			k++;
		}

		/* Copiar los elementos restantes (si hay) en caso de que los del otro vector se hayan acabado */
		while (i < n1) {
			arr[k] = leftAux[i];
			i++;
			k++;
		}

		while (j < n2) {
			arr[k] = rightAux[j];
			j++;
			k++;
		}
	}

	// Función para ordenar los vectores. No devuelve nada, porque los cambios se realizan sobre
	// el vector arr.
	private static void sort(Integer[] arr, int left, int right) {
		if (left < right) {
			// Punto medio
			int m = (left + right) / 2;

			// Ordenar las 2 mitades
			sort(arr, left, m);
			sort(arr, m + 1, right);

			// Combinar las mitades ordenadas.
			merge(arr, left, m, right);
		}
	}
}
