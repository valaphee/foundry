/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

package com.valaphee.foundry.util

class MdColor(
    private val shades: ColorGradient
) : Color(shades.getColor(500.0f, 50.0f, 900.0f)) {
    infix fun tone(tone: Int) = shades.getColor(tone.toFloat(), 50.0f, 900.0f)

    infix fun linearTone(tone: Int) = tone(tone).toLinear()

    companion object {
        val Red = MdColor(
            ColorGradient(
                50.0f to fromHex("FFEBEE"),
                100.0f to fromHex("FFCDD2"),
                200.0f to fromHex("EF9A9A"),
                300.0f to fromHex("E57373"),
                400.0f to fromHex("EF5350"),
                500.0f to fromHex("F44336"),
                600.0f to fromHex("E53935"),
                700.0f to fromHex("D32F2F"),
                800.0f to fromHex("C62828"),
                900.0f to fromHex("B71C1C")
            )
        )
        val Pink = MdColor(
            ColorGradient(
                50.0f to fromHex("FCE4EC"),
                100.0f to fromHex("F8BBD0"),
                200.0f to fromHex("F48FB1"),
                300.0f to fromHex("F06292"),
                400.0f to fromHex("EC407A"),
                500.0f to fromHex("E91E63"),
                600.0f to fromHex("D81B60"),
                700.0f to fromHex("C2185B"),
                800.0f to fromHex("AD1457"),
                900.0f to fromHex("880E4F")
            )
        )
        val Purple = MdColor(
            ColorGradient(
                50.0f to fromHex("F3E5F5"),
                100.0f to fromHex("E1BEE7"),
                200.0f to fromHex("CE93D8"),
                300.0f to fromHex("BA68C8"),
                400.0f to fromHex("AB47BC"),
                500.0f to fromHex("9C27B0"),
                600.0f to fromHex("8E24AA"),
                700.0f to fromHex("7B1FA2"),
                800.0f to fromHex("6A1B9A"),
                900.0f to fromHex("4A148C")
            )
        )
        val DeepPurple = MdColor(
            ColorGradient(
                50.0f to fromHex("EDE7F6"),
                100.0f to fromHex("D1C4E9"),
                200.0f to fromHex("B39DDB"),
                300.0f to fromHex("9575CD"),
                400.0f to fromHex("7E57C2"),
                500.0f to fromHex("673AB7"),
                600.0f to fromHex("5E35B1"),
                700.0f to fromHex("512DA8"),
                800.0f to fromHex("4527A0"),
                900.0f to fromHex("311B92")
            )
        )
        val Indigo = MdColor(
            ColorGradient(
                50.0f to fromHex("E8EAF6"),
                100.0f to fromHex("C5CAE9"),
                200.0f to fromHex("9FA8DA"),
                300.0f to fromHex("7986CB"),
                400.0f to fromHex("5C6BC0"),
                500.0f to fromHex("3F51B5"),
                600.0f to fromHex("3949AB"),
                700.0f to fromHex("303F9F"),
                800.0f to fromHex("283593"),
                900.0f to fromHex("1A237E"),
            )
        )
        val Blue = MdColor(
            ColorGradient(
                50.0f to fromHex("E3F2FD"),
                100.0f to fromHex("BBDEFB"),
                200.0f to fromHex("90CAF9"),
                300.0f to fromHex("64B5F6"),
                400.0f to fromHex("42A5F5"),
                500.0f to fromHex("2196F3"),
                600.0f to fromHex("1E88E5"),
                700.0f to fromHex("1976D2"),
                800.0f to fromHex("1565C0"),
                900.0f to fromHex("0D47A1"),
            )
        )
        val LightBlue = MdColor(
            ColorGradient(
                50.0f to fromHex("E1F5FE"),
                100.0f to fromHex("B3E5FC"),
                200.0f to fromHex("81D4FA"),
                300.0f to fromHex("4FC3F7"),
                400.0f to fromHex("29B6F6"),
                500.0f to fromHex("03A9F4"),
                600.0f to fromHex("039BE5"),
                700.0f to fromHex("0288D1"),
                800.0f to fromHex("0277BD"),
                900.0f to fromHex("01579B"),
            )
        )
        val Cyan = MdColor(
            ColorGradient(
                50.0f to fromHex("E0F7FA"),
                100.0f to fromHex("B2EBF2"),
                200.0f to fromHex("80DEEA"),
                300.0f to fromHex("4DD0E1"),
                400.0f to fromHex("26C6DA"),
                500.0f to fromHex("00BCD4"),
                600.0f to fromHex("00ACC1"),
                700.0f to fromHex("0097A7"),
                800.0f to fromHex("00838F"),
                900.0f to fromHex("006064"),
            )
        )
        val Teal = MdColor(
            ColorGradient(
                50.0f to fromHex("E0F2F1"),
                100.0f to fromHex("B2DFDB"),
                200.0f to fromHex("80CBC4"),
                300.0f to fromHex("4DB6AC"),
                400.0f to fromHex("26A69A"),
                500.0f to fromHex("009688"),
                600.0f to fromHex("00897B"),
                700.0f to fromHex("00796B"),
                800.0f to fromHex("00695C"),
                900.0f to fromHex("004D40"),
            )
        )
        val Green = MdColor(
            ColorGradient(
                50.0f to fromHex("E8F5E9"),
                100.0f to fromHex("C8E6C9"),
                200.0f to fromHex("A5D6A7"),
                300.0f to fromHex("81C784"),
                400.0f to fromHex("66BB6A"),
                500.0f to fromHex("4CAF50"),
                600.0f to fromHex("43A047"),
                700.0f to fromHex("388E3C"),
                800.0f to fromHex("2E7D32"),
                900.0f to fromHex("1B5E20"),
            )
        )
        val LightGreen = MdColor(
            ColorGradient(
                50.0f to fromHex("F1F8E9"),
                100.0f to fromHex("DCEDC8"),
                200.0f to fromHex("C5E1A5"),
                300.0f to fromHex("AED581"),
                400.0f to fromHex("9CCC65"),
                500.0f to fromHex("8BC34A"),
                600.0f to fromHex("7CB342"),
                700.0f to fromHex("689F38"),
                800.0f to fromHex("558B2F"),
                900.0f to fromHex("33691E"),
            )
        )
        val Lime = MdColor(
            ColorGradient(
                50.0f to fromHex("F9FBE7"),
                100.0f to fromHex("F0F4C3"),
                200.0f to fromHex("E6EE9C"),
                300.0f to fromHex("DCE775"),
                400.0f to fromHex("D4E157"),
                500.0f to fromHex("CDDC39"),
                600.0f to fromHex("C0CA33"),
                700.0f to fromHex("AFB42B"),
                800.0f to fromHex("9E9D24"),
                900.0f to fromHex("827717"),
            )
        )
        val Yellow = MdColor(
            ColorGradient(
                50.0f to fromHex("FFFDE7"),
                100.0f to fromHex("FFF9C4"),
                200.0f to fromHex("FFF59D"),
                300.0f to fromHex("FFF176"),
                400.0f to fromHex("FFEE58"),
                500.0f to fromHex("FFEB3B"),
                600.0f to fromHex("FDD835"),
                700.0f to fromHex("FBC02D"),
                800.0f to fromHex("F9A825"),
                900.0f to fromHex("F57F17"),
            )
        )
        val Amber = MdColor(
            ColorGradient(
                50.0f to fromHex("FFF8E1"),
                100.0f to fromHex("FFECB3"),
                200.0f to fromHex("FFE082"),
                300.0f to fromHex("FFD54F"),
                400.0f to fromHex("FFCA28"),
                500.0f to fromHex("FFC107"),
                600.0f to fromHex("FFB300"),
                700.0f to fromHex("FFA000"),
                800.0f to fromHex("FF8F00"),
                900.0f to fromHex("FF6F00"),
            )
        )
        val Orange = MdColor(
            ColorGradient(
                50.0f to fromHex("FFF3E0"),
                100.0f to fromHex("FFE0B2"),
                200.0f to fromHex("FFCC80"),
                300.0f to fromHex("FFB74D"),
                400.0f to fromHex("FFA726"),
                500.0f to fromHex("FF9800"),
                600.0f to fromHex("FB8C00"),
                700.0f to fromHex("F57C00"),
                800.0f to fromHex("EF6C00"),
                900.0f to fromHex("E65100"),
            )
        )
        val DeepOrange = MdColor(
            ColorGradient(
                50.0f to fromHex("FBE9E7"),
                100.0f to fromHex("FFCCBC"),
                200.0f to fromHex("FFAB91"),
                300.0f to fromHex("FF8A65"),
                400.0f to fromHex("FF7043"),
                500.0f to fromHex("FF5722"),
                600.0f to fromHex("F4511E"),
                700.0f to fromHex("E64A19"),
                800.0f to fromHex("D84315"),
                900.0f to fromHex("BF360C"),
            )
        )
        val Brown = MdColor(
            ColorGradient(
                50.0f to fromHex("EFEBE9"),
                100.0f to fromHex("D7CCC8"),
                200.0f to fromHex("BCAAA4"),
                300.0f to fromHex("A1887F"),
                400.0f to fromHex("8D6E63"),
                500.0f to fromHex("795548"),
                600.0f to fromHex("6D4C41"),
                700.0f to fromHex("5D4037"),
                800.0f to fromHex("4E342E"),
                900.0f to fromHex("3E2723"),
            )
        )
        val Grey = MdColor(
            ColorGradient(
                50.0f to fromHex("FAFAFA"),
                100.0f to fromHex("F5F5F5"),
                200.0f to fromHex("EEEEEE"),
                300.0f to fromHex("E0E0E0"),
                400.0f to fromHex("BDBDBD"),
                500.0f to fromHex("9E9E9E"),
                600.0f to fromHex("757575"),
                700.0f to fromHex("616161"),
                800.0f to fromHex("424242"),
                900.0f to fromHex("212121"),
            )
        )
        val BlueGrey = MdColor(
            ColorGradient(
                50.0f to fromHex("ECEFF1"),
                100.0f to fromHex("CFD8DC"),
                200.0f to fromHex("B0BEC5"),
                300.0f to fromHex("90A4AE"),
                400.0f to fromHex("78909C"),
                500.0f to fromHex("607D8B"),
                600.0f to fromHex("546E7A"),
                700.0f to fromHex("455A64"),
                800.0f to fromHex("37474F"),
                900.0f to fromHex("263238"),
            )
        )
    }
}
