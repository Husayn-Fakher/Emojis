package com.example.emojis.network

import com.example.emojis.network.models.Emoji
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

/**
 * Created By Fakher_Husayn on 04-Aug-20
 **/
class EmojiConverterFactory : JsonDeserializer<Array<Emoji>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Array<Emoji> {

        var response = json.toString()

        response = response.replace("{", "")
        response = response.replace("}", "")

        val lines = response.split(",").toTypedArray()

        val myList = Array(lines.size) {
            Emoji(
                0,
                "",
                ""
            )
        }

        var i = 0
        for (a in lines) {
            val line = a.split("\":").toTypedArray()

            val b = Emoji(
                0,
                line.get(0).replace("\"", ""),
                line.get(1).replace("\"", "")
            )

            myList[i] = b
            i++

        }

        return myList
    }
}