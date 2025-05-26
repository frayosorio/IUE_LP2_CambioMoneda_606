package servicios;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

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

    public static TimeSeriesCollection getDatosGrafica(List<LocalDate> fechas,
            List<Double> cambios, String titulo) {
        var serie = new TimeSeries(titulo);
        IntStream.range(0, fechas.size())
                .forEach((i) -> {
                    var fecha = fechas.get(i);
                    serie.add(new Day(fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear()),
                            cambios.get(i));
                });
        var datosGrafica = new TimeSeriesCollection();
        datosGrafica.addSeries(serie);
        return datosGrafica;
    }

    public static JFreeChart getGrafica(TimeSeriesCollection datosGrafica,
            String titulo) {
        return ChartFactory.createTimeSeriesChart(titulo, "Fechas",
                "Cambio por USD", datosGrafica);
    }

    public static void mostrarGrafica(JPanel pnl, JFreeChart grafica) {
        pnl.removeAll();
        var pnlGrafica = new ChartPanel(grafica);
        pnlGrafica.setPreferredSize(new Dimension(pnl.getWidth(), pnl.getHeight()));
        pnlGrafica.setMouseWheelEnabled(true);
        pnl.setLayout(new BorderLayout());
        pnl.add(pnlGrafica, BorderLayout.CENTER);
        pnl.validate();
    }

}
