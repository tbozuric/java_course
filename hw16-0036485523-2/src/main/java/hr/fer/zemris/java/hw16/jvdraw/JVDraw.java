package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorLabel;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.components.JListGeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.models.DefaultDrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.tools.DrawCircle;
import hr.fer.zemris.java.hw16.jvdraw.tools.DrawFilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.tools.DrawLine;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectJvdGenerator;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectPainter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;

import static hr.fer.zemris.java.hw16.jvdraw.util.UtilParser.*;


/**
 * This class represents PAint application. The application offers the ability to draw circles, lines and filled circles
 * and save and export images.
 */
public class JVDraw extends JFrame implements DrawingModelListener {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -3557443398644117436L;

    /**
     * The change background color text.
     */
    private static final String CHANGE_BACKGROUND_COLOR = "Change background color";

    /**
     * The change foreground color text.
     */
    private static final String CHANGE_FOREGROUND_COLOR = "Change foreground color.";

    /**
     * The draw circle tool.
     */
    private Tool drawCircle;

    /**
     * The draw filled circle tool.
     */
    private Tool drawFilledCircle;

    /**
     * The draw line tool.
     */
    private Tool drawLine;

    /**
     * The drawing model.
     */
    private DrawingModel model;

    /**
     * The canvas.
     */
    private JDrawingCanvas canvas;

    /**
     * The list of {@link GeometricalObject} that are drawn on the {@link JDrawingCanvas}.
     */
    private JList<GeometricalObject> objects;

    /**
     * The foreground color.
     */
    private JColorArea fgColorArea;

    /**
     * The background color.
     */
    private JColorArea bgColorArea;


    /**
     * The save menu item.
     */
    private JMenuItem save;

    /**
     * The export menu item.
     */
    private JMenuItem export;

    /**
     * The flag indicating whether the model is modified.
     */
    private boolean modified;

    /**
     * The path to saved image.
     */
    private Path filePath;

    /**
     * Creates an instance of {@link JVDraw}.
     */
    public JVDraw() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(0, 0);
        setSize(800, 800);
        setTitle("JVDraw");
        setLayout(new BorderLayout());

        model = new DefaultDrawingModel();
        model.addDrawingModelListener(this);
        objects = new JListGeometricalObject(model);

        initActions();
        initGUI();

