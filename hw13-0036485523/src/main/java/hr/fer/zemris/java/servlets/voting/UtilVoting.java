package hr.fer.zemris.java.servlets.voting;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Auxiliary class that  offers methods for loading bands for which to vote,
 * and sorted final results.
 */
public class UtilVoting {

    /**
     * Loads from the "database" all the bands for which it is possible to vote.
     *
     * @param context the servlet context.
     * @return the map of the bands associated with the identifier.
     * @throws IOException if an error occurs while reading the data from "database".
     */
    public static Map<Integer, Band> getBands(ServletContext context) throws IOException {
        String fileName = context.getRealPath("/WEB-INF/glasanje-definicija.txt");
        Path path = Paths.get(fileName);
        Map<Integer, Band> bands = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\t");
                bands.put(Integer.parseInt(parts[0]),
                        new Band(parts[1], parts[2]));
            }
        }
        return bands;
    }

    /**
     * Returns sorted results from voting.
     *
     * @param context the servlet context.
     * @return the sorted results from voting.
     * @throws IOException if an error occurs while reading.
     */
    public  static List<Vote> getSortedVotes(ServletContext context) throws IOException {
        String fileName = context.getRealPath("/WEB-INF/glasanje-rezultati.txt");
        Path path = Paths.get(fileName);
        Map<Integer, Band> bands = getBands(context);
        List<Vote> votes = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("utf-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\t");
                votes.add(new Vote(bands.get(Integer.parseInt(parts[0])), Integer.parseInt(parts[1])));
            }
        }
        return sortVotes(votes);
    }

    /**
     * Returns the sorted list of votes.
     *
     * @param votes the votes.
     * @return the sorted list of votes.
     */
    private  static List<Vote> sortVotes(List<Vote> votes) {
        return votes.stream().sorted((s1, s2) -> Integer.compare(s2.getNumberOfVotes(), s1.getNumberOfVotes()))
                .collect(Collectors.toList());
    }

    /**
     * Represents on vote for the favourite musical band.
     */
    public static class Vote {

        /**
         * The musical {@link Band}.
         */
        private Band band;

        /**
         * The number of votes for the band..
         */
        private int numberOfVotes;

        /**
         * Creates an instance of {@link Vote}.
         *
         * @param band          the musical band.
         * @param numberOfVotes the number of votes.
         */
        public Vote(Band band, int numberOfVotes) {
            this.band = band;
            this.numberOfVotes = numberOfVotes;
        }

        /**
         * Returns the musical {@link Band}.
         *
         * @return the musical band.
         */
        public Band getBand() {
            return band;
        }

        /**
         * Returns the number of votes for the band.
         *
         * @return the number of votes for the band.
         */
        public int getNumberOfVotes() {
            return numberOfVotes;
        }
    }

    /**
     * Represents an musical band.
     * Each band has a name and a link to the song performed by this band.
     */
    public static class Band {

        /**
         * The name of the musical band.
         */
        private String nameOfMusicalBand;

        /**
         * The link to the song performed by this band.
         */
        private String linkToSong;

        /**
         * Creates an instance of {@link Band}.
         *
         * @param nameOfMusicalBand the name of musical band.
         * @param linkToSong        the link to the song performed by this band.
         */
        public Band(String nameOfMusicalBand, String linkToSong) {
            this.nameOfMusicalBand = nameOfMusicalBand;
            this.linkToSong = linkToSong;
        }

        /**
         * Returns the name of musical band.
         *
         * @return the name of musical band.
         */
        public String getNameOfMusicalBand() {
            return nameOfMusicalBand;
        }

        /**
         * Returns the link to the song performed by this band.
         *
         * @return the link to the song performed by this band.
         */
        public String getLinkToSong() {
            return linkToSong;
        }
    }
}


