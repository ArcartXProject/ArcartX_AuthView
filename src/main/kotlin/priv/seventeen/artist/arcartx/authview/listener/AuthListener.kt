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
package priv.seventeen.artist.arcartx.authview.listener

import fr.xephi.authme.api.v3.AuthMeApi
import priv.seventeen.artist.arcartx.api.ArcartXAPI
import priv.seventeen.artist.arcartx.authview.ArcartXAuthView.language
import priv.seventeen.artist.arcartx.authview.ArcartXAuthView.setting
import priv.seventeen.artist.arcartx.authview.view.ViewHandler.VIEW_ID
import priv.seventeen.artist.arcartx.event.client.ClientExtraSlotClickEvent
import priv.seventeen.artist.arcartx.event.client.ClientInitializedEvent
import taboolib.common.platform.event.SubscribeEvent


@SubscribeEvent
fun onPlayerClientLoaded(event: ClientInitializedEvent.End) {
    if(!setting.enable){
        return
    }
    if (AuthMeApi.getInstance().isRegistered(event.player.name)) {
        ArcartXAPI.getUIRegistry().open(event.player, VIEW_ID) {
            // 当ui打开后发起回调
            ArcartXAPI.getUIRegistry().sendPacket(event.player, VIEW_ID, "init", mapOf(
                "type" to "login",
                "title" to language.loginTitle
            ))
        }

    } else {
        ArcartXAPI.getUIRegistry().open(event.player, VIEW_ID) {
            // 当ui打开后发起回调
            ArcartXAPI.getUIRegistry().sendPacket(event.player, VIEW_ID, "init", mapOf(
                "type" to "register",
                "title" to language.registerTitle
            ))
        }
    }
}

@SubscribeEvent
fun onPlayerClickSlot(event: ClientExtraSlotClickEvent){
    if(!AuthMeApi.getInstance().isAuthenticated(event.player)){
        event.isCancelled = true
    }
}