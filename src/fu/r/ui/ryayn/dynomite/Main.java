package fu.r.ui.ryayn.dynomite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import fu.r.ui.ryayn.dynomite.DownloadAndUnzip;
import fu.r.ui.ryayn.dynomite.VersionCheck;

public class Main {

    public static void main(String[] args) {
        VersionCheck.check();
        JFrame frame = new JFrame("Augustus DYNOMITE addon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel versionLabel = new JLabel("DYNOMITE ADDON. Version: " + getVersion());
        versionLabel.setBounds(10, 10, 300, 25);
        panel.add(versionLabel);

        JButton extractButton = new JButton("INJECT");
        extractButton.setBounds(10, 40, 100, 25);
        panel.add(extractButton);

        extractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DownloadAndUnzip.start();
                JOptionPane.showMessageDialog(panel, "Successfuly injected!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private static String getVersion() {
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
