import net.andreinc.mockneat.MockNeat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

import static java.nio.file.StandardOpenOption.*;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class Main {
    public static void main(String[] args) {
        final Path path = Paths.get("C:\\Users\\Kacper\\Downloads\\filtered11.csv");
        final int records = 300000;

        MockNeat m = MockNeat.threadLocal();
        System.out.println("START: " + LocalTime.now());



        m.fmt("#{MMSI},#{TSTAMP},#{LONGITUDE},#{LATITUDE},#{COG},#{SOG},#{HEADING},#{NAVSTAT},#{IMO},#{NAME},#{CALLSIGN},#{TYPE},#{A},#{B},#{C},#{D},#{DRAUGHT},#{DEST},#{ETA}")
                .param("MMSI", m.ints().range(100000000, 900000000))
                .param("TSTAMP", m.localDates())
                .param("LONGITUDE", m.doubles().range(0, 90))
                .param("LATITUDE", m.doubles().range(0, 180.0))
                .param("COG", m.doubles().range(0, 300))
                .param("SOG", m.doubles().range(0, 15))
                .param("HEADING", m.ints().range(500, 599))
                .param("NAVSTAT", m.ints().range(0, 15))
                .param("IMO", m.ints().range(0, 2))
                .param("NAME", m.names().first())
                .param("CALLSIGN", m.names().last())
                .param("TYPE", m.ints().range(61, 69))
                .param("A", m.doubles().range(0, 15))
                .param("B", m.doubles().range(0, 15))
                .param("C", m.doubles().range(0, 15))
                .param("D", m.doubles().range(0, 15))
                .param("DRAUGHT", m.doubles().range(1, 5))
                .param("DEST", m.cities().capitals())
                .param("ETA", m.localDates())
                .list(records)
                .consume(list -> {
                    try {
                        Files.write(path, list, CREATE_NEW, APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println("STOP: " + LocalTime.now());
    }
}





