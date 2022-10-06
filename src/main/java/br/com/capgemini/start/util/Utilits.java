package br.com.capgemini.start.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Utilits {
	private Utilits() {}

	public static String mensagemDataHora(LocalDateTime dataHora) {
		LocalDateTime agora = LocalDateTime.now();

		long meses = ChronoUnit.MONTHS.between(dataHora, agora);
		if (meses == 1) {
			return "1 mês";
		}
		if (meses > 1) {
			return meses + " meses";
		}

		long dias = ChronoUnit.DAYS.between(dataHora, agora);
		if (dias == 1) {
			return "1 dias";
		}
		if (dias > 1) {
			return dias + " dias";
		}

		long horas = ChronoUnit.HOURS.between(dataHora, agora);
		if (horas == 1) {
			return "1 hora";
		}
		if (horas > 1) {
			return horas + " horas";
		}

		long minutos = ChronoUnit.MINUTES.between(dataHora, agora);
		if (minutos == 0) {
			return "agora";
		}
		if (minutos == 1) {
			return "1 minuto";
		}

		return minutos + " minutos";
	}
}
