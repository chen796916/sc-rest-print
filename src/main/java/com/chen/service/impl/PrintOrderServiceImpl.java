package com.chen.service.impl;

import com.chen.model.Order;
import com.chen.model.OrderGood;
import com.chen.model.TemplateModel;
import com.chen.service.PrintOrderService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PrintOrderServiceImpl implements PrintOrderService {

    @Value("${rest.school}")
    private String schoolName;

    @Value("${rest.restaurant}")
    private String restName;

    @Value("${rest.window}")
    private String windowName;

    @Value("${rest.printFilePath}")
    private String printFilePath;

    @Value("${rest.printerName}")
    private String printerName;

    @Autowired
    private TemplateEngine engine;

    @Override
    public void doPrint(Order order, OrderGood[] orderGoods) {
        List<OrderGood> myOrder = new ArrayList<>();
        if (order.getSchoolName().equals(schoolName) && order.getRestName().equals(restName)) {
            Arrays.stream(orderGoods).forEach(good -> {
                if (good.getWindowName().equals(windowName)) {
                    myOrder.add(good);
                }
            });
        }
        if (myOrder.size() != 0) {
            generatePdf(myOrder, order);
        }
    }

    private void generatePdf(List<OrderGood> orderGoods, Order order) {
        TemplateModel model = new TemplateModel(engine,"order.html");
        model.setOrder(order);
        model.setOrderGoodList(orderGoods);
        String pdfName = order.getId() + ".pdf";
        model.parse2Pdf(printFilePath + pdfName);
        printPdf(printFilePath + pdfName);
    }

    private void printPdf(String filePath) {
        File file = new File(filePath);
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(file.getName());
            if (printerName != null) {
                // 查找并设置打印机
                //获得本台电脑连接的所有打印机
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                if(printServices == null || printServices.length == 0) {
                    System.out.print("打印失败，未找到可用打印机，请检查。");
                    return ;
                }
                PrintService printService = null;
                //匹配指定打印机
                for (int i = 0;i < printServices.length; i++) {
                    System.out.println(printServices[i].getName());
                    if (printServices[i].getName().contains(printerName)) {
                        printService = printServices[i];
                        break;
                    }
                }
                if(printService!=null){
                    printJob.setPrintService(printService);
                }else{
                    System.out.print("打印失败，未找到名称为" + printerName + "的打印机，请检查。");
                    return ;
                }
            }
            //设置纸张及缩放
            PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
            //设置多页打印
            Book book = new Book();
            PageFormat pageFormat = new PageFormat();
            //设置打印方向
            pageFormat.setOrientation(PageFormat.PORTRAIT);//纵向
            pageFormat.setPaper(getPaper());//设置纸张
            book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
            printJob.setPageable(book);
            printJob.setCopies(1);//设置打印份数
            //添加打印属性
            HashPrintRequestAttributeSet pars = new HashPrintRequestAttributeSet();
            pars.add(Sides.ONE_SIDED); //设置单双页
            printJob.print(pars);
        } catch (IOException | PrinterException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Paper getPaper() {
        Paper paper = new Paper();
        // 默认为A4纸张，对应像素宽和高分别为 595, 842
        int width = 595;
        int height = 842;
        // 设置边距，单位是像素，10mm边距，对应 28px
        int marginLeft = 10;
        int marginRight = 0;
        int marginTop = 10;
        int marginBottom = 0;
        paper.setSize(width, height);
        // 下面一行代码，解决了打印内容为空的问题
        paper.setImageableArea(marginLeft, marginRight, width - (marginLeft + marginRight), height - (marginTop + marginBottom));
        return paper;
    }
}
