/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fastbooks.managedbeans;

import com.fastbooks.facade.FbInvoiceFacade;
import com.fastbooks.modelo.LineChartItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.EJB;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Guadalupe
 */
@Named(value = "dashboardController")
@ViewScoped
public class DashboardController implements Serializable {

    private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
    private PieChartModel pieModel2;
    private CartesianChartModel combinedModel;

    @Inject
    UserData userData;
    @EJB
    FbInvoiceFacade iFacade;

    private @Getter
    @Setter
    String from;
    private @Getter
    @Setter
    String to;
    private @Getter
    @Setter
    String op = "TM";
    
    private @Getter @Setter List<LineChartItem> listGlob;

    /**
     * Creates a new instance of DashboardController
     */
    public DashboardController() {

    }

    public CartesianChartModel getCombinedModel() {
        return combinedModel;
    }

    public void setCombinedModel(CartesianChartModel combinedModel) {
        this.combinedModel = combinedModel;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    public void setPieModel2(PieChartModel pieModel2) {
        this.pieModel2 = pieModel2;
    }

    public LineChartModel getAnimatedModel1() {
        return animatedModel1;
    }

    public BarChartModel getAnimatedModel2() {
        return animatedModel2;
    }

    @PostConstruct
    public void init() {
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayMonth = c.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayMonth = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        //createAnimatedModels();
        createPieModel2();
        this.combinedModel = initLinearWithDates(sdf.format(firstDayMonth), sdf.format(lastDayMonth), "This Month", op);
        //this.animatedModel2 = initBarModel();
        //this.listGlob = this.iFacade.getLineChartDataMonth(from, to, "This month", this.userData.getCurrentCia().getIdCia().toString(), Locale.US, op);
        //createCombinedModel();
    }

    public void changeFilterLinearSales() {
        switch (this.op) {
            case "LW":
                this.combinedModel = initLinearWithDates(this.from, this.to, "Last week", op);
                break;
            case "TW":
                this.combinedModel = initLinearWithDates(this.from, this.to, "This week", op);
                break;
            case "L7":
                this.combinedModel = initLinearWithDates(this.from, this.to, "Last 7 days", op);
                break;
            case "LM":
                this.combinedModel = initLinearWithDates(this.from, this.to, "Last Month", op);
                break;

            case "TM":
                this.combinedModel = initLinearWithDates(this.from, this.to, "This Month", op);
                break;

            case "L3D":
                this.combinedModel = initLinearWithDates(this.from, this.to, "Last 30 Days", op);
                break;
            case "TQ":
                this.combinedModel = initLinearWithDates(this.from, this.to, "This quarter", op);
                break;

            case "LQ":
                this.combinedModel = initLinearWithDates(this.from, this.to, "Last quarter", op);
                break;
            case "L12":
                this.combinedModel = initLinearWithDates(this.from, this.to, "Last 12 months", op);
                break;
            case "LY":
                this.combinedModel = initLinearWithDates(this.from, this.to, "Last year", op);
                break;

            case "TY":
                this.combinedModel = initLinearWithDates(this.from, this.to, "This year", op);
                break;
            case "TYQ":
                this.combinedModel = initLinearWithDates(this.from, this.to, "This year by quarter", op);
                break;
            case "LYQ":
                this.combinedModel = initLinearWithDates(this.from, this.to, "Last year by quarter", op);
                break;

            default:

                break;

        }

    }

    private void createAnimatedModels() {
        animatedModel1 = initLinearModel();
        animatedModel1.setTitle("Sales");
        animatedModel1.setAnimate(true);
        animatedModel1.setLegendPosition("se");
        //Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        //yAxis.setMin(0);
        //yAxis.setMax(10);

        animatedModel2 = initBarModel();
        animatedModel2.setTitle("Income");
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        //yAxis = animatedModel2.getAxis(AxisType.Y);
        //yAxis.setMin(0);
        //yAxis.setMax(200);
    }

    private void createPieModels() {
        createPieModel2();
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        List<LineChartItem> list = this.iFacade.getBarChartDataMonth(this.userData.getCurrentCia().getIdCia().toString(), "30");

        /*for (LineChartItem lineChartItem : list) {
            ChartSeries open = new ChartSeries();
            open.setLabel(lineChartItem.getLabel());
            open.set("Invoices", 1000);
            model.addSeries(open);

        }*/
         ChartSeries open = new ChartSeries();
        open.setLabel("Open");
        open.set("Open", 120);
     

        ChartSeries overdue = new ChartSeries();
        overdue.setLabel("Overdue");
        overdue.set("Overdue", 52);
      
        
        ChartSeries paid = new ChartSeries();
        paid.setLabel("Paid");
        paid.set("Paid", 52);
       

        model.addSeries(open);
        model.addSeries(overdue);
        model.addSeries(paid); 
        model.setTitle("Income invoices");
        return model;
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Marzo");

        series1.set(1, 3000.57);
        series1.set(7, 2000.34);
        series1.set(15, 4345.65);
        series1.set(23, 7545.63);
        series1.set(30, 9456.43);

        //LineChartSeries series2 = new LineChartSeries();
        /*series2.setLabel("Abril");
 
        series2.set(100, 6);
        series2.set(200, 3);
        series2.set(300, 2);
        series2.set(400, 7);
        series2.set(500, 9);*/
        model.addSeries(series1);
        // model.addSeries(series2);

        return model;
    }

    private BarChartModel initLinearWithDates(String from, String to, String title, String option) {
        BarChartModel model = new BarChartModel();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date dFrom = sdf.parse(from);
            Date dTo = sdf.parse(to);
            Calendar cFrom = Calendar.getInstance();
            cFrom.setTime(dFrom);
            Calendar cTo = Calendar.getInstance();
            cTo.setTime(dTo);
            long daysBetween = (((cFrom.getTimeInMillis() - cTo.getTimeInMillis()) / (24 * 60 * 60 * 1000)) * -1) + 1;
            Locale locale = new Locale(this.userData.getCountry().getLanguage()) == null ? new Locale("EN") : new Locale(this.userData.getCountry().getLanguage());
            String month = new SimpleDateFormat("MMMMM", locale).format(dFrom.getTime());

            LineChartSeries series1 = new LineChartSeries();
            series1.setLabel("Sales");
            /*  boolean flag = true;
            int i = 1;
            while (flag) {

                if (i < daysBetween) {
                    if (i == 1) {
                        series1.set(month + " " +i, Double.parseDouble(i + "000.00"));
                    }else{
                        series1.set(i, Double.parseDouble(i + "000.00"));
                    }
                    
                    i += 8;
                } else if (i > daysBetween) {
                    i = (int) daysBetween;
                    series1.set(month + " " +i, Double.parseDouble( i + "000.00"));
                } else if (i == daysBetween) {
                    flag = false;
                }
            }*/

            List<LineChartItem> list = new ArrayList<>();
            list = this.iFacade.getLineChartDataMonth(from, to, title, this.userData.getCurrentCia().getIdCia().toString(), locale, option);
            for (int i = 1; i < list.size(); i++) {
                series1.set(list.get(i).getLabel(), list.get(i).getValue());
                series1.setMarkerStyle("filledCircle', size:'10.0");
            }

            model.addSeries(series1);
            model.setTitle(list.get(0).getLabel() + " " + this.userData.formatMaster(list.get(0).getValue().toString()));
            model.setLegendPosition("se");
            Axis xAxis = model.getAxis(AxisType.X);
            xAxis.setMin(0);
            xAxis.setMax(daysBetween + 1);
            model.setAnimate(true);

        } catch (ParseException e) {
            System.out.println("com.fastbooks.managedbeans.DashboardController.initLinearWithDates(DateParse)");
            e.printStackTrace();
        }

        return model;
    }

