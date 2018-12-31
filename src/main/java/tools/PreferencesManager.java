package tools;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
//    public static void main(String [] args) {
//    
//        PreferencesManager.getInstance().removePreference("firstUsage");
//        try {
//            System.out.println(PreferencesManager.getInstance().getPreference("firstUsage", Boolean.class));
//        } catch (UnsupportedTypeException ex) {
//            Logger.getLogger(PreferencesManager.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(PreferencesManager.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IncorrectKeyException ex) {
//            Logger.getLogger(PreferencesManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }

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
//        @Override
//        public String toString() {
//            try {
//                String propFileName = "labels_" + PropertiesGetter.getValue("application", "language");
//                return PropertiesGetter.getValue(propFileName, "unsupportedTypeException");
//            } catch (IOException e) {
//                return "UnsupportedTypeException{}";
//            }
//        }
    }

    public class IncorrectKeyException extends Throwable {
//        @Override
//        public String toString() {
//            try {
//                String propFileName = "labels_" + PropertiesGetter.getValue("application", "language");
//                return PropertiesGetter.getValue(propFileName, "incorrectKeyException");
//            } catch (IOException e) {
//                return "IncorrectKeyException{}";
//            }
//        }
    }
}
