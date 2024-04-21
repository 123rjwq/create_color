package com.example.pr_38_rom_kir_color_v1

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Получение ссылки на кнопку для открытия диалогового окна выбора цвета
        val buttonOpenDialog = findViewById<Button>(R.id.buttonOpenDialog)
        buttonOpenDialog.setOnClickListener {
            // Вызов метода для отображения диалогового окна выбора цвета
            showColorPickerDialog()
        }
    }

    private fun showColorPickerDialog() {
        // Инициализация представления диалогового окна из XML
        val dialogView = layoutInflater.inflate(R.layout.dialog_color_picker, null)

        // Инициализация ползунков и текстовых полей
        val seekBarRed = dialogView.findViewById<SeekBar>(R.id.seekBarRed)
        val seekBarGreen = dialogView.findViewById<SeekBar>(R.id.seekBarGreen)
        val seekBarBlue = dialogView.findViewById<SeekBar>(R.id.seekBarBlue)
        val editTextRed = dialogView.findViewById<EditText>(R.id.editTextRed)
        val editTextGreen = dialogView.findViewById<EditText>(R.id.editTextGreen)
        val editTextBlue = dialogView.findViewById<EditText>(R.id.editTextBlue)
        val previewColorView = dialogView.findViewById<View>(R.id.previewColorView)

        // Создание диалогового окна с настройками
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Получение текущих значений RGB из ползунков и текстовых полей
                val red = seekBarRed.progress
                val green = seekBarGreen.progress
                val blue = seekBarBlue.progress

                // Создание цвета из RGB
                val color = Color.rgb(red, green, blue)

                // Получение ссылки на View и изменение его цвета фона
                val colorView = findViewById<View>(R.id.colorView)
                colorView.setBackgroundColor(color)

                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()

        // Обработка изменения положения ползунков
        seekBarRed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                editTextRed.setText(progress.toString())
                updatePreviewColor(previewColorView, progress, seekBarGreen.progress, seekBarBlue.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Обработка изменения положения ползунков для seekBarGreen
        seekBarGreen.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                editTextGreen.setText(progress.toString())
                updatePreviewColor(previewColorView, seekBarRed.progress, progress, seekBarBlue.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

// Обработка изменения положения ползунков для seekBarBlue
        seekBarBlue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                editTextBlue.setText(progress.toString())
                updatePreviewColor(previewColorView, seekBarRed.progress, seekBarGreen.progress, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        // Обработка изменения текстовых полей
        editTextRed.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val progress = s.toString().toIntOrNull() ?: 0
                seekBarRed.progress = progress
                updatePreviewColor(previewColorView, progress, seekBarGreen.progress, seekBarBlue.progress)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Обработка изменения текстовых полей для editTextGreen
        editTextGreen.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val progress = s.toString().toIntOrNull() ?: 0
                seekBarGreen.progress = progress
                updatePreviewColor(previewColorView, seekBarRed.progress, progress, seekBarBlue.progress)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

// Обработка изменения текстовых полей для editTextBlue
        editTextBlue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val progress = s.toString().toIntOrNull() ?: 0
                seekBarBlue.progress = progress
                updatePreviewColor(previewColorView, seekBarRed.progress, seekBarGreen.progress, progress)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updatePreviewColor(view: View, red: Int, green: Int, blue: Int) {
        // Обновление цвета предварительного просмотра на основе выбранных компонентов цвета
        view.setBackgroundColor(Color.rgb(red, green, blue))
    }
}


