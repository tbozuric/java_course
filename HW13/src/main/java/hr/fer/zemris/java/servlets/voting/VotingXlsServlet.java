package hr.fer.zemris.java.servlets.voting;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static hr.fer.zemris.java.servlets.voting.UtilVoting.Vote;

/**
 * This class is a servlet that allows you to download voting results
 * as an XLS file filled with voting data.
 * This action is accessible on local URL /glasanje-xls.
 */
@WebServlet("/glasanje-xls")
public class VotingXlsServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 2097727262860879303L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HSSFWorkbook workbook = createExcelFile(req.getServletContext());
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=\"votingResults.xls\"");
        workbook.write(resp.getOutputStream());
        workbook.close();
    }

    /**
     * Creates an excel file based on the data obtained by voting for favourite musical band.
     *
     * @param context the servlet context.
     * @return an excel file based on the data obtained by voting for favourite musical band.
     * @throws IOException if an error occurs while reading the data.
     */
    private HSSFWorkbook createExcelFile(ServletContext context) throws IOException {
        List<Vote> votes = UtilVoting.getSortedVotes(context);
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Results");
        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell((short) 0).setCellValue("Band");
        rowhead.createCell((short) 1).setCellValue("Number of votes");

        for (int i = 0; i < votes.size(); i++) {
            HSSFRow row = sheet.createRow((short) i + 1);
            row.createCell((short) 0).setCellValue(votes.get(i).getBand().getNameOfMusicalBand());
            row.createCell((short) 1).setCellValue(votes.get(i).getNumberOfVotes());
        }
        return hwb;
    }
}
