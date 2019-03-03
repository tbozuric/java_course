package hr.fer.zemris.java.dao.sql;

import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.model.Poll;
import hr.fer.zemris.java.model.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the DAO subsystem using SQL technology.
 * This concrete implementation expects the link to be available via the {@link SQLConnectionProvider} class.
 *
 * @see hr.fer.zemris.java.ConnectionSetterFilter
 */
public class SQLDAO implements DAO {


    @Override
    public List<Poll> getPolls() throws DAOException {
        List<Poll> polls = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement("select id, title, message from Polls order by id")) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    Poll poll = new Poll();
                    poll.setId(rs.getLong(1));
                    poll.setTitle(rs.getString(2));
                    poll.setMessage(rs.getString(3));
                    polls.add(poll);
                }
            }
        } catch (Exception ex) {
            throw new DAOException("An error occurred while retrieving the list of available polls.", ex);
        }
        return polls;
    }

    @Override
    public Poll getPoll(long id) throws DAOException {
        Poll poll = null;
        Connection con = SQLConnectionProvider.getConnection();

        try (PreparedStatement pst = con.prepareStatement("select id, title, message from Polls where id=?")) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs != null && rs.next()) {
                    poll = new Poll();
                    poll.setId(rs.getLong(1));
                    poll.setTitle(rs.getString(2));
                    poll.setMessage(rs.getString(3));
                }
            }
        } catch (Exception ex) {
            throw new DAOException("An error occurred while retrieving the poll.", ex);
        }
        return poll;
    }

    @Override
    public PollOption getPollOption(long id) throws DAOException {
        PollOption pollOption = null;
        Connection con = SQLConnectionProvider.getConnection();


        try (PreparedStatement pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from " +
                "PollOptions where id=?")) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs != null && rs.next()) {
                    pollOption = new PollOption();
                    pollOption.setId(rs.getLong(1));
                    pollOption.setOptionTitle(rs.getString(2));
                    pollOption.setOptionLink(rs.getString(3));
                    pollOption.setPoll(getPoll(rs.getLong(4)));
                    pollOption.setVotesCount(rs.getLong(5));
                }
            }
        } catch (Exception ex) {
            throw new DAOException("An error occurred while retrieving the poll.", ex);
        }
        return pollOption;
    }

    @Override
    public List<PollOption> getPollOptions(long pollId, boolean sorted) throws DAOException {
        List<PollOption> pollOptions = new ArrayList<>();
        Connection con = SQLConnectionProvider.getConnection();

        String query = "select id, optionTitle, optionLink , pollID, votesCount " +
                "from PollOptions WHERE pollID=? order by ?";


        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setLong(1, pollId);
            if (!sorted) {
                pst.setString(2, "id");
            } else {
                pst.setString(2, "votesCount DESC");
            }
            try (ResultSet rs = pst.executeQuery()) {
                while (rs != null && rs.next()) {
                    PollOption pollOption = new PollOption();
                    pollOption.setId(rs.getLong(1));
                    pollOption.setOptionTitle(rs.getString(2));
                    pollOption.setOptionLink(rs.getString(3));
                    pollOption.setPoll(getPoll(rs.getLong(4)));
                    pollOption.setVotesCount(rs.getLong(5));
                    pollOptions.add(pollOption);
                }
            }
        } catch (Exception ex) {
            throw new DAOException("An error occurred while retrieving the list of available polls.", ex);
        }

        return pollOptions;
    }

    @Override
    public void incrementVotesCount(long id) throws DAOException {
        Connection con = SQLConnectionProvider.getConnection();
        PreparedStatement pst;
        try {
            pst = con.prepareStatement(	"UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id=?");
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("An error occurred while updating the poll option.", ex);
        }
    }
}