package hr.fer.zemris.java.fractals.Newton;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.java.math.Complex;
import hr.fer.zemris.java.math.ComplexPolynomial;
import hr.fer.zemris.java.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.parser.ComplexParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * The class is used for drawing fractals derived from Newton-Raphson iteration.
 * The program asks user to enter roots(complex numbers),  general syntax for complex numbers is of form <code><p>a+ib </p></code> or <code><p>a-ib </p></code>
 * where parts that are zero can be dropped, but not both (empty string is not legal complex number);
 * for example, zero can be given as <code><p> 0, i0, 0+i0, 0-i0.</p></code>
 * If there is 'i' present but no b is given, you must assume that b=1.
 * User must enter at least two roots , and program ends by typing "done" keyword.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Newton%27s_method">Newon Raphson method</a>
 */
public class NewtonRaphson {
    /**
     * A keyword to finish the program.
     */
    private static final String DONE_KEYWORD = "done";

    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments(not used in this program).
     */
    public static void main(String[] args) {

        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots , one root per line. Enter 'done' when done.");


        try (Scanner sc = new Scanner(System.in)) {
            int i = 1;
            List<Complex> roots = new ArrayList<>();
            while (true) {
                System.out.print("Root " + i + "> ");
                String line = sc.nextLine();
                if (line.equals(DONE_KEYWORD)) {
                    if (roots.size() < 2) {
                        System.out.println("You must enter at least two roots.");
                        break;
                    }
                    System.out.println("Image of fractal will appear shortly. Thank you.");
                    Complex[] rootsAsArray = new Complex[roots.size()];
                    ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(roots.toArray(rootsAsArray));
                    System.out.println(rootedPolynomial.toComplexPolynom().derive());
                    FractalViewer.show(new Producer(rootedPolynomial));
                    break;

                }
                line = line.trim().replaceAll("\\s+", " ");
                try {
                    roots.add(ComplexParser.getInstance().parseComplexNumber(line));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                i++;
            }
        }
    }

    /**
     * Represents a worker used to speed up rendering of fractals.
     */
    public static class Worker implements Callable<Void> {

        /**
         * Module limit.
         */
        private static final double MODULE_LIMIT = 1e-3;

        /**
         * Root closeness limit.
         */
        private static final double ROOT_CLOSENESS_LIMIT = 1e-3;

        /**
         * The minimum value of the real part.
         */
        double reMin;

        /**
         * The maximum value of the real part.
         */
        double reMax;

        /**
         * The minimum value of the imaginary part.
         */
        double imMin;

        /**
         * The maximum value of the imaginary part.
         */
        double imMax;

        /**
         * The width of the frame.
         */
        int width;

        /**
         * The height of the frame.
         */
        int height;

        /**
         * Used in parallelization to distribute a certain part of the work to each thread.
         * The pixel row from which we begin computation.
         */
        int yMin;

        /**
         * Used in parallelization to distribute a certain part of the work to each thread.
         * The pixel row with which we end computation.
         */
        int yMax;

        /**
         * The number of iterations.
         */
        int m;

        /**
         * The data containing every pixel.
         */
        short[] data;
        /**
         *
         */
        ComplexRootedPolynomial polynomial;

        /**
         * Creates an instance of worker used to speed up rendering.
         *
         * @param reMin      the minimum value of the real part.
         * @param reMax      the maximum value of the real part..
         * @param imMin      the minimum value of the imaginary part.
         * @param imMax      the maximum value of the imaginary part.
         * @param width      the width of the frame.
         * @param height     the height of the frame.
         * @param yMin       the pixel row from which we begin computation.
         * @param yMax       The pixel row from which we end computation.
         * @param m          the number of iterations.
         * @param data       the data containing every pixel.
         * @param polynomial the polynomial.
         */
        public Worker(double reMin, double reMax, double imMin,
                      double imMax, int width, int height, int yMin, int yMax,
                      int m, short[] data, ComplexRootedPolynomial polynomial) {
            super();
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.m = m;
            this.data = data;
            this.polynomial = polynomial;
        }


        @Override
        public Void call() {
            System.out.println("Calculating from " + yMin + " to " + yMax);
            calculate();
            return null;
        }

        /**
         * Calculates color for every pixel in a frame using Newton-Rapshon iteration.
         */
        private void calculate() {
            int offset = yMin * width;
            ComplexPolynomial complexPolynomialDerive = polynomial.toComplexPolynom().derive();
            for (int y = yMin; y <= yMax; y++) {
                for (int x = 0; x < width; x++) {
                    Complex zn = mapToComplexPlain(y, x);
                    Complex zn1;
                    double module;
                    int iters = 0;
                    do {
                        Complex numerator = polynomial.apply(zn);
                        Complex denominator = complexPolynomialDerive.apply(zn);
                        Complex fraction = numerator.divide(denominator);
                        zn1 = zn.sub(fraction);
                        module = zn1.sub(zn).module();
                        zn = zn1;
                        iters++;
                    } while (module > MODULE_LIMIT && iters < m);
                    int index = polynomial.indexOfClosestRootFor(zn1, ROOT_CLOSENESS_LIMIT);
                    if (index == -1) {
                        data[offset++] = 0;
                    } else {
                        data[offset++] = (short) (index + 1);
                    }
                }
            }
        }

        /**
         * Maps x and y coordinates to complex plain and returns a new complex number with mapped values
         * as real and imaginary part.
         *
         * @param y y-coordinate.
         * @param x x-coordinate.
         * @return a new complex number with mapped values to complex plain.
         */
        private Complex mapToComplexPlain(int y, int x) {
            double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
            double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
            return new Complex(cre, cim);
        }
    }

    /**
     * Represents a fractal producer.
     */
    public static class Producer implements IFractalProducer {

        /**
         * The maximum number of iterations.
         */
        private static final int NUMBER_OF_ITERATIONS = 16 * 16 * 16;

        /**
         * The thread pool.
         */
        private static ExecutorService pool;

        /**
         * Represents a complex rooted polynomial.
         *
         * @see ComplexRootedPolynomial
         */
        private ComplexRootedPolynomial polynomial;

        /**
         * Creates an instance of producer.
         *
         * @param polynomial complex rooted polynomial {@link ComplexRootedPolynomial}.
         */
        public Producer(ComplexRootedPolynomial polynomial) {
            this.polynomial = polynomial;
            pool = getFixedThreadPool();
        }

        /**
         * Daemonic thread factory that produces threads which have daemon flag set to true.
         *
         * @return a new {@link ExecutorService} as daemonic thread factory.
         */
        private ExecutorService getFixedThreadPool() {
            return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });
        }

        /**
         * @see hr.fer.zemris.java.fractals.viewer.IFractalProducer#produce(double, double, double, double, int, int, long,
         * hr.fer.zemris.java.fractals.viewer.IFractalResultObserver)
         */
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                            int width, int height, long requestNo, IFractalResultObserver observer) {
            System.out.println("Start calculating...");
            short[] data = new short[width * height];
            final int numberOfTapes = 8 * Runtime.getRuntime().availableProcessors();
            int numberOfYPerTape = height / numberOfTapes;


            List<Future<Void>> results = new ArrayList<>();

            for (int i = 0; i < numberOfTapes; i++) {
                int yMin = i * numberOfYPerTape;
                int yMax = (i + 1) * numberOfYPerTape - 1;
                if (i == numberOfTapes - 1) {
                    yMax = height - 1;
                }

                Worker work = new Worker(reMin, reMax, imMin, imMax, width, height, yMin, yMax, NUMBER_OF_ITERATIONS, data, polynomial);
                results.add(pool.submit(work));
            }
            for (Future<Void> work : results) {
                try {
                    work.get();
                } catch (InterruptedException | ExecutionException ignored) {
                }
            }
            System.out.println("Computation is finished. I will inform the observer ie. GUI!");
            observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
        }
    }
}
