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
package priv.seventeen.artist.arcartx.authview.view

import fr.xephi.authme.api.v3.AuthMeApi
import org.bukkit.entity.Player
import priv.seventeen.artist.arcartx.api.ArcartXAPI
import priv.seventeen.artist.arcartx.authview.ArcartXAuthView.language
import priv.seventeen.artist.arcartx.authview.ArcartXAuthView.setting
import priv.seventeen.artist.arcartx.core.ui.adapter.ArcartXUI
import priv.seventeen.artist.arcartx.core.ui.adapter.CallBackType
import taboolib.platform.util.bukkitPlugin
import java.io.File
import java.util.*

object ViewHandler {

    const val VIEW_ID = "ArcartXAuthView:Main"

    // 注册UI回调（或者使用自定义包事件监听器实现也可）
    fun registerView(){
        // 写出UI文件
        bukkitPlugin.saveResource("view.yml", false)
        // 注册UI并注册回调
        // 由于这里是直接给UI注册的回调，和CustomPacketEvent的实现方式不同，直接注册回调的情况下，客户端中该UI中发起的发包只会被这个回调处理，不会产生identifier的冲突
        // 如果使用CustomPacketEvent的方式，则需要注意identifier尽可能独特避免和其它插件冲突
        ArcartXAPI.getUIRegistry().register(VIEW_ID, File(bukkitPlugin.dataFolder, "view.yml")).let { ui: ArcartXUI ->
            ui.registerCallBack(CallBackType.PACKET) {
                val identifier: String = it.identifier.lowercase(Locale.ROOT)
                val data: List<String> = it.data
                val player: Player = it.player
                when (identifier) {
                    "login" -> handleLogin(ui, player, data)
                    "register" -> handleRegister(ui, player, data)
                    "change" -> handleChangePassword(ui, player, data)
                }
            }
        }
    }

    // 重载UI和语言文件
    fun reload(){
        language.reload()
        setting.reload()
        ArcartXAPI.getUIRegistry().reload(VIEW_ID, File(bukkitPlugin.dataFolder, "view.yml"))
    }


    // 处理登录
    private fun handleLogin(ui: ArcartXUI, player: Player, data: List<String>) {
        if (isDataEmpty(data, 1)) {
            sendMessage(ui, player, language.inputIncomplete)
            return
        }

        val pass = AuthMeApi.getInstance().checkPassword(player.name, data[0])
        if (!pass) {
            player.kickPlayer(language.passwordError.replace("&", "§"))
        } else {
            sendMessageAndClose(ui, player, language.passwordCorrect)
            AuthMeApi.getInstance().forceLogin(player)
        }
    }

    // 处理注册
    private fun handleRegister(ui: ArcartXUI, player: Player, data: List<String>) {
        if (AuthMeApi.getInstance().isRegistered(player.name)) {
            sendMessageAndClose(ui, player, language.registeredAgain)
            return
        }

        if (isDataEmpty(data, 2)) {
            sendMessage(ui, player, language.inputIncomplete)
            return
        }

        if (passwordsNotMatch(data[0], data[1])) {
            sendMessage(ui, player, language.passwordInconsistent)
            return
        }

        AuthMeApi.getInstance().registerPlayer(player.name, data[0])
        AuthMeApi.getInstance().forceLogin(player)
        sendMessageAndClose(ui, player, language.registerSuccess)
    }

    // 处理改密
    private fun handleChangePassword(ui: ArcartXUI, player: Player, data: List<String>) {
        if (isDataEmpty(data, 3)) {
            sendMessage(ui, player, language.inputIncomplete)
            return
        }

        val oldPassword = data[0]
        if (!AuthMeApi.getInstance().checkPassword(player.name, oldPassword)) {
            sendMessage(ui, player, language.originalPasswordError)
            return
        }

        if (passwordsNotMatch(data[1], data[2])) {
            sendMessage(ui, player, language.passwordInconsistent)
            return
        }

        AuthMeApi.getInstance().changePassword(player.name, data[1])
        sendMessageAndClose(ui, player, language.changePasswordSuccess)
    }


    // 检查数据是否为空
    private fun isDataEmpty(data: List<String>?, requiredSize: Int): Boolean {
        return data == null || data.size < requiredSize || data.stream().anyMatch { obj: String -> obj.isEmpty() }
    }

    // 检查密码是否不匹配
    private fun passwordsNotMatch(pass1: String, pass2: String): Boolean {
        return pass1 != pass2
    }

    // 发送信息
    private fun sendMessage(ui: ArcartXUI, player: Player, message: String) {
        ui.sendPacket(player, "result", message)
    }

    // 发送信息并关闭
    private fun sendMessageAndClose(ui: ArcartXUI, player: Player, message: String) {
        ui.sendPacket(player, "result", message)
        ui.sendPacket(player, "close", "")
    }

}