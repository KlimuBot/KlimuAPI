package eus.klimu.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * A period of time between two dates.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatePeriod implements Serializable {

    /**
     * The initial date where the period starts.
     */
    private Date startDate;
    /**
     * The final date where the period ends.
     */
    private Date endDate;

    /**
     * Check if the date period is correct.
     * @return True if the start date goes before the end date. Otherwise false.
     */
    public boolean check() {
        return startDate.before(endDate);
    }

}
