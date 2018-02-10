package okhttp3.mock.matchers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Request;
import okio.Buffer;

public class JSONBodyStringParamMatcher extends PatternMatcher {
    private final String key;

    public JSONBodyStringParamMatcher(String key, Pattern pattern) {
        super(pattern);
        this.key = key;
    }

    @Override
    protected String getText(Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            JSONObject jsonObject = new JSONObject(buffer.readUtf8());
            return jsonObject.getString(key);
        } catch (final IOException | JSONException e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return "body(" + key + "~=" + pattern.pattern() + ")";
    }

}
