/*
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
package net.objectlab.kit.datecalc.common;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

public abstract class AbstractDateTestCase<E> extends TestCase {
    protected abstract E newDate(final String date);

    protected abstract KitCalculatorsFactory<E> getDateCalculatorFactory();

    protected void checkDate(final String string, final DateCalculator<E> calendar, final String string2) {
        Assert.assertEquals(string, newDate(string2), calendar.getCurrentBusinessDate());
    }

    protected Set<E> newHolidaysSet() {
        final Set<E> holidays = new HashSet<E>();
        holidays.add(newDate("2006-08-28"));
        holidays.add(newDate("2006-12-25"));
        holidays.add(newDate("2006-12-26"));
        return holidays;
    }

    protected Set<E> createUKHolidays() {
        final Set<E> uk = new HashSet<E>();
        uk.add(newDate("2006-01-01"));
        uk.add(newDate("2006-08-28"));
        uk.add(newDate("2006-12-25"));
        uk.add(newDate("2006-12-26"));
        return uk;
    }

    protected Set<E> createUSHolidays() {
        final Set<E> us = new HashSet<E>();
        us.add(newDate("2006-07-04"));
        us.add(newDate("2006-11-28"));
        us.add(newDate("2006-12-25"));
        return us;
    }

    protected void registerHolidays(final String name, final Set<E> holidays) {
        getDateCalculatorFactory().registerHolidays(name, holidays);
    }

    protected WorkingWeek getWorkingWeek(final WorkingWeek ww) {
        return ww;
    }

    protected DateCalculator<E> newDateCalculator(final String name, final String type) {
        return getDateCalculatorFactory().getDateCalculator(name, type);
    }

    /**
     * Based on UK Holidays for Aug 2006.
     * 
     * @param startDate
     * @param tenor
     * @param spotLag
     * @param expectedDate
     * @param holidayHandlerType
     */
    protected void checkMoveByTenor(final String startDate, final Tenor tenor, final int spotLag, final String expectedDate,
            final String holidayHandlerType) {
        final DateCalculator<E> cal = newDateCalculator("bla", holidayHandlerType);
        cal.setNonWorkingDays(createUKHolidays());
        cal.setStartDate(newDate(startDate));
        checkDate("Move start:" + startDate + " tenor:" + tenor + " daysToSpot:" + spotLag, cal.moveByTenor(tenor, spotLag),
                expectedDate);
    }

}
