package servicios;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entidades.CambioMoneda;

public class CambioMonedaServicio {

    public static List<CambioMoneda> getDatos(String nombreArchivo) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
        try {
            Stream<String> lineas = Files.lines(Paths.get(nombreArchivo));
            return lineas.skip(1)
                    .map(linea -> linea.split(","))
                    .map(textos -> new CambioMoneda(textos[0],
                            LocalDate.parse(textos[1], formatoFecha),
                            Double.parseDouble(textos[2])))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            System.out.println(ex);
            return Collections.emptyList();
        }
    }

    public static List<String> getMonedas(List<CambioMoneda> datos) {
        return datos.stream()
                .map(CambioMoneda::getMoneda)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<CambioMoneda> filtrarCambioMonedas(String moneda,
            LocalDate desde, LocalDate hasta,
            List<CambioMoneda> datos) {
        return datos.stream()
                .filter(cambio -> cambio.getMoneda().equals(moneda)
                        && !(cambio.getFecha().isBefore(desde) || cambio.getFecha().isAfter(hasta)))
                .collect(Collectors.toList());
    }

    public static List<LocalDate> getFechas(List<CambioMoneda> datos) {
        return datos.stream()
                .sorted(Comparator.comparing(CambioMoneda::getFecha))
                .map(CambioMoneda::getFecha)
                .collect(Collectors.toList());
    }

    public static List<Double> getCambios(List<CambioMoneda> datos) {
        return datos.stream()
                .sorted(Comparator.comparing(CambioMoneda::getFecha))
                .map(CambioMoneda::getCambio)
                .collect(Collectors.toList());
    }

}
