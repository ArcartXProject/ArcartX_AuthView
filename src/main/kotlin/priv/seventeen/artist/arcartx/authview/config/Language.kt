/*
    Copyright (C) 2024-2025 17Artist

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package priv.seventeen.artist.arcartx.authview.config

import com.google.gson.annotations.SerializedName
import priv.seventeen.artist.arcartx.internal.config.ArcartXConfig
import taboolib.platform.util.bukkitPlugin

class Language: ArcartXConfig(bukkitPlugin,"language.yaml") {

    @SerializedName("loginTitle")
    var loginTitle: String = "&8欢迎回到&c小猪出海服务器&8,请登录"

    @SerializedName("registerTitle")
    var registerTitle: String = "&8欢迎加入&c小猪出海服务器&8,请注册"

    @SerializedName("changePasswordTitle")
    var changePasswordTitle: String = "&8修改密码"

    // 输入内容不完整
    @SerializedName("inputIncomplete")
    var inputIncomplete: String = "&c输入内容不完整"

    // 密码不一致
    @SerializedName("passwordInconsistent")
    var passwordInconsistent: String = "&c密码不一致"

    // 原密码错误
    @SerializedName("originalPasswordError")
    var originalPasswordError: String = "&c原密码错误"

    // 密码错误
    @SerializedName("passwordError")
    var passwordError: String = "&c密码错误"

    // 密码正确
    @SerializedName("passwordCorrect")
    var passwordCorrect: String = "&a密码正确"

    // 注册成功
    @SerializedName("registerSuccess")
    var registerSuccess: String = "&a注册成功"


    // 你已经注册过了无法再次注册
    @SerializedName("registeredAgain")
    var registeredAgain: String = "&c你已经注册过了无法再次注册"


    // 修改密码成功
    @SerializedName("changePasswordSuccess")
    var changePasswordSuccess: String = "&a修改密码成功"

    init {
        load()
    }

}