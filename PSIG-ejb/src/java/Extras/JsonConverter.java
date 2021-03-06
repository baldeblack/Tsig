package Extras;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonConverter {

    public JsonConverter(){}

    public JSONArray convertToJSON(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }
    
    
    
}
