package Parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.*;

public class OptionsParser {
    private String rover = null;
    private String camera = null;
    private Integer photosPerDay = null;
    private Integer daysPerQuery = null;
    private LocalDate startDate = null;
    private LocalDate endDate = null;

    private DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public OptionsParser () {
    }

    public void ParseOptionString (String optionsString) {
        // User has (possibly) entered alternate rover/camera/photos per day/number days per query
        Pattern pat = Pattern.compile("\\/", Pattern.CASE_INSENSITIVE); 
        String[] optionStrings = pat.split(optionsString);
        
        // Not the most elegant way to handle this but, it'll do for this exercise.
        for (String optionString : optionStrings) {
            String [] optionKeyValue = optionString.split(" ");
            if (optionKeyValue.length == 2) {
                String optionKey = optionKeyValue[0].trim();
                String optionValue = optionKeyValue[1].trim();

                if (optionKey.equalsIgnoreCase("r")) {
                    rover = optionValue;
                } 
                else if (optionKey.equalsIgnoreCase("c")) {
                    camera = optionValue;
                }

                else if (optionKey.equalsIgnoreCase("ppd")) {
                    try {
                        Integer ppd = Integer.parseInt(optionValue);
                        photosPerDay = ppd;
                    }
                    catch (Exception e) {
                    }
                }
                //else if (optionKey.equalsIgnoreCase("dpq")) {
                //    try {
                //        Integer dpq = Integer.parseInt(optionValue);
                //        daysPerQuery = dpq;
                //    }
                //    catch (Exception e) {
                //    }
                //}
                else if (optionKey.equalsIgnoreCase("sd")) {
                    try {
                        LocalDate sd = LocalDate.parse(optionValue, dtformatter);
                        startDate = sd;
                    }
                    catch (Exception e) {
                    }
                }
                // 
                else if (optionKey.equalsIgnoreCase("ed")) {
                    try {
                        LocalDate ed = LocalDate.parse(optionValue, dtformatter);
                        endDate = ed;
                    }
                    catch (Exception e) {
                    }
                }
            }
        }
        // Reset startDate and EndDate to NULL if they're incomplete or invalid
        if ((startDate == null && endDate != null) || (startDate != null && endDate == null) || (startDate != null && endDate != null && startDate.isAfter(endDate))) {
                startDate = null;
                endDate = null;
        }
    }

    public String getRover () {
        return (rover);
    }

    public String getCamera () {
        return (camera);
    }

    public Integer getPhotosPerDay () {
        return (photosPerDay);
    }

    public Integer getDaysPerQuery () {
       return (daysPerQuery);
    }

    public LocalDate getStartDate () {
        return (startDate);
     }

     public LocalDate getEndDate () {
        return (endDate);
     }
 }
