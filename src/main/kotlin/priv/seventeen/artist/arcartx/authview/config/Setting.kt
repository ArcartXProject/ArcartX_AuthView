package priv.seventeen.artist.arcartx.authview.config

import com.google.gson.annotations.SerializedName
import priv.seventeen.artist.arcartx.internal.config.ArcartXConfig
import taboolib.platform.util.bukkitPlugin

class Setting : ArcartXConfig(bukkitPlugin,"enable") {

    @SerializedName("enableView")
    var enable: Boolean = true
}