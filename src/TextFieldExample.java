import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextFieldExample extends JFrame {
    private JTextField textField;
    private JButton saveButton;
    private JButton clearButton;
    private JRadioButton appendRadioButton;
    private JRadioButton overwriteRadioButton;
    private JCheckBox readCheckBox;
    private JList<String> list;
    private DefaultListModel<String> listModel;

    public TextFieldExample() {
        // Создание подписи к текстовому полю
        JLabel label = new JLabel("Добавить автобиографию:");

        // Создание текстового поля
        textField = new JTextField(20);

        // Создание кнопки сохранения
        saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTextToFile();
            }
        });

        // Создание кнопки очистки
        clearButton = new JButton("Очистить");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });

        // Создание радиокнопок
        appendRadioButton = new JRadioButton("Дописать в файл");
        overwriteRadioButton = new JRadioButton("Перезаписать файл");

        // Создание флажка "Прочитано"
        readCheckBox = new JCheckBox("Прочитано");

        // Создание списка
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSize(200, 200);
        // Загрузка строк из файла в список
        loadStringsFromFile();

        // Создание панели с подписью, текстовым полем, кнопками, радиокнопками, флажком и списком
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(textField);
        panel.add(saveButton);
        panel.add(clearButton);
        panel.add(appendRadioButton);
        panel.add(overwriteRadioButton);
        panel.add(readCheckBox);
        panel.add(new JScrollPane(list));

        // Добавление панели в окно
        getContentPane().add(panel, BorderLayout.CENTER);

        // Настройка окна
        setTitle("Пример текстового поля");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Сохранение введенного текста в файл
    private void saveTextToFile() {
        String text = textField.getText();
        boolean isRead = readCheckBox.isSelected();
        try {
            boolean appendToFile = appendRadioButton.isSelected();
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", appendToFile));
            writer.write(text);
            writer.newLine(); // Добавляем перевод строки для разделения записей
            writer.close();
            System.out.println("Текст успешно сохранен в файл.");
            listModel.addElement(text + (isRead ? " (Прочитано)" : "")); // Добавляем новую строку в список с пометкой "Прочитано", если флажок установлен
            textField.setText(""); // Очищаем текстовое поле
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Загрузка строк из файла в список
    private void loadStringsFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                listModel.addElement(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextFieldExample();
    }
}