package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents the status bar.The status bar shows information in form:
 * length : xy          Ln : x Col : x Sel :     yyyy/MM/dd HH:mm:ss.
 */
public class StatusBar extends JPanel implements CaretListener {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -3026022000612941435L;

    /**
     * The length label.
     */
    private JLabel lengthLabel;

    /**
     * The line label.
     */
    private JLabel lineLabel;

    /**
     * The column label.
     */
    private JLabel columnLabel;

    /**
     * The selected label.
     */
    private JLabel selectedLabel;

    /**
     * The clock component.
     */
    private ClockComponent dateAndTime;

    /**
     * The listeners.
     */
    private List<SelectedAreaListener> listeners;


    /**
     * Creates an instance of status bar.
     * @param panel the panel.
     * @param flp the localization provider.
     */
    public StatusBar(JPanel panel, FormLocalizationProvider flp) {
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setPreferredSize(new Dimension(panel.getWidth(), 25));
        this.setLayout(new BorderLayout());
        this.listeners = new ArrayList<>();

        lengthLabel = new LJLabel("length", flp);
        lineLabel = new LJLabel("line", flp);
        columnLabel = new LJLabel("column", flp);
        selectedLabel = new LJLabel("selected", flp);

        dateAndTime = new ClockComponent();
        add(lengthLabel, BorderLayout.WEST);

        JPanel data = new JPanel();
        data.add(lineLabel);
        data.add(columnLabel);
        data.add(selectedLabel);
        add(data, BorderLayout.CENTER);

        add(dateAndTime, BorderLayout.EAST);

        updateLength(0);
        updateCurrentLineColumnAndSelected(0, 0, 0);

    }

    /**
     * Updates the length of document.
     *
     * @param length the length of document.
     */
    public void updateLength(int length) {
        updateLabel(lengthLabel, length);
    }

    /**
     * Updates the current line, column and selected area length.
     *
     * @param currentLine   the current line.
     * @param currentColumn the current column.
     * @param selected      the length of selected area.
     */
    public void updateCurrentLineColumnAndSelected(int currentLine, int currentColumn, int selected) {
        updateLabel(lineLabel, currentLine);
        updateLabel(columnLabel, currentColumn);
        updateLabel(selectedLabel, selected);
    }

    /**
     * Updates the given label text.
     *
     * @param label the label.
     * @param line  the new value.
     */
    private void updateLabel(JLabel label, int line) {
        if (!label.getText().contains(":")) {
            label.setText(label.getText() + " : " + line);
        } else {
            label.setText(label.getText().substring(0, label.getText().indexOf(":") + 1) + line);
        }
    }

    /**
     * Returns the clock component.
     *
     * @return the clock component.
     */
    public ClockComponent getDateAndTime() {
        return dateAndTime;
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        updateStatusBar((JTextArea) e.getSource());
    }
    
    /**
     * Updates the values( length, line, column , selected) in the status bar.
     * 
     * @param editArea the current text area.
     */
    public void updateStatusBar(JTextArea editArea) {
        int lineNumber = 1;
        int columnNumber = 1;
        try {
            int caretPos = editArea.getCaretPosition();
            lineNumber = editArea.getLineOfOffset(caretPos);
            columnNumber = caretPos - editArea.getLineStartOffset(lineNumber);
            lineNumber += 1;
            columnNumber += 1;
        } catch (Exception ignored) {
        }
        int selected = Math.abs(editArea.getCaret().getDot() - editArea.getCaret().getMark());
        updateCurrentLineColumnAndSelected(lineNumber, columnNumber, selected);
        updateLength(editArea.getText().length());
        notifyListeners();

    }


    /**
     * Adds a new {@link SelectedAreaListener}.
     *
     * @param listener the {@link SelectedAreaListener}.
     */
    public void add(SelectedAreaListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Removes a {@link SelectedAreaListener}
     *
     * @param listener the {@link SelectedAreaListener} that we want to remove.
     */
    public void remove(SelectedAreaListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /**
     * Informs the observers that the selected area changed.
     */
    public void notifyListeners() {
        for (SelectedAreaListener listener : listeners) {
            listener.selectedAreaChanged();
        }
    }


    /**
     * Represents the clock component. Clock shows current date and time.
     * The format is : yyyy/MM/dd HH:mm:ss.
     */
    public static class ClockComponent extends JPanel implements ActionListener {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 8200378405971965833L;

        /**
         * The date format.
         */
        private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        /**
         * The clock label.
         */
        private JLabel clock;

        /**
         * The timer.
         */
        private Timer timer;


        /**
         * Creates an instance of {@link ClockComponent}.
         */
        public ClockComponent() {
            clock = new JLabel();
            clock.setHorizontalAlignment(SwingConstants.RIGHT);
            add(clock);
            updateClock();
            timer = new Timer(1000, this);
            timer.start();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            updateClock();
        }

        /**
         * Updates clock.
         */
        private void updateClock() {
            clock.setText(dateFormat.format(Calendar.getInstance().getTime()));
        }

        /**
         * Returns the timer.
         *
         * @return timer.
         */
        public Timer getTimer() {
            return timer;
        }
    }
}


