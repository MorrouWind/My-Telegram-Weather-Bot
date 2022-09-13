import com.google.common.net.UrlEscapers;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    public static String getWeather(String message, Model model) throws IOException {
        //191f78a8ca631cc01fe3dd0eb34f8534 - my Telegram_Bot_Maven key
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=6fff53a641b9b9a799cfd6b079f5cd4e");
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray jsonArray = object.getJSONArray("weather");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject anyObject = jsonArray.getJSONObject(i);
            model.setIcon((String) anyObject.get("icon"));
            model.setMain((String) anyObject.get("main"));
        }

        return "City: " + model.getName() + "\n"
                + "Temperature: " + model.getTemp() + "C°" + "\n"
                +  "Humidity: " + model.getHumidity() + "%" + "\n"
                + "Main" + model.getMain() + "\n"
                + "http://openweathermap.org/img/wn/" + model.getIcon() + "@2x.png";///10d@2x.png
    }

}
