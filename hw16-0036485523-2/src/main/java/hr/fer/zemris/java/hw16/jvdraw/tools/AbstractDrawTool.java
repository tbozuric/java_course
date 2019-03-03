package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;

/**
 * Represents abstract draw tool.
 */
public abstract class AbstractDrawTool {

    /**
     * The drawing model.
     */
    DrawingModel model;

    /**
     * The foreground color provider.
     */
    IColorProvider fgColorProvider;

    /**
     * The background color provider.
     */
    IColorProvider bgColorProvider;

    /**
     * Creates an instance of {@link AbstractDrawTool}.
     *
     * @param model           the drawing model.
     * @param fgColorProvider the foreground color provider.
     * @param bgColorProvider the background color provider.
     */
    public AbstractDrawTool(DrawingModel model, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        this.model = model;
        this.fgColorProvider = fgColorProvider;
        this.bgColorProvider = bgColorProvider;
    }
}
