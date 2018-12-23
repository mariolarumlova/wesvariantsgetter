import model.Config;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlParser {

    public static void main(String[] args) {
        try {
            Config config = read("/config.yaml");
            write(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config read(String path) throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream in = YamlParser.class
                .getResourceAsStream(path)) {
            return yaml.loadAs(in, Config.class);
        }
    }

    public static void write(Config config) {
        Map<String, Map<String, Object>> map = config.getAsMap();

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);
        String output = yaml.dump(map);
        System.out.println(output);
    }
}