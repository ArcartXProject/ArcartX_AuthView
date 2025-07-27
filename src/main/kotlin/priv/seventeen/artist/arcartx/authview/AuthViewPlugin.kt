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
package priv.seventeen.artist.arcartx.authview

import priv.seventeen.artist.arcartx.authview.command.OpenCommand
import priv.seventeen.artist.arcartx.authview.command.ReloadCommand
import priv.seventeen.artist.arcartx.authview.config.Language
import priv.seventeen.artist.arcartx.authview.view.ViewHandler.registerView
import priv.seventeen.artist.arcartx.internal.command.AXCommand
import priv.seventeen.artist.arcartx.internal.command.AXCommandContainer
import priv.seventeen.artist.arcartx.internal.message.ArcartXSender.Companion.printStart
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.platform.util.bukkitPlugin

object ArcartXAuthView : Plugin() {

    lateinit var language: Language

    override fun onEnable() {
        bukkitPlugin.printStart(0)
        language = Language()
        // 注册UI
        registerView()
        // 注册命令
        AXCommandContainer(bukkitPlugin).register(AXCommand("arcartxAuthView","aav").apply {
            registerSubCommand(OpenCommand())
            registerSubCommand(ReloadCommand())
        })
    }
}