        addJListListener();
        addWindowListener();
    }


    /**
     * Initializes meta action data.
     */
    private void initActions() {
        openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        saveJvdAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        saveAsJvdAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
        exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
    }


    /**
     * Initiates a graphical user interface with menus, canvas and listx component..
     */
    private void initGUI() {
        fgColorArea = new JColorArea(Color.RED, this, CHANGE_FOREGROUND_COLOR);
        bgColorArea = new JColorArea(Color.BLUE, this, CHANGE_BACKGROUND_COLOR);

        drawCircle = new DrawCircle(model, fgColorArea, bgColorArea);
        drawFilledCircle = new DrawFilledCircle(model, fgColorArea, bgColorArea);
        drawLine = new DrawLine(model, fgColorArea, bgColorArea);

        Tool currentTool = drawLine;

        JColorLabel label = new JColorLabel(fgColorArea, bgColorArea);
        createToolbar();

        add(label, BorderLayout.PAGE_END);
        canvas = new JDrawingCanvas(model, currentTool);
        add(canvas, BorderLayout.CENTER);
        add(new JScrollPane(objects), BorderLayout.LINE_END);

        createMenus();
    }

    /**
     * Creates menus for our application. Adds file menu..
     * File menu has options: <p><code> open , save, save as , export ,exit.</code></p>
     */
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem(openDocumentAction);

        save = new JMenuItem(saveJvdAction);
        export = new JMenuItem(exportAction);
        JMenuItem saveAs = new JMenuItem(saveAsJvdAction);
        JMenuItem exit = new JMenuItem(exitAction);

        save.setEnabled(false);
        export.setEnabled(false);

        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.add(export);
        file.add(exit);

        menuBar.add(file);
        setJMenuBar(menuBar);
    }

    /**
     * Creates toolbar.
     * The toolbar has options: <p><code>change foreground/background color, draw line/circle/filled circle</code></p>
     */
    private void createToolbar() {

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(true);

        toolBar.add(fgColorArea);
        toolBar.addSeparator();
        toolBar.add(bgColorArea);
        toolBar.addSeparator();

        ButtonGroup tools = new ButtonGroup();
        JToggleButton drawLine = new JToggleButton(drawLineAction);
        JToggleButton drawCircle = new JToggleButton(drawCircleAction);
        JToggleButton drawFilledCircle = new JToggleButton(drawFilledCircleAction);

        drawLine.setSelected(true);

        tools.add(drawLine);
        tools.add(drawCircle);
        tools.add(drawFilledCircle);

        toolBar.add(drawLine);
        toolBar.add(drawCircle);
        toolBar.add(drawFilledCircle);

        add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Adds appropriate listeners to {@link JList}. They allow editing of {@link GeometricalObject}s, deleting...
     */
    private void addJListListener() {
        objects.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    GeometricalObject clicked = objects.getSelectedValue();
                    GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
                    if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        try {
                            editor.checkEditing();
                            editor.acceptEditing();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(JVDraw.this, "Invalid arguments");
                        }
                    }
                }
            }
        });

        objects.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int selectedIndex = objects.getSelectedIndex();
                    if (selectedIndex != -1) {
                        model.remove(objects.getSelectedValue());
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_ADD) {
                    int selectedIndex = objects.getSelectedIndex();
                    if (selectedIndex != -1) {
                        model.changeOrder(objects.getSelectedValue(), -1);
                        objects.setSelectedIndex(selectedIndex - 1);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                    int selectedIndex = objects.getSelectedIndex();
                    if (selectedIndex != -1) {
                        model.changeOrder(objects.getSelectedValue(), 1);
                        objects.setSelectedIndex(selectedIndex + 1);

                    }
                }
            }
        });
    }

    /**
     * Changes the current state to draw line.
     */
    private AbstractAction drawLineAction = new AbstractAction("Line") {

        /**
         *The default serial version UID.
         */
        private static final long serialVersionUID = 1388939635161631608L;

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setState(drawLine);
        }
    };

    /**
     * Changes the current state to draw circle.
     */
    private AbstractAction drawCircleAction = new AbstractAction("Circle") {

        /**
         *The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setState(drawCircle);
        }
    };

    /**
     * Changes the current state to draw filled circle.
     */
    private AbstractAction drawFilledCircleAction = new AbstractAction("Filled circle") {

        /**
         *The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.setState(drawFilledCircle);
        }
    };

    /**
     * Represents the action for saving the image in JVD format..
     */
    private Action saveJvdAction = new AbstractAction("Save") {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            saveImageAsJvd(filePath);
            if (filePath != null) {
                save.setEnabled(false);
                modified = false;
            }
        }
    };

    /**
     * Auxiliary method for saving a image (JVD format) to the given file path.
     *
     * @param filePath the path to the file.
     */
    private void saveImageAsJvd(Path filePath) {
        if (filePath == null) {
            this.filePath = saveAsAction();
            if (this.filePath == null) {
                return;
            }
            if (!this.filePath.toString().endsWith(".jvd"))
                this.filePath = Paths.get(this.filePath.toString() + ".jvd");
        }
        try {
            Files.deleteIfExists(this.filePath);
        } catch (IOException ignorable) {
        }

        try (BufferedWriter writer = Files.newBufferedWriter(this.filePath, StandardOpenOption.CREATE)) {
            int size = model.getSize();

            GeometricalObjectVisitor jvdGenerator = new GeometricalObjectJvdGenerator();
            for (int i = 0; i < size; i++) {
                model.getObject(i).accept(jvdGenerator);
            }

            writer.write(((GeometricalObjectJvdGenerator) jvdGenerator).getGeneratedJvd());
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(JVDraw.this, "An error occurred while writing the image. "
                    , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(JVDraw.this, "The image is saved."
                , "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Represents the action for saving images (JVD format) at some location on the disk.
     */
    private Action saveAsJvdAction = new AbstractAction("Save As") {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            Path filePath = saveAsAction();
            if (filePath == null) {
                return;
            }
            saveImageAsJvd(filePath);
        }
    };

    /**
     * Auxiliary method for selecting the location where the JVD will be saved.
     *
     * @return the path where the file will be saved .
     */
    private Path saveAsAction() {
        Path newPath;
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save image in JVD format");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image description file (JVD)", "jvd");
        jfc.setFileFilter(filter);
        jfc.setAcceptAllFileFilterUsed(false);

        if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(JVDraw.this, "Nothing was saved",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        newPath = jfc.getSelectedFile().toPath();
        if (!overwriteFileIfExist(newPath, "Save image")) {
            return null;
        }
        return newPath;
    }

    /**
     * Checks if there is already such a file and whether it exists if the user wants to overwrite that file.
     *
     * @param newPath the path to the file.
     * @param title   the title of option pane.
     * @return true if user wants to overwrite file.
     */
    private boolean overwriteFileIfExist(Path newPath, String title) {
        if (newPath.toFile().isFile() && newPath.toFile().exists()) {
            int answer = JOptionPane.showConfirmDialog(JVDraw.this,
                    "The file already exist. Do you want to overwrite the file?",
                    title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            return answer != JOptionPane.NO_OPTION && answer != JOptionPane.CLOSED_OPTION;
        }
        return true;
    }

    /**
     * Represents the action for exporting image to JPG, GIF and PNG format.
     */
    private Action exportAction = new AbstractAction("Export") {

        /**
         *The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            GeometricalObjectVisitor bbCalculator = new GeometricalObjectBBCalculator();
            int size = model.getSize();
            for (int i = 0; i < size; i++) {
                model.getObject(i).accept(bbCalculator);
            }

            Rectangle box = ((GeometricalObjectBBCalculator) bbCalculator).getBoundingBox();
            BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

            Graphics2D g = image.createGraphics();
            g.setBackground(Color.WHITE);
            g.clearRect(0, 0, box.width, box.height);
            g.translate(-box.getX(), -box.getY());

            GeometricalObjectVisitor painter = new GeometricalObjectPainter(g);
            for (int i = 0; i < size; i++) {
                model.getObject(i).accept(painter);
            }
            g.dispose();

            Path newPath;
            JFileChooser jfc = new JFileChooser();
            jfc.setAcceptAllFileFilterUsed(false);

            jfc.setFileFilter(new FileNameExtensionFilter("PNG image ", "png"));
            jfc.setFileFilter(new FileNameExtensionFilter("GIF image ", "gif"));
            jfc.setFileFilter(new FileNameExtensionFilter("JPG image ", "jpg"));

            jfc.setDialogTitle("Export image");
            if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(JVDraw.this, "Nothing was exported",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            newPath = jfc.getSelectedFile().toPath();
            String selectedFileType = jfc.getFileFilter().getDescription();
            selectedFileType = selectedFileType.substring(0, selectedFileType.indexOf(" ")).toLowerCase();

            if (!newPath.toString().endsWith("." + selectedFileType)) {
                newPath = Paths.get(newPath.toString() + "." + selectedFileType);
            }

            if (!overwriteFileIfExist(newPath, "Export image")) {
                return;
            }

            try {
                Files.deleteIfExists(newPath);
            } catch (IOException ignorable) {
            }

            try {
                ImageIO.write(image, selectedFileType, newPath.toFile());
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(JVDraw.this, "An error occurred while exporting.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(JVDraw.this, "The image is successfully exported.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    };


    /**
     * Represents the action for opening JVD file from the hard drive.
     */
    private Action openDocumentAction = new AbstractAction("Open") {

        /**
         * The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent arg0) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Open JVD file");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image description file (JVD)", "jvd");
            fc.setAcceptAllFileFilterUsed(false);

            fc.setFileFilter(filter);
            if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = fc.getSelectedFile();
            Path filePath = fileName.toPath();
            if (!Files.isReadable(filePath) || !fileName.toString().endsWith(".jvd")) {
                JOptionPane.showMessageDialog(JVDraw.this, "File"
                        + fileName.getAbsolutePath()
                        + "is not readable.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (modified) {
                int answer = JOptionPane.showConfirmDialog(JVDraw.this,
                        "Do you want to save the image before exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    saveImageAsJvd(JVDraw.this.filePath);
                }
            }

            if (model.getSize() != 0) {
                while (model.getSize() != 0) {
                    model.remove(model.getObject(0));
                }
            }

            try {
                try (BufferedReader br = Files.newBufferedReader(filePath)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String parts[] = line.split(" ");

                        switch (parts[0]) {
                            case "LINE":
                                GeometricalObject loadedLine = getLine(parts);
                                if (loadedLine != null) {
                                    model.add(loadedLine);
                                }
                                break;
                            case "CIRCLE":
                                GeometricalObject loadedCircle = getCircle(parts);
                                if (loadedCircle != null) {
                                    model.add(loadedCircle);
                                }
                                break;
                            case "FCIRCLE":
                                GeometricalObject loadedFilledCircle = getFilledCircle(parts);
                                if (loadedFilledCircle != null) {
                                    model.add(loadedFilledCircle);
                                }
                                break;
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JVDraw.this,
                        "An error occurred while reading the file " + fileName.getAbsolutePath()
                        , "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    /**
     * Represents the action for exiting the whole program. Before exit, if there is image,
     * user is asked if he wants to save specified image.
     */
    private Action exitAction = new AbstractAction("Exit") {

        /**
         *The default serial version UID.
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            exitAction();
        }
    };

    /**
     * Exits the application and asks the user whether to save the currently modified image.
     */
    private void exitAction() {
        if (modified) {
            int answer = JOptionPane.showConfirmDialog(JVDraw.this,
                    "Do you want to save the image before exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                saveImageAsJvd(filePath);
            }
            dispose();
        }
        dispose();
    }


    /**
     * Adds a window listener when the user attempts to close the app.
     */
    public synchronized void addWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction();
            }
        });
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        modified();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        modified();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        modified();
    }


    private void modified() {
        if (!modified) {
            save.setEnabled(true);
            export.setEnabled(true);
            modified = true;
        }
        if (model.getSize() == 0) {
            save.setEnabled(false);
            exportAction.setEnabled(false);
            modified = false;
        }
    }

    /**
     * The method invoked when running the program.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
    }

}
