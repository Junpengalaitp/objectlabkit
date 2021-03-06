/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 *
 * Based in London, we are world leaders in the design and development
 * of bespoke applications for the securities financing markets.
 *
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
 * $Id$
 *
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.objectlab.kit.datecalc.joda;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import net.objectlab.kit.datecalc.common.CalculatorConstants;
import net.objectlab.kit.datecalc.common.PeriodCountBasis;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * Joda <code>LocalDatePeriod</code> based implementation of the
 * {@link net.objectlab.kit.datecalc.common.PeriodCountCalculator}.
 *
 * @author Benoit Xhenseval
 *
 */
public class LocalDatePeriodCountCalculator implements PeriodCountCalculator<LocalDate> {

    public int dayDiff(final LocalDate start, final LocalDate end, final PeriodCountBasis basis) {
        int diff;

        switch (basis) {
        case CONV_30_360:
            diff = diffConv30v360(start, end);
            break;

        case CONV_360E_ISDA:
            diff = diff360EIsda(start, end);
            break;

        case CONV_360E_ISMA:
            diff = diff360EIsma(start, end);
            break;
        default:
            final Period p = new Period(start, end, PeriodType.days());
            diff = p.getDays();
        }

        return diff;
    }

    private int diff360EIsma(final LocalDate start, final LocalDate end) {
        int dayStart = start.getDayOfMonth();
        int dayEnd = end.getDayOfMonth();
        if (dayEnd == CalculatorConstants.MONTH_31_DAYS) {
            dayEnd = CalculatorConstants.MONTH_30_DAYS;
        }
        if (dayStart == CalculatorConstants.MONTH_31_DAYS) {
            dayStart = CalculatorConstants.MONTH_30_DAYS;
        }
        return (end.getYear() - start.getYear()) * CalculatorConstants.YEAR_360 + (end.getMonthOfYear() - start.getMonthOfYear()) * CalculatorConstants.MONTH_30_DAYS + dayEnd - dayStart;
    }

    private int diff360EIsda(final LocalDate start, final LocalDate end) {
        if (start.equals(end)) {
            return 0;
        }
        int dayStart = start.getDayOfMonth();
        int dayEnd = end.getDayOfMonth();

        if (start.dayOfMonth().getMaximumValue() == dayStart) {
            dayStart = CalculatorConstants.MONTH_30_DAYS;
        }
        if (end.getMonthOfYear() != 2 && end.dayOfMonth().getMaximumValue() == dayEnd) {
            dayEnd = CalculatorConstants.MONTH_30_DAYS;
        }

        return (end.getYear() - start.getYear()) * CalculatorConstants.YEAR_360 + (end.getMonthOfYear() - start.getMonthOfYear()) * CalculatorConstants.MONTH_30_DAYS + dayEnd - dayStart;
    }

    private int diffConv30v360(final LocalDate start, final LocalDate end) {
        int dayStart = start.getDayOfMonth();
        int dayEnd = end.getDayOfMonth();
        if (dayEnd == CalculatorConstants.MONTH_31_DAYS && dayStart >= CalculatorConstants.MONTH_30_DAYS) {
            dayEnd = CalculatorConstants.MONTH_30_DAYS;
        }
        if (dayStart == CalculatorConstants.MONTH_31_DAYS) {
            dayStart = CalculatorConstants.MONTH_30_DAYS;
        }
        return (end.getYear() - start.getYear()) * CalculatorConstants.YEAR_360 + (end.getMonthOfYear() - start.getMonthOfYear()) * CalculatorConstants.MONTH_30_DAYS + dayEnd - dayStart;
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public double monthDiff(final LocalDate start, final LocalDate end, final PeriodCountBasis basis) {
        return yearDiff(start, end, basis) * CalculatorConstants.MONTHS_IN_YEAR;
    }

    public double yearDiff(final LocalDate start, final LocalDate end, final PeriodCountBasis basis) {
        double diff = 0.0;

        switch (basis) {
        case ACT_ACT:
            final int startYear = start.getYear();
            final int endYear = end.getYear();
            if (startYear != endYear) {
                final LocalDate endOfStartYear = start.dayOfYear().withMaximumValue();
                final LocalDate startOfEndYear = end.withDayOfYear(1);

                final int diff1 = new Period(start, endOfStartYear, PeriodType.days()).getDays();
                final int diff2 = new Period(startOfEndYear, end, PeriodType.days()).getDays();
                diff = (diff1 + 1.0) / start.dayOfYear().getMaximumValue() + (endYear - startYear - 1.0)
                        + (double) diff2 / (double) end.dayOfYear().getMaximumValue();
            }
            break;

        case CONV_30_360:
        case CONV_360E_ISDA:
        case CONV_360E_ISMA:
        case ACT_360:
            diff = dayDiff(start, end, basis) / CalculatorConstants.YEAR_360_0;
            break;

        case ACT_365:
            diff = dayDiff(start, end, basis) / CalculatorConstants.YEAR_365_0;
            break;

        default:
            throw new UnsupportedOperationException("Sorry ACT_UST is not supported");
        }

        return diff;
    }
}

/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 *
 * Based in London, we are world leaders in the design and development
 * of bespoke applications for the securities financing markets.
 *
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more about us</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 */
