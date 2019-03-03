package hr.fer.zemris.java.servlets;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class representing a servlet that accepts a three parameters a (integer from [-100,100]) b (integer
 * from [-100,100]) and n (where n>=1 and n<=5). If any parameter is invalid, the <code>error.jsp</code>
 * will be displayed.Action  dynamically creates a Microsoft Excel document with n pages. On page i there is a
 * table with two columns. The first column contains integer numbers from a to b. The second column
 * contains i-th powers of these numbers.
 * This action is accessible on local URL /powers.
 */
@WebServlet("/powers")
public class ExcelPowersServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -381820192813161527L;

    /**
     * The upper limit for numbers.
     */
    private static final int UPPER_LIMIT = 100;

    /**
     * The lower limit for numbers.
     */
    private static final int LOWER_LIMIT = -100;

    /**
     * The lower limit for number of pages.
     */
    private static final int LOWER_LIMIT_PAGE = 1;

    /**
     * The upper limit for number of pages.
     */
    private static final int UPPER_LIMIT_PAGE = 5;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String a = req.getParameter("a");
        String b = req.getParameter("b");
        String n = req.getParameter("n");

        Integer lowerBound = getNumber(a);
        Integer upperBound = getNumber(b);
        Integer numberOfPages = getNumber(n);

        if (lowerBound == null || upperBound == null || numberOfPages == null) {
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }
        if (!isNumberValid(lowerBound, LOWER_LIMIT, UPPER_LIMIT) || !isNumberValid(upperBound, LOWER_LIMIT, UPPER_LIMIT)
                || !isNumberValid(numberOfPages, LOWER_LIMIT_PAGE, UPPER_LIMIT_PAGE)) {
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (lowerBound > upperBound) {
            Integer temp;
            temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }

        HSSFWorkbook workbook = createExcelFile(lowerBound, upperBound, numberOfPages);
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
        workbook.write(resp.getOutputStream());
        workbook.close();
    }

    /**
     * Creates an excel file.
     *
     * @param a the lower limit.
     * @param b the upper limit.
     * @param n the number of pages.
     * @return an excel file.
     */
    private HSSFWorkbook createExcelFile(int a, int b, int n) {
        HSSFWorkbook hwb = new HSSFWorkbook();
        for (int i = 1; i <= n; i++) {
            HSSFSheet sheet = hwb.createSheet("sheet" + i);
            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Numbers");
            rowhead.createCell((short) 1).setCellValue(i + "th-power");
            int k = 1;
            for (int j = a; j <= b; j++) {
                HSSFRow row = sheet.createRow((short) k++);
                row.createCell((short) 0).setCellValue(j);
                row.createCell((short) 1).setCellValue(Math.pow(j, i));
            }
        }
        return hwb;
    }

    /**
     * Parses the string into the integer.
     *
     * @param number the number for parsing.
     * @return integer value if string is parsable to number , otherwise a null reference.
     */
    private Integer getNumber(String number) {
        Integer num = null;
        try {
            num = Integer.valueOf(number);
        } catch (NumberFormatException ignorable) {
        }
        return num;
    }

    /**
     * Checks whether the number is in the given range.
     *
     * @param number the check number.
     * @param lower  the lower limit.
     * @param upper  the upper limit.
     * @return true if the number is in the desired range.
     */
    private boolean isNumberValid(Integer number, Integer lower, Integer upper) {
        return number >= lower && number <= upper;
    }
}
