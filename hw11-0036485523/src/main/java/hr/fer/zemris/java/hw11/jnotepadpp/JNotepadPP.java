package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.listeners.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class represents Notepad++ application. The application offers the ability to open files, copy, cut,
 * and paste text, sort, search for unique rows,
 * change language in English, English, and German, save files and view statistics about the current document.
 *
 * @see <a href="https://notepad-plus-plus.org/news/">Similar application with more feautures</a>
 */
public class JNotepadPP extends JFrame implements MultipleDocumentListener, SelectedAreaListener {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -6872959879373405986L;

    /**
     * The title of the document that does not exist on the HDD.
     */
    private static final String UNTITLED_DOCUMENT = "Untitled";


    /**
     * The multiple document model.
     */
    private MultipleDocumentModel model;

    /**
     * The panel.
     */
    private JPanel panel;

    /**
     * The list of buttons used to edit the document.
     * These buttons are observers over the selection in the text.
     */
    private List<AbstractButton> editObservers;

    /**
     * The list of buttons used to save the document.
     * These buttons are observers over the modification status of document.
     */
    private List<AbstractButton> saveObservers;

    /**
     * The buttons used to print the statistics or close the tab.
     * These buttons are observers of the existence of the document in the program.
     */
    private List<AbstractButton> existDocumentObservers;

