package tools;

import java.io.IOException;
import java.util.prefs.Preferences;

public final class PreferencesManager {

    private Preferences prefs;

    private static volatile PreferencesManager instance;

    private PreferencesManager() {}

    public static PreferencesManager getInstance() {
        if (instance == null) {
            synchronized (PreferencesManager.class) {
                if (instance == null) {
                    instance = new PreferencesManager();
                }
            }
        }
        return instance;
    }

    public <T> T getPreference(String key, Class<T> type) throws UnsupportedTypeException, IOException, IncorrectKeyException {
        String defaultValue = PropertiesGetter.getValue("application", key);
        if (defaultValue == null) {
            throw new IncorrectKeyException();
        }

        prefs = Preferences.userRoot().node(this.getClass().getName());

        if (type.isInstance(new Boolean("true"))){
            Boolean out = prefs.getBoolean(key, Boolean.parseBoolean(defaultValue));
            return type.cast(out);
        } else if (type.isInstance("stringObject")) {
            String out = prefs.get(key, defaultValue);
            return type.cast(out);
        } else if (type.isInstance(5)) {
            Integer out = prefs.getInt(key, Integer.parseInt(defaultValue));
            return type.cast(out);
        }

        throw new UnsupportedTypeException();
    }

    public <T> void setPreference(String key, T value, Class<T> type) throws UnsupportedTypeException {
        prefs = Preferences.userRoot().node(this.getClass().getName());

        if (type.isInstance(new Boolean("true"))) {
            prefs.putBoolean(key, Boolean.parseBoolean(value.toString()));
        } else if (type.isInstance("stringObject")) {
            prefs.put(key, value.toString());
        } else if (type.isInstance(5)) {
            prefs.putInt(key, Integer.parseInt(value.toString()));
        } else {
            throw new UnsupportedTypeException();
        }

    }

    public <T> void removePreference(String key) {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        prefs.remove(key);
    }


//USAGE EXAMPLE//

//    public static void main(String[] args) {
//        try {
//            tools.PreferencesManager.getInstance().setPreference("test", "zmiana", String.class);
//            String testString = tools.PreferencesManager.getInstance().getPreference("test", String.class);
//            System.out.println(testString);
//
//            tools.PreferencesManager.getInstance().removePreference("test");
//            testString = tools.PreferencesManager.getInstance().getPreference("test", String.class);
//            System.out.println(testString);
//        } catch (UnsupportedTypeException | IOException | IncorrectKeyException e) {
//            e.printStackTrace();
//        }
//    }

    public class UnsupportedTypeException extends Throwable {
    }

    public class IncorrectKeyException extends Throwable {
    }
}
