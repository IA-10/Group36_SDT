package com.org.augos.mysolar.network;

import org.json.JSONObject;

public interface VolleyJSONCallback {
  void onSuccessResponse(JSONObject result);
  void onError(JSONObject result);
  void onVolleyError(String error);
}