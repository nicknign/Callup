package dlmj.callup.BusinessLogic.Network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Two on 15/8/20.
 */
public class CustomJsonRequest extends Request<JSONObject> {
    private Response.Listener<JSONObject> mListener;
    private Map<String, String> mParams;

    public CustomJsonRequest(String url, Map<String, String> params,
                             Response.Listener<JSONObject> responseListener,
                             Response.ErrorListener errorListener){
        this(Method.GET, url, params, responseListener, errorListener);
    }

    public CustomJsonRequest(int method, String url, Map<String, String> params,
                             Response.Listener<JSONObject> responseListener,
                             Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = responseListener;
        this.mParams = params;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        mListener.onResponse(response);
    }
}