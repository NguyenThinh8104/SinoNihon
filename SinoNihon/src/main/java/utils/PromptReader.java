package utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PromptReader {

    public static String readPrompt(String filePath) {
        // Đọc file từ resources
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
            if (is == null) {
                System.err.println("❌ Lỗi: Không tìm thấy file tại " + filePath);
                return "";
            }

            // Đọc toàn bộ nội dung với định dạng UTF-8 để không lỗi font Tiếng Việt
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}