package hr.fer.zemris.java.servlets.voting;

import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.model.PollOption;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This class is a servlet that allows you to download voting results
 * as an XLS file filled with voting data.
 * This action is accessible on local URL /servleti/glasanje-xls.
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 2097727262860879303L;

    /**
     * The default width of "title" column.
     */
    private static final int DEFAULT_TITLE_COLUMN_WIDTH = 7000;

    /**
     * The default width of "votes" column.
     */
    private static final int DEFAULT_VOTES_COLUMN_WIDTH = 5000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            HSSFWorkbook workbook = createExcelFile(Long.parseLong(req.getParameter("pollID")));

            if (workbook == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename=results.xls");

            workbook.write(resp.getOutputStream());
            workbook.close();

            resp.getOutputStream().write(workbook.getBytes());
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DAOException ex) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates an excel file based on the data obtained by voting.
     *
     * @param pollID the poll id.
     * @return an excel file based on the data obtained by voting.
     */
    private HSSFWorkbook createExcelFile(long pollID) {
        List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID, true);

        //invalid poll id
        if (pollOptions.size() == 0) {
            return null;
        }

        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Results");
        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell((short) 0).setCellValue("Option title");
        rowhead.createCell((short) 1).setCellValue("Number of votes");

        for (int i = 0; i < pollOptions.size(); i++) {
            HSSFRow row = sheet.createRow((short) i + 1);
            row.createCell((short) 0).setCellValue(pollOptions.get(i).getOptionTitle());
            row.createCell((short) 1).setCellValue(pollOptions.get(i).getVotesCount());
        }
        sheet.setColumnWidth(0, DEFAULT_TITLE_COLUMN_WIDTH);
        sheet.setColumnWidth(1, DEFAULT_VOTES_COLUMN_WIDTH);
        return hwb;
    }
}
