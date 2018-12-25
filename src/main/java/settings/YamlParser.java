package settings;

import model.Config;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

public class YamlParser {

    public static void main(String[] args) {
        try {
            Config config = read("/config_example.yaml");
            //TODO: How to copy files from RESOURCES folder to analysis folder?
            URL resource = YamlParser.class.getClassLoader().getResource("config.yaml");
            File file = Paths.get(resource.toURI()).toFile();
            String filePath = file.getAbsolutePath();
            write(config, filePath);//"E:\\Projects\\wesvariantsgetter\\src\\main\\resources");
        } catch (IOException | URISyntaxException e) {
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

    public static String makeYaml(Config config) {
//        TEMP
        Map<String, Object> rules = config.getRules();
        rules.put("mapper", "christmas");
        config.setRules(rules);
//        TEMP

        Map<String, Map<String, Object>> map = config.getAsMap();

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);
        return yaml.dump(map);
    }

    public static void write(Config config, String path) {
        String yamlToWrite = makeYaml(config);
            try (PrintWriter out = new PrintWriter(path)) {
                out.println(yamlToWrite);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
}