    private void createPieModel2() {
        pieModel2 = new PieChartModel();

        List<LineChartItem> list = this.iFacade.getBarChartDataMonth(this.userData.getCurrentCia().getIdCia().toString(), "365");
        for (LineChartItem li : list) {
            pieModel2.set(li.getLabel(), li.getValue());
        }
        /*pieModel2.set("Expenses", 325);
          pieModel2.set("Shoes", 325);
          pieModel2.set("Tv's", 702);
         pieModel2.set("Others", 421);*/

        pieModel2.setTitle("Income");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(true);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(250);
    }

    private void createCombinedModel() {
        combinedModel = new BarChartModel();

        BarChartSeries boys = new BarChartSeries();
        boys.setLabel("Income");

        boys.set("$4,000", 120);
        boys.set("$3,500", 100);
        boys.set("$2,000", 44);
        boys.set("$2,200", 150);
        boys.set("$1,900", 25);

        LineChartSeries girls = new LineChartSeries();
        girls.setLabel("Expenses");

        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);

        combinedModel.addSeries(boys);
        combinedModel.addSeries(girls);

        combinedModel.setTitle("Profit and Loss");
        combinedModel.setLegendPosition("ne");
        combinedModel.setMouseoverHighlight(false);
        combinedModel.setShowDatatip(false);
        combinedModel.setShowPointLabels(true);
        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(200);
    }

}
