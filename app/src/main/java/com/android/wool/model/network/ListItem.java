package com.android.wool.model.network;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by AiMr on 2017/9/21.
 */
public interface ListItem extends Parcelable {
    <T extends ListItem> T newObject();
    void parsFromJson(JSONObject json) throws JSONException;
}
