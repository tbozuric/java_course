package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

import java.util.List;

/**
 * Interface to data subsystem.
 */
public interface DAO {

    /**
     * Returns a list of available polls in the database.
     *
     * @return a list of available polls in the database.
     * @throws DAOException if an error occurs while communicating with the database.
     */
    List<Poll> getPolls() throws DAOException;

    /**
     * Returns the poll associated with the given id.
     *
     * @param id the poll id.
     * @return the poll associated with the given id.
     * @throws DAOException if an error occurs while communicating with the database.
     */
    Poll getPoll(long id) throws DAOException;

    /**
     * Returns the poll option associated with the given id.
     *
     * @param id the poll option id.
     * @return the poll option associated with the  given id.
     * @throws DAOException if an error occurs while communicating with the database.
     */
    PollOption getPollOption(long id) throws DAOException;

    /**
     * Returns the list of poll options related to the given poll(id).
     *
     * @param pollId the poll id.
     * @param sorted a flag indicating whether we want data sorted by number of votes.
     * @return the list of poll options related to the poll.
     * @throws DAOException if an error occurs while communicating with the database.
     */
    List<PollOption> getPollOptions(long pollId, boolean sorted) throws DAOException;

    /**
     * Increases the number of votes(+1) for the poll option associated with the given id.
     *
     * @param id the poll option id.
     * @throws DAOException if an error occurs while communicating with the database.
     */
    void incrementVotesCount(long id) throws DAOException;

}