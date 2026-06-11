import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class VoiceJarvis {
    public static void main(String[] args) {
        try {
            Configuration configuration = new Configuration();

            // Acoustic model
            configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
            // Dictionary
            configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
            // Language model
            configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
            recognizer.startRecognition(true);

            System.out.println("Voice-enabled Jarvis is listening...");

            SpeechResult result;
            while ((result = recognizer.getResult()) != null) {
                String command = result.getHypothesis();
                System.out.println("You said: " + command);

                if (command.contains("hello")) {
                    System.out.println("Hello Raviraj!");
                } else if (command.contains("youtube")) {
                    System.out.println("Opening YouTube...");
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://www.youtube.com");
                } else if (command.contains("exit")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Command not recognized.");
                }
            }

            recognizer.stopRecognition();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
