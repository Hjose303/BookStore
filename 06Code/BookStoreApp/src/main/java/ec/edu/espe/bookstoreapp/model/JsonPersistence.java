package ec.edu.espe.bookstoreapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonPersistence {

    private static final Path DATA_DIRECTORY = Path.of("data");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
        @Override
        public void write(JsonWriter out, LocalDate date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(date.toString());
            }
        }

        @Override
        public LocalDate read(
                JsonReader in
        ) throws IOException {
            if (in.peek()
                    == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            return LocalDate.parse(in.nextString());
        }
    })
            .create();

    public static <T> void saveList(
            String fileName,
            List<T> data
    ) {
        try {
            Files.createDirectories(DATA_DIRECTORY);

            Path file = DATA_DIRECTORY.resolve(fileName);

            try (Writer writer = Files.newBufferedWriter(file)) {
                GSON.toJson(data, writer);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error al guardar " + fileName, e);
        }
    }

    public static <T> ArrayList<T> loadList(
            String fileName,
            TypeToken<ArrayList<T>> typeToken
    ) {
        Path file = DATA_DIRECTORY.resolve(fileName);

        if (!Files.exists(file)) {
            return new ArrayList<>();
        }

        try (Reader reader = Files.newBufferedReader(file)) {
            Type type = typeToken.getType();

            ArrayList<T> data = GSON.fromJson(reader, type);

            return data == null ? new ArrayList<>() : data;

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(
                    "Error al cargar " + fileName, e);
        }
    }
}
