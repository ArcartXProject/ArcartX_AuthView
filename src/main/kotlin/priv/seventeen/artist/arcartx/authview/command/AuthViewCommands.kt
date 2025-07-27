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
package priv.seventeen.artist.arcartx.authview.command

import org.bukkit.entity.Player
import priv.seventeen.artist.arcartx.api.ArcartXAPI
import priv.seventeen.artist.arcartx.authview.ArcartXAuthView.language
import priv.seventeen.artist.arcartx.authview.view.ViewHandler.VIEW_ID
import priv.seventeen.artist.arcartx.authview.view.ViewHandler.reload
import priv.seventeen.artist.arcartx.internal.command.CommandContext
import priv.seventeen.artist.arcartx.internal.command.annotation.CommandHandler
import priv.seventeen.artist.arcartx.internal.command.annotation.SenderType
import priv.seventeen.artist.arcartx.internal.command.executor.MultiCommandExecutor
import priv.seventeen.artist.arcartx.internal.command.executor.SingleCommandExecutor

class ReloadCommand : SingleCommandExecutor() {
    override val name = "reload"
    override val permission = "aav.command.admin.reload"
    override val only = SenderType.OP
    override val description = "重载插件"
    override val argsDescription: Array<String> = arrayOf()

    override fun execute(context: CommandContext) {
        context.sendMessage("重载中...")
        reload()
        context.sendMessage("重载完成")
    }
}

class OpenCommand : MultiCommandExecutor() {
    override val name = "open"

    @CommandHandler(
        command = "register",
        description = "打开注册界面",
        permission = "aav.command.open",
        args = [],
        senderType = SenderType.PLAYER
    )
    fun openRegister(context: CommandContext) {
        val player = context.getSender()as Player
        ArcartXAPI.getUIRegistry().open(player, VIEW_ID) {
            // 当ui打开后发起回调
            ArcartXAPI.getUIRegistry().sendPacket(player, VIEW_ID, "init", mapOf(
                "type" to "register",
                "title" to language.registerTitle
            ))
        }
    }

    @CommandHandler(
        command = "login",
        description = "打开登录界面",
        permission = "aav.command.open",
        args = [],
        senderType = SenderType.PLAYER
    )
    fun openLogin(context: CommandContext) {
        val player = context.getSender()as Player
        ArcartXAPI.getUIRegistry().open(player, VIEW_ID) {
            // 当ui打开后发起回调
            ArcartXAPI.getUIRegistry().sendPacket(player, VIEW_ID, "init", mapOf(
                "type" to "login",
                "title" to language.loginTitle
            ))
        }
    }

    @CommandHandler(
        command = "changePassword",
        description = "打开修改密码界面",
        permission = "aav.command.open",
        args = [],
        senderType = SenderType.PLAYER
    )
    fun openChangePassword(context: CommandContext) {
        val player = context.getSender()as Player
        ArcartXAPI.getUIRegistry().open(player, VIEW_ID) {
            // 当ui打开后发起回调
            ArcartXAPI.getUIRegistry().sendPacket(player, VIEW_ID, "init", mapOf(
                "type" to "change",
                "title" to language.changePasswordTitle
            ))
        }
    }
}