package com.scotiabankcolpatria.hiring;

import java.util.Arrays;

public class CreditRiskAssessment {
	
	/**
	 * Realiza el calculo de la desviacion estandar
	 * @author Albeiro Silva
	 * @param paymentDelays arreglo de enteros con el numero de dias de retraso en los pagos
	 * @return double
	 */
	public double standardDeviation(int[] paymentDelays) {

		double media = Arrays.stream(paymentDelays).average().getAsDouble();
		double datosRaiz = Arrays.stream(paymentDelays)
				.mapToDouble(e -> Math.pow(e - media, 2))
				.average()
				.getAsDouble();

		return Math.sqrt(datosRaiz);

	}

	/**
	 * Encuentra el indice del pago anomalo con el pico mas alto
	 * @author Albeiro Silva
	 * @param paymentDelays arreglo de enteros con el numero de dias de retraso en los pagos
	 * @return int
	 */
	public int paymentDelayMaxPeakIndex(int[] paymentDelays) {
		int tamanioVector = paymentDelays.length;

		int pagoAnomalo = -1;
		for (int i = 0; i < tamanioVector; i++) {
			boolean finVector = ((tamanioVector - 1) == i);
			int actual = paymentDelays[i];
			int sucesor = !finVector ? paymentDelays[i + 1] : 0;
			if (i == 0) {
				if (actual > sucesor) {
					pagoAnomalo = i;
				}
			} else {
				int antecesor = paymentDelays[i - 1];
				if (finVector) {
					if ((actual > antecesor) && (actual > 0)) {
						pagoAnomalo = i;
					}
				} else {
					if ((actual > antecesor) && (actual > sucesor)) {
						pagoAnomalo = i;
					}
				}
			}
		}
		return pagoAnomalo;
	}

	/**
	 * Realiza el calculo de la probabilidad de los pagos tardios para cada periodo de los diferentes productos
	 * @author Albeiro Silva
	 * @param paymentDelays arreglo de dos dimensiones con los pagos tardios de un producto para distintos periodos de tiempo
	 * @return double[]
	 */
	public double[] latePaymentProbabilityByPeriod(int[][] paymentDelays) {
		int tamanioColumna = paymentDelays.length;
		int tamanioFila = paymentDelays[0].length;
		int pagosAtrasados = 0;
		double[] probabilidadesPagosTardios = new double[tamanioFila];
		for (int i = 0; i < tamanioFila; i++) {
			for (int j = 0; j < tamanioColumna; j++) {
				if (paymentDelays[j][i] > 0) {
					pagosAtrasados++;
				}
				if (j == (tamanioColumna - 1)) {
					double probabilidad = (double) pagosAtrasados / tamanioColumna;
					probabilidadesPagosTardios[i] = probabilidad;
					pagosAtrasados = 0;
				}
			}
		}
		return probabilidadesPagosTardios;
	}

}
