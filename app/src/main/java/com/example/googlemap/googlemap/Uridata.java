package com.example.googlemap.googlemap;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by SCITMASTER on 2018-03-08.
 */

public class Uridata  implements Serializable {
    Uri uri;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uridata(Uri uri) {

        this.uri = uri;
    }
}
