package com.yunge.myretrofitmvvm.java.model

import org.litepal.crud.LitePalSupport

class DataChrome(var class_id: String,var utag: String,var img_1024_768: String): LitePalSupport() {
    @Transient val id: Long = 0
}
