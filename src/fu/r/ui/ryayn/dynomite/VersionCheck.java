package fu.r.ui.ryayn.dynomite;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class VersionCheck {

    public static String check() {
        try {
            URL url = new URL("https://pastebin.com/raw/ZXSM9sk6");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                return content.toString();
            } else {
                return "Ошибка получения версии";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка: " + e.getMessage();
        }
    }
}