    /**
     * The localization provider.
     */
    private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);


    /**
     * The status bar.
     */
    private StatusBar statusBar;

    /**
     * Creates an instance of {@link JNotepadPP}.
     */
    public JNotepadPP() {


        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(0, 0);
        setSize(1000, 400);
        addWindowListener();
        setTitle("JNotepad++");
        editObservers = new ArrayList<>();
        saveObservers = new ArrayList<>();
        existDocumentObservers = new ArrayList<>();
        initGui();

    }

    /**
     * Adds a window listener when the user attempts to close the app.
     *
     * @see #exitApplication()
     */
    private void addWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });
    }

    /**
     * Initiates a graphical user interface with menus, toolbar and text component..
     */
    private void initGui() {
        model = new DefaultMultipleDocumentModel();
        model.addMultipleDocumentListener(this);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add((DefaultMultipleDocumentModel) model, BorderLayout.CENTER);
        statusBar = new StatusBar(panel, flp);
        statusBar.add((SelectedAreaListener) this);

        panel.add(statusBar, BorderLayout.SOUTH);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        initActions();
        createMenus();
        createToolbar();


    }


    /**
     * Creates toolbar.
     * The toolbar has options: <p><code>New, Open , Close, Save, Save As , Cut, Copy , Paste , Summary</code></p>
     */
    private void createToolbar() {

        JToolBar toolBar = new JToolBar("Tools");
        toolBar.setFloatable(true);


        toolBar.add(new JButton(createBlankDocumentAction));
        toolBar.add(new JButton(openDocumentAction));
        JButton close = new JButton(closeDocumentAction);
        close.setEnabled(false);
        toolBar.add(close);

        toolBar.addSeparator();

        JButton save = new JButton(saveDocumentAction);
        JButton saveAs = new JButton(saveAsDocumentAction);
        save.setEnabled(false);
        saveAs.setEnabled(false);
        saveObservers.add(save);
        existDocumentObservers.add(saveAs);

        toolBar.add(save);
        toolBar.add(saveAs);

        JButton cut = new JButton(cutTextAction);
        JButton copy = new JButton(copyTextAction);
        JButton paste = new JButton(pasteTextAction);

        editObservers.add(cut);
        editObservers.add(copy);
        existDocumentObservers.add(paste);
        existDocumentObservers.add(close);

        cut.setEnabled(false);
        copy.setEnabled(false);
        paste.setEnabled(false);
        toolBar.addSeparator();

        toolBar.add(cut);
        toolBar.add(copy);
        toolBar.add(paste);

        toolBar.addSeparator();

        JButton statisticalInfo = new JButton(statisticalInfoAction);
        statisticalInfo.setEnabled(false);
        existDocumentObservers.add(statisticalInfo);

        toolBar.add(statisticalInfo);
        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Creates menus for our application. Adds menus : file , edit, view, languages and tools.
     * File menu has options: <p><code> new, open , save, save as , close ,exit.</code></p>
     * Edit menu has options : <p><code>cut, copy, paste </code></p>
     * View menu has options : <p><code>summary</code></p>
     * Languages menu has options : <p><code>German, Croatian, English</code></p>
     * Tools menu has options : <p><code>Change case : to uppercase, to lowercase , invert case ;
     * Sort : ascending, descending Unique; </code></p>
     */
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu(new LocalizableAction("file", flp));
        menuBar.add(fileMenu);
        fileMenu.add(new JMenuItem(createBlankDocumentAction));
        fileMenu.add(new JMenuItem(openDocumentAction));
        
        JMenuItem save = new JMenuItem(saveDocumentAction);
        save.setEnabled(false);
        fileMenu.add(save);
        
        JMenuItem saveAs = new JMenuItem(saveAsDocumentAction);
        saveAs.setEnabled(false);
        fileMenu.add(saveAs);
        saveObservers.add(save);
        existDocumentObservers.add(saveAs);

        JMenuItem close = new JMenuItem(closeDocumentAction);
        close.setEnabled(false);
        fileMenu.add(close);
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(exitAction));
        existDocumentObservers.add(close);

        JMenu editMenu = new JMenu(new LocalizableAction("edit", flp));
        menuBar.add(editMenu);
        JMenuItem cut = new JMenuItem(cutTextAction);
        cut.setEnabled(false);
        
        JMenuItem copy = new JMenuItem(copyTextAction);
        copy.setEnabled(false);
        JMenuItem paste = new JMenuItem(pasteTextAction);
        paste.setEnabled(false);
        
        editObservers.add(cut);
        editObservers.add(copy);
        existDocumentObservers.add(paste);

        editMenu.add(cut);
        editMenu.add(copy);
        editMenu.add(paste);

        JMenu viewMenu = new JMenu(new LocalizableAction("view", flp));
        menuBar.add(viewMenu);
        JMenuItem statisticalInfo = new JMenuItem(statisticalInfoAction);
        statisticalInfo.setEnabled(false);
        existDocumentObservers.add(statisticalInfo);
        viewMenu.add(statisticalInfo);

        JMenu languages = new JMenu(new LocalizableAction("languages", flp));
        menuBar.add(languages);
        
        JMenuItem english = new JMenuItem(englishLanguage);
        JMenuItem german = new JMenuItem(germanLanguage);
        JMenuItem croatian = new JMenuItem(croatianLanguage);

        languages.add(english);
        languages.add(german);
        languages.add(croatian);


        JMenu tools = new JMenu(new LocalizableAction("tools", flp));
        JMenu changeCase = new JMenu(new LocalizableAction("changeCase", flp));
        tools.add(changeCase);
        
        JMenuItem toUppercase = new JMenuItem(toUpperCaseAction);
        JMenuItem toLowercase = new JMenuItem(toLowerCaseAction);
        JMenuItem invert = new JMenuItem(invertCase);

        changeCase.add(toUppercase);
        changeCase.add(toLowercase);
        changeCase.add(invert);

        toUppercase.setEnabled(false);
        toLowercase.setEnabled(false);
        invert.setEnabled(false);

        JMenu sort = new JMenu(new LocalizableAction("sort", flp));
        tools.add(sort);
        
        JMenuItem ascSort = new JMenuItem(ascendingSort);
        JMenuItem descSort = new JMenuItem(descendingSort);
        ascSort.setEnabled(false);
        descSort.setEnabled(false);
        sort.add(ascSort);
        sort.add(descSort);
        
        JMenuItem unique = new JMenuItem(removeUniqueAction);
        unique.setEnabled(false);
        tools.add(unique);

        editObservers.add(toUppercase);
        editObservers.add(toLowercase);
        editObservers.add(invert);
        editObservers.add(ascSort);
        editObservers.add(descSort);
        editObservers.add(unique);

        menuBar.add(tools);
        this.setJMenuBar(menuBar);
    }

    /**
     * Initializes meta action data. This method gives each action name, short description, and associated icon
     */
    private void initActions() {

        Icons icons = Icons.getInstance();

        createBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
        createBlankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
        createBlankDocumentAction.putValue(Action.SMALL_ICON, icons.getIcon("createBlank"));


        openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        openDocumentAction.putValue(Action.SMALL_ICON, icons.getIcon("open"));


        saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveDocumentAction.putValue(Action.SMALL_ICON, icons.getIcon("save"));

        saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
        saveAsDocumentAction.putValue(Action.SMALL_ICON, icons.getIcon("saveAs"));

        closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
        closeDocumentAction.putValue(Action.SMALL_ICON, icons.getIcon("close"));

        cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        cutTextAction.putValue(Action.SMALL_ICON, icons.getIcon("cut"));

        copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        copyTextAction.putValue(Action.SMALL_ICON, icons.getIcon("copy"));

        pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
        pasteTextAction.putValue(Action.SMALL_ICON, icons.getIcon("paste"));

        statisticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control f2"));
        statisticalInfoAction.putValue(Action.SMALL_ICON, icons.getIcon("statistics"));

        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        exitAction.putValue(Action.SMALL_ICON, icons.getIcon("exit"));

        englishLanguage.putValue(Action.SMALL_ICON, icons.getIcon("englishDescription"));
        croatianLanguage.putValue(Action.SMALL_ICON, icons.getIcon("croatianDescription"));
        germanLanguage.putValue(Action.SMALL_ICON, icons.getIcon("germanDescription"));

    }


    /**
     * Represents the action for crating a new blank document. The blank document will have a name in format:  *[1-9]+
     */
    private Action createBlankDocumentAction = new LocalizableAction("new",
            "createBlankDescription", "mnemonicNew", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            model.createNewDocument();
        }
    };

    /**
     * Represents the action for changing program's language to English.
     */
    private Action englishLanguage = new LocalizableAction("english", "englishDescription",
            "mnemonicEnglish", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("en");
        }
    };

    /**
     * Represents the action for changing program's language to Croatian.
     */
    private Action croatianLanguage = new LocalizableAction("croatian", "croatianDescription",
            "mnemonicCroatian", flp) {
        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("hr");
        }
    };

    /**
     * Represents the action for changing program's language to German.
     */
    private Action germanLanguage = new LocalizableAction("german", "germanDescription",
            "mnemonicGerman", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            LocalizationProvider.getInstance().setLanguage("de");
        }
    };

    /**
     * Represents the action for opening document from the hard drive.
     */
    private Action openDocumentAction = new LocalizableAction("open", "openDocumentDescription",
            "mnemonicOpen", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent arg0) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle(flp.getString("openFile"));
            if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = fc.getSelectedFile();
            Path filePath = fileName.toPath();
            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("file")
                        + fileName.getAbsolutePath()
                        + flp.getString("notExists"), flp.getString("error"), JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                model.loadDocument(filePath);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JNotepadPP.this,
                        flp.getString("fileReadingError") + fileName.getAbsolutePath()
                        , flp.getString("error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    /**
     * Represents the action for saving the current selected document.
     */
    private Action saveDocumentAction = new LocalizableAction("save", "saveDocumentDescription",
            "mnemonicSave", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            saveDocument(model.getCurrentDocument().getFilePath());
        }
    };

    /**
     * Auxiliary method for saving a file to the given file path.
     *
     * @param filePath the path to the file.
     */
    private void saveDocument(Path filePath) {
        if (filePath == null) {
            filePath = saveAsAction();
            if (filePath == null) {
                return;
            }
        }
        try {
            model.saveDocument(model.getCurrentDocument(), filePath);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("fileWritingError")
                            + model.getCurrentDocument().getFilePath().toAbsolutePath()
                    , flp.getString("error"), JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, flp.getString("file") + " "
                            + e.getMessage() + " " + flp.getString("alreadyOpen") + " .",
                    flp.getString("error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("saveMessage")
                , flp.getString("information"), JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Represents the action for saving documents at some location on the disk.
     */
    private Action saveAsDocumentAction = new LocalizableAction("saveAs",
            "saveAsDocumentDescription", "mnemonicSaveAs", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Path newPath = saveAsAction();
            if (newPath == null) {
                return;
            }
            saveDocument(newPath);
        }
    };

    /**
     * Auxiliary method for selecting the location where the file will be saved
     *
     * @return the path where the file wil be saved .
     */
    private Path saveAsAction() {
        Path newPath;
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle(flp.getString("saveFileTittle"));
        if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("notSavedMessage"),
                    flp.getString("warning"), JOptionPane.WARNING_MESSAGE);
            return null;
        }
        newPath = jfc.getSelectedFile().toPath();
        if (newPath.toFile().isFile() && newPath.toFile().exists()) {
            int answer = JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString("overwriteFileMessage"),
                    flp.getString("saveFileTittle"), JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.NO_OPTION || answer == JOptionPane.CLOSED_OPTION) {
                return null;
            }
        }
        return newPath;
    }

    /**
     * Represents the action for closing the document.
     *
     * @see #closeDocument(SingleDocumentModel)
     */
    private Action closeDocumentAction = new LocalizableAction("close", "closeDocumentDescription",
            "mnemonicClose", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            closeDocument(model.getCurrentDocument());

        }
    };

    /**
     * Closes  the current document model.If the document is modified,
     * user is asked if he wants to save this document.
     *
     * @param singleDocumentModel the current document model.
     */
    private void closeDocument(SingleDocumentModel singleDocumentModel) {
        if (singleDocumentModel != null) {
            if (singleDocumentModel.isModified()) {
                String file = singleDocumentModel.getFilePath() == null ?
                        UNTITLED_DOCUMENT : singleDocumentModel.getFilePath().toString();
                int answer = JOptionPane.showConfirmDialog(JNotepadPP.this,
                        flp.getString("theFile") + " " +
                                file + " " +
                                flp.getString("closeTabMessage"), flp.getString("saveFileTittle"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (answer == JOptionPane.YES_OPTION) {
                    saveDocument(singleDocumentModel.getFilePath());
                } else if (answer == JOptionPane.CLOSED_OPTION) {
                    return;
                }
            }
            setEditVisibility(false);
            model.closeDocument(singleDocumentModel);
        }
    }

    /**
     * Represents the copy text action.
     */
    private Action copyTextAction = new LocalizableAction("copy", "copyTextDescription",
            "mnemonicCopy", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            model.getCurrentDocument().getTextComponent().copy();
        }
    };

    /**
     * Represents the cut text action.
     */
    private Action cutTextAction = new LocalizableAction("cut", "cutTextDescription",
            "mnemonicCut", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            model.getCurrentDocument().getTextComponent().cut();
        }
    };

    /**
     * Represents the paste text action.
     */
    private Action pasteTextAction = new LocalizableAction("paste", "pasteTextDescription",
            "mnemonicPaste", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            model.getCurrentDocument().getTextComponent().paste();
        }
    };

    /**
     * Represents the action for printing statistical information about current document.
     */
    private Action statisticalInfoAction = new LocalizableAction("summary",
            "statisticalInfoDescription", "mnemonicStatistics", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            int numberOfCharacters = model.getCurrentDocument().getTextComponent().getText().length();
            int numberOfNonBlankCharacters = model.getCurrentDocument().getTextComponent().getText()
                    .replaceAll("\\s+", "").length();
            int numberOfLines = model.getCurrentDocument().getTextComponent().getLineCount();

            JOptionPane.showMessageDialog(JNotepadPP.this,
                    flp.getString("statusBarMessageStart") + numberOfCharacters + " "
                            + flp.getString("characters") + ", " +
                            numberOfNonBlankCharacters + " " + flp.getString("non-blankCharacters") + " , " +
                            numberOfLines + " " + flp.getString("lines") + "."
                    , flp.getString("statisticalInfo"), JOptionPane.INFORMATION_MESSAGE);
        }
    };

    /**
     * Represents the action for exiting the whole program. Before exit, if there is any unsaved
     * document, user is asked if he wants to save specified documents.
     */
    private Action exitAction = new LocalizableAction("exit", "exitApplicationDescription",
            "mnemonicExit", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            exitApplication();

        }
    };

    /**
     * Represents the action for changing the selected text to uppercase.
     */
    private Action toUpperCaseAction = new LocalizableAction("toUpperCase", "toUpperCaseDescription",
            "mnemonicUpper", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            changeCase(String::toUpperCase);
        }
    };

    /**
     * Represents the action for changing the selected text to lowercase.
     */
    private Action toLowerCaseAction = new LocalizableAction("toLowerCase", "toLowerCaseDescription",
            "mnemonicLower", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            changeCase(String::toLowerCase);
        }
    };

    /**
     * Represents the action for inverting selected text.
     */
    private Action invertCase = new LocalizableAction("invertCase", "invertCaseDescription",
            "mnemonicInvert", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            changeCase(this::invertCase);

        }

        /**
         *Auxiliary method for inverting case of selected text.
         * @param text the input text.
         * @return the inverted text.
         */
        private String invertCase(String text) {
            char[] characters = text.toCharArray();
            for (int i = 0; i < characters.length; i++) {
                char c = characters[i];
                if (Character.isLowerCase(c)) {
                    characters[i] = Character.toUpperCase(c);
                } else if (Character.isUpperCase(c)) {
                    characters[i] = Character.toLowerCase(c);
                }
            }
            return new String(characters);
        }

    };

    /**
     * Changes the case of selected lines.
     * This is template method.
     *
     * @param function the function for changing the case
     * @see <a href="https://en.wikipedia.org/wiki/Template_method_pattern" >Template method</a>
     */
    private void changeCase(Function<String, String> function) {
        JTextArea editor = model.getCurrentDocument().getTextComponent();
        Document doc = editor.getDocument();
        int dot = editor.getCaret().getDot();
        int mark = editor.getCaret().getMark();
        int len = Math.abs(dot - mark);
        int offset = 0;
        if (len != 0) {
            offset = Math.min(dot, mark);
        } else {
            len = doc.getLength();
        }
        try {
            String text = doc.getText(offset, len);
            text = function.apply(text);
            doc.remove(offset, len);
            doc.insertString(offset, text, null);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Represents the action for ascending sorting.
     */
    private Action ascendingSort = new LocalizableAction("ascending", "ascendingDescription",
            "mnemonicAscending", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Collator collator = Collator.getInstance(new Locale(flp.getCurrentLanguage()));
            sort(collator::compare);
        }
    };

    /**
     * Represents the action for descending sorting.
     */
    private Action descendingSort = new LocalizableAction("descending", "descendingDescription",
            "mnemonicDescending", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Collator collator = Collator.getInstance(new Locale(flp.getCurrentLanguage()));
            sort((s1, s2) -> collator.compare(s2, s1));
        }
    };

    /**
     * Sorts only the selected lines of text using rules of currently defined language.
     * If user selection spans only part of some line, whole line is affected.
     * This is template method.
     *
     * @param comparator the localized comparator.
     * @see <a href="https://en.wikipedia.org/wiki/Template_method_pattern" >Template method</a>
     * @see Collator#compare(String, String)
     */
    private void sort(Comparator<String> comparator) {
        JTextArea area = model.getCurrentDocument().getTextComponent();
        try {
            int dot = area.getLineOfOffset(area.getCaret().getDot());
            int mark = area.getLineOfOffset(area.getCaret().getMark());

            int lineStart = Math.min(dot, mark);
            int lineEnd = Math.max(dot, mark);
            int startOffset = area.getLineStartOffset(lineStart);
            int endOffset = area.getLineEndOffset(lineEnd);


            List<String> lines = new ArrayList<>();

            Document doc = area.getDocument();
            int numOfLines = area.getText().split("\\n").length;
            processSelectedLines(area, lineStart, lineEnd, lines, doc, numOfLines);

            lines = lines.stream().sorted(comparator).collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            lines.forEach(sb::append);

            doc.remove(startOffset, endOffset - startOffset);
            doc.insertString(startOffset, sb.toString(), null);


        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Removes from selection all lines which are duplicates (only the first occurrence is retained).
     */
    private Action removeUniqueAction = new LocalizableAction("unique", "uniqueDescription",
            "mnemonicUnique", flp) {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea area = model.getCurrentDocument().getTextComponent();

            try {
                int dot = area.getLineOfOffset(area.getCaret().getDot());
                int mark = area.getLineOfOffset(area.getCaret().getMark());

                int lineStart = Math.min(dot, mark);
                int lineEnd = Math.max(dot, mark);

                StringBuilder sb = new StringBuilder();
                Set<String> set = new LinkedHashSet<>();

                int startOffset = area.getLineStartOffset(lineStart);
                int endOffset = area.getLineEndOffset(lineEnd);

                Document doc = area.getDocument();
                int numOfLines = area.getText().split("\\n").length;
                processSelectedLines(area, lineStart, lineEnd, set, doc, numOfLines);
                doc.remove(startOffset, endOffset - startOffset);
                set.forEach(sb::append);
                doc.insertString(startOffset, sb.toString(), null);

            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
     * Processes the selected line and adds them to the collection.
     *
     * @param area       the text component.
     * @param lineStart  the index of first line.
     * @param lineEnd    the index of end line.
     * @param collection collection.
     * @param doc        the document.
     * @param numOfLines the number of selected lines.
     * @throws BadLocationException if it is not possible to process the lines.
     */
    private void processSelectedLines(JTextArea area, int lineStart, int lineEnd, Collection<String> collection,
                                      Document doc, int numOfLines) throws BadLocationException {
        for (int i = lineStart; i <= lineEnd; i++) {
            int start = area.getLineStartOffset(i);
            int end = area.getLineEndOffset(i);
            String text = doc.getText(start, Math.abs(end - start));
            if (i == numOfLines - 1) {
                text = text.contains("\n") ? text : text + "\n";
            }
            collection.add(text);
        }
    }

    /**
     * Exits the application and asks the user whether to save the currently modified documents.
     */
    private void exitApplication() {
        int answer = JOptionPane.showConfirmDialog(JNotepadPP.this,
                flp.getString("exitApplicationMessage"), flp.getString("exit"), JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            while (model.getCurrentDocument() != null) {
                SingleDocumentModel singleDocumentModel = model.getCurrentDocument();
                if (singleDocumentModel.isModified()) {
                    String file = singleDocumentModel.getFilePath() == null ? UNTITLED_DOCUMENT :
                            singleDocumentModel.getFilePath().toString();
                    answer = JOptionPane.showConfirmDialog(JNotepadPP.this,
                            flp.getString("beforeExitMessage") + " " + file + " " +
                                    flp.getString("afterExitMessage"),
                            flp.getString("saveFileTittle"), JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.NO_OPTION) {
                        model.closeDocument(singleDocumentModel);
                        continue;
                    } else if (answer == JOptionPane.CLOSED_OPTION) {
                        return;
                    }
                    saveDocument(singleDocumentModel.getFilePath());
                } else {
                    model.closeDocument(singleDocumentModel);
                }
            }
            statusBar.getDateAndTime().getTimer().stop();
            statusBar.remove((SelectedAreaListener) this);
            dispose();
        }
    }

    @Override
    public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
        String title = currentModel.getFilePath() == null ? UNTITLED_DOCUMENT : currentModel.getFilePath().toString();
        setTitle(title + " - JNotepad++");
        currentModel.getTextComponent().requestFocus();
        updateEditVisibility(currentModel);

        if (previousModel != null && !previousModel.equals(currentModel)) {
            previousModel.getTextComponent().removeCaretListener(statusBar);
            currentModel.getTextComponent().addCaretListener(statusBar);
            statusBar.updateStatusBar(currentModel.getTextComponent());
        }

        if (previousModel != null && previousModel.isModified() != currentModel.isModified()) {
            if (currentModel.isModified()) {
                setSaveVisibility(true);
                return;
            }
            setSaveVisibility(false);
            previousModel.getTextComponent().removeCaretListener(statusBar);
        }

    }


    @Override
    public void documentAdded(SingleDocumentModel singleDocumentModel) {
        singleDocumentModel.getTextComponent().requestFocus();
    }

    /**
     * Updates the values( length, line, column , selected) in the status bar.
     *
     * @param model the current single document model.
     */
    private void updateEditVisibility(SingleDocumentModel model) {
        if (model == null) {
            setExistDocumentElementsVisibility(false);
            return;
        }
        setExistDocumentElementsVisibility(true);
        JTextArea editArea = model.getTextComponent();
        int len = Math.abs(editArea.getCaret().getDot() - editArea.getCaret().getMark());
        if (len != 0) {
            setEditVisibility(true);
        } else {
            setEditVisibility(false);
        }
    }

    @Override
    public void documentRemoved(SingleDocumentModel currentModel) {
        if (currentModel != null) {
            String title = currentModel.getFilePath() == null ? UNTITLED_DOCUMENT : currentModel.getFilePath().toString();
            setTitle(title + " - JNotepad++ ");
            updateEditVisibility(currentModel);
            if (currentModel.isModified()) {
                setSaveVisibility(true);
            }
            return;
        }
        setTitle("JNotepad++");
        statusBar.updateLength(0);
        statusBar.updateCurrentLineColumnAndSelected(0, 0, 0);
        setExistDocumentElementsVisibility(false);
        setSaveVisibility(false);

    }

    /**
     * Changes the visibility of  the edit buttons.
     *
     * @param visibility the  desired visibility.
     */
    private void setEditVisibility(boolean visibility) {
        for (AbstractButton btn : editObservers) {
            btn.setEnabled(visibility);
        }
    }

    /**
     * Changes the visibility of buttons  that depends on the existence of documents.
     *
     * @param visibility the desired visibility.
     */
    private void setExistDocumentElementsVisibility(boolean visibility) {
        for (AbstractButton btn : existDocumentObservers) {
            btn.setEnabled(visibility);
        }
    }

    /**
     * Changes the visibility of the save buttons
     *
     * @param visibility the desired visibility.
     */
    private void setSaveVisibility(boolean visibility) {
        for (AbstractButton btn : saveObservers) {
            btn.setEnabled(visibility);
        }
    }

    @Override
    public void selectedAreaChanged() {
        updateEditVisibility(model.getCurrentDocument());
    }


    /**
     * The method invoked when running the program.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }


}
