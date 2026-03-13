package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeUtils {
    public static String extractVideoId(String youyubeUrl){
        if (youyubeUrl == null || youyubeUrl.trim().isEmpty()) return null;

        String pattern = "(?<=watch\\\\?v=|/videos/|embed\\\\/|youtu.be\\\\/|\\\\/v\\\\/|\\\\/e\\\\/|watch\\\\?v%3D|watch\\\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\\u200C\\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\\\&\\\\?\\\\n]*";
        Pattern compliedPattern = Pattern.compile(pattern);
        Matcher matcher = compliedPattern.matcher(youyubeUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
