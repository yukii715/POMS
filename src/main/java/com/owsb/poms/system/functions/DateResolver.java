package com.owsb.poms.system.functions;

import java.time.*;
import javax.swing.JComboBox;

public class DateResolver {
    /**
     * Connects three combo boxes: year, month, day with logic:
     * - Year: current year + next 2 years
     * - Month: enabled after year is selected
     * - Day: enabled after month is selected, auto-filled based on year & month
     *
     * @param cbYear  JComboBox<String> for year
     * @param cbMonth JComboBox<String> for month
     * @param cbDay   JComboBox<String> for day
     * @param isInitializing
     */
    public static void connect(JComboBox<String> cbYear, JComboBox<String> cbMonth, JComboBox<String> cbDay, boolean isInitializing) {
        if (!isInitializing){
            cbMonth.setEnabled(false);
            cbDay.setEnabled(false);
        }
        
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();

        // Populate Year ComboBox (current year to +2)
        cbYear.removeAllItems();
        for (int i = 0; i <= 2; i++) {
            cbYear.addItem(String.valueOf(currentYear + i));
        }
        
        if (isInitializing)
        {
            populateCbMonth(cbYear, cbMonth, cbDay, currentYear, currentMonth);
            populateCbDay(cbYear, cbMonth, cbDay, currentYear, currentMonth, currentDay);
        }

        cbYear.addActionListener(e -> {
            if (isInitializing) return;
            
            cbMonth.removeAllItems();
            cbDay.removeAllItems();

            populateCbMonth(cbYear, cbMonth, cbDay, currentYear, currentMonth);
        });

        cbMonth.addActionListener(e -> {
            if (isInitializing) return;
            
            cbDay.removeAllItems();
            
            populateCbDay(cbYear, cbMonth, cbDay, currentYear, currentMonth, currentDay);
        });
    }
    
    public static void populateCbMonth(JComboBox<String> cbYear, JComboBox<String> cbMonth, JComboBox<String> cbDay, int currentYear, int currentMonth){
        String selectedYearStr = (String) cbYear.getSelectedItem();
        if (selectedYearStr == null) return;

        int selectedYear = Integer.parseInt(selectedYearStr);

        cbMonth.setEnabled(true);
        int startMonth = (selectedYear == currentYear) ? currentMonth : 1;
        for (int month = startMonth; month <= 12; month++) {
            cbMonth.addItem(String.format("%02d", month));
        }
        cbDay.setEnabled(false);
    }
    
    public static void populateCbDay(JComboBox<String> cbYear, JComboBox<String> cbMonth, JComboBox<String> cbDay, int currentYear, int currentMonth, int currentDay){
        String selectedYearStr = (String) cbYear.getSelectedItem();
        String selectedMonthStr = (String) cbMonth.getSelectedItem();
        if (selectedYearStr == null || selectedMonthStr == null) return;

        int year = Integer.parseInt(selectedYearStr);
        int month = Integer.parseInt(selectedMonthStr);

        cbDay.setEnabled(true);
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        int startDay = (year == currentYear && month == currentMonth) ? currentDay : 1;

        for (int day = startDay; day <= daysInMonth; day++) {
            cbDay.addItem(String.format("%02d", day));
        }
    }
}
